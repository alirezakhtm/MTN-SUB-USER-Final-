/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobtakeran.mtn.sendsms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
 
/**
 *
 * @author www.javadb.com
 */
public class HeaderHandlerResolver implements HandlerResolver {
    String msisdn;
    public HeaderHandlerResolver(String mobileNumber){
        msisdn=mobileNumber;
    }
public List<Handler> getHandlerChain(PortInfo portInfo) {
      List<Handler> handlerChain = new ArrayList<Handler>();
      
      MtnHeaderSoapHandler headerHandler = new MtnHeaderSoapHandler(msisdn);
      
      handlerChain.add(headerHandler);
 
      return handlerChain;
   }
}