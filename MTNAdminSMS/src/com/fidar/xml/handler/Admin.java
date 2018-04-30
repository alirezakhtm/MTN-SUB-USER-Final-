/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.xml.handler;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alirzea
 */
@XmlRootElement(name = "admin")
public class Admin {
    private String phone, serviceCode, correlator, reportTableName, title;

    public String getPhone() {
        return phone;
    }

    @XmlElement(name = "phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    @XmlElement(name = "servicecode")
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCorrelator() {
        return correlator;
    }

    @XmlElement(name = "correlator")
    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    public String getReportTableName() {
        return reportTableName;
    }

    @XmlElement(name = "reporttablename")
    public void setReportTableName(String reportTableName) {
        this.reportTableName = reportTableName;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }
    
}
