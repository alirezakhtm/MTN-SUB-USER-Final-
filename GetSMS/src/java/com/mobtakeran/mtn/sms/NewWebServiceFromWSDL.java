/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobtakeran.mtn.sms;

import com.mtn.sms.ThSMSDelivery;
import com.mtn.sms.ThSMSReceive;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

/**
 *
 * @author Administrator
 */
@WebService(serviceName = "SmsNotificationService", portName = "SmsNotification", endpointInterface = "org.csapi.wsdl.parlayx.sms.notification.v2_2.service.SmsNotification", targetNamespace = "http://www.csapi.org/wsdl/parlayx/sms/notification/v2_2/service", wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/parlayx_sms_notification_service_2_2.wsdl")
@HandlerChain(file = "handlerchain.xml")
public class NewWebServiceFromWSDL {
    
    @Resource
    private WebServiceContext ctx;

    public void notifySmsReception(java.lang.String correlator, org.csapi.schema.parlayx.sms.v2_2.SmsMessage message) {
        Thread th = new Thread(new ThSMSReceive(correlator, message));
        th.start();
    }

    public void notifySmsDeliveryReceipt(java.lang.String correlator, org.csapi.schema.parlayx.sms.v2_2.DeliveryInformation deliveryStatus) {
        Thread th = new Thread(new ThSMSDelivery(correlator, deliveryStatus, ctx));
        th.start();
    }
    
}
