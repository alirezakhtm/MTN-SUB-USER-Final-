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
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    private String msisdn;
    private String shortcode;
    private String sendtime;
    private Integer indx;
    private String content;
    private String correlator;

    public Log() {
    }

    public Log(Integer indx) {
        this.indx = indx;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrelator() {
        return correlator;
    }

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.processdigidaroo.database.object.Log[ indx=" + indx + " ]";
    }
    
}
