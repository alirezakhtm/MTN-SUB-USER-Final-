/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.rahkar.mtn.service;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 *
 * @author alirzea
 */
@Service
public class DBServices {
    
    private static final String activemq_username = "admin",
            activemq_password = "admin",
            redis_queue_name = "FailedQueue";
    
    public long getNumberRequestInRedisQueue(){
        Jedis redis = new Jedis("localhost");
        return redis.llen(redis_queue_name);
    }
    
    public void importDataToQueu() {
        Jedis redis = new Jedis("localhost");
        List<String> lst = new ArrayList<String>();
        long len = redis.llen(redis_queue_name);
        System.out.println("len -> " + len);
        for(int n = 0; n < len; n++){
            String data = redis.lpop(redis_queue_name);
//            if(this.insterToQ(data, q1)){
//                if(!this.insterToQ(data, q2)){
//                    redis.lpush(redis_queue_name, data);
//                }
//            }else{
//                redis.lpush(redis_queue_name, data);
//            }
            this.insterToQ(data);
            this.saveInFile(data);
        }
    }
    
    public boolean insterToQ(String msg){
        boolean ans = false;
        byte[] buffer = msg.getBytes();
        try{
            DatagramPacket datagramPacket = new DatagramPacket(
                    buffer,
                    0,
                    buffer.length,
                    InetAddress.getByName("127.0.0.1"),
                    9999);
            DatagramSocket socket = new DatagramSocket();
            socket.send(datagramPacket);
            socket.close();
        }catch(Exception e){
            System.err.println("DataSync - NewWebServiceFromWSDL - syncOrderRelation : " + e);
        }
        return ans;
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
    
}
