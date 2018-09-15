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
import com.rahkar.xml.model.User;
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
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DatabaseService databaseService;
    
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        model.put("password", "");
        return "login";
    }
    
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String showLoginPageInFirstPage(ModelMap model){
        model.put("password", "");
        return "redirect:/login";
    }
    
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String getLoginParameter(@RequestParam("password") String password, ModelMap model){
        model.put("password", password);
        if(loginService.isUserLegal(password)){
            if(loginService.isUserAdmin(password)){
                List<Service> lstService = databaseService.getAllServices();
                List<User> lstUser = userService.getAllUser();
                model.put("lstServices", lstService);
                model.put("lstUsers", lstUser);
                return "admin-console";
            }else{
                return "redirect:/report";
            }
        }else{
            return "user-not-valid";
        }
    }
    
}
