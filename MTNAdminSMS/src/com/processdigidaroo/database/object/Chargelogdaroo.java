/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.processdigidaroo.database.object;

import java.io.Serializable;

/**
 *
 * @author alirezakhtm
 */
public class Chargelogdaroo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long indx;
    private String tel;
    private Integer amount;
    private String isSucess;
    private String failedMessage;
    //@Temporal(TemporalType.DATE)
    private String chargeDate;
    //@Temporal(TemporalType.TIME)
    private String chargeTime;
    private String chargeRefID;
    private String serviceCode;

    public Chargelogdaroo() {
    }

    public Chargelogdaroo(
                            String tel,
                            Integer amount, 
                            String isSucess, 
                            String failedMessage, 
                            String chargeDate, 
                            String chargeTime, 
                            String chargeRefID, 
                            String serviceCode) {
        this.tel = tel;
        this.amount = amount;
        this.isSucess = isSucess;
        this.failedMessage = failedMessage;
        this.chargeDate = chargeDate;
        this.chargeTime = chargeTime;
        this.chargeRefID = chargeRefID;
        this.serviceCode = serviceCode;
    }
    
    

    public Chargelogdaroo(Long indx) {
        this.indx = indx;
    }

    public Long getIndx() {
        return indx;
    }

    public void setIndx(Long indx) {
        this.indx = indx;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public String getFailedMessage() {
        return failedMessage;
    }

    public void setFailedMessage(String failedMessage) {
        this.failedMessage = failedMessage;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeRefID() {
        return chargeRefID;
    }

    public void setChargeRefID(String chargeRefID) {
        this.chargeRefID = chargeRefID;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indx != null ? indx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chargelogdaroo)) {
            return false;
        }
        Chargelogdaroo other = (Chargelogdaroo) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.processdigidaroo.database.object.Chargelogdaroo[ indx=" + indx + " ]";
    }
    
}
