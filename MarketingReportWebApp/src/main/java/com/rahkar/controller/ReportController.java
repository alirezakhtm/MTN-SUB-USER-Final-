/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.rahkar.controller;

import com.rahkar.model.RowTableModel;
import com.rahkar.model.Service;
import com.rahkar.service.DatabaseService;
import com.rahkar.service.LoginService;
import com.rahkar.service.ReportService;
import com.rahkar.service.UserService;
import com.rahkar.xml.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author alirzea
 */
@Controller
@SessionAttributes("password")
public class ReportController {
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DatabaseService databaseService;
    
    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public String showReportTable(ModelMap model){
        String currentPassword = (String) model.get("password");
        if(loginService.isUserLegal(currentPassword)){
            if(loginService.isUserAdmin(currentPassword)){
                List<Service> lstService = databaseService.getAllServices();
                List<User> lstUser = userService.getAllUser();
                List<RowTableModel> lstRows = new ArrayList<>();
                for(Service service : lstService){
                    List<RowTableModel> tempLstRows = reportService.getReportTableForService(service.getServiceCode());
                    lstRows.addAll(tempLstRows);
                }
                model.put("lstServices", lstService);
                model.put("lstUsers", lstUser);
                model.put("lstRows", lstRows);
                return "admin-console";
            }else{
                Map<String, String> mapUser = loginService.getLegalUsers();
                String serviceCode = mapUser.get(currentPassword);
                List<RowTableModel> lstRows = reportService.getReportTableForService(Integer.parseInt(serviceCode));
                model.put("trows", lstRows);
                return "report";
            }            
        }else{
            return "redirect:/login";
        }
    }
    
}
