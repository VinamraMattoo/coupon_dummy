//  all ajax calls are handled in this  js  file

"use strict";
/* ========= create coupon ajax function ===========
 *  ajax call to server with the  create  json data and hits  the  particular  url
 */


function createCouponAjax(inputJson, flag) {
    $.ajax({

        type: "POST",
        url: "../rws/coupon",
        data: JSON.stringify(inputJson[0]),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            refreshOnUpdate();
            if (flag == "fast") {
                fastCouponCode(data);
            } else
                showStatusMessage(data, "create");
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}
/* ========= edit coupon ajax function ===========
 *  ajax call to server with the  edit json data and hits  the  particular  url
 */

function editCouponAjax(editJson, couponId) {
    $.ajax({

        type: "PUT",
        url: "../rws/coupon/" + couponId,
        data: JSON.stringify(editJson[0]),
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            refreshOnUpdate();
            showStatusMessage(couponId, "edited");
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
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
        url: "../rws/coupon/" + couponId,

        success: function (data) {
            if (flag == "edit") {
                $("#editStatusBar").empty();
                $("#edit_publish").prop('checked', false);
                populateEditCouponValues(data);
            }
            else if (flag == "publish") {
                validateAndPublish(data, couponId);
            }
            else if (flag == "quick") {
                populateQuickCreation(data);
            }
            else
                populateCouponDetailsTable(data, couponId);
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }

    });
}
/*coupon deletion ajax call*/
function deleteCoupon(couponsId, flag, lastUpdatedOn) {
    $.ajax({

        type: "DELETE",
        url: "../rws/coupon/" + couponsId + "?lastUpdatedOn=" + lastUpdatedOn,

        success: function () {
            refreshOnUpdate();
            if (flag == "list")
                showStatusMessage(couponsId, "deleted");
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}
/*coupon deactivation ajax call*/
function deactivateCoupon(deactivationId, flag, lastUpdatedOn) {
    $.ajax({

        type: "PUT",
        url: "../rws/coupon/" + deactivationId + "/deactivate" + "?lastUpdatedOn=" + lastUpdatedOn,
        success: function () {
            refreshOnUpdate();
            if (flag == "list")
                showStatusMessage(deactivationId, "deactivated");
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}

/*publish coupon ajax call*/
function publishCoupon(cid, lastUpdatedOn) {
    $.ajax({

        type: "PUT",
        url: "../rws/coupon/" + cid + "/publish" + "?lastUpdatedOn=" + lastUpdatedOn,
        success: function () {
            showStatusMessage(cid, "publish");
            refreshOnUpdate();
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}

/*ajax call for extending the validity*/

function extendValidityOperation(validity, couponId, lastUpdatedOn) {

    $.ajax({

        type: "PUT",
        url: "../rws/coupon/" + couponId + "/extendValidity/" + validity + "?lastUpdatedOn=" + lastUpdatedOn,
        success: function (data) {
            refreshOnUpdate();
            showStatusMessage(couponId, "edited");
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },


        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }

    });
}
/*create coupon code ajax call*/
function createCouponCodeAjax(inputJson, couponId) {
    $.ajax({

        type: "POST",
        url: "../rws/coupon/" + couponId + "/code",
        data: JSON.stringify(inputJson[0]),
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            refreshOnUpdate();
            showStatusMessage(data, couponId);
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}
/*ajax call for viewing code details*/
function getCodeDetails(couponId, codeId) {
    $.ajax({
        type: "GET",
        url: "../rws/coupon/" + couponId + "/code/" + codeId,
        success: function (data) {
            populateViewCode(data);
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });

}

/*ajax call for deactivating a coupon code*/
function deactivateCode(couponId, codeId) {
    $.ajax({

        type: "PUT",
        url: "../rws/coupon/" + couponId + "/code/" + codeId + "/deactivate",

        success: function () {
            refreshOnUpdate();
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}

/*ajax call for getting referral details*/
function getReferralDetails() {
    $.ajax({

        type: "GET",
        url: "../rws/getReferrers",

        success: function (data) {
            referralJSON = data;
            trimmedJSON = referralJSON;
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}

/*ajax call to get category details*/
function getCategoryDetails() {
    $.ajax({

        type: "GET",
        url: "../rws/coupon/getCategories",

        success: function (data) {
            categoryJSON = data;
            loadAllJSP();
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}

/*ajax call for getting brand details*/
function getBrandsDetails() {
    $.ajax({

        type: "GET",
        url: "../rws/brands",
        success: function (data) {
            brandJSON = data;
        },
        statusCode: {
            409: function (request) {
                var message;
                message = request.getResponseHeader('X-Cpnen-web-Error-Detail');
                showStatusMessage(message, "conflict");
            }
        },
        error: function (errMsg) {
            showStatusMessage(errMsg, "errMsg");
        }
    });
}