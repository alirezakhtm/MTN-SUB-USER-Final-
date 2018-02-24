/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.data.object;

/**
 *
 * @author alirezakhtm
 */
public class DataReceived {
    private String userID;
    private String SPID;
    private String productID;
    private String serviceID;
    private String serviceList;
    private int updateType;
    private String updateTime;
    private String updateDesc;
    private String effectiveTime;
    private String expiryTime;
    private String extensionInfo;
    private String result;
    private String resultDescription;

    public DataReceived(
            String userID, String SPID, String productID,
            String serviceID, String serviceList, int updateType,
            String updateTime, String updateDesc, String effectiveTime,
            String expiryTime, String extensionInfo, String result,
            String resultDescription) {
        this.userID = userID;
        this.SPID = SPID;
        this.productID = productID;
        this.serviceID = serviceID;
        this.serviceList = serviceList;
        this.updateType = updateType;
        this.updateTime = updateTime;
        this.updateDesc = updateDesc;
        this.effectiveTime = effectiveTime;
        this.expiryTime = expiryTime;
        this.extensionInfo = extensionInfo;
        this.result = result;
        this.resultDescription = resultDescription;
    }

    public DataReceived() {
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSPID() {
        return SPID;
    }

    public void setSPID(String SPID) {
        this.SPID = SPID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getExtensionInfo() {
        return extensionInfo;
    }

    public void setExtensionInfo(String extensionInfo) {
        this.extensionInfo = extensionInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    @Override
    public String toString() {
        return "[UserID: '"+userID+"',"
                + " SPID: '"+SPID+"',"
                + " productID: '"+productID+"',"
                + " serviceID: '"+serviceID+"',"
                + " serviceList: '"+serviceList+"',"
                + " updateType: '"+updateType+"',"
                + " updateTime: '"+updateTime+"',"
                + " updateDesc: '"+updateDesc+"',"
                + " effectiveTime: '"+effectiveTime+"',"
                + " expiryTime: '"+expiryTime+"',"
                + " extensionInfo: '"+extensionInfo+"',"
                + " result: '"+result+"',"
                + " resultDescription: '"+resultDescription+"']";
    }
    
}
