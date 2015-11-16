/*
 *
 * */

/*
 * To show the coupon values just created for the coupon-id
 */
function showCoupon(id) {
    hideOtherDivs(3);
    getCouponDetails(id, "view");
    return false;
}

/*function which is called when edit buttn is clicked
 *calls a generic editCoupon() function
 */
function onEditClick() {
    var id = $("#storedCouponId").val();
    editCoupon(id);
}

/*gets the editCoupon.jsp into the div
 and the mapping.jsp into the div*/
function editCoupon(cid) {
    hideOtherDivs(5);
    getCouponDetails(cid, "edit");
}


/*function shows messages in the main div and error messages*/
function showStatusMessage(coupnId, eventType) {
    hideOtherDivs(4);
    if (eventType == "create") {

        $("#infoPageId").html(" Your Coupon has been created successfully. Click <a href=\"#\" onclick=\"showCoupon(" + coupnId + ")\">here</a> to view the coupon.");
    }
    else if (eventType == "edited") {
        $("#infoPageId").html(" Your Coupon has been modified successfully. Click <a href=\"#\" onclick=\"showCoupon(" + coupnId + ")\">here</a> to view the coupon.");
    }
    else if (eventType == "errMsg") {
        $("#infoPageId").html(" Something went wrong. Server responded with the message " + eventType + ".");
    }

    return false;
}


/*checkEventHandler function is called when a check or uncheck event occurs*/
var checkedMapping = [];
var checkedBrands = [];
function checkEventHandler() {
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
        console.log(JSON.stringify(rows));
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
