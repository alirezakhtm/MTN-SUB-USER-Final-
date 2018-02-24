package sms;


import com.mtn.sms.ThSMSReceive;
import org.csapi.schema.parlayx.sms.v2_2.SmsMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alirezakhtm
 */
public class FakeSMS {
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            SmsMessage message = new SmsMessage();
            message.setMessage("Hi");
            message.setSenderAddress("989194018087");
            message.setSmsServiceActivationNumber("12");
            //message.setDateTime(XMLGregorianCalendar.class.newInstance());
            Thread th = new Thread(new ThSMSReceive("Digidaroo", message));
            th.start();
        }
        
    }
}
