/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobtakeran.mtn.sendsms;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class MtnHeaderSoapHandler implements SOAPHandler<SOAPMessageContext>{
    private String msisdn;
    public MtnHeaderSoapHandler(String mobileNumber){
        msisdn = mobileNumber;
    }
   @Override
    public boolean handleMessage(SOAPMessageContext context) {

	
        String spID=
                "980110004319"
                
               ,serviceID="98012000012190";//98012000012089";//98012000012190"; //this service ID is for sending SMS (without linkid)
        

	Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

	//if this is a request, true for outbound messages, false for inbound
	if(isRequest){

	try{
	    SOAPMessage soapMsg = context.getMessage();
            SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
            SOAPHeader soapHeader = soapEnv.getHeader();

            //if no header, add one
            if (soapHeader == null){
            	soapHeader = soapEnv.addHeader();
            }

            
            //add a soap header, 
            QName qname = new QName("http://www.huawei.com.cn/schema/common/v2_1", "RequestSOAPHeader");
            SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);
            
            SOAPElement soapSPID = soapHeaderElement.addChildElement("spId");
            soapSPID.addTextNode(spID);
           
            
            SOAPElement soapServiceID = soapHeaderElement.addChildElement("serviceId");
            soapServiceID.addTextNode(serviceID);
           
            SOAPElement soapOA = soapHeaderElement.addChildElement("OA");
            soapOA.addTextNode("tel:"+msisdn);
            
            SOAPElement soapFA = soapHeaderElement.addChildElement("FA");
            soapFA.addTextNode("tel:"+msisdn);
            
            soapMsg.saveChanges();

            //tracking
            soapMsg.writeTo(System.out);


	   }catch(SOAPException e){
		System.err.println(e);
	   }catch(IOException e){
		System.err.println(e);
	   }

         }
        else{
            System.out.println("soap response is end by morteza");
        }

	   //continue other handler chain
	   return true;
    }

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("Client : handleFault()......");
		return true;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("Client : close()......");
	}

	@Override
	public Set<QName> getHeaders() {
		System.out.println("Client : getHeaders()......");
		return null;
	}

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    } 

}