<%@include file="commons/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-10">
            <div class="panel panel-default">
                <div class="panel-heading">Report Table</div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Sub. Number</th>
                                <th>Un-Sub. Number</th>
                                <th>Service Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${trows}" var="rows">
                                <tr>
                                    <td>${rows.date}</td>
                                    <td>${rows.subNumber}</td>
                                    <td>${rows.unSubNumber}</td>
                                    <td>${rows.serviceName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>            
        </div>
        <div class="col-md-1"></div>
    </div>
</div>


<%@include file="commons/footer.jspf" %>
