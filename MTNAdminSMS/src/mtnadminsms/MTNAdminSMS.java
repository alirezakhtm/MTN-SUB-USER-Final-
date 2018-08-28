/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtnadminsms;

import com.fidar.database.handler.DBHandler;
import com.fidar.xml.handler.Admin;
import com.fidar.xml.handler.XmlHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.sms.object.SMSQueueObj;
import com.processdigidaroo.database.object.ReportTbl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alirzea
 */
public class MTNAdminSMS {

    private Timer gardTimer = new Timer();
    private boolean bFelagStartLogic = false;
    private boolean bFelagMatchTime = false;
    
    private final XmlHandler xmlhandler = new XmlHandler();
    private final String activeMqUsername, activeMqPassword;
    
    public MTNAdminSMS() {
        Properties pro = new Properties();
        try{
            InputStream input = new FileInputStream(new File("config.properties"));
            pro.load(input);
        }catch(IOException e){
            System.out.println("[*] ERROR : MTNAdminSMS/Constructor : " + e);
        }
        activeMqUsername = pro.getProperty("activemq-username");
        activeMqPassword = pro.getProperty("activemq-password");
        deleteAdminsFromPriceList(xmlhandler.getLstAdmin());
        gardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Thread th_check_setting = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Calendar cal = Calendar.getInstance();
                        int iHour = Integer.parseInt(new SimpleDateFormat("HH").format(cal.getTime()));
                        int iMinute = Integer.parseInt(new SimpleDateFormat("mm").format(cal.getTime()));
                        int hourSetting = Integer.parseInt(xmlhandler.getSetting().getSendtime().getHour());
                        int minuteSetting = Integer.parseInt(xmlhandler.getSetting().getSendtime().getMinute());
                        if(!bFelagStartLogic){
                            if(!bFelagMatchTime){
                                if(hourSetting == iHour &&  minuteSetting == iMinute){
                                    bFelagMatchTime = true;
                                    // start application logic
                                    applicationLogic();
                                }
                            }
                        }
                        if(bFelagMatchTime && hourSetting == iHour && minuteSetting == iMinute + 1){
                            bFelagMatchTime = false;
                        }
                    }
                });
                th_check_setting.start();
            }
        }, 0, 1000);
    }
    
    private void applicationLogic(){
        bFelagStartLogic = true;
        List<Admin> lstAdmin = xmlhandler.getLstAdmin();
        for(Admin admin : lstAdmin){
            String reportMessage = getReport(admin.getTitle(), admin.getCorrelator(), admin.getReportTableName());
            SMSQueueObj smsObject = new SMSQueueObj(
                    admin.getPhone(),
                    reportMessage,
                    Integer.parseInt(admin.getServiceCode())
            );
            Gson gson = new GsonBuilder().create();
            String message = gson.toJson(smsObject, SMSQueueObj.class);
            System.err.println("[*] INFO - JSON >>> " + message);
            pushToQueue("SendSMS-Queue", message);
        }
        bFelagStartLogic = false;
    }
    
    private String getReport(String title, String tagName, String reportTableName){
        DBHandler db = new DBHandler();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String strCal = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        db.open();
        ReportTbl report = db.getReportObjectForSpecificDate(strCal, reportTableName);
        db.close();
        System.out.println("[*] Report >>> " + report.toString());
        String str = "Subject: " + title + " ";
        str += "Date:" + report.getDate() + " ";
        db.open();
        str += "NewSub:"+ db.getNewSubUser(strCal, tagName) +" ";
        str += "NewUnSub:" + db.getNewUnSubUser(strCal, tagName) + " ";
        str += "T.S:" + db.getTotalSubUser(tagName) + " ";
        str += "A.U:" + db.getActiveUserNumber(tagName) + " ";
        db.close();
        List<Integer> lstPrice = report.getLstPrice();
        List<Integer> lstSuccessful = report.getLstSuccess();
        List<Integer> lstFailed = report.getLstFailed();
        Map<Integer, Integer> mapPriceSuccess = new HashMap<>();
        Map<Integer, Integer> mapPriceFail = new HashMap<>();
        for(int i = 0; i < lstPrice.size(); i++){
            if(mapPriceSuccess.containsKey(lstPrice.get(i))){
                int num = mapPriceSuccess.get(lstPrice.get(i)) + lstSuccessful.get(i);
                mapPriceSuccess.put(lstPrice.get(i), num);
            }else{
                mapPriceSuccess.put(lstPrice.get(i), lstSuccessful.get(i));
            }

            if(mapPriceFail.containsKey(lstPrice.get(i))){
                int num = mapPriceFail.get(lstPrice.get(i)) + lstFailed.get(i);
                mapPriceFail.put(lstPrice.get(i), num);
            }else{
                mapPriceFail.put(lstPrice.get(i), lstFailed.get(i));
            }
        }
        for(int n : mapPriceSuccess.keySet()){
            str += "\n" + n + ":" + mapPriceSuccess.get(n);
        }
        return str;
    }
    
    private void pushToQueue(String QName, String message){
        ActiveMQConnectionFactory factory = new
            ActiveMQConnectionFactory(activeMqUsername, activeMqPassword, "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QName);//"SendSMS-Queue"
            MessageProducer producer = session.createProducer(destination);
            TextMessage msg = session.createTextMessage(message);
            producer.send(msg);
            producer.close();
            session.close();
            connection.stop();
            connection.close();
        }catch(JMSException e){
            System.err.println("[*] ERORR : MTNAdminSMS/pushToQueu : " + e);
        }
    }
    
    private void deleteAdminsFromPriceList(List<Admin> lst){
        DBHandler db = new DBHandler();
        for(Admin admin : lst){
            db.open();
            db.deleteAdminRecordFromTable(admin.getPhone(), Integer.parseInt(admin.getServiceCode()));
            db.close();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MTNAdminSMS adminSMS = new MTNAdminSMS();
    }
    
}

