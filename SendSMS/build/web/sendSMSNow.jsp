<%-- 
    Document   : sendSMSNow
    Created on : Sep 5, 2016, 5:23:38 AM
    Author     : Administrator
--%>

<%@page import="java.net.URLDecoder"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mobtakeran.mtn.smsservice.SimpleReference"%>
<%@page import="java.util.AbstractList"%>
<%@page import="com.mobtakeran.mtn.sendsms.HeaderHandlerResolver"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
      <hr/>
    <%
    try {
        String msisdn=null;
        msisdn = request.getParameter("msisdn");
        if(msisdn!=null){
        String message = request.getParameter("message");
        try{
        message = URLDecoder.decode(message);
        }catch(Exception exx){
          System.out.println("exx error SEND SMS error:"+exx.getMessage());
        }
        com.mobtakeran.mtn.smsservice.SendSmsService service = new com.mobtakeran.mtn.smsservice.SendSmsService();
        
        HeaderHandlerResolver resolver=new HeaderHandlerResolver(msisdn);
        service.setHandlerResolver(resolver);
        
	com.mobtakeran.mtn.smsservice.SendSms port = service.getSendSms();
       
	 // TODO initialize WS operation arguments here
	java.util.List<java.lang.String> addresses =new ArrayList<String>();
        addresses.add("tel:"+msisdn);

      
	java.lang.String senderName = "7707";
	com.mobtakeran.mtn.smsservice.ChargingInformation charging = null;
	
	com.mobtakeran.mtn.smsservice.SimpleReference receiptRequest =new SimpleReference();
        receiptRequest.setCorrelator("mobreal");
        receiptRequest.setEndpoint("http://10.130.158.140:8080/GetSMS/SmsNotificationService");
        receiptRequest.setInterfaceName("notifySmsDeliveryReceipt");
	java.lang.String encode = "";
	java.lang.Integer sourceport = Integer.valueOf(0);
	java.lang.Integer destinationport = Integer.valueOf(0);
	java.lang.Integer esmClass = Integer.valueOf(0);
	java.lang.Integer dataCoding = Integer.valueOf(0);
	// TODO process result here
         PrintWriter outer = response.getWriter();

        outer.print("OK");
	java.lang.String result = port.sendSms(addresses, senderName, charging, message, receiptRequest, encode, sourceport, destinationport, esmClass, dataCoding);
	out.println("Result = "+result);
        }
        else{
            PrintWriter outer = response.getWriter();
            outer.print("NOK");
        }
        
	
    } catch (Exception ex) {
	// TODO handle custom exceptions here
        out.println("error = "+ex.getMessage());
    }
    %>
    <%-- end web service invocation --%><hr/>

    </body>
</html>
