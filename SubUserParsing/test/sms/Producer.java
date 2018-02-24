/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 *
 * @author alirezakhtm
 */
public class Producer {
    private static final String BROKER_HOST = "tcp://localhost:%d"; 
    private static final int BROKER_PORT = 61616;//Util.getBrokerPort(); 
    private static final String BROKER_URL = String.format(BROKER_HOST, BROKER_PORT); 
    private static final Boolean NON_TRANSACTED = false; 
    private static final int NUM_MESSAGES_TO_SEND = 100; 
    private static final long DELAY = 100; 
 
    public static void main(String[] args) { 
 
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", BROKER_URL); 
        Connection connection = null;
        
        try { 
 
            connection = connectionFactory.createConnection(); 
            connection.start(); 
 
            Session session = connection.createSession(NON_TRANSACTED, Session.AUTO_ACKNOWLEDGE); 
            Destination destination = session.createQueue("test-queue"); 
            MessageProducer producer = session.createProducer(destination); 
 
            for (int i = 0; i < NUM_MESSAGES_TO_SEND; i++) { 
                TextMessage message = session.createTextMessage("Message #" + i); 
                System.out.println("Sending message #" + i); 
                producer.send(message); 
                Thread.sleep(DELAY); 
            } 
 
            producer.close(); 
            session.close(); 
 
        } catch (Exception e) { 
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
        } 
    } 
}
