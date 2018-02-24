/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.sms.object.DeliveryStatus;
import com.mtn.sms.object.SMS;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory; 
import org.csapi.schema.parlayx.sms.v2_2.SmsMessage;
import org.csapi.schema.parlayx.sms.v2_2.DeliveryInformation;
/**
 *
 * @author Administrator
 */
@WebService(
        serviceName = "SmsNotificationService",
        portName = "SmsNotification",
        endpointInterface = "org.csapi.wsdl.parlayx.sms.notification.v2_2.service.SmsNotification",
        targetNamespace = "http://www.csapi.org/wsdl/parlayx/sms/notification/v2_2/service",
        wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/parlayx_sms_notification_service_2_2.wsdl")
@HandlerChain(file = "handlerchain.xml")
public class NewWebServiceFromWSDL {
    
    @Resource
    private WebServiceContext ctx;

    private static final String BROKER_HOST = "tcp://localhost:%d"; 
    private static final int BROKER_PORT = 61618;
    private static final String BROKER_URL = String.format(BROKER_HOST, BROKER_PORT); 
    private static final Boolean NON_TRANSACTED = false;
    
    public void notifySmsReception(String correlator, SmsMessage message) {
        // create SMS object from MTN
        SMS sms = new SMS(correlator, message);
        // convert information that received to json format
        Gson gson = new GsonBuilder().create();
        String jsonObject = gson.toJson(sms);
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
            Destination destination = session.createQueue("Received-SMS"); 
            MessageProducer producer = session.createProducer(destination); 
            // create message
            TextMessage txtmessage = session.createTextMessage(jsonObject);
            // send message
            producer.send(txtmessage);
            // close connection
            producer.close(); 
            session.close(); 
        } catch (JMSException e) { 
            System.out.println("NewWebServiceFromWSDL - notifySmsReception - 01: " + e.getMessage()); 
        } 
        finally { 
            if (connection != null) { 
                try { 
                    connection.close(); 
                } catch (JMSException e) { 
                    System.out.println("CNewWebServiceFromWSDL - notifySmsReception - 02: " + e.getMessage()); 
                } 
            } 
        }
    }

    public void notifySmsDeliveryReceipt(String correlator, DeliveryInformation deliveryStatus) {
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
            System.out.println("NewWebServiceFromWSDL - notifySmsDeliveryReceipt - 01: " +
                    e.getMessage()); 
        } 
        finally { 
            if (connection != null) { 
                try { 
                    connection.close(); 
                } catch (JMSException e) { 
                    System.out.println("CNewWebServiceFromWSDL - notifySmsDeliveryReceipt - 02: " +
                            e.getMessage()); 
                } 
            } 
        }
    }
    
}
