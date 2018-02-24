
package com.mobtakeran.mtn.smsservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "PolicyException", targetNamespace = "http://www.csapi.org/schema/parlayx/common/v2_1")
public class PolicyException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private PolicyException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public PolicyException_Exception(String message, PolicyException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public PolicyException_Exception(String message, PolicyException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.mobtakeran.mtn.smsservice.PolicyException
     */
    public PolicyException getFaultInfo() {
        return faultInfo;
    }

}
