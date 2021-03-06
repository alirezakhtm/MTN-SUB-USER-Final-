
package com.digidaroo.smsWS;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ServiceException", targetNamespace = "http://www.csapi.org/schema/parlayx/common/v2_1")
public class ServiceExceptionException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ServiceException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public ServiceExceptionException(String message, ServiceException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public ServiceExceptionException(String message, ServiceException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.digidaroo.smsWS.ServiceException
     */
    public ServiceException getFaultInfo() {
        return faultInfo;
    }

}
