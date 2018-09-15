<%@include file="commons/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <div class="panel panel-success">
                <div class="panel-heading">Define New User</div>
                <div class="panel-body">
                    <form class="form" method="post">
                        <div class="form-group">
                            <label for="ServiceName">Service Name</label>
                            <select class="form-control" id="ServiceName" name="serviceCode">
                                <c:forEach items="${lstServices}" var="service">
                                    <option value="${service.serviceCode}">${service.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="text" id="password" name="password" class="form-control">
                        </div>
                        <input class="form-control btn btn-success" type="submit" value="Save">
                    </form>
                </div>
                <div class="panel-footer"></div>
            </div>
            
        </div>
        <div class="col-md-4"></div>
    </div>
</div>


<%@include file="commons/footer.jspf" %>
