/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.json.handler;

/**
 *
 * @author alirezakhtm
 */
public class DatabaseObj {
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private String activeMQUsername;
    private String activeMQPassword;
    
    public DatabaseObj() {
    }

    public DatabaseObj(String dbName, String dbUsername, String dbPassword, String activeMQUsername, String activeMQPassword) {
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.activeMQUsername = activeMQUsername;
        this.activeMQPassword = activeMQPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getActiveMQUsername() {
        return activeMQUsername;
    }

    public void setActiveMQUsername(String activeMQUsername) {
        this.activeMQUsername = activeMQUsername;
    }

    public String getActiveMQPassword() {
        return activeMQPassword;
    }

    public void setActiveMQPassword(String activeMQPassword) {
        this.activeMQPassword = activeMQPassword;
    }
    
}
