/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.data.object.DataReceived;
import com.mtn.database.handler.DBHandler;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alireza
 */
public class AppLogic {
    private Timer guardTimer = new Timer();
    private boolean bProcessCurrent = false;
    private DBHandler db = new DBHandler();
    
    public void startGuardTimer(){
        guardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!bProcessCurrent){
                    bProcessCurrent = true;
                    DoProcess();
                }
            }
        }, 0, 1000);
    }
    
    private void DoProcess(){
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory(
                        db.getActiveMQUsername(),
                        db.getActiveMQPassword(),
                        "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("API-DataSync");
            MessageConsumer consumer = session.createConsumer(destination);
            db.open();
            Map<String,String> mapService = db.getListOfCostumerService(); // Map<serviceId, Indx>
            db.close();
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while(true){
                Message message = consumer.receive(2000);
                if(message!=null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        DataReceived dataReceived = gson.fromJson(strJson, DataReceived.class);
                        executorService.execute(new ThreadCallCostumerDataSyncURL(mapService, dataReceived));
                    }
                }else{
                    break;
                }
            }
            executorService.shutdown();
            while(!executorService.isTerminated()){}
        }catch(JMSException e){
            System.err.println("[ERROR] " + e);
        }
        // finish process
        bProcessCurrent = false;
    }
}


class ThreadCallCostumerDataSyncURL implements Runnable{

    private Map<String, String> mapCustomer;
    private DataReceived dataReceived;
    DBHandler db = new DBHandler();
    public ThreadCallCostumerDataSyncURL(Map<String, String> mapCustomer, DataReceived dataReceived) {
        this.mapCustomer = mapCustomer;
        this.dataReceived = dataReceived;
    }
    
    @Override
    public void run() {
        String strIndx = mapCustomer.get(dataReceived.getServiceID());
        if(strIndx == null || strIndx == ""){
            // finish
        }else{
            int indx = Integer.parseInt(strIndx);
            db.open();
            String urlDataSync = db.getCostumerDataSyncURL(indx);
            db.close();
            urlDataSync += "?userid=" + dataReceived.getUserID() +
                    "&serviceid=" + dataReceived.getServiceID() +
                    "&productid=" + dataReceived.getProductID() +
                    "&result=" + dataReceived.getResult() + 
                    "&resultdescription=" + dataReceived.getResultDescription() +
                    "&spid=" + dataReceived.getSPID();
            try{
                URL url = new URL(urlDataSync);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();
                db.open();
                db.saveConsumerCallLog(indx, urlDataSync, "DataSync");
                db.close();
            }catch(IOException e){
                System.err.println("[ERROR] " + e);
            }
        }
    }
    
}