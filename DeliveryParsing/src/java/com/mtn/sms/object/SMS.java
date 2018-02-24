/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.csapi.schema.parlayx.sms.v2_2.SmsMessage;
/**
 *
 * @author alirezakhtm
 */
public class SMS {
    private final String message;
    private final String senderAddress;
    private final String SMSServiceActivationNumber;
    private final String correlator;
    private final String date;

    public SMS(String message, String senderAddress, String SMSServiceActivationNumber, String correlator, String date) {
        this.message = message;
        this.senderAddress = senderAddress;
        this.SMSServiceActivationNumber = SMSServiceActivationNumber;
        this.correlator = correlator;
        this.date = date;
    }

    public SMS(String correlator, SmsMessage receivedMessage) {
        this.message = receivedMessage.getMessage();
        this.senderAddress = receivedMessage.getSenderAddress();
        this.SMSServiceActivationNumber = receivedMessage.getSmsServiceActivationNumber();
        this.correlator = correlator;
        Calendar cal = Calendar.getInstance();
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    @Override
    public String toString() {
        return "[message: '"+message+"',"
                + " senderAddress: '"+senderAddress+"',"
                + " SMSServiceActivationNumber: '"+SMSServiceActivationNumber+"',"
                + " correlator: '"+correlator+"',"
                + " date: '"+date+"']";
    }
}
