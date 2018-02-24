/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverfordatasync;

import com.mtn.database.handler.DBHandler;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
public class ServerForDataSync {

    private static String strJson = "";
    private static DBHandler db = new DBHandler();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            byte[] buffer = new byte[100 * 1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(9999, InetAddress.getByName("127.0.0.1"));
            while(true){
                socket.receive(datagramPacket);
                strJson = new String(datagramPacket.getData()).replace((char)((byte)0x00), '*');
                strJson = strJson.replaceAll("*", "");
                System.out.println(strJson);
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        
                        // Connect to ActiveMQ and put data into Queue
                        ActiveMQConnectionFactory connectionFactory = 
                                new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), "tcp://localhost:61616");
                        Connection connection = null;
                        try{
                            connection = connectionFactory.createConnection();
                            connection.start();
                            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                            Destination destination = session.createQueue("DataSync-Queue");
                            MessageProducer producer = session.createProducer(destination);
                            TextMessage message = session.createTextMessage(strJson);
                            producer.send(message);
                            producer.close();
                            // move data to API-DataSync
                            destination = session.createQueue("API-DataSync");
                            MessageProducer producer2 = session.createProducer(destination);
                            producer2.send(message);
                            producer2.close();
                            // close all
                            session.close();
                            connection.close();
                            System.out.println("Moved to Queue.");
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
                });
                th.start();
            }
        }catch(Exception e){
            System.err.println("ServerForDataSync - main : " + e);
            Thread thException = new Thread(new Runnable() {
                @Override
                public void run() {
                    restartActiveMQService(strJson);
                }
            });
            thException.start();
        }
    }
    
    public static void restartActiveMQService(String strJson){
        String SERVICE_NAME = "ActiveMQ";
        String[] stopScript = {"cmd.exe", "/c", "sc", "stop", SERVICE_NAME};//to stop service
        String[] startScript = {"cmd.exe", "/c", "sc", "start", SERVICE_NAME};//to start service
        try {
            Runtime.getRuntime().exec(stopScript);
            Thread.sleep(2000);
            Runtime.getRuntime().exec(startScript);
            // Connect to ActiveMQ and put data into Queue
            ActiveMQConnectionFactory connectionFactory = 
                    new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), "tcp://localhost:61616");
            Connection connection = null;
            try{
                connection = connectionFactory.createConnection();
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue("DataSync-Queue");
                MessageProducer producer = session.createProducer(destination);
                TextMessage message = session.createTextMessage(strJson);
                producer.send(message);
                producer.close();
                session.close();
                System.out.println("Moved to Queue.");
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
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex);
        }
        
    }
    
}
