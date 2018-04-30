/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.processdigidaroo.database.object;

/**
 *
 * @author alirezakhtm
 */
public class Message {
    private String dateSend;
    private String successfullPaymentMessage;
    private String failedPaymentMessage;

    public Message() {
    }
    
    public Message(String dateSend, String successfullPaymentMessage, String failedPaymentMessage) {
        this.dateSend = dateSend;
        this.successfullPaymentMessage = successfullPaymentMessage;
        this.failedPaymentMessage = failedPaymentMessage;
    }
    
    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public String getSuccessfullPaymentMessage() {
        return successfullPaymentMessage;
    }

    public void setSuccessfullPaymentMessage(String successfullPaymentMessage) {
        this.successfullPaymentMessage = successfullPaymentMessage;
    }

    public String getFailedPaymentMessage() {
        return failedPaymentMessage;
    }

    public void setFailedPaymentMessage(String failedPaymentMessage) {
        this.failedPaymentMessage = failedPaymentMessage;
    }
    
}
