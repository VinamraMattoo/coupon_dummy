// Load the Visualization API and the piechart package.
google.charts.load('current', {'packages': ['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawTables);
//draw these when loading the web-app
function drawTables() {
    drawAreaUsageChart();
    drawAreaDetailsChart();
    drawBrandDiscountDetailsChart();
    drawCouponDiscountDetailsChart();
    statusDetailsChart();
    expiryDetailsChart();
}


// chart one drawing functions
function drawAreaUsageChart() {

    $.ajax({
        url: "../rws/coupon/area/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Area');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.area, currentObj.count]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart1Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Area',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});

        }
    });
}
function drawActorUsageChart() {

    $.ajax({
        url: "../rws/coupon/actor/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Actor');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.actorType, currentObj.count]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart1Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Actor',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});

        }
    });
}
function drawCategoryUsageChart() {

    $.ajax({
        url: "../rws/coupon/category/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Category');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.couponCategory, currentObj.count]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart1Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Category',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});

        }
    });
}
function drawContextTypeChart() {

    $.ajax({
        url: "../rws/coupon/contextType/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Context Type');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.contextType, currentObj.count]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart1Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Context-Type',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});

        }
    });
}
function drawDiscountRangeChart() {

    $.ajax({
        url: "../rws/coupon/discountRange",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Range');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.range, currentObj.numberOfCoupons]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart1Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Discount-Range',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});

        }
    });
}


//chart two drawing functions
function drawAreaDetailsChart() {
    $.ajax({
        url: "../rws/couponDiscount/area/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Area');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.area, currentObj.count]);
            }

            var chart = new google.visualization.PieChart(document.getElementById('chart2Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Area',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});
        }
    });
}
function drawBrandDetailsChart() {
    $.ajax({
        url: "../rws/couponDiscount/brand/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Brand');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.brand, currentObj.count]);
            }
            var chart = new google.visualization.PieChart(document.getElementById('chart2Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Brand',animation:
            {duration: 3000, "startup": true,easing: 'out'}, pieStartAngle: 270,slices: {  0: {color: '#3fc1be',}}
            });
        }
    });
}
function drawReferrerTypeChart() {
    $.ajax({
        url: "../rws/couponDiscount/referrerType/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Referrer Type');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.referrerType, currentObj.count]);
            }
            var chart = new google.visualization.PieChart(document.getElementById('chart2Div'));
            chart.draw(dTable, {width: 300, height: 240,title:'By Referrer-Type',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});
        }
    });
}

//chart 3 drawing function
function drawBrandDiscountDetailsChart() {
    $.ajax({
        url: "../rws/coupon/brand/discountDetails",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Brand');
            dTable.addColumn('number', 'Discount Given');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.brand, currentObj.discountGiven]);
            }
            var chart = new google.visualization.PieChart(document.getElementById('chart3Div'));
            chart.draw(dTable, {width: 300, height: 240,title: 'By Brand',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});
        }
    });
}
function drawAreaDiscountDetailsChart() {
    $.ajax({
        url: "../rws/coupon/area/discountDetails",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Area');
            dTable.addColumn('number', 'Discount Given');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            for (i = 0; i < chartData.length; i++) {
                var currentObj = chartData[i];
                dTable.addRow([currentObj.area, currentObj.discountGiven]);
            }
            var chart = new google.visualization.PieChart(document.getElementById('chart3Div'));
            chart.draw(dTable, {width: 300, height: 240,title: 'By Area',animation:
            {duration: 3000, "startup": true,easing: 'out'}, is3D: true});
        }
    });
}
//chart 4 drawing function
function drawCouponDiscountDetailsChart() {
    $.ajax({
        url: "../rws/couponDiscount/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Day');
            dTable.addColumn('number', 'Minimum Discount');
            dTable.addColumn('number', 'Maximum Discount');
            dTable.addColumn('number', 'Average Discount');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            var output = chartData.discountData;
            for (i = 0; i < output.length; i++) {
                var currentObj = output[i];
                dTable.addRow([currentObj.dayOfWeek,currentObj.minDiscount,currentObj.maxDiscount,currentObj.avgDiscount]);
            }
            var chart = new google.visualization.ComboChart(document.getElementById('chart4Div'));
            chart.draw(dTable, {width: 300, height: 240,animation:
            {duration: 3000, "startup": true,easing: 'out'}, vAxis: {title: 'Coupons'},
                hAxis: {title: 'Day of Week'},
                vAxis: {title: 'Discount Amount(in Rs)'},
                hAxis: {title: 'Day by Day '},
                title:'Daily Distribution',
                legend: "none",
                seriesType: 'bars',
                series: {2: {type: 'line'}}
            });
        }
    });
}
//chart 5 drawing function
function statusDetailsChart() {
// ajax call to the server
    $.ajax({
        url:"../rws/couponDiscount/status/details",
        dataType:'json',

        success:function(data){
            //converting the json to a string
            var  arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string','Day');
            dTable.addColumn('number','Applied');
            dTable.addColumn('number','Cancelled');
            dTable.addColumn('number','Requested');

// ..... add more columns here

//loop through the length of the array to populate
            var i;
            var output = chartData.discountData;
            for (i = 0; i < output.length; i++) {
                var currentObj = output[i];
                dTable.addRow([currentObj.dayOfWeek,currentObj.applied,currentObj.cancelled,currentObj.requested]);
            }

            var chart = new google.visualization.ComboChart(document.getElementById('chart5Div'));
            chart.draw(dTable, {width: 300, height: 240,
                vAxis: {title: 'Number of Coupon Discount Requests'},
                hAxis: {title: 'Day by Day '},
                title:"Daily Distribution",
                seriesType: 'bars',
                legend: "none"
            });

        }
    });
}
//chart 6 drawing function
function expiryDetailsChart() {
// ajax call to the server

    $.ajax({
        url: "../rws/couponExpiry/details",
        dataType: 'json',
        success: function (data) {
            //converting the json to a string
            var arr = JSON.stringify(data);

            var chartData = JSON.parse(arr);
            var dTable = new google.visualization.DataTable();

//adding colums
            dTable.addColumn('string', 'Range');
            dTable.addColumn('number', 'Count');
// ..... add more columns here

//loop through the length of the array to populate the rows
            var i;
            var output = chartData.weeklyData;
            for (i = 0; i < output.length; i++) {
                var outData = output[i];
                dTable.addRow([outData.range, outData.couponsCount]);
           }

            var chart = new google.visualization.LineChart(document.getElementById('chart6Div'));
            chart.draw(dTable, {width: 300, height: 240,
                vAxis: {title: 'Number of Coupons'},
                hAxis: {title: 'Week by Week '},
                title:'Weekly Distribution',animation:
            {duration: 3000, "startup": true,easing: 'out'},legend:'none', is3D: true});

        }
    });
}

