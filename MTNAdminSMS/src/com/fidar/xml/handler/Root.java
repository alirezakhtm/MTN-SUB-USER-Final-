/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.xml.handler;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alirzea
 */
@XmlRootElement(name = "root")
public class Root {
    private List<Admin> lstAdmin;
    private Setting setting;

    public List<Admin> getLstAdmin() {
        return lstAdmin;
    }

    @XmlElement(name = "admin")
    @XmlElementWrapper(name = "admins")
    public void setLstAdmin(List<Admin> lstAdmin) {
        this.lstAdmin = lstAdmin;
    }

    public Setting getSetting() {
        return setting;
    }

    @XmlElement(name = "setting")
    public void setSetting(Setting setting) {
        this.setting = setting;
    }
    
}
