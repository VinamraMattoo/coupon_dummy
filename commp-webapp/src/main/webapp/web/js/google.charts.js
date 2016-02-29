// Load the Visualization API and the piechart package.
google.charts.load('current', {'packages': ['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawTables);
function drawTables() {
    drawGatewayUsageChart();
    dailySMSUsageChart();
    dailySMSStatsChart();
}

function drawGatewayUsageChart() {

    $.ajax({
        url: "../rws/sms/gateway/usage",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Gateway Name');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.gatewayName, currentObj.count]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart1'));
            chart.draw(dTable, {width: 300,legend: 'none', height: 240,animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true, title: "Gateway usage chart"});

        }
    });
}

function drawSourceUsageChart() {

    $.ajax({
        url: "../rws/sms/source/usage",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Gateway Name');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.source, currentObj.count]);
            }
            var chart = new google.visualization.PieChart(document.getElementById('chart1'));


            function selectHandler() {
                var selectedItem = chart.getSelection()[0];
                if (selectedItem) {
                    var value = data.getValue(selectedItem.row, selectedItem.column);
                    alert('The user selected ' + value);
                }
            }

            // Listen for the 'select' event, and call my function selectHandler() when
            // the user selects something on the chart.
            google.visualization.events.addListener(chart, 'select', selectHandler);

            chart.draw(dTable, {width: 300,legend: 'none', height: 240,animation: {duration: 3000, "startup": true,easing: 'out'}, is3D: true, title: "Source usage chart"});

        }
    });
}
function dailySMSUsageChart() {

    $.ajax({
        url: "../rws/sms/daily/smsType?days=7",
        dataType: 'json',
        success: function (data) {
            var i, j, k,flag;
            data.sort(function(a, b)
            {
                var x = a.date;
                var y = b.date;
                return x > y ? 1 : x < y ? -1 : 0;
            });
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Days');
            // looping and getting the typpe data from the meta-data
            for (i = 0; i < typeData.length; i++) {
                dTable.addColumn("number",typeData[i]);
            }


//loop through the length of the array to populate the rows

            for (i = 0; i < chartData.length; i++) {
                var tempArr=[];
                var currentObj = chartData[i];
                tempArr.push(currentObj.day);

                for (j=0;j< typeData.length;j++)
                {
                    flag = 0;
                    for(k=0; k < currentObj.smsTypes.length; k++)
                    {
                        if (currentObj.smsTypes[k].typeName == typeData[j]) {
                            tempArr.push(currentObj.smsTypes[k].count);
                            flag=1;
                        }
                    }
                if (flag == 0) {
                    tempArr.push(0);
                }}
                dTable.addRow(tempArr);
            }

            var chart = new google.visualization.ColumnChart(document.getElementById('chart2'));
            chart.draw(dTable, {width: 300,legend: 'none', height: 240,animation: {duration: 3000, "startup": true,easing: 'out'}, title: "Daily Usage", pieHole: 0.4,});

        }
    });
}



function dailySMSStatsChart() {

    $.ajax({
        url: "../rws/sms/daily/status?days=7",
        dataType: 'json',
        success: function (data) {
            data.sort(function(a, b)
            {
                var x = a.date;
                var y = b.date;
                return x > y ? 1 : x < y ? -1 : 0;
            });
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Day');
            dTable.addColumn('number', 'Delivered');
            dTable.addColumn('number', 'Failed');
            dTable.addColumn('number', 'Pending');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.day, currentObj.delivered , currentObj.failed , currentObj.pending]);
            }


            var chart = new google.visualization.ComboChart(document.getElementById('chart3'));
            chart.draw(dTable, {width: 300, height: 240,
                animation: {duration: 3000,
                    "startup": true,easing: 'out'},
                vAxis: {title: 'SMS'},
                hAxis: {title: 'Day by Day'},
                title:"Daily Stats",
                seriesType: 'bars',
                legend: "none"

            });

        }
    });
}