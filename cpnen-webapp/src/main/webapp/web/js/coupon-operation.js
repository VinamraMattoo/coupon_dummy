//  all ajax calls are handled in this  js  file


/* ========= create coupon ajax function ===========
 *  ajax call to server with the  create  json data and hits  the  particular  url
 */


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
/* ========= edit coupon ajax function ===========
 *  ajax call to server with the  edit json data and hits  the  particular  url
 */

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
/* ========= get coupon details ajax function ===========
 *  ajax call to server with the  get coupon details json data and hits  the  particular  url
 *  for showing veiw coupon page
 */

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


//======================================  INCOMPLETE CODES  =======================================
function onDeactivationClick() {
    confirm("Do you really want to deactivate?");
}

function onDeleteClick() {
    confirm("Do you really want to delete?");
}
function onGenerateCodeClick() {
    confirm("showing couponcodes");
}


