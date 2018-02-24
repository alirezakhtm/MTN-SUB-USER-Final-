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
public class ServicesObj {
    private int serviceCode;
    private String serviceName;
    private String SPID;
    private String serviceID;
    private String welcomeMT;
    private String cancelMT;
    private int price;
    private String gategory;
    private String correlatorID;
    private String phoneNumber;

    public ServicesObj(int serviceCode, String serviceName, String SPID, String serviceID, String welcomeMT, String cancelMT, int price, String gategory, String correlatorID, String numberPhone) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.SPID = SPID;
        this.serviceID = serviceID;
        this.welcomeMT = welcomeMT;
        this.cancelMT = cancelMT;
        this.price = price;
        this.gategory = gategory;
        this.correlatorID = correlatorID;
        this.phoneNumber = numberPhone;
    }

    public ServicesObj() {
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSPID() {
        return SPID;
    }

    public void setSPID(String SPID) {
        this.SPID = SPID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getWelcomeMT() {
        return welcomeMT;
    }

    public void setWelcomeMT(String welcomeMT) {
        this.welcomeMT = welcomeMT;
    }

    public String getCancelMT() {
        return cancelMT;
    }

    public void setCancelMT(String cancelMT) {
        this.cancelMT = cancelMT;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGategory() {
        return gategory;
    }

    public void setGategory(String gategory) {
        this.gategory = gategory;
    }

    public String getCorrelatorID() {
        return correlatorID;
    }

    public void setCorrelatorID(String correlatorID) {
        this.correlatorID = correlatorID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
