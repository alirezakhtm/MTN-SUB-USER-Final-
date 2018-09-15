/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.service;

import com.rahkar.xml.model.Root;
import com.rahkar.xml.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alirzea
 */
@Component
public class LoginService {
    
    @Autowired
    private XMLService xmls;
    
    /**
     * @return "password" => "serviceCode number"
     */
    public Map<String, String> getLegalUsers(){
        Map<String, String> map = new HashMap<>();
        Root root = xmls.getConfigFileData();
        List<User> lstUser = root.getUsers();
        for(User u : lstUser){
            map.put(u.getPassword(), u.getValue());
        }
        return map;
    }
    
    public boolean isUserLegal(String password){
        return this.getLegalUsers().keySet().contains(password);
    }
    
    public boolean isUserAdmin(String password){
        Root root = xmls.getConfigFileData();
        List<User> lstUser = root.getUsers();
        User admin = new User();
        for(User user : lstUser){
            if(user.getRole().equals("admin")){
                admin = user;
                break;
            }
        }
        if(admin.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }
    
}
