/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database;

import com.fidar.forms.object.RevenueObj;
import com.fidar.forms.object.ServiceUserReportObj;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alireza
 */
public class DBHandler {
    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    private final String url = "jdbc:mysql://localhost:3306/mobtakerandb?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private final String strUsername = "root";
    private final String strPass = "";
    
    public final static int ADMIN = 0;
    public final static int USER = 1;
    
    public void open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection)DriverManager.getConnection(url, strUsername, strPass);
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("DBHandler - open : " + e);
        }
    }
    
    public void close(){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("DBHandler - close : " + e);
        }
    }
    
    /***************************************************************************
     *                      User Table - tbl_user                              *
     ***************************************************************************/
    public boolean isUserCorrect(String username, String password){
        try{
            String query = "SELECT count(*) FROM `mobtakerandb`.`tbl_user` WHERE `USERNAME` = '"+username+"' and `PASSWORD` = '"+password+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            int num = 0;
            while(rst.next()){
                num = rst.getInt(1);
            }
            return num > 0;
        }catch(SQLException e){
            System.err.println("DBHandler - isUserCorrect : " + e);
            return false;
        }
    }
    
    public int getUserPeriority(String username){
        int ans = -1;
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_user` WHERE `USERNAME` = '"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getInt("PERIORITY");
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getUserPeriority : " + e);
        }
        return ans;
    }
    /***************************************************************************
     *                     Service Table - tbl_services                        *
     ***************************************************************************/
    public List<String> getAllServiceName(){
        List<String> lstAns = new ArrayList<>();
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_services`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                lstAns.add(rst.getString("serviceName"));
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getAllServiceName : " + e);
        }
        return lstAns;
    }
    
    /***************************************************************************
     *                     Report Table - tbl_report_*                         *
     ***************************************************************************/
    public List<RevenueObj> getRevenueReport(String startDate, String stopDate, String serviceName){
        List<RevenueObj> lst = new ArrayList<>();
        List<String> lstDate = getSerialDate(startDate, stopDate);
        for(String strDate : lstDate){
            String query = "SELECT * FROM `mobtakerandb`.`tbl_report_"+serviceName+"` WHERE `datePayment` like '%"+strDate+"%'";
            try{
                stm = conn.createStatement();
                rst = stm.executeQuery(query);
                while(rst.next()){
                    RevenueObj ro = new RevenueObj(strDate, serviceName, rst.getString("cash"), rst.getString("customerNumber"));
                    lst.add(ro);
                }
            }catch(SQLException e){
                System.err.println("DBHandler - getRevenueReport : " + e);
            }
        }
        
        return lst;
    }
    
    private List<String> getSerialDate(String startDate, String stopDate){
        List<String> lst = new ArrayList<>();
        String[] strStart = startDate.split("-");
        String[] strStop = stopDate.split("-");
        int start_m = Integer.parseInt(strStart[1]);
        int start_d = Integer.parseInt(strStart[2]);
        int stop_m = Integer.parseInt(strStop[1]);
        int stop_d = Integer.parseInt(strStop[2]);
        if(start_m < stop_m){
            for(int m = start_m; m <= stop_m; m++){
                for(int d = 1; d <= 30; d++){
                    String s = String.format("%s-%02d-%02d", strStart[0],m,d);
                    lst.add(s);
                }
            }
        }else{
            if(start_m == stop_m && start_d <= stop_d){
                for(int d = start_d; d <= stop_d; d++){
                    String s = String.format("%s-%02d-%02d", strStart[0],start_m,d);
                    lst.add(s);
                }
            }
        }
        return lst;
    }
    
    /***************************************************************************
     *                Service User Table - tbl_service_user                    *
     ***************************************************************************/
    public List<ServiceUserReportObj> getServiceUser(){
        List<ServiceUserReportObj> lst = new ArrayList<>();
        try{
            Map<String,String> mapService = new HashMap<>();
            String queryServices = "SELECT * FROM `mobtakerandb`.`tbl_services`";
            stm = conn.createStatement();
            rst = stm.executeQuery(queryServices);
            while(rst.next()){
                mapService.put(rst.getString("serviceCode"), rst.getString("serviceName"));
            }
            for(String sc : mapService.keySet()){
                String queryUser = "SELECT count(*) FROM `mobtakerandb`.`tbl_service_users` "
                        + "WHERE `serviceCode` = '"+sc+"' and `status` = '1'";
                stm = conn.createStatement();
                rst = stm.executeQuery(queryUser);
                rst.next();
                ServiceUserReportObj suro = new ServiceUserReportObj(mapService.get(sc), rst.getInt(1));
                lst.add(suro);
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getServiceUser : " + e);
        }
        return lst;
    }
    
    
    
}
