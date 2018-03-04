/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.database.handler;

import com.mtn.json.handler.ConfigHandler;
import com.mtn.sms.object.DeliveryStatus;
import com.mtn.sms.object.SMS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author alirezakhtm
 */
public class DBHandler {
    private String dbUsername = "root";
    private String dbPassword = "";
    private String dbName = "";
    private String dbUrl = "";
    private Connection conn = null;
    private Statement stm = null;
    private ResultSet rst = null;
    
    public DBHandler() {
        ConfigHandler configHandler = new ConfigHandler();
        this.dbName = configHandler.getDbName();
        this.dbPassword = configHandler.getDbPassword();
        this.dbUsername = configHandler.getDbUsername();
        this.dbUrl = "jdbc:mysql://localhost:3306/"
                    + dbName
                    + "?useSSL=false&useUnicode=true&characterEncoding=utf-8";
    }
    
    public void open(){
        try{
            //if(conn.isClosed()){
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            //}else{
                //close();
                //open();
            //}
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("DBHandler - open : " + e.getMessage());
        }
    }
    
    public void close(){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("DBHandler - close : " + e.getMessage());
        }
    }
    
    /**************************************************************************
     *                          Save SMS Message                              *
     **************************************************************************/
    public void saveSMSOnLogTbl(SMS sms){
        try{
            String q = "INSERT INTO `mobtakerandb`.`log`\n" +
                        "(`msisdn`,\n" +
                        "`shortcode`,\n" +
                        "`sendtime`,\n" +
                        "`content`,\n" +
                        "`correlator`)\n" +
                        "VALUES\n" +
                        "('" + sms.getSenderAddress() + "',\n" +
                        "'" + sms.getSMSServiceActivationNumber() + "',\n" +
                        "'" + sms.getDate() + "',\n" +
                        "'" + sms.getMessage() + "',\n" +
                        "'" + sms.getCorrelator() + "')";
            stm = conn.createStatement();
            stm.execute(q);
        }catch(SQLException e){
            System.err.println("DBHandler - saveSMSOnLogTbl : " + e.getMessage());
        }
    }
    
    /***************************************************************************
     *                           Save Delivery Message                         *
     ***************************************************************************/
    public void saveSMSDelivery(DeliveryStatus ds){
        try{
            String q = "INSERT INTO `mobtakerandb`.`sendsms_deliverylog` "
                    + "(`tel`,"
                    + "`deliverystatus`,"
                    + "`deliveryDate`,"
                    + "`deliveryTime`,"
                    + "`traceUniqueIDHeaderInfo`) "
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, ds.getAddress());
            ps.setString(2, ds.getValueOfDeliveryStatus());
            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            ps.setString(4, new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
            ps.setString(5, ds.getTraceUniqueID() == null ? "" : ds.getTraceUniqueID());
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("DBHandler - saveSMSDelivery : " + e.getMessage());
        }
    }
}
