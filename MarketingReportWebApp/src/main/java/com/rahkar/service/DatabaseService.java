/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.service;

import com.rahkar.model.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alirzea
 */
@Component
public class DatabaseService {
    
    @Autowired
    private XMLService xmlService;
    
    
    private Connection open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + xmlService.getConfigFileData().getDatabase().getName() 
                            + "?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&characterSetResults=UTF-8",
                    xmlService.getConfigFileData().getDatabase().getUser(),
                    xmlService.getConfigFileData().getDatabase().getPass());
            return connection;
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("DatabaseService/getTableModel - " + e);
            return null;
        }
    }
    
    public int getSubUserNum(int serviceCode, String date) {
        return this.getNumberParameterUser(serviceCode, date, "regDate");
    }
    
    public int getUnSubUserNum(int serviceCode, String date) {
        return this.getNumberParameterUser(serviceCode, date, "unRegDate");
    }
    
    public String getServiceName(int serviceCode){
        String ans = "";
        Connection conn = this.open();
        try{    
            String query = "select `serviceName` from `tbl_services` where `serviceCode` = '" + serviceCode + "'";
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getString(1);
        }catch(SQLException e){
            System.err.println("DatabaseService/getServiceName - " + e);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                System.err.println("DatabaseService/getServiceName - 02 - " + ex);
            }
        }
        return ans;
    }
    
    private int getNumberParameterUser(int serviceCode, String date, String dateName){
        int ans = 0;
        Connection conn = this.open();
        try{    
            String query = "select count(*) from `tbl_service_users` where `"+dateName+"` like '%" + date + "%' and `serviceCode` = '" + serviceCode + "'";
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("DatabaseService/getNumberParameterUser/" + dateName + " - " + e);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                System.err.println("DatabaseService/getNumberParameterUser/" + dateName + " - 02 - " + ex);
            }
        }
        return ans;
    }
    
    public List<Service> getAllServices(){
        List<Service> lstService = new ArrayList<>();
        Connection conn = this.open();
        try{
            String query = "select * from `tbl_services`";
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(query);
            while(rst.next()){
                Service service = new Service(
                        rst.getInt("serviceCode"),
                        rst.getString("serviceName"),
                        rst.getString("serviceId"),
                        rst.getString("spid"),
                        rst.getString("welcomeMT")
                );
                lstService.add(service);
            }
        }catch(SQLException e){
            System.err.println("DatabaseService/getAllService - " + e);
        }finally{
            try{
                conn.close();
            }catch(SQLException e){
                System.err.println("DatabaseService/getAllService - 02 - " + e);
            }
        }
        return lstService;
    }

    public int getYesterdayPayment(int serviceCode, String date) {
        int ans = 0;
        Connection con = this.open();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNow = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateNow);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String yesterdayDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            System.out.println("[*] yesterdayDate >>> " + yesterdayDate);
//            String query = "select count(*) from `chargelogdaroo` where `serviceCode` = '" 
//                    + serviceCode + "' and `chargeDate` like '%" + yesterdayDate + "%' and `isSucess` = '1' and "
//                    + "`tel` in (select `msisdn` from `tbl_service_users` where `regDate` like '%" + date + "%' and `serviceCode` = '" + serviceCode + "')";
            String query = "select count(*) from `tbl_service_users` where `regDate` like '%" 
                    + yesterdayDate + "%' and `serviceCode` COLLATE latin1_general_ci = '" + serviceCode 
                    + "' and `msisdn` in (select `tel` from `chargelogdaroo` where `serviceCode` COLLATE latin1_general_ci = '"+serviceCode+"' and `chargeDate` like '%"+date+"%')";
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        } catch (ParseException | SQLException ex) {
            System.err.println("DatabaseService/getYesterdayPayment - " + ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println("DatabaseService/getYesterdayPayment - 02 - " + ex);
            }
        }
        return ans;
        
    }
    
}
