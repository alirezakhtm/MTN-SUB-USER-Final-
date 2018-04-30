/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datasync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.data.object.DataReceived;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.jws.WebService;
import org.csapi.schema.parlayx.data.v1_0.ProductDetail;
import org.csapi.schema.parlayx.data.v1_0.UserID;
import javax.xml.ws.Holder;
import org.csapi.schema.parlayx.data.v1_0.NamedParameterList;
/**
 *
 * @author Administrator
 */
@WebService(
        serviceName = "DataSyncService",
        portName = "DataSync",
        endpointInterface = "org.csapi.wsdl.parlayx.data.sync.v1_0.service.DataSync",
        targetNamespace = "http://www.csapi.org/wsdl/parlayx/data/sync/v1_0/service",
        wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/sag_data_sync_service_1_0.wsdl")
public class NewWebServiceFromWSDL {

    public int syncSubscriptionData(String msisdn, String serviceId, String productId,
            int updateType, ProductDetail productDetail) {
        return 777;
    }

    public int changeMSISDN(String msisdn, String newMSISDN, String timeStamp) {
        return 666;
    }

    public void syncOrderRelation(UserID userID, String spID, String productID, 
            String serviceID, String serviceList, int updateType, String updateTime,
            String updateDesc, String effectiveTime, String expiryTime,
            Holder<NamedParameterList> extensionInfo,
            Holder<Integer> result, Holder<String> resultDescription) {
                        
//        System.out.println("sync order called: userid:"+userID.getID()+" | spID:"+spID+" | productID:"+productID+ " | "+
//                "serviceID:"+serviceID+" | updateType:"+updateType+" | updateTime:"+updateTime+
//                " | updateDesc:"+updateDesc+" | effectiveTime:"+effectiveTime+" | extentionInfo:"+extensionInfo.value.toString()+
//                " | result:"+result.value+ " | resultDescription:"+resultDescription.value);
        DataReceived dataReceived = new DataReceived(
                userID.getID(),
                spID,
                productID,
                serviceID,
                serviceList,
                updateType,
                updateTime,
                updateDesc,
                effectiveTime,
                expiryTime,
                /*extensionInfo.value.toString()*/"null",
                /*result.value.toString()*/"null",
                /*resultDescription.value*/"null");
        Gson gson = new GsonBuilder().create();
        String strJson = gson.toJson(dataReceived, DataReceived.class);
        byte[] buffer = strJson.getBytes();
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
    }

    public void syncMSISDNChange(String msisdn, String newMSISDN, javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo, javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) {
        
    }

    public void syncSubscriptionActive(org.csapi.schema.parlayx.data.v1_0.UserID userID, java.lang.String spID, java.lang.String productID, java.lang.String serviceID, javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo, javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) {
        
    }
    
}
