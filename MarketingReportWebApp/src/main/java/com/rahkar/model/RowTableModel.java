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
public class RowTableModel {
    
    private String date;
    private int subNumber;
    private int unSubNumber;
    private String serviceName;
    private int paymentYesterday;

    public RowTableModel() {
    }

    public RowTableModel(String date, int subNumber, int unSubNumber, String serviceName, int paymentYesterday) {
        this.date = date;
        this.subNumber = subNumber;
        this.unSubNumber = unSubNumber;
        this.serviceName = serviceName;
        this.paymentYesterday = paymentYesterday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSubNumber() {
        return subNumber;
    }

    public void setSubNumber(int subNumber) {
        this.subNumber = subNumber;
    }

    public int getUnSubNumber() {
        return unSubNumber;
    }

    public void setUnSubNumber(int unSubNumber) {
        this.unSubNumber = unSubNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPaymentYesterday() {
        return paymentYesterday;
    }

    public void setPaymentYesterday(int paymentYesterday) {
        this.paymentYesterday = paymentYesterday;
    }
    
}
