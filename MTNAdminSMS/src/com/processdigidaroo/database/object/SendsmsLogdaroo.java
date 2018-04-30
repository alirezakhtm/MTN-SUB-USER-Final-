/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.processdigidaroo.database.object;

import java.io.Serializable;

/**
 *
 * @author alirezakhtm
 */
public class SendsmsLogdaroo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer indx;
    private String tel;
    private String sentDate;
    private String sentTime;
    private String sendRespondRefCode;
    private String serviceCode;
    private String failMessage;

    public SendsmsLogdaroo() {
    }

    public SendsmsLogdaroo(String tel, String sentDate, String sentTime, String sendRespondRefCode, String serviceCode, String failMessage) {
        this.tel = tel;
        this.sentDate = sentDate;
        this.sentTime = sentTime;
        this.sendRespondRefCode = sendRespondRefCode;
        this.serviceCode = serviceCode;
        this.failMessage = failMessage;
    }
    
    

    public SendsmsLogdaroo(Integer indx) {
        this.indx = indx;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getSendRespondRefCode() {
        return sendRespondRefCode;
    }

    public void setSendRespondRefCode(String sendRespondRefCode) {
        this.sendRespondRefCode = sendRespondRefCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indx != null ? indx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SendsmsLogdaroo)) {
            return false;
        }
        SendsmsLogdaroo other = (SendsmsLogdaroo) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.processdigidaroo.database.object.SendsmsLogdaroo[ indx=" + indx + " ]";
    }
    
}
