//To show the coupon values just created
function showCoupon(Id) {
    hideOthers(3);
    $.get("./view_coupon.jsp", function (data) {
        $("#viewCreatedCoupon").empty().append(data);
        getCouponDetails(Id, "view");
    });

    return false;
}

function onEditClick() {
    var id = couponId;
    editCoupon(id);
}


function editCoupon(cid) {
    hideOthers(5);
    $.get("./editCoupon.jsp", function (data) {
        $("#editCoupon").empty().append(data);
        $.get("./mapping.jsp", function (data) {
            $("#edit_couponMapping").append(data);
        });
    });
    getCouponDetails(cid, "edit");


}


$(document).ready(function () {
    $.get("./create.jsp", function (data) {
        $("#createCoupon").append(data);
        $.get("./mapping.jsp", function (data) {
            $("#couponMapping").append(data);
        });
    });
    $.get("./listCoupon.jsp", function (data) {
        $("#showCoupons").append(data);
    });
    $.get("./listCouponCode.jsp", function (data) {
        $("#listCouponCodes").append(data);
    });


});

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



function checkStatusVal(status) {

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

function checkForDeactivation(status) {

    if (status != null) {

        $("#deactivateCpn").css('display', 'none');
        $("#generateCode").css('display', 'none');
        $("#editCpn").css('display', 'none');
        $("#deleteCpn").css('display', 'none');

    }

    return;

}