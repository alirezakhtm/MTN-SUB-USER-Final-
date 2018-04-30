/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.data.parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.data.object.DataReceived;
import com.mtn.database.handler.DBHandler;
import com.mtn.database.object.MOMTLogObj;
import com.mtn.database.object.ServiceUserObj;
import com.mtn.database.object.ServicesObj;
import com.mtn.sms.object.SMSQueueObj;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alirezakhtm
 */
public class ThreadDataPasing implements Runnable {
    
    private final DataReceived dataReceived;
    private final List<ServicesObj> lstServicesObjs;
    private DBHandler db = new DBHandler();
    
    public ThreadDataPasing(DataReceived dataReceived, List<ServicesObj> lstServicesObjs) {
        this.dataReceived = dataReceived;
        this.lstServicesObjs = lstServicesObjs;
    }
    
    @Override
    public void run(){
        System.out.println("Thread Start.\t" + dataReceived.getSPID());
        boolean bFindServices = false;
        for(ServicesObj so : lstServicesObjs){
            if(so.getServiceID().equals(dataReceived.getServiceID())){
                bFindServices = true;
                ActiveMQConnectionFactory connectionFactory = 
                        new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), "tcp://localhost:61616");
                Connection connection = null;
                try{
                    connection = connectionFactory.createConnection();
                    connection.start();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    Destination destination = session.createQueue("SendSMS-Queue");
                    MessageProducer producer = session.createProducer(destination);
                    SMSQueueObj smsqo = new SMSQueueObj(
                            dataReceived.getUserID().substring(2),
                            dataReceived.getUpdateDesc().equals("Addition") 
                                    ? so.getWelcomeMT() : so.getCancelMT(),
                            so.getServiceCode()
                    );
                    Gson gson = new GsonBuilder().create();
                    String strJson = gson.toJson(smsqo, SMSQueueObj.class);
                    TextMessage message = session.createTextMessage(strJson);
                    producer.send(message);
                    producer.close();
                    session.close();
                    // save data on database - tbl_service_users
                    ServiceUserObj suo = new ServiceUserObj(
                            so.getServiceCode(),
                            dataReceived.getUserID().substring(2),
                            dataReceived.getUpdateDesc().equals("Addition") 
                                    ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                            format(Calendar.getInstance().getTime())
                                    : null,
                            !dataReceived.getUpdateDesc().equals("Addition") 
                                    ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                            format(Calendar.getInstance().getTime())
                                    : null,
                            dataReceived.getUpdateDesc().equals("Addition") ? 1 : 0);
                    db.open();
                    int indx = db.isSubUserExist(dataReceived.getUserID().substring(2));
                    db.close();
                    if(indx == -1){
                        db.open();
                        db.saveServiceUser(suo);
                        db.close();
                    }else{
                        db.open();
                        db.updateSubUser(indx, suo);
                        db.close();
                    }
                    // save data on database - tbl_mo_mt_log
                    MOMTLogObj momtlo = new MOMTLogObj(
                            so.getServiceCode()+"",
                            dataReceived.getUserID().substring(2),
                            "null",
                            smsqo.getMessage(),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                    format(Calendar.getInstance().getTime()));
                    db.open();
                    db.saveMOMTLog(momtlo);
                    db.close();
                }catch(JMSException e){
                    System.err.println(e);
                }
                break;
            }
        }
        if(!bFindServices){
            ActiveMQConnectionFactory connectionFactory = 
                    new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), "tcp://localhost:61616");
            Connection connection = null;
            try{
                connection = connectionFactory.createConnection();
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue("Garbage-DataReceived");
                MessageProducer producer = session.createProducer(destination);
                Gson gson = new GsonBuilder().create();
                String strJson = gson.toJson(dataReceived, DataReceived.class);
                TextMessage message = session.createTextMessage(strJson);
                producer.send(message);
                producer.close();
                session.close();
            }catch(JMSException e){
                System.err.println(e);
            }finally{
                if(connection != null){
                    try{
                        connection.close();
                    }catch(JMSException e){
                        System.err.println(e);
                    }
                }
            }
        }
    }
}