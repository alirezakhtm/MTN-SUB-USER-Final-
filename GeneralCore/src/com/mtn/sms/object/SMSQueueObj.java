/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms.object;

/**
 *
 * @author alirezakhtm
 */
public class SMSQueueObj {
    private final String msisdn;
    private final String message;
    private final int serviceCode;

    public SMSQueueObj(String msisdn, String message, int serviceCode) {
        this.msisdn = msisdn;
        this.message = message;
        this.serviceCode = serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getMessage() {
        return message;
    }

    public int getServiceCode() {
        return serviceCode;
    }
    
}
