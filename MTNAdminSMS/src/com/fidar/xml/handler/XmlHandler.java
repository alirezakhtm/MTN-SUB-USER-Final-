/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.xml.handler;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author alirzea
 */
public class XmlHandler {
    private List<Admin> lstAdmin;
    private Setting setting;

    public XmlHandler() {
        try{
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Root root = (Root) unmarshaller.unmarshal(new File("config.xml"));
            lstAdmin = root.getLstAdmin();
            setting = root.getSetting();
        }catch(JAXBException e){
            System.err.println("[*] ERROR : XmlHandler/Constructor : " + e);
        }
    }
    
    public List<Admin> getLstAdmin() {
        return lstAdmin;
    }

    public void setLstAdmin(List<Admin> lstAdmin) {
        this.lstAdmin = lstAdmin;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
    
}
