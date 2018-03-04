package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.PrintWriter;

public final class IsActive_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Hello World!</h1>\n");
      out.write("         ");

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
            String qres = "SELECT content FROM mobtakerandb.log where msisdn like '%"+msisdn+"%'  order by indx desc limit 1;'";
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
        outer.print("OK");
        else
        outer.print("NOK");
            
	
	out.println("MSsidn="+msisdn+" activation is "+res);
        }
        
        
	
    } catch (Exception ex) {
	// TODO handle custom exceptions here
        out.println("error = "+ex.getMessage());
    }
    
      out.write("\n");
      out.write("    ");
      out.write("<hr/>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
