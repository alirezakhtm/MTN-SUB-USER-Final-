/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.ws.WebServiceContext;
import org.csapi.schema.parlayx.sms.v2_2.DeliveryInformation;
/**
 *
 * @author alirezakhtm
 */
public class DeliveryStatus {
    private String address;
    private String nameOfDeliveryStatus;
    private String valueOfDeliveryStatus;
    private String correlator;
    private String traceUniqueID;
    private String date;

    public DeliveryStatus(String correlator, DeliveryInformation obj, WebServiceContext wscx) {
        this.correlator = correlator;
        this.address = obj.getAddress();
        this.nameOfDeliveryStatus = obj.getDeliveryStatus().name();
        this.valueOfDeliveryStatus = obj.getDeliveryStatus().value();
        Calendar cal = Calendar.getInstance();
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        try{
            this.traceUniqueID = (String)wscx.getMessageContext().get("traceUniqueID");
        }catch(Exception e){
            this.traceUniqueID = "none";
        }
    }

    public DeliveryStatus() {
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameOfDeliveryStatus() {
        return nameOfDeliveryStatus;
    }

    public void setNameOfDeliveryStatus(String nameOfDeliveryStatus) {
        this.nameOfDeliveryStatus = nameOfDeliveryStatus;
    }

    public String getValueOfDeliveryStatus() {
        return valueOfDeliveryStatus;
    }

    public void setValueOfDeliveryStatus(String valueOfDeliveryStatus) {
        this.valueOfDeliveryStatus = valueOfDeliveryStatus;
    }

    public String getCorrelator() {
        return correlator;
    }

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    public String getTraceUniqueID() {
        return traceUniqueID;
    }

    public void setTraceUniqueID(String traceUniqueID) {
        this.traceUniqueID = traceUniqueID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "["
                + "address: '"+this.address+"',"
                + " nameOfDeliveryStatus: '"+this.nameOfDeliveryStatus+"',"
                + " valueOfDeliveryStatus: '"+this.valueOfDeliveryStatus+"',"
                + " correlator: '"+this.correlator+"',"
                + " traceUniqueID: '"+this.traceUniqueID+"',"
                + " date: '"+this.date+"']";
    }
}
