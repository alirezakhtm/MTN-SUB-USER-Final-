/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package badusersremover;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alireza
 */
public class BadUsersRemover {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //logic01();
        try{
            logic02();
        }catch(SQLException e){
            System.err.println("[*] ERROR main/logic02 >> " + e);
        }
    }
    
    public static void logic01(){
        DBHandler db = new DBHandler();
        // for service code 1
        Connection conn = db.open();
        List<String> lstMsisdn = db.getListMsisdn(conn, 1);
        db.close(conn);
        System.out.println("[*] length of msisdn for service code 1 is " + lstMsisdn.size());
        int count = 0;
        for(String msisdn : lstMsisdn){
            conn = db.open();
            boolean isBadUser = db.msisdnHaveError(conn, msisdn);
            db.close(conn);
            if(isBadUser){
//                conn = db.open();
//                db.removeMsisdnFromTable(conn, msisdn);
//                db.close(conn);
                System.out.println("<"+count+"> ["+msisdn+"] is BAD USER.");
                count++;
            }
            if(count == 11){
                break;
            }
        }
    }
    
    public static void logic02() throws SQLException{
        DBHandler db = new DBHandler();
        Connection conn = db.open();
        DataPackage dp = db.getMsisdnBadUser(0, conn);
        conn.close();
        int Count = 0;
        while(true){
            if(dp.getMsisdn().size() > 0){
                Count += dp.getMsisdn().size();
                conn = db.open();
                db.deleteMsisdn(conn, dp.getMsisdn());
                conn.close();

                conn = db.open();
                dp = db.getMsisdnBadUser(dp.getMaximumIndx(), conn);
                conn.close();
            }else{
                break;
            }
        }
        
        System.out.println("[*] Finish Proccess. " + Count + " record(s) removed.");
    }
    
}

class DBHandler {
    private static final String username = "root";
    private static final String password = "Mortez@M0btakeran";
    private static final String url = "jdbc:mysql://localhost:3306/mobtakerandb?"
            + "useUnicode=true&characterEncoding=utf-8";
    
    public Connection open(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url, username, password);
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("[*] Error [DBHandler/open] >> " + e);
        }
        return conn;
    }
    
    public void close(Connection conn){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("[*] Error [DBHandler/close] >> " + e);
        }
    }
    
    public List<String> getListMsisdn(Connection conn, int serviceCode){
        List<String> lst = new ArrayList<>();
        try{
            String query = "select `msisdn` from `mobtakerandb`.`tbl_service_users` "
                    + "where `status` = '1' and `serviceCode` = '"+serviceCode+"'";
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(query);
            while(rst.next()){
                lst.add(rst.getString(1));
            }
        }catch(SQLException e){
            System.err.println("[*] Error [DBHandler/getListMsisdn] >> " + e);
        }
        return lst;
    }
    
    public boolean msisdnHaveError(Connection conn, String msisdn) { 
        boolean ans = false;
        try{
            String query = "select count(*) from `mobtakerandb`.`chargelogdaroo`"
                    + " where `tel`='"+msisdn+"' and `failedMessage` like '%33%'";
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(query);
            ans = rst.getInt(1) > 0;
        }catch(SQLException e){
            System.err.println("[*] Error [DBHandler/msisdnHaveError] >> " + e);
        }
        return ans;
    }
    
    public void removeMsisdnFromTable(Connection conn, String msisdn){
        try{
            String query = "delete from `mobtakerandb`.`tbl_service_users` where `msisdn` = '"+msisdn+"'";
            Statement stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("[*] Error [DBHandler/removeMsisdnFromTable] >> " + e);
        }
    }
    
    public DataPackage getMsisdnBadUser(int indx, Connection conn){
        List<String> lstMsisdn = new ArrayList<>();
        int maxIndx = Integer.MIN_VALUE;
        DataPackage dp = new DataPackage();
        try{
            String query = "select distinct(tel), indx from mobtakerandb.chargelogdaroo where failedMessage like '%33%' and indx > '"+indx+"' limit 1000";
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(query);
            while(rst.next()){
                lstMsisdn.add(rst.getString(1));
                int indxCurrent = rst.getInt(2);
                maxIndx = (maxIndx < indxCurrent) ? indxCurrent : maxIndx;
            }
        }catch(SQLException e){
            System.err.println("");
        }
        dp.setMaximumIndx(maxIndx);
        dp.setMsisdn(lstMsisdn);
        return dp;
    }
    
    public void deleteMsisdn(Connection conn, List<String> lst){
        try{
            String query = "delete from `mobtakerandb`.`tbl_service_users` \n" + 
                    "where \n";
            for(String msisdn : lst){
                query += "concat('0',`msisdn`) = '" + msisdn + "' or \n";
            }
            query = query.substring(0, query.lastIndexOf("or"));
            Statement stm = conn.createStatement();
            stm.execute(query);
            System.out.println("[*] INFO - " + lst.size() + " number of msisdn removed from tables.");
        }catch(SQLException e){
            System.err.println("[*] Error [DBHandler/deleteMsisdn] >> " + e);
        }
    }
}

class DataPackage {
    private List<String> msisdn;
    private int maximumIndx;

    public List<String> getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(List<String> msisdn) {
        this.msisdn = msisdn;
    }

    public int getMaximumIndx() {
        return maximumIndx;
    }

    public void setMaximumIndx(int maximumIndx) {
        this.maximumIndx = maximumIndx;
    }
    
    
}