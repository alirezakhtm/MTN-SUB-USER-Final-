/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.json.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author alirezakhtm
 */
public class ConfigHandler {
    private String dbName;
    private String dbUsername;
    private String dbPassword;

    public ConfigHandler() {
        try{
            File file = new File("config.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = br.readLine();
            Gson gson = new GsonBuilder().create();
            DatabaseObj db = gson.fromJson(line, DatabaseObj.class);
            this.dbName = db.getDbName();
            this.dbPassword = db.getDbPassword();
            this.dbUsername = db.getDbUsername();            
        }catch(Exception e){
            System.err.println(">> ConfigHandler Constructor >> " + e.getMessage());
        }
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

    @Override
    public String toString() {
        return "{'username':'"+dbUsername+"','password':'"+dbPassword+"','dbName':'"+dbName+"'}";
    }
    
    
    
}
