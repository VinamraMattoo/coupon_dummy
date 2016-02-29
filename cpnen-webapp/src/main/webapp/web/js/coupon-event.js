/*
 *all button click events other than submit is handled here
 * */
"use strict";
/*
 * To show the coupon values just created for the coupon-id
 */
var logoutFlag;

/*function is called when a view coupon is requested*/
function showCoupon(ids) {
    getCouponDetails(ids, "view");
}

/*function called when logout is pressed asking for user confirmation*/
function logoutFunction() {
    var confirmation = confirm("Do you really want to logout?");
    if (confirmation == true) {
        logoutFlag = true;
        $("#logoutForm").attr('action', "../logout");
    }
    else {
        logoutFlag = false;
        return;
    }
}


/*function to refresh listing tables*/
function refreshOnUpdate() {

    $("#serverTable").bootstrapTable('refresh', {url: '../rws/coupon/list'});
    $("#codeListTable").bootstrapTable('refresh', {url: '../rws/coupon/codes'});

}
/*function calls the show coupon page*/
function showCouponFromCode() {
    showCoupon($("#couponFromCode").val());
}

/*function which is called when edit buttn is clicked
 *calls a generic showEditCoupon() function
 */
function viewCouponEdit() {
    var id = $("#storedCouponId").val();
    showEditCoupon(id);
}

/*shows the edit coupon div */
function showEditCoupon(cid) {
    getCouponDetails(cid, "edit");
}
/*function makes products and tables disabled*/
function globalSelected(flag) {
    var globalId, divToBeHidden, context, tableArray = [], tableInfoArray = [];
    var mappingArray = ['area', 'product', 'brand', 'referral'];

    if (flag == 0) {
        globalId = "createGlobal";
        divToBeHidden = "couponMapping";
        context = 'create';
        tableInfoArray = ["areaTable", "mappingTable", "brandTable", "referrersTable"];
        tableArray = ["createMappingAreaGlobal", "createMappingProductsGlobal", "createMappingBrandsGlobal", "createMappingReferrerSourcesGlobal"];
    }
    else if (flag == 1) {
        globalId = "editGlobal";
        divToBeHidden = "edit_couponMapping";
        context = 'edit';
        tableInfoArray = ["editAreaTable", "editMappingTable", "editBrandTable", "editReferrersTable"];
        tableArray = ["editMappingAreasGlobal", "editMappingProductsGlobal", "editMappingBrandsGlobal", "editMappingReferrerSourcesGlobal"];
    }
    else if (flag == 2) {
        globalId = "quickGlobal";
        divToBeHidden = "quick_couponMapping";
        context = 'quick';
        tableInfoArray = ["quickAreaTable", "quickMappingTable", "quickBrandTable", "quickReferrersTable"];
        tableArray = ["quickMappingAreasGlobal", "quickMappingProductsGlobal", "quickMappingBrandsGlobal", "quickMappingReferrerSourcesGlobal"];
    }
    else {
        globalId = "fastGlobal";
        divToBeHidden = "fast_couponMapping";
        context = 'fast';
        tableInfoArray = ["fastAreaTable", "fastMappingTable", "fastBrandTable", "fastReferrersTable"];
        tableArray = ["fastMappingAreasGlobal", "fastMappingProductsGlobal", "fastMappingBrandsGlobal", "fastMappingReferrerSourcesGlobal"];
    }
    if (($("#" + globalId).is(':checked')) == true) {
        for (var i = 0; i < tableInfoArray.length; i++) {
            $("#" + tableArray[i]).attr('disabled', 'disabled');
        }
        for (var j = 0; j < mappingArray.length; j++) {
            tableGlobalSelector(context, mappingArray[j], 1);
        }
    }
    else {
        $("#" + divToBeHidden).find('input').removeAttr('disabled');
        for (var k = 0; k < tableInfoArray.length; k++) {
            var id = tableInfoArray[k] + "Info";
            $("#" + id).empty();
        }
    }
}
/*jquery event handler to show manage coupon glyphicon*/
$('#navBarToggle').click(function () {
    if ($('#glyphiconId').hasClass('glyphicon-chevron-up')) {
        $('#glyphiconId').removeClass('glyphicon-chevron-up');
        $('#glyphiconId').addClass('glyphicon-chevron-down');
    }
    else {
        $('#glyphiconId').removeClass('glyphicon-chevron-down');
        $('#glyphiconId').addClass('glyphicon-chevron-up');
    }
});
/*function shows messages in the main div and error messages*/
function showStatusMessage(serverResponse, eventType) {
    hideOtherDivs(4);
    if (eventType == "create") {

        $("#statusInfo").html(" <br><br><h3>Coupon Created</h3><br>Your coupon has been created successfully. <br> Click <a href=\"javascript:void(0)\" onclick=\"showCoupon(" + serverResponse + ")\">here</a> to view the coupon.");
    }
    else if (eventType == "edited") {
        $("#statusInfo").html(" <br><br><h3>Coupon Edited</h3><br>Your coupon has been modified successfully.<br> Click <a href=\"javascript:void(0)\" onclick=\"showCoupon(" + serverResponse + ")\">here</a> to view the coupon.");
    }
    else if (eventType == "publish") {
        $("#statusInfo").html(" <br><br><h3>Coupon Published</h3><br>Your coupon has been published.<br> Click <a href=\"javascript:void(0)\" onclick=\"showCoupon(" + serverResponse + ")\">here</a> to view the coupon.");
    }
    else if (eventType == "conflict") {
        $("#statusInfo").html(" <br><br><h3>Conflict </h3> <br><h4>" + serverResponse + "</h4>");
    }
    else if (eventType == "errMsg") {
        $("#statusInfo").html(" <br><br><h3>Server Error </h3><br> Server responded with the message <br><h4>Response status : " + serverResponse['status'] + "<br>Response text : " + serverResponse['statusText'] + "</h4>");
    }
    else if (eventType == "deactivated") {
        $("#statusInfo").html("<br><br><h3>Coupon Deactivated </h3><br> Your coupon has been deactivated successfully.<br> Click <a href=\"javascript:void(0)\" onclick=\"showCoupon(" + serverResponse + ")\">here</a> to view the coupon.");
    }
    else if (eventType == "deleted") {
        $("#statusInfo").html("<br><br><h3>Coupon Deleted </h3><br> Your coupon has been deleted successfully.<br> Click <a href=\"javascript:void(0)\" onclick=\"hideOtherDivs(" + 0 + ")\">here</a> to create new coupon.");
    }

    else if (eventType == "codeDeactivated") {
        $("#statusInfo").html(" <br><br><h3>Code Deactivated </h3><br> Your code has been deactivated successfully.");
        $("#codeListTable").bootstrapTable('refresh');
    }
    else {
        $("#statusInfo").html("<br><br><h3>Coupon and Code created </h3><br> Your quick coupon has been created successfully.<br> Click <a href=\"javascript:void(0)\" onclick=\"showCoupon(" + eventType + ")\">here</a> to view the coupon." +
            "<br> Click <a href=\"javascript:void(0)\" onclick=\"getCodeDetails(" + eventType + "," + serverResponse + ")\">here</a> to view the code details. ");
    }

    return false;
}

/*function to confirm the deactivation*/
function deactivateThisCoupon() {
    var confirmation = confirm("Do you really want to deactivate?");
    if (confirmation == true) {
        var couponId = $("#storedCouponId").val();
        var lastUpdatedOn = $("#viewStoredLastUpdatedOn").val();
        deactivateCoupon(couponId, "list", lastUpdatedOn);
    }
}

function deactivatingThisCoupon(id, str, lastUpdatedTime) {
    var confirmation = confirm("Do you really want to deactivate?");
    if (confirmation == true) {
        deactivateCoupon(id, str, lastUpdatedTime);
    }
}
function deactivatingThisCode(id, str) {
    var confirmation = confirm("Do you really want to deactivate?");
    if (confirmation == true) {
        deactivateCode(id, str);
    }
}
function deletingThisCoupon(id, str, lastUpdatedOn) {
    var confirmation = confirm("Do you really want to delete?");
    if (confirmation == true) {
        deleteCoupon(id, str, lastUpdatedOn);
    }
}

/*function to confirm the deletion*/
function deleteThisCoupon() {
    var confirmation = confirm("Do you really want to delete?");
    if (confirmation == true) {
        var couponId = $("#storedCouponId").val();
        var lastUpdatedOn = $("#viewStoredLastUpdatedOn").val();
        deleteCoupon(couponId, "list", lastUpdatedOn);
    }
}

/*function called quick coupon creation is clicked*/
function quickCoupon() {
    var id = $("#storedCouponId").val();
    getCouponDetails(id, "quick");
}

