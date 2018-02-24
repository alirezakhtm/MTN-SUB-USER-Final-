/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidartech.setting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.JOptionPane;

/**
 *
 * @author alirezakhtm
 */
public class SettingHandler {

    private String delay;
    
    public SettingHandler() {
        try{
            Gson gson = new GsonBuilder().create();
            String str = Files.readAllLines(Paths.get("setting.json"), StandardCharsets.UTF_8).get(0);
            Setting setting = gson.fromJson(str, Setting.class);
            this.delay = setting.getDelay();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDelay() {
        return delay;
    }
    
    public void saveSettingJsonFile(){
        try{
            Setting setting = new Setting(this.delay);
            Gson gson = new GsonBuilder().create();            
            String strJson = gson.toJson(setting, Setting.class);
            File file = new File("setting.json");
            if(file.exists()) file.delete();
            Files.write(Paths.get("setting.json"), strJson.getBytes(), StandardOpenOption.CREATE_NEW);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
}
