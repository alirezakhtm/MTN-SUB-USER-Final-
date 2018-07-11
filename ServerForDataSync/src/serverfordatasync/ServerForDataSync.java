/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverfordatasync;

import com.mtn.database.handler.DBHandler;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import redis.clients.jedis.Jedis;

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
                strJson = new String(datagramPacket.getData());//.replace((char)((byte)0x00), '*');
                char ch = (char)((byte)0x00);
                //strJson = strJson.replaceAll("*", "");
                strJson = strJson.replaceAll(ch+"", "");
                System.out.println(strJson);
                /*
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
                            System.out.println("Fialed - " + strJson);
                            // move data to file
                            saveInFile(strJson);
                            // move data to redis
                            saveInRedis(strJson);
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
                */
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
                    System.out.println("Fialed - " + strJson);
                    // move data to file
                    saveInFile(strJson);
                    // move data to redis
                    saveInRedis(strJson);
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
        }catch(Exception e){
            System.err.println("ServerForDataSync - main : " + e);
//            Thread thException = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    restartActiveMQService(strJson);
//                }
//            });
//            thException.start();
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
    
    public static void saveInFile(String msg){
        String fileName = "failedQueue_" +
                new SimpleDateFormat("yyyy-MM-dd")
                        .format(Calendar.getInstance().getTime()) + ".log";
        File file = new File(fileName);
        if(file.exists()){
            try{
                Files.write(Paths.get(fileName), (msg + "\n").getBytes(), StandardOpenOption.APPEND);
            }catch(IOException e){
                System.err.println("ServerForDataSync/saveToFile - 01 - " + e);
            }
        }else{
            try{
                Files.write(Paths.get(fileName), (msg + "\n").getBytes(), StandardOpenOption.CREATE_NEW);
            }catch(IOException e){
                System.err.println("ServerForDataSync/saveToFile - 02 - " + e);
            }
        }
    }
    
    public static void saveInRedis(String msg){
        Jedis redis = new Jedis("localhost");
        redis.lpush("FailedQueue", msg);
    }
    
}
