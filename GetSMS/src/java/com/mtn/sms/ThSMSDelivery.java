/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.sms.object.DeliveryStatus;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import javax.xml.ws.WebServiceContext;
import org.csapi.schema.parlayx.sms.v2_2.DeliveryInformation;

/**
 *
 * @author alirezakhtm
 */
public class ThSMSDelivery implements Runnable{

    private final String correlator;
    private final DeliveryInformation deliveryStatus;
    private final WebServiceContext ctx;
    
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
        try{
            DatagramPacket datagramPacket = new DatagramPacket(
                    jsonObject.getBytes(),
                    0,
                    jsonObject.getBytes().length,
                    InetAddress.getByName("127.0.0.1"),
                    9899);
            DatagramSocket socket = new DatagramSocket();
            socket.send(datagramPacket);
            socket.close();
        }catch(IOException e){
            System.err.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") + " >>> " + e);
        }
    }
    
}
