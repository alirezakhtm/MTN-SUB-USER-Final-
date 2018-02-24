/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.forms.object;


/**
 *
 * @author alireza
 */
public class RevenueObj {
    private String date;
    private String serviceName;
    private String price;
    private String customerNumber;

    public RevenueObj(String date, String serviceName, String price, String customerNumber) {
        this.date = date;
        this.serviceName = serviceName;
        this.price = price;
        this.customerNumber = customerNumber;
    }

    public RevenueObj() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    
}
