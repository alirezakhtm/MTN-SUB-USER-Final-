/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.processdigidaroo.database.object;

import java.util.List;

/**
 *
 * @author alirezakhtm
 */
public class ReportTbl {
    /**
     *  `customerNumber`,
        `cash`,
        `successNumber`,
        `failedNumber`,
        `priceList`,
        `successList`,
        `failedList`,
        `datePayment`,
        `processStartedTime`,
        `processFinishedTime`,
        `failedErrorList`
     */
    private int customerNumber;
    private int cash;
    private List<Integer> lstPrice;
    private List<Integer> lstSuccess;
    private List<Integer> lstFailed;
    private String date;

    public ReportTbl() {
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public List<Integer> getLstPrice() {
        return lstPrice;
    }

    public void setLstPrice(List<Integer> lstPrice) {
        this.lstPrice = lstPrice;
    }

    public List<Integer> getLstSuccess() {
        return lstSuccess;
    }

    public void setLstSuccess(List<Integer> lstSuccess) {
        this.lstSuccess = lstSuccess;
    }

    public List<Integer> getLstFailed() {
        return lstFailed;
    }

    public void setLstFailed(List<Integer> lstFailed) {
        this.lstFailed = lstFailed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
