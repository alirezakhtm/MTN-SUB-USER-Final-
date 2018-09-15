/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.model;

/**
 *
 * @author alirzea
 */
public class Service {
    
    private int serviceCode;
    private String name;
    private String serviceId;
    private String spId;
    private String welcomeMT;

    public Service() {
    }

    public Service(int serviceCode, String name, String serviceId, String spId, String welcomeMT) {
        this.serviceCode = serviceCode;
        this.name = name;
        this.serviceId = serviceId;
        this.spId = spId;
        this.welcomeMT = welcomeMT;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getWelcomeMT() {
        return welcomeMT;
    }

    public void setWelcomeMT(String welcomeMT) {
        this.welcomeMT = welcomeMT;
    }
    
    
}
