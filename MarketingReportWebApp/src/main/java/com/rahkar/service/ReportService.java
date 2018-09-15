/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.rahkar.service;

import com.rahkar.model.RowTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alirzea
 */
@Component
public class ReportService {
    
    @Autowired
    private DatabaseService databaseService;
    
    
    private static List<String> lstDates = new LinkedList<>();
    
    static{
        Calendar calNow = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(calNow.getTime());
        calNow.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(calNow.getTime());
        calNow.add(Calendar.DAY_OF_MONTH, -1);
        String twoDaysAgo = new SimpleDateFormat("yyyy-MM-dd").format(calNow.getTime());
        lstDates.add(today);
        lstDates.add(yesterday);
        lstDates.add(twoDaysAgo);
    }
    
    
    public List<RowTableModel> getReportTableForService(int serviceCode){
        Calendar calNow = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(calNow.getTime());
        if(!today.equals(lstDates.get(0))){
            lstDates.clear();
            calNow.add(Calendar.DAY_OF_MONTH, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(calNow.getTime());
            calNow.add(Calendar.DAY_OF_MONTH, -1);
            String twoDaysAgo = new SimpleDateFormat("yyyy-MM-dd").format(calNow.getTime());
            lstDates.add(today);
            lstDates.add(yesterday);
            lstDates.add(twoDaysAgo);
        }
        List<RowTableModel> lstAns = new ArrayList<>();
        for(String s : lstDates){
            RowTableModel model = new RowTableModel(
                    s,
                    databaseService.getSubUserNum(serviceCode, s),
                    databaseService.getUnSubUserNum(serviceCode, s),
                    databaseService.getServiceName(serviceCode),
                    databaseService.getYesterdayPayment(serviceCode, s)
            );
            lstAns.add(model);
        }
        return lstAns;
    }
    
}
