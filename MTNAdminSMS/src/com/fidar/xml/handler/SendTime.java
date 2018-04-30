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
@XmlRootElement(name = "sendtime")
public class SendTime {
    private String hour, minute;

    public String getHour() {
        return hour;
    }

    @XmlElement(name = "hour")
    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    @XmlElement(name = "minute")
    public void setMinute(String minute) {
        this.minute = minute;
    }
    
}
