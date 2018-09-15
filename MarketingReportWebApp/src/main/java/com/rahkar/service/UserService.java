/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.service;

import com.rahkar.xml.model.Root;
import com.rahkar.xml.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alirzea
 */
@Component
public class UserService {
    
    @Autowired
    private XMLService xmls;
    
    public boolean addUser(int serviceCode, String password){
        Root root = xmls.getConfigFileData();
        List<User> lstUsers = root.getUsers();
        for(User user : lstUsers){
            if(user.getPassword().equals(password)){
                return false;
            }
        }
        lstUsers.add(new User("simple", password, serviceCode+""));
        root.setUsers(lstUsers);
        xmls.saveData(root);
        return true;
    }
    
    public List<User> getAllUser(){
        return xmls.getConfigFileData().getUsers();
    }
    
    public void deleteUser(String password){
        Root root = xmls.getConfigFileData();
        List<User> lstUsers = root.getUsers();
        List<User> lstUsersAns = new ArrayList<>();
        for(User user : lstUsers){
            if(!user.getPassword().equals(password)){
                lstUsersAns.add(user);
            }
        }
        root.setUsers(lstUsersAns);
        xmls.saveData(root);
    }
}
