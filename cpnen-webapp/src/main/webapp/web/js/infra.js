//  all utility functions are handled in this  js  file


/* ========= show coupon function ===========
 * To show the coupon values just created for the coupon-id
 */


function showCoupon(Id) {
    hideOthers(3);
    $.get("./view_coupon.jsp", function (data) {
        $("#viewCreatedCoupon").empty().append(data);
        getCouponDetails(Id, "view");
    });

    return false;
}

/*function which is called when edit buttn is clicked
 *calls a generic editCoupon() function
 */
function onEditClick() {
    var id = couponId;
    editCoupon(id);
}

/*gets the editCoupon.jsp into the div
 and the mapping.jsp into the div*/
function editCoupon(cid) {
    couponId = cid;
    hideOthers(5);
    $.get("./editCoupon.jsp", function (data) {
        $("#editCoupon").empty().append(data);
        editDateTimePicker();
        $.get("./mapping.jsp", function (data) {
            $("#edit_couponMapping").append(data);
        });
    });
    getCouponDetails(cid, "edit");


}
/*date time picker for calender input */
function dateTimeInput() {
    $('#applicableFrom').datetimepicker({
        format: 'YYYY-MM-DDTHH:mm:ss'
    });

    $('#applicableTill').datetimepicker({
        useCurrent: false,
        format: 'YYYY-MM-DDTHH:mm:ss'
    });
    $("#applicableFrom").on("dp.change", function (e) {
        $('#applicableTill').data("DateTimePicker").minDate(e.date);
    });
    $("#applicableTill").on("dp.change", function (e) {
        $('#applicableFrom').data("DateTimePicker").maxDate(e.date);
    });

}

/*edit date time picker for calender input*/
function editDateTimePicker() {
    $('#edit_applicableFrom').datetimepicker({
        format: 'YYYY-MM-DDTHH:mm:ss'
    });

    $('#edit_applicableTill').datetimepicker({
        useCurrent: false,
        format: 'YYYY-MM-DDTHH:mm:ss'
    });
    $("#edit_applicableFrom").on("dp.change", function (e) {
        $('#edit_applicableTill').data("DateTimePicker").minDate(e.date);
    });
    $("#edit_applicableTill").on("dp.change", function (e) {
        $('#edit_applicableFrom').data("DateTimePicker").maxDate(e.date);
    });

}

/*function gets create.jsp ,mapping.jsp,listCoupon.jsp and listCouponCodes.jsp into the divs */
$(document).ready(function () {
    $.get("./create.jsp", function (data) {

        $("#createCoupon").append(data);
        dateTimeInput();
        $.get("./mapping.jsp", function (data) {
            $("#couponMapping").append(data);
            eventHandler();
        });
    });
    $.get("./listCoupon.jsp", function (data) {
        $("#showCoupons").append(data);
    });
    $.get("./listCouponCode.jsp", function (data) {
        $("#listCouponCodes").append(data);
    });


});

/*function shows messages in the main div and error messages*/
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

/*function to check value of the status field  */
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
/*function to check value of the deactivation field  */
function checkForDeactivation(status) {

    if (status != null) {

        $("#deactivateCpn").css('display', 'none');
        $("#generateCode").css('display', 'none');
        $("#editCpn").css('display', 'none');
        $("#deleteCpn").css('display', 'none');

    }

    return;

}

/*eventHandler function is called when a check or uncheck event occurs*/
var checkedMapping = [];
var checkedBrands = [];
function eventHandler() {
    $('#mappingTable').on('check.bs.table', function (e, row) {
        var val = row.type;
        checkedMapping.push({
            productId: row.id,
            name: row.name,
            type: val.toUpperCase()
        });
        console.log(checkedMapping);
    });
    $("#mappingTable").on('check-all.bs.table', function (rows) {
        document.write(JSON.stringify(rows));
    });


    $('#mappingTable').on('uncheck.bs.table', function (e, row) {
        $.each(checkedMapping, function (index, value) {

            if (value.id === row.id) {
                checkedMapping.splice(index, 1);
            }
        });
        console.log(checkedMapping);
    });


    $("#brandTable").on('check.bs.table', function (e, row) {

        checkedBrands.push({
            brandId: row.id
        });
        console.log(checkedBrands);
    });

    $("#brandTable").on('uncheck.bs.table', function (e, row) {
        $.each(checkedBrands, function (index, value) {
            if (value.id === row.id) {
                checkedBrands.splice(index, 1);
            }
        });
        console.log(checkedBrands);
    });
}
/*returns the brands checked*/
function getBrands() {

    var brands = [];
    if ($("#global").is(':checked') === false) {
        $.each(checkedBrands, function (index, value) {

            brands.push(value);

        });
    }
    return brands;

}
/*returns the mappings checked*/
function getMappings() {

    var mappings = [];

    if ($("#global").is(':checked') === false) {
        $.each(checkedMapping, function (index, value) {

            mappings.push(value);

        });
    }
    return mappings;

}
/*function hides all the other divs which are there  in the array*/
function hideOthers(id) {
    var divArray = ["createCoupon", "showCoupons", "listCouponCodes", "viewCreatedCoupon", "statusInfo", "editCoupon", "createCode"];
    for (var i = 0; i < divArray.length; i++) {
        if (i == id) {
            $("#" + divArray[i]).show();
            continue;
        }
        $("#statusBar").empty();
        $("#" + divArray[i]).hide();
    }
}
/*function toggles div while editing coupon*/
function editOthers(id) {
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
function showOthers(id) {
    var arr = ["couponAttributes", "couponMapping", "couponRules"];
    for (var i = 0; i < arr.length; i++) {
        if (i == id) {
            $("#" + arr[i]).show();
            continue;
        }
        $("#" + arr[i]).hide();
    }
}

/*checks the type of discount selected while creating*/
function checkDicountType(flag) {
    if (flag == 'create') {
        var createChoice = $("#ruleType").val();
        if (createChoice == "FLAT") {
            $("#percent").prop("disabled", true);
            $("#flatAmount").prop("disabled", false);
        } else {
            $("#flatAmount").prop("disabled", true);
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
/*data formatter of listCouponCode.jsp to add a delete option*/
function codeOptionsFormatter(value, row, index) {
    return [

        '  <a class="edit ml10" href="javascript:void(0)" title="deactivate">',
        '<span title="deactivate"  class="glyphicon glyphicon-remove"></span>',
        '</a>'
    ].join('');
}

window.codeOptionsEvents = {
    'click .remove': function (value, row, index) {
        alert(c_id);
        //call delete here
        console.log(value, row, index);
    }
};


/*function to map the data returned into the view coupon table*/
function populateView(response, id) {
    couponId = id;
    var tags = "";
    for (var key in response) {

        if (key == "discountRule") {
            tags += populateDiscount(response[key]);
        }
        else if (key == "applicableFrom" || key == "applicableTill" || key == "createdOn" || key == "publishedOn" || key == "deactivatedOn") {
            tags += "<tr><td>" + key + " </td><td>" + getDateInFormat(response[key]) + "</td></tr>";
            if (key == "publishedOn") {
                checkStatusVal(response[key]);
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