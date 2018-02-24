/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobtakeran.mtn.sendsms.webservice;

import com.mobtakeran.mtn.sendsms.HeaderHandlerResolver;
import com.mobtakeran.mtn.smsservice.PolicyException_Exception;
import com.mobtakeran.mtn.smsservice.ServiceException_Exception;
import com.mobtakeran.mtn.smsservice.SimpleReference;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Administrator
 */
@WebService(serviceName = "MySendSMSWebService")
public class MySendSMSWebService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String sendSMSToIrancellUser(@WebParam(name = "message") String message,@WebParam(name = "msisdn") String msisdn) throws PolicyException_Exception, ServiceException_Exception {
        com.mobtakeran.mtn.smsservice.SendSmsService service = new com.mobtakeran.mtn.smsservice.SendSmsService();
        
        HeaderHandlerResolver resolver=new HeaderHandlerResolver(msisdn);
        service.setHandlerResolver(resolver);
        
	com.mobtakeran.mtn.smsservice.SendSms port = service.getSendSms();
       
	 // TODO initialize WS operation arguments here
	java.util.List<java.lang.String> addresses =new ArrayList<String>();
        addresses.add("tel:"+msisdn);

      
	java.lang.String senderName = "7707";
	com.mobtakeran.mtn.smsservice.ChargingInformation charging = null;
	
	com.mobtakeran.mtn.smsservice.SimpleReference receiptRequest =new SimpleReference();
        receiptRequest.setCorrelator("mobreal");
        receiptRequest.setEndpoint("http://10.130.158.140:8080/GetSMS/SmsNotificationService");
        receiptRequest.setInterfaceName("notifySmsDeliveryReceipt");
	java.lang.String encode = "";
	java.lang.Integer sourceport = Integer.valueOf(0);
	java.lang.Integer destinationport = Integer.valueOf(0);
	java.lang.Integer esmClass = Integer.valueOf(0);
	java.lang.Integer dataCoding = Integer.valueOf(0);
        
        String result = port.sendSms(addresses, senderName, charging, message, receiptRequest, encode, sourceport, destinationport, esmClass, dataCoding);
        System.out.println("the result of send sms with wsdl is:"+result);
        
        //PrintWriter outer = response.getWriter();
        
        return "sent successfully:"+result;
    }
}
