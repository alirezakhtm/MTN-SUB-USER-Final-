/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms.parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.database.handler.DBHandler;
import com.mtn.database.object.MOMTLogObj;
import com.mtn.database.object.ServicesObj;
import com.mtn.sms.object.DeliveryStatus;
import com.mtn.sms.object.SMS;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQQueueSession;

/**
 *
 * @author alirezakhtm
 */
public class SMSParsing {
    private final String BROKER_HOST = "tcp://localhost:%d"; 
    private final int BROKER_PORT = 61616;
    private final String BROKER_URL = String.format(BROKER_HOST, BROKER_PORT); 
    private final Boolean NON_TRANSACTED = false; 
    private final long TIMEOUT = 2000;
    private static boolean bProcessCurrent_smsParsing = false;
    private static boolean bProcessCurrent_deliveryParsing = false;
    private final Timer TimerCheckExistMessage = new Timer();
    private final Timer TimerCheckExistDelivery = new Timer();
    private final DBHandler db = new DBHandler();
    private int TIME_DELAY = 1000; // default timer for timers that will be runed

    public void setTimeOfCuardTimer(int miliSec){
        this.TIME_DELAY = miliSec;
    }
    /**
     * this function start a timer for guarding of continuance process and
     * prevent of stopping process
     */
    public void startGuardTimer(boolean parsingSMS, boolean parsingDelivery){
        // if parsing SMS choosen, start timer for it.
        if(parsingSMS){
            // config timer for procssing of SMS
            TimerCheckExistMessage.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // check flag of process, if it stoped, start process again
                    if(!bProcessCurrent_smsParsing){
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                parsSMSReceived();
                            }
                        });
                        th.start();
                    }
                }
            }, 0, TIME_DELAY);
        }
        
        // if parsing of delivery SMS choosen, start timer for it
        if(parsingDelivery){
            // config timer for procssing of SMS's delivery
            TimerCheckExistDelivery.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // check felag of process (delivery SMS), if it stopped, start process again
                    if(!bProcessCurrent_deliveryParsing){
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                parsDelivery();
                            }
                        });
                        th.start();
                    }
                }
            }, 0, TIME_DELAY);
        }
    }
    
    /**
     * this function recall by startGuardTimer.
     * this function contained of all logic process.
     */
    private void parsSMSReceived(){
        // start process, for preventing of overlap process set flag true
        SMSParsing.bProcessCurrent_smsParsing = true;
        // create connection
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), BROKER_URL); 
        Connection connection = null; 
 
        try { 
            // initialize connection
            connection = connectionFactory.createConnection(); 
            connection.start(); 
            // receive message from ActiveMQ
            Session session = connection.createSession(
                    NON_TRANSACTED,
                    Session.AUTO_ACKNOWLEDGE
            ); 
            Destination destination = session.createQueue("Received-SMS"); 
            MessageConsumer consumer = session.createConsumer(destination); 
            
            // open database connection
            db.open();
            while (true) { 
                Message message = consumer.receive(TIMEOUT); 
                // analysis message
                
                if (message != null) { 
                    if (message instanceof TextMessage) { 
                        String msg = ((TextMessage) message).getText(); 
                        Gson gson = new GsonBuilder().create();
                        SMS sms = gson.fromJson(msg, SMS.class);
                        // logic for parsing SMS exist in Queue
                        // show message in consoul
                        System.out.println(sms);
                        // save data on mobtakerandb in Log table
                        db.saveSMSOnLogTbl(sms);
                        List<ServicesObj> lstServicesObjs = db.getAllServices();
                        int iServiceCode = -1;
                        for(ServicesObj so : lstServicesObjs){
                            if(so.getCorrelatorID().equals(sms.getCorrelator())){
                                iServiceCode = so.getServiceCode();
                                break;
                            }
                        }
                        MOMTLogObj momtlo = new MOMTLogObj(
                                iServiceCode + "", 
                                sms.getSenderAddress(), 
                                sms.getMessage(), 
                                null, 
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                        format(Calendar.getInstance().getTime()));
                        db.saveMOMTLog(momtlo);
                    } 
                } else { 
                    break; 
                }
            } 
            // close database connection
            db.close();
            // close connection
            consumer.close(); 
            session.close();
 
        } catch (JMSException e) { 
            System.out.println("Caught exception!"); 
        } 
        finally { 
            if (connection != null) { 
                try { 
                    connection.close(); 
                } catch (JMSException e) { 
                    System.out.println("Could not close an open connection..."); 
                } 
            } 
            bProcessCurrent_smsParsing = false;
        }
    }
    
    private void parsDelivery(){
        // start process for parsing of delivery and set felag
        SMSParsing.bProcessCurrent_deliveryParsing = true;
        // create connection for ActiveMQ
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), BROKER_URL);
        Connection connection = null;
        try{
            // initialize connection from ActiveMQConnectionFactory object
            connection = connectionFactory.createConnection();
            // start connection
            connection.start();
            // create session for receiving message from Queue
            Session session = connection.createSession(
                    NON_TRANSACTED,
                    ActiveMQQueueSession.AUTO_ACKNOWLEDGE
            );
            // create destination
            Destination destination = session.createQueue("DeliveryStatus-SMS");
            // create consumer for receive object from Queue
            MessageConsumer consumer = session.createConsumer(destination);
            // receive message
            // fro inserting data, database poolconnection capture
            db.open();
            while(true){
                // get message from Queue
                Message message = consumer.receive(TIMEOUT);
                if(message != null){
                    // check message, this must be text
                    if(message instanceof TextMessage){
                        // recovery object from json
                        String msg = ((TextMessage)message).getText();
                        // get message from json
                        Gson gson = new GsonBuilder().create();
                        DeliveryStatus ds = gson.fromJson(msg, DeliveryStatus.class);
                        // insert object to database
                        db.saveSMSDelivery(ds);
                    }
                }else{
                    break;
                }
            }
            // close poolconnection of database
            db.close();
            
        }catch(JMSException e){
            System.err.println("Delivery of SMS parsing has been stoped for some reason.");
            System.err.println("Error code : " + e.getErrorCode());
            System.err.println("Linked Exception : " + e.getLinkedException().getMessage());
            System.err.println("Message : " + e.getMessage());
            System.err.println(e);
        }finally{
            if (connection != null) { 
                try { 
                    connection.close(); 
                } catch (JMSException e) { 
                    System.out.println("Could not close an open connection..."); 
                } 
            } 
            bProcessCurrent_deliveryParsing = false;
        }
    }
}
