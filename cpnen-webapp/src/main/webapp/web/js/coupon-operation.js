function createCouponAjax(inputJson) {
    $.ajax({

        type: "POST",
        url: "/cpnen/web/rws/coupon",
        data: JSON.stringify(inputJson[0]),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            showStatusMessage(data, "create");
        },
        failure: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}

function editCouponAjax(editJson) {
    $.ajax({

        type: "PATCH",
        url: "/cpnen/web/rws/coupon/" + couponId,
        data: JSON.stringify(editJson[0]),
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            showStatusMessage(errMsg, "edited");
        },
        failure: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });

}

function getCouponDetails(couponId) {

    $.ajax({

        type: "GET",
        url: "/cpnen/web/rws/coupon/" + couponId,

        success: function (data) {

            populateEdit(data);
        },
        failure: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}