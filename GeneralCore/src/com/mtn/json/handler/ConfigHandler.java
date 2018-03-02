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
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author alirezakhtm
 */
public class ConfigHandler {
    private String dbName = "";
    private String dbUsername = "";
    private String dbPassword = "";
    private String activeMQUsername = "";
    private String activeMQPassword = "";
    
    
    public ConfigHandler() {
        try{
            File file = new File("config.json");
            if(file.exists()) System.out.println("[*] config.json found.");
            else System.out.println("[*] config.json not found.");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = br.readLine();
            Gson gson = new GsonBuilder().create();
            DatabaseObj db = gson.fromJson(line, DatabaseObj.class);
            this.dbName = db.getDbName();
            this.dbPassword = db.getDbPassword();
            this.dbUsername = db.getDbUsername();       
            this.activeMQUsername = db.getActiveMQUsername();
            this.activeMQPassword = db.getActiveMQPassword();
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

    
    
    @Override
    public String toString() {
        return "{'username':'"+dbUsername+"','password':'"+dbPassword+"','dbName':'"+dbName+"'}";
    }
    
    public void saveNewConfig(){
        try{
            DatabaseObj databaseObj = new DatabaseObj(
                    dbName,
                    dbUsername,
                    dbPassword,
                    activeMQUsername,
                    activeMQPassword
            );
            Gson gson = new GsonBuilder().create();
            String strGson = gson.toJson(databaseObj, DatabaseObj.class);
            File file = new File("config.json");
            if(file.exists()) file.delete();
            Files.write(Paths.get("config.json"), strGson.getBytes(), StandardOpenOption.CREATE_NEW);
        }catch(IOException e){
            System.err.println(e);
        }
    }
    /*
    public static void main(String[] args) {
        try{
            DatabaseObj databaseObj = new DatabaseObj("db", "db", "");
            Gson gson = new GsonBuilder().create();
            String strGson = gson.toJson(databaseObj, DatabaseObj.class);
            Files.write(Paths.get("config.json"), strGson.getBytes(), StandardOpenOption.CREATE_NEW);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
    */
}
