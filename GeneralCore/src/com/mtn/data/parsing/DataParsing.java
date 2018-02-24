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
import com.mtn.database.object.ServicesObj;
import java.util.List;
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
 * @author alirezakhtm
 */
public class DataParsing {
    private final Timer guardTimer = new Timer();
    private static boolean bProcessCurrent = false;
    private int TIME_DELAY = 1000;
    private final int TIME_OUT = 2000;
    private DBHandler db = new DBHandler();

    public DataParsing(int milisec) {
        this.TIME_DELAY = milisec;
    }
    
    public void startGuardTimer(){
        guardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!DataParsing.bProcessCurrent){
                            // start process again
                            parseDataReceived();
                        }
                    }
                });
                th.start();
            }
        }, 0, TIME_DELAY);
    }
    
    private void parseDataReceived(){
        DataParsing.bProcessCurrent = true;
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), "tcp://localhost:61616");
        Connection connection = null;
        try{
            // create connection for receive message from Queue
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("DataSync-Queue");
            MessageConsumer consumer = session.createConsumer(destination);
            // get list of all services
            db.open();
            List<ServicesObj> lstServicesObjs = db.getAllServices();
            db.close();
            ExecutorService executor = Executors.newFixedThreadPool(1);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String msg = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        DataReceived dataReceived = gson.fromJson(msg, DataReceived.class);
                        System.out.println(dataReceived.toString());
                        // save them and send message
                        ThreadDataPasing myThread = 
                                new ThreadDataPasing(dataReceived, lstServicesObjs);
                        executor.execute(myThread);
                    }
                }else{
                    executor.shutdown();
                    while(!executor.isTerminated()){}
                    break;
                }
            }
            consumer.close();
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
            DataParsing.bProcessCurrent = false;
        }
    }
}