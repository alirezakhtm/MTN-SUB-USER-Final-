/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database.handler;

import com.processdigidaroo.database.object.Log;
import com.processdigidaroo.database.object.ReportTbl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author alirzea
 */
public class DBHandler {
    private final String dbName;
    private final String username;
    private final String password;
    private final String url;
    
    private Connection conn;
    private ResultSet rst;
    private Statement stm;
    
    public DBHandler() {
        Properties pro = new Properties();
        try{
            InputStream input = new FileInputStream(new File("config.properties"));
            pro.load(input);
        }catch(IOException e){
            System.err.println("[*] ERROR : " + e);
        }
        this.dbName = pro.getProperty("database-dbname");
        this.password = pro.getProperty("database-password");
        this.username = pro.getProperty("database-username");
        this.url = "jdbc:mysql://localhost:3306/" + this.dbName 
                + "?useSSL=false" 
                + "&useUnicode=true&characterEncoding=utf-8";
    }
    
    public void deleteAdminRecordFromTable(String msisdn, int serviceCode){
        try{
            String query = "delete from `" + this.dbName + "`.`tbl_service_users` where `msisdn`='" + msisdn + "' and serviceCode = '" + serviceCode + "' ";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("[*] ERORR : DBHandler/deleteAdminRecordeFromTable : " + e);
        }
    }
    
    public List<Log> getDigidarooUsers(String tagName){
        List<Log> lstAns = new ArrayList<>();
        try{
            String query_serviceCode = "select * from " + this.dbName +".tbl_services where"
                    + " correlatorId='" + tagName + "'";
            // run above query and use its'result ...
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            int iServiceCode = rst.getInt("serviceCode");         // reached result (above)
            String strShortCode = (rst.getString("numberPhone").contains("tel:"))
                    ? rst.getString("numberPhone") : "tel:"+rst.getString("numberPhone");   //
            
            close();
            open();
            
            String queryFetchData = "select * from " + this.dbName + ".tbl_service_users where `status`='1'"
                    + " and serviceCode='" + iServiceCode + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(queryFetchData);
            while(rst.next()){
                Log l = new Log();
                l.setContent("1");                                      // Content of user that send to MTN
                l.setCorrelator(tagName);                               // Correlator is our tag name that set for it.
                l.setIndx(rst.getInt("indx"));                          // Index of record
                l.setMsisdn("tel:98" + rst.getString("msisdn"));        // Phone number
                l.setSendtime(rst.getString("regDate"));                // Registration date for user
                l.setShortcode(strShortCode);                           // Explain short code for send SMS
                lstAns.add(l);
            }
            
        }catch(SQLException e){
            System.err.println("SQL - 03 : DBHandler_Native >> " + e.getMessage());
        }        
        return lstAns;
    }
    
    public void close(){
        try{
            conn.close();
        }catch(SQLException e){
            System.err.println("SQL - 04 : DBHandler_Native >> " + e.getMessage());
        }
    }
    
    public boolean open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, username, password);
            return true;
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("SQL - 05 : DBHandler_Native >> " + e.getMessage());
            return false;
        }
    }
    
    public int getActiveUserNumber(String tagName) {
        int ans = 0;
        ans = this.getDigidarooUsers(tagName).size();
        return ans;
    }
    
    public int getTotalSubUser(String tagName){
        int ans = 0;
        int iServiceCode = 0;
        
        // get service code from tbl_services
        try{
            String query_serviceCode = "select * from " + this.dbName + ".tbl_services where "
                    + "correlatorId='" + tagName + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            iServiceCode = rst.getInt("serviceCode");
        }catch(SQLException e){
            System.err.println("getTotalSubUser - DBHandler_Native  - 01: " + e);
        }
        
        close();
        open();
        
        String query = "select count(*) from " + this.dbName + ".tbl_service_users where serviceCode='" + iServiceCode + "'";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans += rst.getInt(1);
        }catch(SQLException e){
            System.err.println("getTotalSubUser - DBHandler_Native - 03 : " + e);
        }
        
        // return sum of results from two tables above
        return ans;
    }
    
    public int getNewUnSubUser(String date, String tagName){
        int ans = 0;
        int iServiceCode = 0;
        
        // get service code from tbl_services
        try{
            String query_serviceCode = "select * from " + this.dbName + ".tbl_services where "
                    + "correlatorId='" + tagName + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            iServiceCode = rst.getInt("serviceCode");
        }catch(SQLException e){
            System.err.println("getNewUnSubUser - DBHandler_Native  - 01 : " + e);
        }
        
        /*
        String query = "select count(*) from " + this.dbName + ".tbl_service_users "
                + "where serviceCode = '" + iServiceCode + "' and unRegDate like '%" + date + "%'";
        */
        
        close();
        open();
        
        String query = "select count(*) from " + this.dbName + ".tbl_service_users where"
                + " serviceCode = '" + iServiceCode + "' and unRegDate like '%" + date + "%'";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("getNewUnSubUser - DBHandler_Native - 02 : " + e);
        }
        return ans;
    }
    
    public int getNewSubUser(String date, String tagName){
        int ans = 0;
        int iServiceCode = 0;
        
        // get service code from tbl_services
        try{
            String query_serviceCode = "select * from " + this.dbName + ".tbl_services where "
                    + "correlatorId='" + tagName + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            iServiceCode = rst.getInt("serviceCode");
        }catch(SQLException e){
            System.err.println("getNewSubUser - DBHandler_Native  - 01 : " + e);
        }
        
        close();
        open();
        
        String query = "select count(*) from " + this.dbName + ".tbl_service_users "
                + "where serviceCode = '" + iServiceCode + "' and regDate like '%" + date + "%'";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("getNewSubUser - DBHandler_Native - 02 : " + e);
        }
        return ans;
    }
    
    public ReportTbl getReportObjectForSpecificDate(String date, String reportTableName){
        ReportTbl report = new ReportTbl();
        try{
            String query = "SELECT * FROM `"+this.dbName+"`.`" 
                    + reportTableName 
                    + "` WHERE `datePayment` like '%" + date + "%'";
            System.out.println("[*] INFO - Query is >>> " + query);
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                int customerNumber = rst.getInt("customerNumber");
                int cash = rst.getInt("cash");
                String strLstPrice = rst.getString("priceList");
                String strLstSuccess = rst.getString("successList");
                String strLstFailed = rst.getString("failedList");
                String datePayment = rst.getString("datePayment");
                report.setCash(cash);
                report.setCustomerNumber(customerNumber);
                report.setDate(datePayment);
                report.setLstFailed(convertStrToList(strLstFailed));
                report.setLstPrice(convertStrToList(strLstPrice));
                report.setLstSuccess(convertStrToList(strLstSuccess));
            }
        }catch(SQLException e){
            System.err.println("SQL - 09 - Report : DBHandler_Native > " + e.getMessage());
        }
        return report;
    }
    
    private List<Integer> convertStrToList(String str){
        List<Integer> lst = new ArrayList<>();
        String strTemp = str.substring(0, str.length()-1);
        String[] strArray = strTemp.split(",");
        for(String s : strArray){
            lst.add(Integer.parseInt(s));
        }
        return lst;
    }
}
