/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverforgetsms;

import com.mtn.database.handler.DBHandler;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alireza
 */
public class ServerForGetSMS {

    private static DBHandler db = new DBHandler();
    
    /**
     * this server received converted SMS object to JSON from 9898 UDP port.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        byte[] buffer = new byte[100*1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        try{
            DatagramSocket serverSocket = new DatagramSocket(9898, InetAddress.getByName("127.0.0.1"));
            while(true){
                serverSocket.receive(datagramPacket);
                byte[] dataRec = datagramPacket.getData();
                String strData = new String(dataRec);//.replace((char)((byte)0x00), '*');
                char ch = (char)((byte)0x00);
                strData = strData.replaceAll(ch+"", "");
                strData = editStringOfData(strData);
                ActiveMQConnectionFactory connectionFactory = 
                        new ActiveMQConnectionFactory(
                                db.getActiveMQUsername(),
                                db.getActiveMQPassword(),
                                "tcp://localhost:61616"
                        );
                Connection connection = null;
                try{
                    connection = connectionFactory.createConnection();
                    connection.start();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    Destination destination = session.createQueue("Received-SMS");
                    MessageProducer producer = session.createProducer(destination);
                    TextMessage message = session.createTextMessage(strData);
                    producer.send(message);
                    producer.close();
                    // move data to API-SMSReceived
                    destination = session.createQueue("API-SMSReceive");
                    MessageProducer producer2 = session.createProducer(destination);
                    producer2.send(message);
                    producer2.close();
                    // close all
                    session.close();
                    connection.close();
                    System.out.println("[*] " + 
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) +
                            " new SMS added to Received-SMS Queue.");
                    System.out.println("[*] " + 
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) +
                            " Message added to Queue is : " + strData);
                }catch(JMSException e){
                    System.out.println("[*] " + 
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) +
                            " >>> " + e);
                }
            }
        }catch(IOException e){
            System.out.println("[*] " + 
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) +
                    " >>> " + e);
        }
    }

    private static String editStringOfData(String strData) {
        String[] str = strData.split("|");
        while(strData.contains("}")){
            strData = strData.substring(0, strData.lastIndexOf("}"));
        }
        strData += "}";
        return strData;
    }
    
}
