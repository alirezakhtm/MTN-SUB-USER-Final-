/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.mtn.controller;

import com.rahkar.mtn.service.DBServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author alirzea
 */
@Controller
public class PageController {
    
    @Autowired
    private DBServices db;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showDefaultPage(){
        //map.put("reqCount", db.getNumberRequestInRedisQueue());
        return "Queue of Redis size = " + db.getNumberRequestInRedisQueue()
                + "<br/><a href='/'>Refresh</a>"
                + "<br/><a href='/import'>Import Data to Queue</a>";
    }
    
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @ResponseBody
    public String ImportData(){
        db.importDataToQueu();
        return "<br/><a href='/'>Back to Report</a>";
    }
    
}
