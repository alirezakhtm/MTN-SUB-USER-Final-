/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidartech.setting;

/**
 *
 * @author alirezakhtm
 */
public class Setting {
    private String delay;

    public Setting() {
        
    }
    
    public Setting(String delay) {
        this.delay = delay;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
