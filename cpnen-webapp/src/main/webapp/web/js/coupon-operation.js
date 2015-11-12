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


function populateView(response, id) {
    couponId = id;
    var tags = "";
    for (var key in response) {

        tags += "<tr><td>" + key + " </td><td>" + response[key] + "</td></tr>";
        if (key == "publishedOn") {
            checkStatusVal(response[key]);
        }
        if (key == "deactivatedOn") {
            checkForDeactivation(response[key]);
        }


        /*   for (var key2 in key) {
         tags += "<tr><td>" + key2 + " </td><td>" + key[key2] + "</td></tr>";
         if (key2 == "publishedOn") {
         checkStatusVal([key2]);
         }
         }*/


    }

    $('#viewCurrentCoupon').append(tags);


}
//should write code from here
function onDeactivationClick() {
    confirm("Do you really want to deactivate?");
}

function onDeleteClick() {
    confirm("Do you really want to delete?");
}
function onGenerateCodeClick() {
    confirm("showing couponcodes");
}