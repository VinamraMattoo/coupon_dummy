//  all infrastructure functions are handled in this  js  file

/*function gets create.jsp ,mapping.jsp,listCoupon.jsp and listCouponCodes.jsp into the divs */
$(document).ready(function () {
    $.get("./create.jsp", function (data) {

        $("#createCoupon").empty().append(data);
        dateTimeInput();
        $.get("./mapping.jsp", function (data) {
            $("#couponMapping").append(data);
            checkEventHandler();
        });
    });
    $.get("./editCoupon.jsp", function (data) {
        $("#editCoupon").empty().append(data);
        editDateTimePicker();
        $.get("./mapping.jsp", function (data) {
            $("#edit_couponMapping").append(data);
        });
    });
    $.get("./listCoupon.jsp", function (data) {
        $("#showCoupons").append(data);
    });
    $.get("./listCouponCode.jsp", function (data) {
        $("#listCouponCodes").append(data);
    });

    $.get("./view_coupon.jsp", function (data) {
        $("#viewCreatedCoupon").empty().append(data);

    });


});


/*function hides all the other divs which are there  in the array*/
function hideOtherDivs(id) {
    var divsArray = ["createCoupon", "showCoupons", "listCouponCodes", "viewCreatedCoupon", "statusInfo", "editCoupon", "createCode", "createCouponCode"];
    for (var i = 0; i < divsArray.length; i++) {
        if (i == id) {
            $("#" + divsArray[i]).show();
            continue;
        }
        $("#statusBar").empty();
        $("#" + divsArray[i]).hide();
    }
}


/*function toggles div while editing coupon*/
function couponEditTabSwitcher(id) {
    var arr = ["edit_couponAttributes", "edit_couponMapping", "edit_couponRules"];
    for (var i = 0; i < arr.length; i++) {
        if (i == id) {
            $("#" + arr[i]).show();
            continue;
        }
        $("#" + arr[i]).hide();
    }
}


/*function toggles div while creating coupon*/
function couponCreateTabSwitcher(id) {
    var arr = ["couponAttributes", "couponMapping", "couponRules"];
    for (var i = 0; i < arr.length; i++) {
        if (i == id) {
            $("#" + arr[i]).show();
            continue;
        }
        $("#" + arr[i]).hide();
    }
}
/* function returns the date and time format currently used*/
function getCurrentDateTimeFormat() {
    return 'YYYY-MM-DDTHH:mm:ss';
}


/*checks the type of discount selected while creating*/
function checkDiscountType(flag) {
    if (flag == 'create') {
        var createChoice = $("#ruleType").val();
        if (createChoice == "FLAT") {
            $("#percent").prop("disabled", true);
            $("#percent").empty();
            $("#flatAmount").prop("disabled", false);
        } else {
            $("#flatAmount").prop("disabled", true);
            $("#flatAmount").empty();
            $("#percent").prop("disabled", false);
        }
    }
    else {
        var editChoice = $("#edit_ruleType").val();
        if (editChoice == "FLAT") {
            $("#edit_percent").prop("disabled", true);
            $("#edit_flatAmount").prop("disabled", false);
        } else {
            $("#edit_flatAmount").prop("disabled", true);
            $("#edit_percent").prop("disabled", false);
        }
    }
}

//untreated code

/*function to check value of the status field  */
function checkForPublished(status) {

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
/*function to check value of the deactivation field  */
function checkForDeactivation(status) {

    if (status != null) {

        $("#deactivateCpn").hide();
        $("#generateCode").hide();
        $("#editCpn").hide();
        $("#deleteCpn").hide();

    }

    return;

}

//this function has to be broken

/*function to map the data returned into the view coupon table*/
function populateCouponDetailsTable(response, id) {

    var tags = "";
    for (var key in response) {
        if (key == "couponId") {
            $("#storedCouponId").val(response[key]);
        }
        else if (key == "discountRule") {
            tags += populateDiscount(response[key]);
        }
        else if (key == "applicableFrom" || key == "applicableTill" || key == "createdOn" || key == "publishedOn" || key == "deactivatedOn") {
            tags += "<tr><td>" + key + " </td><td>" + getDateInFormat(response[key]) + "</td></tr>";
            if (key == "publishedOn") {
                checkForPublished(response[key]);
            }
            if (key == "deactivatedOn") {
                checkForDeactivation(response[key]);
            }
        }
        else {

            tags += "<tr><td>" + key + " </td><td>" + response[key] + "</td></tr>";

        }
    }
    $('#viewCurrentCoupon').append(tags);


}
/*function populates the discount rules from the json array into the view table*/
function populateDiscount(discountArray) {
    var tags;
    for (var key in discountArray) {
        tags += "<tr><td>" + key + " </td><td>" + discountArray[key] + "</td></tr>";
    }
    return tags;
}