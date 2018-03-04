/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtn.sms.handler;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
 
/**
 *
 * @author www.javadb.com
 */
public class MTNHeaderHandlerResolver implements HandlerResolver {
    private String msisdn, spID, serviceID;
    public MTNHeaderHandlerResolver(String mobileNumber, String spID, String serviceID){
        this.msisdn=mobileNumber;
        this.serviceID = serviceID;
        this.spID = spID;
    }
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> handlerChain = new ArrayList<Handler>();
        MTNHeaderSoapHandler headerHandler = new MTNHeaderSoapHandler(msisdn, spID, serviceID);
        handlerChain.add(headerHandler);
        return handlerChain;
   }
}