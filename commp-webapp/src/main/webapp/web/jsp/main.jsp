<%--the landing page for the communication platform webapp--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SMS Engine</title>
    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.0/bootstrap-table.css">
    <link rel="stylesheet"
          href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap-theme.css">

    <link rel="shortcut icon" type="image/png" href="../images/newlogo.png"/>

    <link rel="stylesheet" type="text/css" href="../css/sweetalert.css">
    <link rel="stylesheet" type="text/css" href="../css/googleCharts.css">
    <link rel="stylesheet" href="../css/side-nav.css">
    <link rel="stylesheet" href="../css/bootstrap.table.edit.css">
    <link rel="stylesheet" href="../css/form-input.css">
    <link href="../css/bootstrap-datetimepicker.min.css">

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="../js/google.charts.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.js"></script>

    <script src="../js/sweetalert.min.js"></script>
    <script src="../js/common.js"></script>

</head>

<body>
<%--navbar--%>
<nav class="navbar navbar-fixed-top" role="navigation"
     style="background-color: #3fc1be">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span> <span
                class="icon-bar"></span> <span class="icon-bar"></span> <span
                class="icon-bar"></span>
        </button>
        <a>
            <img class="pull-left" title="Home" src="../images/portea.png" width="200" height="60"
                 onclick="loadThisDiv(0)"
                 style="padding-top: 10px; margin-left: 10px;">
        </a>

        <div style="color: #fff;margin-left:300px;display: inline" class="commpSystemTitle pull-left inline"><h2>SMS
            Engine Management System</h2></div>

    </div>

    <div class="nav navbar-right top-nav" style="height: 80px">

        <form name="logoutForm" id="logoutForm" method="POST">
            <div class="navbar navbar-left hideOverflow" title="${userName}"
                 style="width:130px; text-align: right;background-color: inherit; margin-top: 25px; margin-right: 20px;
                  right: 100px;height: 22px; left: -50px;color: white">
                Hi, ${userName}</div>
            <button type="submit" class="navbar navbar-left"
                    title="Logout from current session" onclick="logoutFunction()"
                    style="text-align: center;background-color: inherit; margin-top: 10px; right: 100px; left: -50px;color: black">
                Logout&nbsp;&nbsp;<span class="glyphicon glyphicon-adjust"></span>
            </button>
        </form>

    </div>


    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav"
            style="background-color: #3fc1be; top: 81px">

            <li>
                <button class="btn btn-link pull-left" href="javascript:void(0)" onclick="loadThisDiv(0)"
                        style="padding-right: 140px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    Dashboard
                </button>
            </li>
            <li>
                <button class="btn btn-link pull-left" href="javascript:void(0)" onclick="loadThisDiv(1)"
                        style="padding-right: 140px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    SMS Group
                </button>
            </li>
            <li>
                <button class="btn btn-link pull-left" href="javascript:void(0)" onclick="loadThisDiv(5)"
                        style="padding-right: 140px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    SMS Type
                </button>
            </li>
            <li>
                <button class="btn btn-link pull-left" href="javascript:void(0)" onclick="loadThisDiv(2)"
                        style="padding-right: 85px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    Gateway - Group Mapping
                </button>
            </li>
            <li>
                <button class="btn btn-link pull-left" href="javascript:void(0)" onclick="loadThisDiv(3)"
                        style="padding-right: 125px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    Configuration
                </button>
            </li>
            <li>
                <button class="btn btn-link pull-left" href="javascript:void(0)" onclick="loadThisDiv(4)"
                        style="padding-right: 95px; border-left-width: 1px; padding-left: 15px; text-decoration: none; color: #ffffff">
                    User management
                </button>
            </li>

        </ul>
    </div>
</nav>

<br><br><br><br>


<div style="padding-left: 200px">
    <div class="row">


        <div style="margin-left:50px;" class="col-md-12">

            <div id="smsGroupings" hidden></div>
            <div id="smsTypes" hidden></div>

            <div id="smsConfigurations" hidden></div>

            <div id="userConfigurations" hidden></div>

            <div id="gatewayMappings" hidden></div>



        </div>
    </div>


</div>
<div id="dashboard"  style="padding-left: 200px">
    <div class="col-md-12" style="text-align: center;margin-top: 0;"><h3 style="margin: 0;">Dashboard</h3>
        <hr style="margin: 0;height: 4px;border-top-width: 5px;padding-left: 150px;">
        <!--Div that will hold the pie chart-->
        <div class="col-md-4">
            <div style="display: inline">
                <strong><span title="This chart showcases the Usage details for SMS " style="margin-left: 15px;padding-left: -10px" id="chart1Title"
                        >Usage Chart</span></strong>
                <select class="googleChartSelect"  onchange="chart1Formatter()" id="chart1Select" style="margin-left: 30px">
                    <option value=1>SMS Gateway Usage</option>
                    <option value=2>SMS Source Usage</option>
                </select>
                <a style="font-size: 10px;margin-right: 50px;" onclick="refreshChart1()" id="chart1Refresh" title="Refresh"
                       ><span
                        class="glyphicon glyphicon-refresh"></span></a>
            </div>
            <div id="chart1"></div>
        </div>


        <div class="col-md-4">
            <div style="display: inline">
                <strong><span title="This chart showcases the daily usage for SMS " style="margin-left: 15px;padding-left: -10px" id="chart2Title"
                        >SMS Chart</span></strong>
                <select class="googleChartSelect"  onchange="chart2Formatter()" id="chart2Select" style="margin-left: 30px">
                    <option value=1>Daily sms stats</option>
                </select>
                <a style="font-size: 10px;margin-right: 50px;"  onclick="refreshChart2()" id="chart2Refresh" title="Refresh"
                       ><span
                        class="glyphicon glyphicon-refresh"></span></a>
            </div>
            <div id="chart2"></div>
        </div>

        <div class="col-md-4">
            <div style="display: inline">
                <strong><span title="This chart showcases the daily usage for SMS " style="margin-left: 15px;padding-left: -10px" id="chart3Title"
                        >SMS Chart</span></strong>
                <select class="googleChartSelect"  onchange="chart3Formatter()" id="chart3Select" style="margin-left: 30px">
                    <option value=1>Daily sms stats</option>
                </select>
                <a style="font-size: 10px;margin-right: 50px;"  onclick="refreshChart3()" id="chart3Refresh" title="Refresh"
                        ><span
                        class="glyphicon glyphicon-refresh"></span></a>
            </div>
            <div id="chart3"></div>
        </div>

    </div>
</div>
<div id="datatipDiv"></div>

<%--local js plugins sources--%>
<script src="../js/moment.js"></script>


<script src="../js/infra.js"></script>

<script src="../js/bootstrap-datetimepicker.min.js"></script>

<script src="../js/state-save.js"></script>

<script src="../js/gateway-mappings-table-formatter.js"></script>

<script src="../js/user-configuration-formatter.js"></script>

<script src="../js/sms-operations.js"></script>

<script src="../js/group-type-edit-operations.js"></script>

<script src="../js/flatten.js"></script>

<script src="../js/group-type-table-formatter.js"></script>

<script src="../js/sms-configuration-formatter.js"></script>

<script src="../js/sweet-alert-operations.js"></script>

<script src="../js/chartsSwitch.js"></script>

<script src="../js/user-target-config-operations.js"></script>

</body>
</html>