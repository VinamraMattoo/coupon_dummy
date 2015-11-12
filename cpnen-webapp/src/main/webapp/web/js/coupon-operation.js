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

function getCouponDetails(couponId, flag) {

    $.ajax({

        type: "GET",
        url: "/cpnen/web/rws/coupon/" + couponId,

        success: function (data) {
            if (flag == "edit")
                populateEdit(data);
            else
                populateView(data, couponId);
        },
        failure: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}


function populateValues(couponId) {

    $.ajax({

        type: "GET",
        url: "/cpnen/web/rws/coupon/" + couponId,

        success: function (data) {

            populateView(data, cpnId);
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}