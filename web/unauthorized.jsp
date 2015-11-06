<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css">

    <!-- jQuery -->
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script
            src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.min.js"></script>

</head>
<body>
<div class="container">
    <br>  <br>  <br>  <br>

    <div class="row">
        <div class="col-md-4 col-md-offset-4">



            <div class="panel panel-default">

                <div class="panel-heading">
                    <h3 class="panel-title">Please sign in</h3>
                </div>
                <div class="row">
                                <div class="col-md-12 col-md-offset-1">
                                    <div class="panel-heading">
                                        <h3 id="status" style="color:red;" class="panel-title"><% if(request.getAttribute("ErrorMessage") != null){
                                                                                                      out.println(request.getAttribute("ErrorMessage"));
                                                                                                  }; %></h3>
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
</body>
</html>

