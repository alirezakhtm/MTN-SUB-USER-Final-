
<%@include file="commons/header.jspf" %>


<div class="container">
    
    <div class="row">
        
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">Login Form</div>
                <div class="panel-body">
                    <form class = "form" method="post">
                        <div class="form-group">
                            <label for="passtxt">Password</label>
                            <input type="password" name="password" id="passtxt" required="required" class="form-control"/>
                        </div>
                        <input type="submit" value="Login" class="btn btn-primary"/>
                    </form>
                </div>
            </div>
            
        </div>
        <div class="col-md-3"></div>
    </div>
    
</div>



<%@include file="commons/footer.jspf" %>




