/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms.handler;

import com.digidaroo.smsWS.PolicyExceptionException;
import com.digidaroo.smsWS.SendSMS;
import com.digidaroo.smsWS.SendSMSWebService;
import com.digidaroo.smsWS.SendSMSWebService_Service;
import com.digidaroo.smsWS.ServiceExceptionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobtakeran.mtn.smsservice.SendSmsService;
import com.mtn.database.handler.DBHandler;
import com.mtn.database.object.MOMTLogObj;
import com.mtn.sms.object.SMSQueueObj;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.mobtakeran.mtn.smsservice.SimpleReference;
import com.mobtakeran.mtn.smsservice.ChargingInformation;
import com.mtn.database.object.ServicesObj;

/**
 *
 * @author alirezakhtm
 */
public class SMSHandler {
    
    private Timer GuardTimer = new Timer();
    private int TIME_DELAY = 1000;
    private final int TIME_OUT = 2000;

    private boolean bCurrentProcess = false;
    
    private DBHandler db = new DBHandler();
    
    public SMSHandler(int milisec) {
        this.TIME_DELAY = milisec;
    }
    
    public void startGuardTimer(){
        GuardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!bCurrentProcess){
                            SMSParsingStart();
                        }
                    }
                });
                th.start();
            }
        }, 0, TIME_DELAY);
    }
    
    private void SMSParsingStart(){
        bCurrentProcess = true;
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(db.getActiveMQUsername(), db.getActiveMQPassword(), "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("SendSMS-Queue");
            MessageConsumer consumer = session.createConsumer(destination);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        SMSQueueObj smsqo = gson.fromJson(strJson, SMSQueueObj.class);
                        sendSMS(smsqo);
                    }
                }else{
                    break;
                }
            }
            consumer.close();
            session.close();
        }catch(JMSException e){
            System.err.println("SMSHandler - SMSParsingStart - 01 : " + e);
        }finally{
            if(connection != null){
                try{
                    connection.close();
                }catch(JMSException e){
                    System.err.println("SMSHandler - SMSParsingStart - 02 : " + e);
                }
            }
            bCurrentProcess = false;
        }
    }
    
    private boolean sendSMS(SMSQueueObj smsqo){
        try {
            // Send SMS Web Service
            
            /*SendSMSWebService_Service sendSMSWebService = new SendSMSWebService_Service();
            sendSMSWebService.setHandlerResolver(new MTNHeaderHandlerResolver(smsqo.getMsisdn(), "spID", "serviceID"));
            SendSMSWebService service = sendSMSWebService.getSendSMSWebServicePort();
            service.sendSMS(smsqo.getMessage(), smsqo.getMsisdn());*/
            String msisdn = smsqo.getMsisdn();
            if(msisdn.contains(":")){
                msisdn = msisdn.substring(msisdn.indexOf(":") + 1, msisdn.length() - 1);
            }
            System.out.println("[*] SendSMSHandler - sendSMS : Start sendSMS function.");
            db.open();
            ServicesObj myService = db.getService(smsqo);
            db.close();
            SendSmsService service = new SendSmsService();
            System.out.println("[*] SendSMSHandler - sendSMS : SendSmsService have been created.");
            MTNHeaderHandlerResolver resolver = new MTNHeaderHandlerResolver(msisdn, myService.getSPID(), myService.getServiceID());
            System.out.println("[*] SendSMSHandler - sendSMS : MTNHeaderHandlerResolver have been created.");
            service.setHandlerResolver(resolver);
            System.out.println("[*] SendSMSHandler - sendSMS : resolver have been set.");
            com.mobtakeran.mtn.smsservice.SendSms port = service.getSendSms();
            // TODO initialize WS operation arguments here
            List<String> addresses =new ArrayList<String>();
            
            addresses.add("tel:" + msisdn);
            
            String senderName = myService.getPhoneNumber();
            ChargingInformation charging = null;
            SimpleReference receiptRequest =new SimpleReference();
            System.out.println("[*] SendSMSHandler - sendSMS : SimpleRefrence have been created.");
            receiptRequest.setCorrelator(myService.getCorrelatorID());
            System.out.println("[*] SendSMSHandler - sendSMS : correlator have been set.");
            receiptRequest.setEndpoint("http://79.175.151.33:80/GetSMS/SmsNotificationService");//10.130.158.140:8080
            System.out.println("[*] SendSMSHandler - sendSMS : endpoint have been set.");
            receiptRequest.setInterfaceName("notifySmsDeliveryReceipt");
            System.out.println("[*] SendSMSHandler - sendSMS : Interface have been set.");
            String encode = "";
            Integer sourceport = Integer.valueOf(0);
            Integer destinationport = Integer.valueOf(0);
            Integer esmClass = Integer.valueOf(0);
            Integer dataCoding = Integer.valueOf(0);
            System.out.println("[*] SendSMSHandler - sendSMS : ready to send by port object.");
            String result = port.sendSms(addresses,
                    senderName,
                    charging,
                    smsqo.getMessage(),
                    receiptRequest,
                    encode,
                    sourceport,
                    destinationport,
                    esmClass,
                    dataCoding);
            System.out.println("the result of send sms with wsdl is:" + result);
            // Save data on database tbl_mo_mt_log
            MOMTLogObj logObj = new MOMTLogObj(smsqo.getServiceCode()+"",
                    msisdn,
                    "",
                    smsqo.getMessage(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                            format(Calendar.getInstance().getTime()));
            db.open();
            db.saveMOMTLog(logObj);
            db.close();
            return true;
        } catch (Exception ex) {
            System.err.println("SMSHandler - sendSMS - 01 : " + ex);
            return false;
        }
    }
}
