/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.sms.object.SMS;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import org.csapi.schema.parlayx.sms.v2_2.SmsMessage;

/**
 *
 * @author alirezakhtm
 */
public class ThSMSReceive implements Runnable{

    private final String correlator; 
    private final SmsMessage message;

    public ThSMSReceive(String correlator, SmsMessage message) {
        this.correlator = correlator;
        this.message = message;
    }
    
    @Override
    public void run() {
        // create SMS object from MTN
        SMS sms = new SMS(correlator, message);
        // convert information that received to json format
        Gson gson = new GsonBuilder().create();
        String jsonObject = gson.toJson(sms);
        try{
            DatagramPacket datagramPacket = new DatagramPacket(
                    jsonObject.getBytes(),
                    0,
                    jsonObject.getBytes().length,
                    InetAddress.getByName("127.0.0.1"),
                    9898);
            DatagramSocket socket = new DatagramSocket();
            socket.send(datagramPacket);
            socket.close();
        }catch(IOException e){
            System.err.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") + " >>> " + e);
        }
    }
}
