<%-- 
    Document   : IsActive
    Created on : Oct 25, 2016, 5:30:56 PM
    Author     : Administrator
--%>


<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   
    <body>
        <h1>Hello World!</h1>
         <%
    try {
        boolean res=false;
        String msisdn=null;
        msisdn = request.getParameter("msisdn");
        if(msisdn!=null){
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
        }
        Connection connection = null;
        java.sql.Statement st = null;
         try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobtakerandb?useUnicode=true&characterEncoding=UTF-8", "root", "Mortez@M0btakeran");

            //String qres = "SELECT content FROM mobtakerandb.log where msisdn like '%9352541642%'  order by indx desc limit 1;'";
            String qres = "SELECT content FROM mobtakerandb.log where msisdn like '%"+msisdn+"%'  order by indx desc limit 1;";
           //SELECT msisdn FROM mobtakerandb.log  where sendtime like '2016-09-21%' 

            st = connection.createStatement();
            java.sql.ResultSet rs = st.executeQuery(qres);

            

            while (rs.next()) {
                if(rs.getString(1).equals("L") || rs.getString(1).equals("l"))
                    res=false;
                else
                    res=true;
                

            }

        } catch (SQLException e) {
            System.out.println("GetAllServices:Connection Failed! Check output console" + e.getMessage());

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (connection != null) {
                    connection.close();
                }
                System.out.println("GetAllServices: Connection closed !!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
         PrintWriter outer = response.getWriter();
        if(res==true)
        outer.print("numberok");
        else
        outer.print("numbernok");
            
	
	out.println("MSsidn="+msisdn+" activation is "+res);
        }
        
        
	
    } catch (Exception ex) {
	// TODO handle custom exceptions here
        out.println("error = "+ex.getMessage());
    }
    %>
    <%-- end web service invocation --%><hr/>

    </body>
</html>
