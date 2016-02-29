//this js file controls the  creation of json from create coupon page and edit coupon page

"use strict";

/*=======create coupon json function=======
 * the function takes data  from the  various input fields and creates a key value pair json structure
 * for the checkbox is :checked is used to get a boolean value
 *for date creation getMilliSec function is called and value is converted to milliseconds and passed into the  json
 */

function submitCreateCoupon(context) {

    var data = $("#createCoupon").map(function () {

            var JsonData = {

                "name": $("#name").val(),

                "description": $("#description").val(),

                "inclusive": !($("#inclusive").is(':checked')),

                "applicationType": $("#applicationType").val(),

                "actorType": $("#actorType").val(),

                "contextType": $("#contextType").val(),

                "applicableFrom": getMilliSec($("#applicableFrom").val()),

                "applicableTill": getMilliSec($("#applicableTill").val()),

                "transactionValMin": $("#transactionValMin").val(),

                "transactionValMax": $("#transactionValMax").val(),

                "discountAmountMin": $("#discountAmountMin").val(),

                "discountAmountMax": $("#discountAmountMax").val(),

                "global": $("#createGlobal").is(':checked'),

                "isAllAreas": ($("#createGlobal").is(':checked')) ? true : $('#createMappingAreaGlobal').is(':checked'),

                "isAllProducts": ($("#createGlobal").is(':checked')) ? true : $('#createMappingProductsGlobal').is(':checked'),

                "isAllBrands": ($("#createGlobal").is(':checked')) ? true : $('#createMappingBrandsGlobal').is(':checked'),

                "isB2B": $("#createB2BId").is(':checked'),

                "isB2C": $("#createB2CId").is(':checked'),

                "category": $("#couponCategory").val(),

                "applicableUseCount": ($("#applicableUseCount").prop('disabled') == true) ? "" :
                    $("#applicableUseCount").val(),

                "nthTime": ($("#nthTime").prop('disabled') == true) ? "" : $("#nthTime").val(),

                "published": (context == 'publish'),

                "nthTimeRecurring": $("#nthTimeRecurring").is(':checked'),

                "productMapping": getMappings("create"),

                "brandMapping": getBrands("create"),

                "referrerMapping": getReferrals("create"),

                "areaMapping": getAreaMappings("create"),

                "discountRule": {

                    "description": $("#ruleDesc").val(),

                    "ruleType": $("#ruleType").val(),

                    "discountFlatAmount": getDiscountValue("FLAT", "create"),

                    "discountPercentage": getDiscountValue("PERCENTAGE", "create")

                }

            };
            return JsonData;
        }
    ).get();


//check if status is published
    $("#statusBar").empty();
    if (checkIfPublished(data, "create", context) === false) {
        createCouponAjax(data, "create");
        $("#statusBar").empty();
        $("#createFlatNotice").empty();

        clearCouponDetails();
    }
}
/*function clears the current values in create coupon divs*/
function clearCouponDetails() {
    couponCreateTabSwitcher(0);

    $('#couponAttributes :input').not(':button, :submit, :reset, :hidden, :checkbox, :radio').val('');
    $('#couponAttributes :checkbox, :radio').prop('checked', false);
    couponCreateTabSwitcher(2);

    $('#couponRules :input').not(':button, :submit, :reset, :hidden, :checkbox, :radio').val('');
    $('#couponRules :checkbox, :radio').prop('checked', false);

    $('#createGlobal').prop('checked', false);

    $("#create_name_response").empty();
    $("#mappingTable").bootstrapTable('refresh');
    $("#brandTable").bootstrapTable('refresh');
    $("#areaTable").bootstrapTable('refresh');
    $("#referrersTable").bootstrapTable('refresh');
    couponCreateTabSwitcher(0);
    areaReferralMappingSwitcher('area', 'create');
    $("#statusBar").empty();

    $("#areaTableInfo").empty();
    $("#mappingTableInfo").empty();
    $("#brandTableInfo").empty();
    $("#referralTableInfo").empty();

}

/*function clears the current values in fast coupon divs*/
function clearFastCouponDetails() {
    couponFastTabSwitcher(0);

    $('#fast_couponAttributes :input').not(':button, :submit, :reset, :hidden, :checkbox, :radio').val('');
    $('#fast_couponAttributes :checkbox, :radio').prop('checked', false);
    couponFastTabSwitcher(2);

    $('#fast_couponRules :input').not(':button, :submit, :reset, :hidden, :checkbox, :radio').val('');
    $('#fast_couponRules :checkbox, :radio').prop('checked', false);

    $('#fastGlobal').prop('checked', false);

    $('#fast_code_response').empty();
    $('#fast_name_response').empty();

    $("#fastMappingTable").bootstrapTable('refresh');
    $("#fastBrandTable").bootstrapTable('refresh');
    $("#fastAreaTable").bootstrapTable('refresh');
    $("#fastReferrersTable").bootstrapTable('refresh');
    couponFastTabSwitcher(0);
    areaReferralMappingSwitcher('area', 'fast');
    $("#fast_statusBar").empty()
}


/*function gets the discount value from the form*/
function getDiscountValue(flag, eventType) {

    var createRuleType;
    var createValueId;
    if (eventType == "create") {
        createRuleType = "ruleType";
        createValueId = "discountValue";
    }
    else if (eventType == "edit") {
        createRuleType = "edit_ruleType";
        createValueId = "edit_discountValue";
    }
    else if (eventType == "fast") {
        createRuleType = "fast_ruleType";
        createValueId = "fast_discountValue";
    }

    else {
        createRuleType = "quick_ruleType";
        createValueId = "quick_discountValue";
    }

    var discountType = $("#" + createRuleType).val();
    if (discountType == flag) {
        return $("#" + createValueId).val()
    }
}
/*get current time zone offset from utc*/
function getUTCOffset() {
    var offset = new Date().getTimezoneOffset();
    return offset * 60000;
}

//function to convert the  given date data into milliseconds

function getMilliSec(date) {
    if (date == "")
        return "";
    /*console.log("original"+moment(date).valueOf());
     console.log("to utc"+localToUTC(moment(date).valueOf()));
     return localToUTC(moment(date).valueOf());*/
    return moment(date).valueOf();

}
/*converts current local time to UTC time format*/
/*function localToUTC(currentDate) {
 return currentDate + getUTCOffset();
 }*/

/*converts current UTC time to local time format*/
/*function UTCtoLocal(currentDate) {
 return currentDate - getUTCOffset();
 }*/

/* ============ edit coupon json function ==============
 * function creates the  json data for the  edit coupon forms
 * similar functionality as create coupon json function
 */


function submitEditCoupon(context) {

    var editJson = $("#editCouponForm").map(function () {
            var editArray = {

                "name": $("#edit_name").val(),

                "description": $("#edit_description").val(),

                "inclusive": !($("#edit_inclusive").is(':checked')),

                "applicationType": $("#edit_applicationType").val(),

                "actorType": $("#edit_actorType").val(),

                "contextType": $("#edit_contextType").val(),

                "applicableFrom": getMilliSec($("#edit_applicableFrom").val()),

                "applicableTill": getMilliSec($("#edit_applicableTill").val()),

                "transactionValMin": $("#edit_transactionValMin").val(),

                "transactionValMax": $("#edit_transactionValMax").val(),

                "discountAmountMin": $("#edit_discountAmountMin").val(),

                "discountAmountMax": $("#edit_discountAmountMax").val(),

                "global": $("#editGlobal").is(':checked'),

                "isAllAreas": ($("#editGlobal").is(':checked')) ? true : $('#editMappingAreasGlobal').is(':checked'),

                "isAllProducts": ($("#editGlobal").is(':checked')) ? true : $('#editMappingProductsGlobal').is(':checked'),

                "isAllBrands": ($("#editGlobal").is(':checked')) ? true : $('#editMappingBrandsGlobal').is(':checked'),

                "isB2B": $("#editB2BId").is(':checked'),

                "isB2C": $("#editB2CId").is(':checked'),

                "applicableUseCount": ($("#edit_applicableUseCount").prop('disabled') == true) ? "" :
                    $("#edit_applicableUseCount").val(),

                "nthTime": ($("#edit_nthTime").prop('disabled') == true) ? "" : $("#edit_nthTime").val(),

                "category": $("#edit_couponCategory").val(),

                "nthTimeRecurring": $("#edit_nthTimeRecurring").is(':checked'),

                "published": (context == 'publish'),

                "productMapping": getMappings("edit"),

                "brandMapping": getBrands("edit"),

                "referrerMapping": getReferrals("edit"),

                "areaMapping": getAreaMappings("edit"),

                "lastUpdatedOn": $("#editStoredLastUpdatedOn").val(),

                "discountRule": {

                    "description": $("#edit_ruleDesc").val(),

                    "ruleType": $("#edit_ruleType").val(),

                    "discountFlatAmount": getDiscountValue("FLAT", "edit"),

                    "discountPercentage": getDiscountValue("PERCENTAGE", "edit")

                }

            };
            return editArray;
        }
    ).
        get();

    var couponId = $("#couponId").val();
    $("#editStatusBar").empty();
    if (checkIfPublished(editJson, "edit", context) == false) {
        couponEditTabSwitcher(0);
        $("#editStatusBar").empty();
        $("#editFlatNotice").empty();

        editCouponAjax(editJson, couponId);
    }
}

/*coupon code creation JSON creation*/
function submitCreateCouponCode() {
    var editJson = $("#createCouponCodeForm").map(function () {
        var editArray = {

            "code": $("#codeName").val(),
            "channelName": $("#codeChannel").val(),
            "reservations": []
        };
        return editArray;
    }).get();


    var couponId = $("#storedCouponId").val();
    createCouponCodeAjax(editJson, couponId);
}
/*copy coupon creation JSON creation*/
function submitQuickCoupon(context) {
    var quickJson = $("#quickCouponForm").map(function () {
        var quickArray = {

            "name": $("#quick_name").val(),

            "description": $("#quick_description").val(),

            "inclusive": !($("#quick_inclusive").is(':checked')),

            "applicationType": $("#quick_applicationType").val(),

            "actorType": $("#quick_actorType").val(),

            "contextType": $("#quick_contextType").val(),

            "applicableFrom": getMilliSec($("#quick_applicableFrom").val()),

            "applicableTill": getMilliSec($("#quick_applicableTill").val()),

            "transactionValMin": $("#quick_transactionValMin").val(),

            "transactionValMax": $("#quick_transactionValMax").val(),

            "discountAmountMin": $("#quick_discountAmountMin").val(),

            "discountAmountMax": $("#quick_discountAmountMax").val(),

            "global": $("#quickGlobal").is(':checked'),

            "isAllAreas": ($("#quickGlobal").is(':checked')) ? true : $('#quickMappingAreasGlobal').is(':checked'),

            "isAllProducts": ($("#quickGlobal").is(':checked')) ? true : $('#quickMappingProductsGlobal').is(':checked'),

            "isAllBrands": ($("#quickGlobal").is(':checked')) ? true : $('#quickMappingBrandsGlobal').is(':checked'),

            "isB2B": $("#quickB2BId").is(':checked'),

            "isB2C": $("#quickB2CId").is(':checked'),


            "category": $("#quick_couponCategory").val(),

            "applicableUseCount": ($("#quick_applicableUseCount").prop('disabled') == true) ? "" :
                $("#quick_applicableUseCount").val(),

            "nthTime": ($("#quick_nthTime").prop('disabled') == true) ? "" : $("#quick_nthTime").val(),


            "nthTimeRecurring": $("#quick_nthTimeRecurring").is(':checked'),

            "published": (context == 'publish'),

            "productMapping": getMappings("quick"),

            "brandMapping": getBrands("quick"),

            "referrerMapping": getReferrals("quick"),

            "areaMapping": getAreaMappings("quick"),


            "discountRule": {

                "description": $("#quick_ruleDesc").val(),

                "ruleType": $("#quick_ruleType").val(),

                "discountFlatAmount": getDiscountValue("FLAT", "quick"),

                "discountPercentage": getDiscountValue("PERCENTAGE", "quick")

            }
        };
        return quickArray;
    }).get();

    $("#quickStatusBar").empty();

    if (checkIfPublished(quickJson, "quick", context) == false) {
        couponQuickTabSwitcher(0);
        $("#quickStatusBar").empty();
        $("#quickFlatNotice").empty();

        createCouponAjax(quickJson, "quick");
    }
}

/*function called on fast coupon submission*/
function submitFastCoupon() {

    var fastJson = $("#fastCouponForm").map(function () {
        var fastArray = {

            "name": $("#fast_name").val(),

            "description": "",

            "inclusive": !($("#fast_inclusive").is(':checked')),

            "applicationType": $("#fast_applicationType").val(),

            "actorType": $("#fast_actorType").val(),

            "contextType": $("#fast_contextType").val(),

            "applicableFrom": getMilliSec($("#fast_applicableFrom").val()),

            "applicableTill": getMilliSec($("#fast_applicableTill").val()),

            "discountAmountMax": $("#fast_discountAmountMax").val(),

            "discountAmountMin": $("#fast_discountAmountMin").val(),

            "global": $("#fastGlobal").is(':checked'),

            "isAllAreas": ($("#fastGlobal").is(':checked')) ? true : $('#fastMappingAreasGlobal').is(':checked'),

            "isAllProducts": ($("#fastGlobal").is(':checked')) ? true : $('#fastMappingProductsGlobal').is(':checked'),

            "isAllBrands": ($("#fastGlobal").is(':checked')) ? true : $('#fastMappingBrandsGlobal').is(':checked'),

            "isB2B": $("#fastB2BId").is(':checked'),

            "isB2C": $("#fastB2CId").is(':checked'),

            "applicableUseCount": ($("#fast_applicableUseCount").prop('disabled') == true) ? "" :
                $("#fast_applicableUseCount").val(),

            "nthTime": ($("#fast_nthTime").prop('disabled') == true) ? "" : $("#fast_nthTime").val(),

            "category": $("#fast_couponCategory").val(),

            "nthTimeRecurring": $("#fast_nthTimeRecurring").is(':checked'),

            "published": true,

            "productMapping": getMappings("fast"),

            "brandMapping": getBrands("fast"),

            "referrerMapping": getReferrals("fast"),

            "areaMapping": getAreaMappings("fast"),

            "discountRule": {

                "ruleType": $("#fast_ruleType").val(),

                "discountFlatAmount": getDiscountValue("FLAT", "fast"),

                "discountPercentage": getDiscountValue("PERCENTAGE", "fast")

            }
        };
        return fastArray;
    }).get();

    $("#fastStatusBar").empty();


    if (checkIfPublished(fastJson, "fast", "fast") == false) {
        couponFastTabSwitcher(0);
        $("#fastStatusBar").empty();
        $("#fastFlatNotice").empty();

        createCouponAjax(fastJson, "fast");
    }


}
/*function does the code creation part in fast creation */
function fastCouponCode(couponId) {
    var fastCodeJson = $("#fastCouponForm").map(function () {
        var editArray = {

            "code": $("#fast_code").val(),
            "channelName": $("#fast_channel").val(),
            "reservations": []
        };
        return editArray;
    }).get();

    createCouponCodeAjax(fastCodeJson, couponId);

}
