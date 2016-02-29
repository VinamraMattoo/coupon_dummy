<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <link rel="stylesheet" href="css/footer.css">

    <link rel="shortcut icon" type="image/png" href="./images/newlogo.png"/>


    <!-- jQuery -->
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>

</head>
<body>
<div class="wrapper container">
    <br> <br> <br> <br>

    <div class="row">
        <h1 align="center">Portea Sms Management System</h1>
        <br>

        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">

                <div class="panel-heading">
                    <h3 class="panel-title">Please sign in</h3>
                </div>
                <div class="row">
                    <div class="col-md-12 col-md-offset-1">
                        <div class="panel-heading">

                            <%--error status --%>
                            <h3 id="status" style="color:red;"
                                class="panel-title"><% if (request.getAttribute("ErrorMessage") != null) {
                                out.println(request.getAttribute("ErrorMessage"));
                            }
                                ; %></h3>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <form id=identicalForm accept-charset="UTF-8" role="form" action="login" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <input id="username" class="form-control"
                                       placeholder="Username" name="username" type="text" required>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" id="psswd"
                                       name="password" type="password" value="" required>
                            </div>

                            <input id="loginButton" class="btn btn-lg btn-success btn-block"
                                   type="submit" name="usr" value="Login">
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="footer">
    <div class="container">
        <div class="bottom-footer">
            <div class="col-md-5" style="padding-left: 65px;">Copyright (c) 2015 Portea Medical</div>
            <div class="col-md-7">
                <ui class="footer-nav col-md-12" style="padding-left: 150px;">
                    <li style="display: inline;padding-left: 200px;"><a href="http://www.portea.com/privacypolicy/">Privacy
                        Policy</a>&nbsp;&nbsp;&nbsp;
                    </li>
                    <li style="display: inline"><a href="http://www.portea.com/terms/">Terms and Conditions</a>
                    </li>
                </ui>
            </div>
        </div>


    </div>
</div>
</body>
</html>

