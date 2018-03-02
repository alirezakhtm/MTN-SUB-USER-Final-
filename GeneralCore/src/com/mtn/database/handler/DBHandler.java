/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.database.handler;

import com.mtn.database.object.MOMTLogObj;
import com.mtn.database.object.ServiceUserObj;
import com.mtn.database.object.ServicesObj;
import com.mtn.json.handler.ConfigHandler;
import com.mtn.sms.object.DeliveryStatus;
import com.mtn.sms.object.SMS;
import com.mtn.sms.object.SMSQueueObj;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alirezakhtm
 */
public class DBHandler {
    private String dbUsername = "root";
    private String dbPassword = "";
    private String dbName = "";
    private String dbUrl = "";
    private String activeMQUsername = "";
    private String activeMQPassword = "";
    private Connection conn = null;
    private Statement stm = null;
    private ResultSet rst = null;
    
    public DBHandler() {
        ConfigHandler configHandler = new ConfigHandler();
        this.dbName = configHandler.getDbName();
        this.dbPassword = configHandler.getDbPassword();
        this.dbUsername = configHandler.getDbUsername();
        this.activeMQUsername = configHandler.getActiveMQUsername();
        this.activeMQPassword = configHandler.getActiveMQPassword();
        this.dbUrl = "jdbc:mysql://localhost:3306/"
                    + dbName
                    + "?useSSL=false&useUnicode=true&characterEncoding=utf-8";
    }

    public String getActiveMQUsername() {
        return activeMQUsername;
    }

    public String getActiveMQPassword() {
        return activeMQPassword;
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
    /***************************************************************************
     *                             Data Parsing                                *
     ***************************************************************************/
    /**
     * @return 
     */
    public List<ServicesObj> getAllServices(){
        List<ServicesObj> lst = new ArrayList<>();
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_services`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ServicesObj obj = new ServicesObj(
                        rst.getInt("serviceCode"),
                        rst.getString("serviceName"),
                        rst.getString("spid"),
                        rst.getString("serviceId"),
                        rst.getString("welcomeMT"),
                        rst.getString("cancelMT"),
                        rst.getInt("price"),
                        rst.getString("gategory"),
                        rst.getString("correlatorId"),
                        rst.getString("numberPhone"));
                lst.add(obj);
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getAllServices : " + e);
        }
        return lst;
    }
    
    /**
     * save ServiceUserObj on tbl_service_users
     * @param suo 
     */
    public void saveServiceUser(ServiceUserObj suo){
        try{
            String query = "INSERT INTO `mobtakerandb`.`tbl_service_users`\n" +
                            "(" +
                            "`serviceCode`,\n" +
                            "`msisdn`,\n" +
                            "`regDate`,\n" +
                            "`unRegDate`,\n" +
                            "`status`)\n" +
                            "VALUES\n" +
                            "(" +
                            "'"+suo.getServiceCode()+"',\n" +
                            "'"+suo.getMsisdn()+"',\n" +
                            "'"+suo.getRegDate()+"',\n" +
                            "'"+suo.getUnRegDate()+"',\n" +
                            "'"+suo.getStatus()+"')";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DBHandler - getAllServices : " + e.getSQLState());
        }
    }
    
    /**
     * save Mo/MT on tbl_mo_mt_log
     * @param momtlo 
     */
    public void saveMOMTLog(MOMTLogObj momtlo){
        try{
            String query = "INSERT INTO `mobtakerandb`.`tbl_mo_mt_log`\n" +
                            "(" +
                            "`serviceCode`,\n" +
                            "`msisdn`,\n" +
                            "`mo`,\n" +
                            "`mt`,\n" +
                            "`registerationDate`)\n" +
                            "VALUES\n" +
                            "(" +
                            "'"+momtlo.getServiceCode()+"',\n" +
                            "'"+momtlo.getMsisdn()+"',\n" +
                            "'"+momtlo.getMo()+"',\n" +
                            "'"+momtlo.getMt()+"',\n" +
                            "'"+momtlo.getRegisterationDate()+"')";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DBHandler - saveMOMTLog : " + e.getSQLState());
        }
    }
    
    /**
     * if exist user, this function return id of row else return -1
     * @param msisdn telephone of user
     * @return id of user if exist else return -1
     */
    public int isSubUserExist(String msisdn){
        int ans = -1;
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_service_users`"
                    + " WHERE `msisdn` = '" + msisdn + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getInt("indx");
            }
        }catch(SQLException e){
            System.err.println("DBHandler - isSubUserExist : " + e.getSQLState());
        }
        return ans;
    }
    
    /**
     * this function update info on tbl_service_users
     * @param indx id of table row
     * @param suo update of user.
     */
    public void updateSubUser(int indx, ServiceUserObj suo){
        try{
            String subQ = suo.getRegDate() == null 
                    ? "`unRegDate` = '"+suo.getUnRegDate()+"',\n" 
                    : "`regDate` = '"+suo.getRegDate()+"',\n";
            String query = "UPDATE `mobtakerandb`.`tbl_service_users`\n" +
                            "SET\n" +
                            "`serviceCode` = '"+suo.getServiceCode()+"',\n" +
                            "`msisdn` = '"+suo.getMsisdn()+"',\n" +
                            subQ +
                            "`status` = '"+suo.getStatus()+"'\n" +
                            "WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            stm.executeUpdate(query);
        }catch(SQLException e){
            System.err.println("DBHandler - updateSubUser : " + e.getSQLState());
        }
    }
    /***************************************************************************
     *                              SMS Sender                                 *
     ***************************************************************************/
    public ServicesObj getService(SMSQueueObj smsqo){
        ServicesObj obj = null;
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_services` WHERE `serviceCode` = '"+smsqo.getServiceCode()+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                obj = new ServicesObj(
                        rst.getInt("serviceCode"),
                        rst.getString("serviceName"),
                        rst.getString("spid"),
                        rst.getString("serviceId"),
                        rst.getString("welcomeMT"),
                        rst.getString("cancelMT"),
                        rst.getInt("price"),
                        rst.getString("gategory"),
                        rst.getString("correlatorId"),
                        rst.getString("numberPhone"));
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getService : " + e.getSQLState());
        }
        return obj;
    }
    
    /***************************************************************************
     *                      Costumer Table - tbl_Costumer                      *
     ***************************************************************************/
    public Map<String,String> getListOfCostumerService(){
        Map<String, String> mapAns = new HashMap<>();
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_consumer`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                mapAns.put(rst.getString("serviceId"), rst.getString("indx"));
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getListOfCostumerService : " + e);
        }
        return mapAns;
    }
    
    public Map<String,String> getListOfCostumerServiceAccordingToCorrelator(){
        Map<String, String> mapAns = new HashMap<>();
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_consumer`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                mapAns.put(rst.getString("correlator"), rst.getString("indx"));
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getListOfCostumerServiceAccordingToCorrelator : " + e);
        }
        return mapAns;
    }
    
    public String getCostumerDataSyncURL(int indx){
        String ans = "";
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_consumer` WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("URLDataSync");
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getCostumerDataSyncURL : " + e);
        }
        return ans;
    }
    
    public String getCostumerDeliveryURL(int indx){
        String ans = "";
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_consumer` WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("URLDeliveryReceived");
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getCostumerDeliveryURL : " + e);
        }
        return ans;
    }
    
    public String getCostumerSMSURL(int indx){
        String ans = "";
        try{
            String query = "SELECT * FROM `mobtakerandb`.`tbl_consumer` WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("URLSMSReceived");
            }
        }catch(SQLException e){
            System.err.println("DBHandler - getCostumerSMSURL : " + e);
        }
        return ans;
    }
    
    /***************************************************************************
     *              Costumer Log Table - tbl_consumer_call_log                 *
     ***************************************************************************/
    /**
     * @param indx_consumer 
     * @param url_call 
     * @param url_description 
     */
    public void saveConsumerCallLog(int indx_consumer, String url_call, String url_description){
        try{
            String query = "INSERT INTO `mobtakerandb`.`tbl_consumer_call_log`\n" +
                    "(`indx_consumer`,\n" +
                    "`callDate`,\n" +
                    "`URLCall`,\n" +
                    "`URLdescription`)\n" +
                    "VALUES\n" +
                    "('"+indx_consumer+"',\n" +
                    "'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"',\n" +
                    "'"+url_call+"',\n" +
                    "'"+url_description+"');";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DBHandler - saveConsumerCallLog : " + e);
        }
    }
}
