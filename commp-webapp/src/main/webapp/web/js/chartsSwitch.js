function chart1Formatter(){
    var x = $("#chart1Select").val();
    switch (x)
    {
        case "1" :
            drawGatewayUsageChart();
                break;
        case "2" :
            drawSourceUsageChart();
                break;
        default :
                alert("Invalid Input");
                break;
    }
}
function chart2Formatter(){
    var x = $("#chart2Select").val();
    switch (x)
    {
        case "1" :
            dailySMSUsageChart();
            break;
        default :
            alert("Invalid Input");
            break;
    }
}
function chart3Formatter(){
    var x = $("#chart3Select").val();
    switch (x)
    {
        case "1" :
            dailySMSStatsChart();
            break;
        default :
            alert("Invalid Input");
            break;
    }
}

/*
function chart4Formatter(){
    alert("default");
}
*/
//refresh buttons on chart
function refreshChart1()
{
    chart1Formatter();
}

function refreshChart2()
{
    chart2Formatter();
}
function refreshChart3()
{
    chart3Formatter();
}

/*
function refreshChart4()
{
    chart4Formatter();
}


*/
