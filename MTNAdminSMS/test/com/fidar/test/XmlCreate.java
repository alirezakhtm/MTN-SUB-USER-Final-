/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.test;

import com.fidar.xml.handler.Admin;
import com.fidar.xml.handler.Root;
import com.fidar.xml.handler.SendTime;
import com.fidar.xml.handler.Setting;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author alirzea
 */
public class XmlCreate {
    public static void main(String[] args) {
        Root root = new Root();
        Setting setting = new Setting();
        SendTime sendtime = new SendTime();
        sendtime.setHour("15");
        sendtime.setMinute("5");
        setting.setSendtime(sendtime);
        root.setSetting(setting);
        List<Admin> lst = new ArrayList<>();
        Admin admin1 = new Admin();
        admin1.setPhone("9016132473");
        admin1.setCorrelator("mobreal");
        admin1.setServiceCode("1");
        admin1.setReportTableName("tbl_report_maroufsho");
        lst.add(admin1);
        Admin admin2 = new Admin();
        admin2.setPhone("9016132473");
        admin2.setCorrelator("mobdigidaro");
        admin2.setServiceCode("2");
        admin2.setReportTableName("tbl_report");
        lst.add(admin2);
        root.setLstAdmin(lst);
        try{
            Marshaller marshaller = (JAXBContext.newInstance(Root.class)).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(root, new File("config.xml"));
        }catch(JAXBException e){
            System.err.println("[*] ERROR  : XmlCreate/main : " + e);
        }
    }
}
