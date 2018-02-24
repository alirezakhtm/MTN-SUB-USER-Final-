/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.sms.object.DeliveryStatus;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.ws.WebServiceContext;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.csapi.schema.parlayx.sms.v2_2.DeliveryInformation;

/**
 *
 * @author alirezakhtm
 */
public class ThSMSDelivery implements Runnable{

    private final String correlator;
    private final DeliveryInformation deliveryStatus;
    private final WebServiceContext ctx;

    private static final String BROKER_HOST = "tcp://localhost:%d"; 
    private static final int BROKER_PORT = 61616;
    private static final String BROKER_URL = String.format(BROKER_HOST, BROKER_PORT); 
    private static final Boolean NON_TRANSACTED = false;
    
    public ThSMSDelivery(String correlator, DeliveryInformation deliveryStatus, WebServiceContext ctx) {
        this.correlator = correlator;
        this.deliveryStatus = deliveryStatus;
        this.ctx = ctx;
    }
    
    @Override
    public void run() {
        // create DeliveryStatus object from MTN
        DeliveryStatus ds = new DeliveryStatus(correlator, deliveryStatus, ctx);
        // convert information that received to json format
        Gson gson = new GsonBuilder().create();
        String jsonObject = gson.toJson(ds);
        // make connection for ActiveMQ
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory("admin", "admin", BROKER_URL); 
        Connection connection = null; 

        try { 
            // initialize connection object of ActiveMQ
            connection = connectionFactory.createConnection(); 
            connection.start(); 
            // create Queue and producer
            Session session = connection.createSession(NON_TRANSACTED, Session.AUTO_ACKNOWLEDGE); 
            Destination destination = session.createQueue("DeliveryStatus-SMS"); 
            MessageProducer producer = session.createProducer(destination); 
            // create message
            TextMessage txtmessage = session.createTextMessage(jsonObject);
            // send message
            producer.send(txtmessage);
            // close connection
            producer.close(); 
            session.close(); 
        } catch (JMSException e) { 
            System.out.println("ThSMSDelivery - run - 01: " +
                    e.getMessage()); 
        } 
        finally { 
            if (connection != null) { 
                try { 
                    connection.close(); 
                } catch (JMSException e) { 
                    System.out.println("ThSMSDelivery - run - 02: " +
                            e.getMessage()); 
                } 
            } 
        }
    }
    
}
