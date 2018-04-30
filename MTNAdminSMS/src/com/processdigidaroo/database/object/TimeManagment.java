/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.processdigidaroo.database.object;

/**
 *
 * @author alirezakhtm
 */
public class TimeManagment {
    private int indx;
    private String dayOfWeek, StartTime, StopTime;

    public TimeManagment() {
    }

    public TimeManagment(String dayOfWeek, String StartTime, String StopTime) {
        this.dayOfWeek = dayOfWeek;
        this.StartTime = StartTime;
        this.StopTime = StopTime;
    }
    
    

    public int getIndx() {
        return indx;
    }

    public void setIndx(int indx) {
        this.indx = indx;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getStopTime() {
        return StopTime;
    }

    public void setStopTime(String StopTime) {
        this.StopTime = StopTime;
    }
    
}
