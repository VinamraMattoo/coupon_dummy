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
    couponId=cid;
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

function editDateTimePicker() {
    $('#edit_applicableFrom').datetimepicker({
        format: 'YYYY-MM-DDHH:mm:ss'
    });

    $('#edit_applicableTill').datetimepicker({
        useCurrent: false,
        format: 'YYYY-MM-DDHH:mm:ss'
    });
    $("#edit_applicableFrom").on("dp.change", function (e) {
        $('#edit_applicableTill').data("DateTimePicker").minDate(e.date);
    });
    $("#edit_applicableTill").on("dp.change", function (e) {
        $('#edit_applicableFrom').data("DateTimePicker").maxDate(e.date);
    });

}


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

function getBrands() {

    var brands = [];
    if ($("#global").is(':checked') === false) {
        $.each(checkedBrands, function (index, value) {

            brands.push(value);

        });
    }
    return brands;

}

function getMappings() {

    var mappings = [];

    if ($("#global").is(':checked') === false) {
        $.each(checkedMapping, function (index, value) {

            mappings.push(value);

        });
    }
    return mappings;

}
function hideOthers(id) {
    var arr = ["createCoupon", "showCoupons", "listCouponCodes", "viewCreatedCoupon", "statusInfo", "editCoupon", "createCode"];
    for (var i = 0; i < arr.length; i++) {
        if (i == id) {
            $("#" + arr[i]).show();
            continue;
        }
        $("#statusBar").empty();
        $("#" + arr[i]).hide();
    }
}
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
        //call edit here
        console.log(value, row, index);
    }
};