"use strict";

/*formatter for code creation date*/
function codeCreateOnFormatter(value) {
    if (value != null) {
        var utcSeconds = value;
        var date = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        return '<div title="Created on : ' + z + '" style="color:purple">' + d + '</div>';

    }
    else {
        return value;
    }
}


/*formatter for code deactivation date*/
function codeDeactivateOnFormatter(value) {
    if (value != null) {
        var utcSeconds = value;
        var d = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        d = (moment(d).format("D/M/YY"));
        return d;
    }
    else {
        return value
    }
}

/*category formatter in code listing page*/
function categoryFormatter(value, row) {
    return dataFormatter(value);
}


/*function to populate the code details table*/
function populateViewCode(response) {
    var tags = "";
    var NOT_AVAILABLE = "N/A";
    for (var key in response) {

        switch (key) {
            case "code" :
                tags += "<tr><td>" + 'Code' + " </td><td>" + response[key] + "</td></tr>";
                break;

            case "channelName" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Channel name' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "createdOn" :
                if (response[key] != null)
                    tags += "<tr><td>" + 'Created on' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Created on' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";

                break;

            case "createdBy" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Created by' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "deactivatedOn" :
                if (response[key] != null)
                    tags += "<tr><td>" + 'Deactivated on' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Deactivated on' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";

                break;

            case "deactivatedBy" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                else
                    tags += "<tr><td>" + 'Deactivated by' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "couponId" :
                $("#couponFromCode").val(response[key]);
                break;
        }
    }

    $('#viewCurrentCode').empty().append(tags);
    hideOtherDivs(9);

}
/*code listing formatter to show code status*/
function codeStatusFormatter(value, row) {
    if (row.deactivatedOn == null) {
        return '<span title="Active Code" style="color: green">Active</span>';
    }
    else {
        return '<span title="Deactivated by : ' + row.deactivatedBy + '  on: ' + (moment(row.deactivatedOn).format("D/M/YY")) + '" style="color: red">Inactive</span>';
    }
}
/*code listing formatter to show coupon view link*/
function codeFormatter(value, row) {

    return '<div ><a style="color: black" title="Click to view code details" onclick="getCodeDetails(' + row.couponId + ',' + row.id + ')" href="javascript:void(0)">' + value + ' </a></div>';
}

