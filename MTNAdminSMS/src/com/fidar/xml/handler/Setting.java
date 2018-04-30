/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.xml.handler;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alirzea
 */
@XmlRootElement(name = "setting")
public class Setting {
    private SendTime sendtime;

    public SendTime getSendtime() {
        return sendtime;
    }

    @XmlElement(name = "sendtime")
    public void setSendtime(SendTime sendtime) {
        this.sendtime = sendtime;
    }
}
