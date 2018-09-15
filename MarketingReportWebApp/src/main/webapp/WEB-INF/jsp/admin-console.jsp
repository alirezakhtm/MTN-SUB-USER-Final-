
<%@include file="commons/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">Service INFO &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/report" class="btn btn-primary">Refresh</a></div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Sub. Number</th>
                                <th>Un-Sub. Number</th>
                                <th>Service Name</th>
                                <th>Payment of Yesterday</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lstRows}" var="row">
                                <tr>
                                    <td>${row.date}</td>
                                    <td>${row.subNumber}</td>
                                    <td>${row.unSubNumber}</td>
                                    <td>${row.serviceName}</td>
                                    <td>${row.paymentYesterday}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">Service INFO</div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Service Id</th>
                                <th>Provider Id</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lstServices}" var="service">
                                <tr>
                                    <td>${service.serviceCode}</td>
                                    <td>${service.name}</td>
                                    <td>${service.serviceId}</td>
                                    <td>${service.spId}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">All Users</div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Password</th>
                                <th>Role</th>
                                <th>Access</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lstUsers}" var="user">
                            <tr>
                                <td>${user.password}</td>
                                <td>${user.role}</td>
                                <td>${user.value}</td>
                                <td><a href="/delete-user?password=${user.password}" class="btn btn-danger">Delete</a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer"><a href="/add-user" class="btn btn-success"><b>Add</b></a></div>
            </div>
        </div>
    </div>
</div>

<%@include file="commons/footer.jspf" %>


