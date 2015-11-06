<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link
            href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css"
            rel="stylesheet">
    <link rel="stylesheet"
          href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.0/bootstrap-table.css">

    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap-theme.css">
    <link href="./css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="./css/side-nav.css">
    <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
    <script
            src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.js"></script>

    <script
            src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
    <script
            src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-en-US.js"></script>


    <script src="./js/moment.js"></script>
    <script src="./js/bootstrap-datetimepicker.min.js"></script>
<%--<link
            href="./plugin/bootstrap.css"
            rel="stylesheet">
    <link rel="stylesheet"
          href="./plugin/bootstrap-table.css">

    <link rel="stylesheet"
          href="./plugin/bootstrap-theme.css">

    <link rel="stylesheet" href="./css/side-nav.css">
    <script src="./plugin/jquery-1.10.js"></script>
    <script
            src="./plugin/bootstrap.js"></script>

    <script
            src="./plugin/bootstrap-table.js"></script>
    <script
            src="./plugin/locale-bootstrap-table-en-us.js"></script>--%>
    <style>
        body {
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-fixed-top" role="navigation"
     style="background-color: #3fc1be">
    <div class="navbar-header">    <link rel="stylesheet" href="./css/bootstrap-datetimepicker.min.css">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span> <span
                class="icon-bar"></span> <span class="icon-bar"></span> <span
                class="icon-bar"></span>
        </button>

        <img src="./images/portea.png" width="200"
             style="padding-top: 10px; margin-left: 10px;">

    </div>


    <div class="nav navbar-right top-nav">
        <form name="logoutForm" method="POST" action="logout">
            <button type="submit" class="navbar navbar-left btn btn-danger"
                    title="log out from current account"
                    style="text-align: center; margin-top: 10px; right: 100px; left: -50px;">
                Logout&nbsp;&nbsp;<span class="glyphicon glyphicon-adjust"></span>
            </button>
        </form>

    </div>

    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav"
            style="background-color: #3fc1be; top: 81px">
            <li><a href="#" data-toggle="collapse" data-target="#demo"
                   id="SysConfig"> <strong style="color: #ffffff">Manage
                Coupons <i class="glyphicon glyphicon-cog gly-spin"></i>
            </strong></a>
                <ul id="demo" class="collapse">
                    <li>
                        <button class="btn btn-link" id="create_new_coupon" onclick="hideOthers(0)"
                                title=" Create new coupons from here"
                                style="padding-right: 70px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                            Create
                            coupons
                        </button>
                    </li>
                    <li>
                        <button class="btn btn-link" onclick="hideOthers(1)"
                                title=" List coupons from here"
                                style="padding-right: 70px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                            List
                            coupons
                        </button>
                    </li>
                    <li>
                        <button class="btn btn-link" onclick="hideOthers(2)"
                                title=" manage coupon codes from here"
                                style="padding-right: 70px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                            List
                            coupon codes
                        </button>
                    </li>

                </ul>
            </li>
        </ul>
    </div>
</nav>

<br><br><br><br>
<%--
<table  style="padding-left:250px"
        id="dummyTable"
       data-toggle="table"
       data-url="./code.txt"
       data-pagination="true"
       data-side-pagination="server"
       data-page-list="[1, 5, 10, 100, ALL]"
       data-height="400">
    <thead>
    <tr>
        <th data-field="id">id</th>
        <th data-field="couponId">c_id</th>
        <th data-field="code">code</th>
        <th data-field="createdOn">created on</th>
        <th data-field="createdBy">created by</th>
        <th data-field="deactivatedOn">deactivated on</th>
        <th data-field="deactivatedBy">deactivated by</th>
        <th data-field="channelName">channel name</th>
        <th data-field="operate" data-formatter="codeOptionsFormatter" data-events="codeOptionsEvents">Item Operate</th>
    </tr>
    </thead>
</table>
<button style="padding-left: 300px" id="testbtn" class="btn btn-success btn-lg">click me</button>
<script>
    $(document).ready(function(){
    $("#testbtn").click(function(){
        $("#dummyTable").bootstrapTable('removeAll');
    });
    });
</script>

--%>

<%--
<div  style="padding-left:300px;padding-top: 150px">
<button id="dummyCouponCodeCreate" class="btn btn-danger">couponCode create</button>
</div>
<script>
    $("#dummyCouponCodeCreate").click(function(){
    $("#dummyCouponCodeCreate").hide();
    $("#createCode").load("./couponCodeCreate.jsp");
    });
</script>
--%>

  <div id="page-wrapper" style="padding-left: 200px">
    <div class="row">
        <div class="col-md-8 col-md-offset-1">

      <div id="createCoupon" hidden></div>

            <div id="showCoupons" hidden></div>
            <div id="listCouponCodes" hidden></div>

    <div id="viewCreatedCoupon" hidden>
        <br><br><br><br>
    </div>

    <div id="editCoupon" hidden>

    </div>

    <div id="statusInfo">
        <br>
        <br>
        <br><br>

        <h2>
            <div id="infoPageId">
                <h2>
                    Hi. Welcome to coupon management website.
                </h2>
            </div>
        </h2>
    </div>
    <div id="createCode" hidden ></div>
        </div>
    </div>


</div>



<script src="js/divManipulation.js"></script>
<script src="./js/extract_create_json.js"></script>
<script src="./js/extract_edit_json.js"></script>
<script src="./js/external_div.js"></script>
<script src="./js/listCouponTable.js"></script>

<script>
    $(document).ready(function(){
        $.get("create.jsp", function(data) {
            $("#createCoupon").append(data);
            $.get("mapping.jsp", function(data) {
                $("#couponMapping").append(data);
            });
        });
        $.get("listCoupon.jsp", function(data) {
            $("#showCoupons").append(data);
        });
        $.get("listCouponCode.jsp", function(data) {
            $("#listCouponCodes").append(data);
        });


            });
    function hideOthers(id) {
        var arr = ["createCoupon", "showCoupons", "listCouponCodes", "viewCreatedCoupon", "statusInfo", "editCoupon","createCode"];
        for (var i = 0; i < arr.length; i++) {
            if (i == id) {
                $("#" + arr[i]).show();
                continue;
            }
            $("#" + arr[i]).hide();
        }
    }
    function editOthers(id) {
        var arr = ["editCouponAttributes", "editCouponMapping", "editCouponRules"];
        for (var i = 0; i < arr.length; i++) {
            if (i == id) {
                $("#" + arr[i]).show();
                continue;
            }
            $("#" + arr[i]).hide();
        }
    }
    function showOthers(id) {
        var arr = [ "couponAttributes", "couponMapping", "couponRules" ];
        for (var i = 0; i < arr.length; i++) {
            if (i == id) {
                $("#" + arr[i]).show();
                continue;
            }
            $("#" + arr[i]).hide();
        }
    }

    function showOthers(id) {
        var arr = [ "couponAttributes", "couponMapping", "couponRules" ];
        for (var i = 0; i < arr.length; i++) {
            if (i == id) {
                $("#" + arr[i]).show();
                continue;
            }
            $("#" + arr[i]).hide();
        }
    }
    function checkDicountType() {
        var choice = $("#ruleType").val();
        if (choice == "FLAT") {
            $("#percent").prop("disabled", true);
            $("#flatAmount").prop("disabled", false);
        } else {
            $("#flatAmount").prop("disabled", true);
            $("#percent").prop("disabled", false);
        }
    }
</script>
</body>
</html>