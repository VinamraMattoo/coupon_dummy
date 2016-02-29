<%--the landing page for the  webapp--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.0/bootstrap-table.css">

    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap-theme.css">

    <%--  <link rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.min.css">
  --%>

    <link href="../css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="../css/side-nav.css">
    <link rel="stylesheet" href="../css/datatip.css">
    <link rel="stylesheet" href="../css/advance-search.css">
    <link rel="stylesheet" href="../css/googleCharts.css">
    <link rel="stylesheet" href="../css/bootstrap-multiselect.css">


    <link rel="shortcut icon" type="image/png" href="../images/newlogo.png"/>
    <link rel="stylesheet" href="../css/bootstrap-table-edit.css">

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="../js/google.charts.js"></script>
    <script
            src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.js"></script>

    <script
            src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
    <script
            src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-en-US.js"></script>

    <title>Coupon main page</title>
</head>
<body>
<%--navbar--%>

<nav class="navbar navbar-fixed-top" role="navigation"
     style="background-color: #3fc1be">
    <div class="navbar-header">
        <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span> <span
                class="icon-bar"></span> <span class="icon-bar"></span> <span
                class="icon-bar"></span>
        </button>
        <a>
            <img title="Home" src="../images/portea.png" width="200" onclick="hideOtherDivs(11)"
                 style="padding-top: 10px; margin-left: 10px;">
        </a>

    </div>

    <div style="color: #fff;margin-left:300px;display: inline" class="cpnSystemTitle pull-left inline"><h2>Coupon
        Management System</h2></div>

    </div>
    <div class="nav navbar-right top-nav" style=" height: 80px;">
        <form name="logoutForm" id="logoutForm" method="POST">
            <div class="navbar navbar-left hideOverflow" title="${userName}"
                 style="width:130px; text-align: right;background-color: inherit; margin-top: 25px; margin-right: 20px;
                  right: 100px;height: 22px; left: -50px;color: white">
                Hi, ${userName}</div>
            <button type="submit" class=" navbar navbar-left"
                    title="Logout from current session" onclick="logoutFunction()"
                    style="text-align: center;background-color: inherit; margin-top: 10px; right: 100px;height: 22px; left: -50px;color: black">
                Logout&nbsp;&nbsp;<span class="glyphicon glyphicon-adjust"></span>
            </button>
        </form>

    </div>

    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav"
            style="background-color: #3fc1be; top: 81px">
            <li>
                <a class="btn btn-link" id="dashboardId" onclick="hideOtherDivs(11)"
                   title="View dashboard"
                   style="padding-right: 120px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    <strong style="color: #ffffff">Dashboard</strong>
                </a>
            </li>
            <li>

                <a href="javascript:void(0)" data-toggle="collapse" data-target="#demo"
                   id="navBarToggle" style="padding-right: 50px;">
                    <strong id="manageCoupon" style="color: #ffffff">Manage
                        Coupons&nbsp;&nbsp;<i id="glyphiconId" class="glyphicon glyphicon-chevron-up"></i>
                    </strong></a>
                <ul id="demo" class="collapse in">
                    <li>
                        <button class="btn btn-link" id="create_new_coupon" onclick="hideOtherDivs(0)"
                                title="Create new coupons"
                                style="padding-right: 85px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                            Create
                            coupons
                        </button>
                    </li>
                    <li>
                        <button class="btn btn-link" onclick="hideOtherDivs(1)"
                                title="Open coupon listing"
                                style="border-left-width: 1px; padding-left: 15px;padding-right: 105px; text-decoration: none; color: #ffffff">
                            List
                            coupons
                        </button>
                    </li>
                    <li>
                        <button class="btn btn-link" onclick="hideOtherDivs(2)"
                                title="Open coupon code listing"
                                style="padding-right: 72px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                            List
                            coupon codes
                        </button>
                    </li>
                    <li>
                        <button class="btn btn-link" onclick="hideOtherDivs(10)"
                                title="Quickly create coupon and code together"
                                style="padding-right: 11px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                            Quick code/coupon creation
                        </button>
                    </li>
                </ul>
            </li>

        </ul>
    </div>
</nav>

<br><br><br><br>
<%--wrapper div for  the  webapp which contains all the  page fragments to be shown--%>
<div id="page-wrapper" style="padding-left: 200px">
    <div class="row">

        <div class="col-md-8 col-md-offset-1">

            <div id="createCoupon" hidden></div>

            <div id="showCoupons" hidden></div>

            <div id="listCouponCodes" hidden></div>


            <div id="viewCreatedCoupon" hidden style="width: 900px;margin-left: 15px;">
                <br><br>
            </div>

            <div id="showEditCoupon" hidden>

            </div>

            <div id="createCouponCode" hidden>

            </div>

            <div id="quickCouponCreate" hidden></div>

            <div id="statusInfo" hidden></div>

            <div id="createCode" hidden></div>

            <div id="viewCouponCode" hidden></div>

            <div id="fastCouponCodeCreate" hidden></div>
        </div>
        <div class="col-md-10 col-md-offset-1">
            <%--Dashvboard--%>
            <div id="dashboard">
                <div class="col-md-12" style="text-align: center;margin-top: 0;"><h3 style="margin: 0;">Dashboard</h3>
                    <hr style="margin: 0;height: 4px;border-top-width: 5px;">
                </div>
                <!--Div that will hold the pie chart-->
                <%--Chart 1--%>
                <div class="col-md-4">
                    <div class="col-md-offset-1" style="display: inline;">
                        <strong>
                            <span title="This chart showcases the number of active coupons by a specific type "
                                  style="margin-left: 15px;padding-left: -10px" id="chart1Title"
                                    >Active Coupons</span>
                        </strong>

                        <div class="pull-right">
                            <select class="googleChartSelect" onchange="chart1Formatter()" style="margin-left: 30px"
                                    id="chart1Select">
                                <option value="1">Area Usage</option>
                                <option value="2">Actor Usage</option>
                                <option value="3">Coupon Category Details</option>
                                <option value="4">Context Type Details</option>
                                <option value="5">Discount Range Details</option>
                            </select>
                            <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart1()" id="chart1Refresh"
                               title="Refresh"><span
                                    class="glyphicon glyphicon-refresh"></span></a></div>
                    </div>
                    <div id="chart1Div"></div>

                </div>
                <%--char 2--%>
                <div class="col-md-4">
                    <div class="col-md-offset-1" style="display: inline;">
                        <strong><span
                                title=" This chart showcases the total number of discounts given today by a specific type"
                                style="margin-left: 15px;padding-left: -10px" id="chart2Title"
                                >Discount Count</span></strong>

                        <div class="pull-right">
                            <select class="googleChartSelect" onchange="chart2Formatter()"
                                    id="chart2Select">
                                <option value=1>Area Discounts Usage</option>
                                <option value=2>Brand Usage</option>
                                <option value=3>Referrer Usage</option>
                            </select>
                            <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart2()" id="chart2Refresh"
                               title="Refresh"><span
                                    class="glyphicon glyphicon-refresh"></span></a></div>
                    </div>
                    <div id="chart2Div"></div>

                </div>
                <%--chart 3--%>
                <div class="col-md-4">
                    <div class="col-md-offset-1" style="display: inline;">
                        <strong><span
                                title="This chart showcases the total discount amount given today by a specific type"
                                style="margin-left: 15px;padding-left: -10px" id="chart3Title"
                                >Discounts Given</span></strong>

                        <div class="pull-right">
                            <select class="googleChartSelect" onchange="chart3Formatter()"
                                    id="chart3Select">
                                <option value=1>Brand Discounts Details</option>
                                <option value=2>Area Discounts Details</option>
                            </select>
                            <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart3()" id="chart3Refresh"
                               title="Refresh"><span
                                    class="glyphicon glyphicon-refresh"></span></a></div>
                    </div>
                    <div id="chart3Div"></div>

                </div>
                <%--chart 4--%>
                <div class="col-md-4">
                    <div class="col-md-offset-1" style="display: inline;">
                        <strong><span title="This chart showcases the min/max discounts given over the past week"
                                      style="margin-left: 15px;padding-left: -10px" id="chart4Title"
                                >Coupon Discount</span></strong>

                        <div class="pull-right">
                            <select class="googleChartSelect" onchange="chart4Formatter()"
                                    id="chart4Select">
                                <option value=1>Daily Distribution</option>
                            </select>
                            <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart4()" id="chart4Refresh"
                               title="Refresh"><span
                                    class="glyphicon glyphicon-refresh"></span></a></div>
                    </div>
                    <div id="chart4Div"></div>

                </div>
                <%--chart 5--%>
                <div class="col-md-4">
                    <div class="col-md-offset-1" style="display: inline;">
                        <strong><span
                                title="This chart showcases the total number of requests which have been applied vs canceled vs in requested state"
                                style="margin-left: 15px;padding-left: -10px" id="chart5Title"
                                >Coupon Status</span></strong>

                        <div class="pull-right">
                            <select class="googleChartSelect" onchange="chart5Formatter()"
                                    id="chart5Select">
                                <option value=1>Daily Distribution</option>
                            </select>
                            <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart5()" id="chart5Refresh"
                               title="Refresh"><span
                                    class="glyphicon glyphicon-refresh"></span></a></div>
                    </div>
                    <div id="chart5Div"></div>

                </div>
                <%--chart 6--%>
                <div class="col-md-4">
                    <div class="col-md-offset-1" style="display: inline;">
                        <strong><span
                                title=" This chart showcases the number of active coupons expiring over the next month , week by week"
                                style="margin-left: 15px;padding-left: -10px" id="chart6Title"
                                >Coupon Expiry</span></strong>

                        <div class="pull-right">
                            <select class="googleChartSelect" onchange="chart6Formatter()"
                                    id="chart6Select">
                                <option value="1">Weekly Distribution</option>
                            </select>
                            <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart6()" id="chart6Refresh"
                               title="Refresh"><span
                                    class="glyphicon glyphicon-refresh"></span></a></div>
                    </div>
                    <div id="chart6Div"></div>

                </div>

            </div>

        </div>
        <div id="datatipDiv"></div>

        <%--local js plugins sources--%>
        <script src="../js/moment.js"></script>

        <script src="../js/bootstrap-datetimepicker.min.js"></script>

        <script src="../js/list-coupon-table.js"></script>

        <script src="../js/list-coupon-code-table.js"></script>

        <script src="../js/json-util.js"></script>

        <script src="../js/coupon-validation.js"></script>

        <script src="../js/coupon-operation.js"></script>

        <script src="../js/coupon-edit.js"></script>

        <script src="../js/google.charts.js"></script>

        <script src="../js/coupon-event.js"></script>

        <script src="../js/coupon-create.js"></script>

        <script src="../js/coupon-details-operation.js"></script>

        <script src="../js/edit-util.js"></script>

        <script src="../js/infra.js"></script>

        <script src="../js/datatip.js"></script>

        <script src="../js/coupon-quick-creation.js"></script>

        <script src="../js/bootstrap.multiselect.js"></script>

        <script src="../js/coupon-state-save.js"></script>
        <script src="../js/state-save.js"></script>
        <script src="../js/chartsSwitch.js"></script>
        <script src="../js/brand-handler.js"></script>

        <script src="../js/global-handler.js"></script>

</body>
</html>









