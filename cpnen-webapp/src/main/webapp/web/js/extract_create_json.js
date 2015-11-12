var couponId;
function showStatusMessage(Id, eventType) {

    hideOthers(4);
    if (eventType == "create") {
        couponId = Id;
        $("#infoPageId").html(" Your Coupon has been created successfully. Click <a href=\"#\" onclick=\"showCoupon(couponId)\">here</a> to view the coupon.");
    }
    else if (eventType == "edited") {
        $("#infoPageId").html(" Your Coupon has been modified successfully. Click <a href=\"#\" onclick=\"showCoupon(couponId)\">here</a> to view the coupon.");
    }
    else if (eventType == "errMsg") {
        $("#infoPageId").html(" Something went wrong. Server responded with the message " + eventType + ".");
    }

    return false;
}


function populateView(response, id) {
    couponId = id;
    var tags = "";
    for (var key in response) {

        tags += "<tr><td>" + key + " </td><td>" + response[key] + "</td></tr>";
        if (key == "publishedOn") {
            checkStatus(response[key]);
        }
        if (key == "deactivatedOn") {
            checkDeactivation(response[key]);
        }


        /*   for (var key2 in key) {
         tags += "<tr><td>" + key2 + " </td><td>" + key[key2] + "</td></tr>";
         if (key2 == "publishedOn") {
         checkStatus([key2]);
         }
         }*/


    }

    $('#viewCurrentCoupon').append(tags);


}

function checkStatus(status) {

    if (status == null) {
        $("#editCpn").show();
        $("#deleteCpn").show();
    }
    else {
        $("#deactivateCpn").show();
        $("#generateCode").show();
        $("#extendAppTill").show();
    }

    return;
}

function checkDeactivation(status) {

    if (status != null) {

        $("#deactivateCpn").css('display', 'none');
        $("#generateCode").css('display', 'none');
        $("#editCpn").css('display', 'none');
        $("#deleteCpn").css('display', 'none');

    }

    return;

}

//untreated code starts
function onDeactivationClick() {
    confirm("Do you really want to deactivate?");
}

function onDeleteClick() {
    confirm("Do you really want to delete?");
}
function onGenerateCodeClick() {
    confirm("showing couponcodes");
}