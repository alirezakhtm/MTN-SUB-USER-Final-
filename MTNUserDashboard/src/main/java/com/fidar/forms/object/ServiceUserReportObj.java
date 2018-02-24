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
public class ServiceUserReportObj {
    private String serviceName;
    private int userNumber;

    public ServiceUserReportObj() {
    }

    public ServiceUserReportObj(String serviceName, int userNumber) {
        this.serviceName = serviceName;
        this.userNumber = userNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }
    
}
