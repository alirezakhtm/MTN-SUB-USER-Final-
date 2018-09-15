/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.xml.model;

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
    private List<User> users;
    private Database database;

    public Root() {
    }

    public Root(List<User> users, Database database) {
        this.users = users;
        this.database = database;
    }

    public List<User> getUsers() {
        return users;
    }

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Database getDatabase() {
        return database;
    }

    @XmlElement(name = "database")
    public void setDatabase(Database database) {
        this.database = database;
    }
    
}
