function chart1Formatter(){
    var x = $("#chart1Select").val();
    switch (x)
    {
        case "1" :
            drawAreaUsageChart();
                break;
        case "2" :
            drawActorUsageChart();
                break;
        case "3" :
            drawCategoryUsageChart();
                break;
        case "4" :
            drawContextTypeChart();
            break;
        case "5" :
            drawDiscountRangeChart();
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
            drawAreaDetailsChart();
            break;
        case "2" :
            drawBrandDetailsChart();
            break;
        case "3" :
            drawReferrerTypeChart();
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
            drawBrandDiscountDetailsChart();
            break;
        case "2" :
            drawAreaDiscountDetailsChart();
            break;
        default :
            alert("Invalid Input");
            break;
    }
}

function chart4Formatter(){
    var x = $("#chart4Select").val();
    switch (x)
    {
        case "1" :
            drawCouponDiscountDetailsChart();
            break;

        default :
            alert("Invalid Input");
            break;
    }
}
function chart5Formatter(){
    var x = $("#chart5Select").val();
    switch (x)
    {
        case "1" :
            statusDetailsChart();
            break;

        default :
            alert("Invalid Input");
            break;
    }
}
function chart6Formatter(){
    var x = $("#chart6Select").val();
    switch (x)
    {
        case "1" :
            expiryDetailsChart();
            break;
        default :
            alert("Invalid Input");
            break;
    }
}


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

function refreshChart4()
{
    chart4Formatter();
}

function refreshChart5()
{
    chart5Formatter();
}

function refreshChart6()
{
    chart6Formatter();
}
