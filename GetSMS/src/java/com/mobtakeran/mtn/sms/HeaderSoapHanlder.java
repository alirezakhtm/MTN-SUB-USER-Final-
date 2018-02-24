/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobtakeran.mtn.sms;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author Administrator
 */
public class HeaderSoapHanlder implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        Boolean outBoundProperty = (Boolean) context
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // if this is an incoming message from the client
        if (!outBoundProperty) {

            try {
                //   System.out.println("m1");
                SOAPHeader header = context.getMessage().getSOAPHeader();
                Iterator<?> headerElements = header.examineAllHeaderElements();
                //   System.out.println("m2");
                while (headerElements.hasNext()) {
                    //  System.out.println("m3");
                    SOAPHeaderElement headerElement = (SOAPHeaderElement) headerElements
                            .next();
                    //System.out.println("m4:"+headerElement.getElementName().getLocalName());
                    if (headerElement.getElementName().getLocalName()
                            .equals("NotifySOAPHeader")) {
                        SOAPHeaderElement notifySOAPHeader = headerElement;
                        Iterator<?> it2 = notifySOAPHeader.getChildElements();
                        while (it2.hasNext()) {
                            Node soapNode = (Node) it2.next();
                            if (soapNode instanceof SOAPElement) {
                                SOAPElement element = (SOAPElement) soapNode;
                                QName elementQname = element.getElementQName();
                                if (elementQname.getLocalPart().equals("serviceId")) {
                                    if (element.getTextContent().equals("98012000012190") == false) {
                                        break;
                                    }
                                }
                                if (elementQname.getLocalPart().equals("traceUniqueID")) {
                                    //System.out.println("m4.5:"+element.getTextContent());
                                    context.put("traceUniqueID", element.getTextContent());
                                    context.setScope("traceUniqueID", Scope.APPLICATION);
                                }

                            }

                        }
                    }

                }
                System.out.println("m5");

            } catch (SOAPException e) {
                System.out.println("error1:" + e.getMessage());
            }

        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }

}
