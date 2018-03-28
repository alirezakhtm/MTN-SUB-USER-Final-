/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.database.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author alirezakhtm
 */
public class MOMTLogObj {
    private String serviceCode;
    private String msisdn;
    private String mo;
    private String mt;
    private String registerationDate;

    public MOMTLogObj(String serviceCode, String msisdn, String mo, String mt, String registerationDate) {
        this.serviceCode = serviceCode;
        this.msisdn = msisdn;
        this.mo = mo;
        this.mt = mt;
        this.registerationDate = registerationDate;
    }

    public MOMTLogObj() {
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getRegisterationDate() {
        return registerationDate;
    }

    public void setRegisterationDate(String registerationDate) {
        this.registerationDate = registerationDate;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(this, MOMTLogObj.class);
        return str;
    }
    
    
    
}
