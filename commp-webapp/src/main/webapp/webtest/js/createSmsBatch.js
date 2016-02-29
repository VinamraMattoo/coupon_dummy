$(window).load(function () {
    //LiveSearch.init(document.getElementById('live-search-example').querySelector('input[name=s]'), {url: '/?s='});
    var limit = 10;
    $("#gn").liveSearch({url: '../testrapi/smsGroup?limit=' + limit + '&name='});
    $("#un").liveSearch({url: '../testrapi/user?limit=' + limit + '&login='});
    $("#createBtn").click(function () {

        $("#loading-content").append('<div id="loader-wrapper"> <div id="loader"></div> </div>');

        var fromDate = $("#fromDate").val(new Date());

        var groupName = $("#gn").val();
        var phoneNumber = $("#pn").val();
        var count = $("#cnt").val();
        var brandName = $("#bn").val();
        var receiverType = $("#rt").val();
        var message = $("#msg").val();
        var login = $("#un").val();
        var attm = $("#attm").prop('checked');
        var appendTimeStampToMessage = (attm === true);

        var urlParams = "count=" + count + "&groupName=" + groupName
                + "&phoneNumber=" + phoneNumber + "&brandName=" + brandName
                + "&receiverType=" + receiverType + "&message=" + message
                + "&appendTimeStampToMessage=" + appendTimeStampToMessage
                + "&login=" + login
            ;
        $.ajax({
            type: 'GET',
            url: "../testrapi/createSmsBatch?" + urlParams,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (data, textStatus, xhr) {
                $("#loading-content").empty();
                var successMsg = xhr.getResponseHeader('info');
                swal({title: "Request successful", text: successMsg}/*,
                 function(){
                 var url = window.location.href.split('?')[0];
                 window.location.href = url + "?" + urlParams;
                 }*/);
            },
            error: function (xhr, textStatus, errorThrown) {
                $("#loading-content").empty();
                var errorMsg = xhr.getResponseHeader('Warning');
                swal("Request failed", errorMsg);
            }
        });
    });

    function getCurrentDateFormat() {
        return 'YYYY-MM-DD HH:mm:ss';
    }

    function editDateTimeInput(startDate, endDate) {
        $('#' + startDate).datetimepicker({
            format: getCurrentDateFormat(),
            widgetPositioning: {horizontal: 'left', vertical: 'bottom'}

        });
        $('#' + endDate).datetimepicker({
            useCurrent: false,
            format: getCurrentDateFormat(),
            widgetPositioning: {horizontal: 'left', vertical: 'bottom'}

        });
        $('#' + startDate).on("dp.change", function (e) {
            $('#' + endDate).data("DateTimePicker").minDate(e.date);
        });
        $("#" + endDate).on("dp.change", function (e) {
            $('#' + startDate).data("DateTimePicker").maxDate(e.date);
        });
    }

    $("#getLatestStatsBtn").click(function () {

        $("#loading-content").append('<div id="loader-wrapper"> <div id="loader"></div> </div>');
        var tillDate = (new Date).getTime(); //milliseconds
        var unit = $("#timeUnit").find(":selected").text();
        var duration = $("#smsCount").val();
        var initialDuration = $("#smsCount").val();
        switch (unit) {
            case "Seconds":
                duration = duration * 1000;
                break;
            case "Minutes":
                duration = duration * 60 * 1000;
                break;
            case "Hours":
                duration = duration * 60 * 60 * 1000;
                break;
            case "Days":
                duration = duration * 24 * 60 * 60 * 1000;
                break;
        }
        var fromDate = tillDate - duration;
        getSmsStats(fromDate, tillDate, initialDuration, unit);
    });

    function getSmsStats(fromDate, tillDate, initialDuration, unit) {
        $.ajax({
            type: 'GET',
            url: "../testrapi/sms/submitted?fromDate=" + fromDate + "&tillDate=" + tillDate,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (data, textStatus, xhr) {
                $("#loading-content").empty();
                var startDate = (data.startDate == null) ? "N/A" : new Date(data.startDate);
                var endDate = (data.endDate == null) ? "N/A" : new Date(data.endDate);
                var duration = ((data.startDate != null) && (data.endDate != null)) ? (data.endDate - data.startDate) / 1000 : "N/A";
                $("#latestStatResult").html("<div> <p>Result:</p><ui>" +
                    "<li>Submission Started  : " + startDate + "</li>" +
                    "<li>Submission Ended    : " + endDate + "</li>" +
                    "<li>Submission duration : " + duration + " seconds " + " </li>" +
                    "<li>Total Submitted     : " + data.count + "</li>" +
                    "</ui></div>");
            },
            error: function (xhr, textStatus, errorThrown) {
                $("#loading-content").empty();
                var errorMsg = xhr.getResponseHeader('Warning');
                swal("Request failed", errorMsg);
            }
        });
    }

    editDateTimeInput('fromDate', 'tillDate');

    $("#getStatsBtn").click(function () {

        $("#loading-content").append('<div id="loader-wrapper"> <div id="loader"></div> </div>');
        var tillVal = $("#tillDate").val();
        var tillDate;
        if (tillVal === "") {
            tillDate = new Date().getTime();
        } else {

            tillDate = new Date(tillVal).getTime();
        }

        var fromVal = $("#fromDate").val();

        if (fromVal === "") {
            $("#loading-content").empty();
            $("#latestStatResult").empty().append("Please specify from date");
            return;
        }
        var fromDate = new Date(fromVal).getTime();

        var duration = secondsToTime((tillDate - fromDate) / 1000);
        getSmsStats(fromDate, tillDate, duration.h + " hours " + duration.m + " minutes " + duration.s + " seconds", "");
        console.log(duration);
    });

    function secondsToTime(secs) {
        var hours = Math.floor(secs / (60 * 60));

        var divisor_for_minutes = secs % (60 * 60);
        var minutes = Math.floor(divisor_for_minutes / 60);

        var divisor_for_seconds = divisor_for_minutes % 60;
        var seconds = Math.ceil(divisor_for_seconds);

        var obj = {
            "h": hours,
            "m": minutes,
            "s": seconds
        };
        return obj;
    }

    function toDate(selector) {
        var from = $(selector).val().split("/");
        return new Date(from[2], from[1] - 1, from[0]);
    }

    var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };

    var groupName = getUrlParameter('groupName');
    var phoneNumber = getUrlParameter('phoneNumber');
    var count = getUrlParameter('count');
    var brandName = getUrlParameter('brandName');
    var receiverType = getUrlParameter('receiverType');
    var message = getUrlParameter('message');
    var appendTimeStampToMessage = getUrlParameter('appendTimeStampToMessage');
    var login = getUrlParameter('login');

    if (groupName !== undefined) {
        $("#gn").val(groupName);
    }
    if (phoneNumber !== undefined) {
        $("#pn").val(phoneNumber);
    }
    if (count !== undefined) {
        $("#cnt").val(count);
    }
    if (brandName !== undefined) {
        $("#bn").val(brandName);
    }
    if (receiverType !== undefined) {
        $("#rt").val(receiverType);
    }
    if (message !== undefined) {
        $("#msg").val(message);
    }
    if (appendTimeStampToMessage !== undefined) {
        $("#attm").val(appendTimeStampToMessage);
    }
    if (login !== undefined) {
        $("#un").val(login);
    }
});
var jsonForBulk = [];
function clickAddMoreBulkSmsBtn() {

  /*
            //loading animation start and ending code

  $("#loading-content").append('<div id="loader-wrapper"> <div id="loader"></div> </div>');
    $("#loading-content").empty();


    */

    var fromDate = $("#fromDate").val(new Date());

    var groupName = $("#gn").val();
    var phoneNumber = $("#pn").val();
    var count = $("#cnt").val();
    var brandName = $("#bn").val();
    var receiverType = $("#rt").val();
    var message = $("#msg").val();
    var login = $("#un").val();
    var attm = $("#attm").prop('checked');
    var appendTimeStampToMessage = (attm == true);

    var json = {
        "count": count,
        "groupName": groupName,
        "phoneNumber": phoneNumber,
        "brandName": brandName,
        "receiverType": receiverType,
        "message": message,
        "appendTimeStampToMessage": appendTimeStampToMessage,
        "login" : login
    };
    jsonForBulk.push(json);
    $('#createBtn').attr("disabled", "disabled");
    $("#createBulkSMSBtn").removeAttr("disabled", "disabled");
    console.log(JSON.stringify(jsonForBulk));

    //clearing the fields
        $("#gn").val("");
        $("#pn").val("");
        $("#cnt").val("");
        $("#bn").val("");
        $("#rt").val("");
        $("#msg").val("");
        $("#attm").val("");
        $("#un").val("");



}

function createBulkSMS(){
    $.ajax({
        type: 'POST',
        url: "../testrapi/createSmsBatch?" + urlParams,
        data: jsonForBulk,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data, textStatus, xhr) {
            $("#loading-content").empty();
            var successMsg = xhr.getResponseHeader('info');
            swal({title: "Request successful", text: successMsg});
        },
        error: function (xhr, textStatus, errorThrown) {
            $("#loading-content").empty();
            var errorMsg = xhr.getResponseHeader('Warning');
            swal("Request failed", errorMsg);
        }
    });
}
