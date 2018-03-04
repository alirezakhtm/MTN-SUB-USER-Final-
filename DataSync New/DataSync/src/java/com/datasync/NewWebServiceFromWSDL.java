/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datasync;


import com.digidaroo.smsWS.PolicyExceptionException;
import com.digidaroo.smsWS.ServiceExceptionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.data.object.DataReceived;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jws.WebService;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Administrator
 */
@WebService(serviceName = "DataSyncService", portName = "DataSync", endpointInterface = "org.csapi.wsdl.parlayx.data.sync.v1_0.service.DataSync", targetNamespace = "http://www.csapi.org/wsdl/parlayx/data/sync/v1_0/service", wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/sag_data_sync_service_1_0.wsdl")
public class NewWebServiceFromWSDL {

    public int syncSubscriptionData(java.lang.String msisdn, java.lang.String serviceId, java.lang.String productId, int updateType, org.csapi.schema.parlayx.data.v1_0.ProductDetail productDetail) {
        //TODO implement this method
        System.out.println("syncSubscriptionData order called"); 
        return 777;
    }

    public int changeMSISDN(java.lang.String msisdn, java.lang.String newMSISDN, java.lang.String timeStamp) {
        //TODO implement this method
        System.out.println("changeMSISDN order called"); 
        return 666;
    }

    public void syncOrderRelation(org.csapi.schema.parlayx.data.v1_0.UserID userID, java.lang.String spID, java.lang.String productID, 
            java.lang.String serviceID, java.lang.String serviceList, int updateType, java.lang.String updateTime,
            java.lang.String updateDesc, java.lang.String effectiveTime, java.lang.String expiryTime,
            javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo,
            javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) throws PolicyExceptionException, ServiceExceptionException {
        
        
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
                /*extensionInfo != null ? extensionInfo.value.toString() : "null"*/"null",
                /*result != null ? result.value.toString() : "null"*/"null",
                /*resultDescription != null ? resultDescription.value : "null"*/"null");
        Gson gson = new GsonBuilder().create();
        String strJson = gson.toJson(dataReceived, DataReceived.class);
        // Connect to ActiveMQ and put data into Queue
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        javax.jms.Connection connection = null;
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

    public void syncMSISDNChange(java.lang.String msisdn, java.lang.String newMSISDN, javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo, javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) {
        //TODO implement this method
System.out.println("syncMSISDNChange order called");       
// throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void syncSubscriptionActive(org.csapi.schema.parlayx.data.v1_0.UserID userID, java.lang.String spID, java.lang.String productID, java.lang.String serviceID, javax.xml.ws.Holder<org.csapi.schema.parlayx.data.v1_0.NamedParameterList> extensionInfo, javax.xml.ws.Holder<Integer> result, javax.xml.ws.Holder<java.lang.String> resultDescription) {
        //TODO implement this method
System.out.println("syncSubscriptionActive order called");         
// throw new UnsupportedOperationException("Not implemented yet.");
    }

    private static String sendSMS(java.lang.String message, java.lang.String msisdn) throws PolicyExceptionException, ServiceExceptionException {
        com.digidaroo.smsWS.SendSMSWebService_Service service = new com.digidaroo.smsWS.SendSMSWebService_Service();
        com.digidaroo.smsWS.SendSMSWebService port = service.getSendSMSWebServicePort();
        String res= port.sendSMS(message, msisdn);
        return res;
    }

    
    
}
