<%-- 
    Document   : user
    Created on : Feb 11, 2018, 5:22:44 PM
    Author     : alireza
--%>

<%@page import="com.fidar.forms.object.ServiceUserReportObj"%>
<%@page import="java.util.List"%>
<%@page import="com.fidar.database.DBHandler"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% DBHandler db = new DBHandler(); %>
<!DOCTYPE html>
<div class="card mb-3">
    <div class="card-header">
      <i class="fa fa-table"></i> Revenue Report</div>
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
          <thead>
            <tr>
                <th>Service Name</th>
                <th>Customer NO</th>
            </tr>
          </thead>
          <tbody>
              <%
                  try{
                      db.open();
                      List<ServiceUserReportObj> lst = db.getServiceUser();
                      db.close();
                      for(ServiceUserReportObj suro : lst){
                          out.println(
                                        "<tr>"
                                            + "<td>"+suro.getServiceName()+"</td>"
                                            + "<td>"+suro.getUserNumber()+"</td>"
                                        + "</tr>");
                      }
                  }catch(Exception e){
                      
                  }
              %>
            
          </tbody>
        </table>
      </div>
    </div>
    <div class="card-footer small text-muted">
        <%
            String strDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:dd").format(Calendar.getInstance().getTime());
            out.println(strDate);
        %>
    </div>
</div>
