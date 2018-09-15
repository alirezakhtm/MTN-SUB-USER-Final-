/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.xml.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alirzea
 */
@XmlRootElement(name = "database")
public class Database {
    private String user;
    private String pass;
    private String name;

    public Database() {
    }

    public Database(String user, String pass, String name) {
        this.user = user;
        this.pass = pass;
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    @XmlAttribute(name = "user")
    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    @XmlAttribute(name = "pass")
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }
    
}
