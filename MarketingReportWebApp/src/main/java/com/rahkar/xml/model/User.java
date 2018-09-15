/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.xml.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author alirzea
 */
@XmlRootElement(name = "user")
public class User {
    private String role;
    private String password;
    private String value;

    public User() {
    }

    public User(String role, String password, String value) {
        this.role = role;
        this.password = password;
        this.value = value;
    }

    public String getRole() {
        return role;
    }

    @XmlAttribute(name = "role")
    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    @XmlAttribute(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }
    
}
