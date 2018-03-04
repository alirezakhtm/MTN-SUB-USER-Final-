/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datasync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.data.object.DataReceived;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jws.WebService;
import org.csapi.schema.parlayx.data.v1_0.ProductDetail;
import org.csapi.schema.parlayx.data.v1_0.UserID;
import javax.xml.ws.Holder;
import org.apache.activemq.ActiveMQConnectionFactory;
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
                extensionInfo.value.toString(),
                result.value.toString(),
                resultDescription.value);
        Gson gson = new GsonBuilder().create();
        String strJson = gson.toJson(dataReceived, DataReceived.class);
        // Connect to ActiveMQ and put data into Queue
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
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
            System.out.println(strJson);
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

    public void syncMSISDNChange(String msisdn, String newMSISDN, javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo, javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) {
        
    }

    public void syncSubscriptionActive(org.csapi.schema.parlayx.data.v1_0.UserID userID, java.lang.String spID, java.lang.String productID, java.lang.String serviceID, javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo, javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) {
        
    }
    
}
