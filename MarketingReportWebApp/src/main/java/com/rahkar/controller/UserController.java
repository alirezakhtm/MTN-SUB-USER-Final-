/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.controller;

import com.rahkar.model.Service;
import com.rahkar.service.DatabaseService;
import com.rahkar.service.LoginService;
import com.rahkar.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author alirzea
 */
@Controller
@SessionAttributes("password")
public class UserController {
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DatabaseService databaseService;
    
    @RequestMapping(path = "/add-user", method = RequestMethod.GET)
    public String addUserPanel(ModelMap model){
        String currentPassword = (String) model.get("password");
        if(loginService.isUserLegal(currentPassword)){
            if(loginService.isUserAdmin(currentPassword)){
                List<Service> lstServices = databaseService.getAllServices();
                model.put("lstServices", lstServices);
                return "add-user";
            }else{
                return "redirect:/login";
            }
        }else{
            return "redirect:/login";
        }
    }
    
    @RequestMapping(path = "/add-user", method = RequestMethod.POST)
    public String addUserDefined(
            @RequestParam(name = "serviceCode") int ServiceCode, 
            @RequestParam(name = "password") String password,
            ModelMap model){
        if(!loginService.isUserLegal(password) && loginService.isUserAdmin(password)){
            return "redirect:/login";
        }
        boolean ans = userService.addUser(ServiceCode, password);
        model.put("message", 
                (ans) ? "User created successfully" : 
                        "Password can not be equal to other users. please try again with new password.");
        model.put("messageClass", (ans) ? "panel-success" : "panel-danger");
        return "/message";
    }
    
    @RequestMapping(path = "/delete-user", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(name = "password") String password, ModelMap model){
        String currentPassword = (String) model.get("password");
        if(!loginService.isUserLegal(currentPassword) && loginService.isUserAdmin(currentPassword)){
            return "redirect:/login";
        }
        if(loginService.isUserAdmin(password)){
            model.put("message", "This Password is ADMIN.");
            model.put("messageClass", "panel-danger");
            return "message";
        }
        userService.deleteUser(password);
        return "redirect:/report";
    }
    
}
