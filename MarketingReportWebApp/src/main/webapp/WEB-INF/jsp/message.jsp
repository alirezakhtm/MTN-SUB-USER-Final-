<%@include file="commons/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel ${messageClass}">
                <div class="panel-heading">Message</div>
                <div class="panel-body">${message}</div>
                <div class="panel-footer">
                    <a class="btn btn-primary" href="/report">Return</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="commons/footer.jspf" %>
