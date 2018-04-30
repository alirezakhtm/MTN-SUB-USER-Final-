/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.database.object;

/**
 *
 * @author alirezakhtm
 */
public class ServiceUserObj {
    private int serviceCode;
    private String msisdn;
    private String regDate;
    private String unRegDate;
    private int status;

    public ServiceUserObj(int serviceCode, String msisdn, String regDate, String unRegDate, int status) {
        this.serviceCode = serviceCode;
        this.msisdn = msisdn;
        this.regDate = regDate;
        this.unRegDate = unRegDate;
        this.status = status;
    }

    public ServiceUserObj() {
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUnRegDate() {
        return unRegDate;
    }

    public void setUnRegDate(String unRegDate) {
        this.unRegDate = unRegDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
