<%@page import="com.fidar.forms.object.RevenueObj"%>
<%@page import="java.util.List"%>
<%@page import="com.fidar.database.DBHandler"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    try{
        String strUsername = (String)session.getAttribute("username");
        if(strUsername.equals("END")){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        }
    }catch(Exception e){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
    
    DBHandler db = new DBHandler();
%>


<div class="card mb-3">
    <div class="card-header">
        <i class="fa fa-gears"></i> Setting
    </div>
    <div class="card-body">
        <form method="post" action="GenerateRevenueReport">
         <div class="row">
            <div class="col-6">
                <div class="form-group">
                  <label for="sel1">Select Service:</label>
                  <select class="form-control" id="sel1" name="service_select">
                      <%
                          db.open();
                          List<String> lst = db.getAllServiceName();
                          db.close();
                          for(String str : lst){
                              out.println("<option>"+str+"</option>");
                          }
                      %>
                  </select>
                </div> 
            </div>
            <div class="col-6">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <label>From Date:</label>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                <div class="form-group">
                                  <label for="sel1">Yaer</label>
                                  <select class="form-control" id="sel1" name="from_y">
                                    <option>2018</option>
                                  </select>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                  <label for="sel1">Month</label>
                                  <select class="form-control" id="sel1" name="from_m">
                                    <%
                                        for(int i = 1; i < 13; i++){
                                            out.println("<option>"+i+"</option>");
                                        }
                                    %>
                                  </select>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                  <label for="sel1">Day</label>
                                  <select class="form-control" id="sel1" name="from_d">
                                    <%
                                        for(int i = 1; i < 31; i++){
                                            out.println("<option>"+i+"</option>");
                                        }
                                    %>
                                  </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <label>To Date:</label>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                <div class="form-group">
                                  <label for="sel1">Year</label>
                                  <select class="form-control" id="sel1" name="to_y">
                                    <option>2018</option>
                                  </select>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                  <label for="sel1">Month</label>
                                  <select class="form-control" id="sel1" name="to_m">
                                    <%
                                        for(int i = 1; i < 13; i++){
                                            out.println("<option>"+i+"</option>");
                                        }
                                    %>
                                  </select>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                  <label for="sel1">Day</label>
                                  <select class="form-control" id="sel1" name="to_d">
                                    <%
                                        for(int i = 1; i < 31; i++){
                                            out.println("<option>"+i+"</option>");
                                        }
                                    %>
                                  </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <input type="submit" name="btnset" value="Generate Report" class="btn btn-primary btn-large"/>
        </div>
        </form>
    </div>
    <div class="card-footer small text-muted">
        
    </div>
</div>
                                  
                                  
<div class="card mb-3">
    <div class="card-header">
      <i class="fa fa-table"></i> Revenue Report</div>
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
          <thead>
            <tr>
                <th>Service Name</th>
                <th>Date</th>
                <th>Customer NO</th>
                <th>Revenue</th>
            </tr>
          </thead>
          <tbody>
              <%
                try{
                    List<RevenueObj> lstReport = (List<RevenueObj>)request.getAttribute("lstRevinue");
                    for(RevenueObj obj : lstReport){
                        out.println("<tr>" +
                                        "<td>"+obj.getServiceName()+"</td>" +
                                        "<td>"+obj.getDate()+"</td>" +
                                        "<td>"+obj.getCustomerNumber()+"</td>" +
                                        "<td>"+obj.getPrice()+"</td>" +
                                    "</tr>");
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