// validation of compulsory elements are done in this  js  file

"use strict";
/* ========= check For Publish function ===========
 *  checks whether publish is selected
 */
function checkIfPublished(data, context, eventType) {
    var flag = validateMinMaxVal(data[0], context);

    if (flag) {
        if (context == "create")
            $("#statusBar").empty().append(flag);
        else if (context == "edit")
            $("#editStatusBar").empty().append(flag);
        else if (context == "quick")
            $("#quickStatusBar").empty().append(flag);
        else
            $("#fastStatusBar").empty().append(flag);
        return true;
    }
    else if (eventType == 'publish' || eventType == 'fast') {
        var status = validateIfPublished(data[0]);
        if (status) {
            if (context == "create")
                $("#statusBar").empty().append(status);
            else if (context == "edit")
                $("#editStatusBar").empty().append(status);
            else if (context == "quick")
                $("#quickStatusBar").empty().append(status);
            else
                $("#fastStatusBar").empty().append(status);

            return true;
        }
        else {
            return false;
        }
    }
    else
        return false;
}
/*function validates values of transaction and discount value*/
function validateMinMaxVal(inputJson, context) {
    var flag = "";
    if (((inputJson["transactionValMin"] != "") && (parseInt(inputJson["transactionValMin"]) < 1)) || ((inputJson["transactionValMax"] != "") && (parseInt(inputJson["transactionValMax"]) < 1))) {
        return '<h6  style="color:red;" >Please specify a transaction value greater than 1</h6>';
    }
    else if ((inputJson["transactionValMin"] != "") && (inputJson["transactionValMax"] != "")) {
        var maxTransactionVal = parseInt(inputJson["transactionValMax"]);
        var minTransactionVal = parseInt(inputJson["transactionValMin"]);
        if (maxTransactionVal < minTransactionVal) {
            return '<h6  style="color:red;" >Please specify a max transaction value greater than min value</h6>';
        }
        if ((inputJson["discountAmountMin"] != "") && (inputJson["discountAmountMax"] != "")) {
            var maxVal = parseInt(inputJson["discountAmountMax"]);
            var minVal = parseInt(inputJson["discountAmountMin"]);
            if (maxVal < minVal) {
                return '<h6  style="color:red;" >Please specify a max discount value greater than min value</h6>';
            }
        }
    }

    else if (inputJson["transactionValMax"] != "") {

        if (inputJson["discountAmountMin"] != "") {
            if ((parseInt(inputJson["transactionValMax"])) < (parseInt(inputJson["discountAmountMin"])))
                return '<h6  style="color:red;" >Please specify a minimum discount amount less than max transaction value </h6>';

        }
        else if (inputJson["discountAmountMax"] != "") {
            if ((parseInt(inputJson["transactionValMax"])) < (parseInt(inputJson["discountAmountMax"])))
                return '<h6  style="color:red;" >Please specify a maximum discount amount less than max transaction value </h6>';

        }
    }


    else if (((inputJson["discountAmountMin"] != "") && (parseInt(inputJson["discountAmountMin"]) < 1)) || ((inputJson["discountAmountMax"] != "") && ( parseInt(inputJson["discountAmountMax"]) < 1))) {
        return '<h6  style="color:red;" >Please specify a discount value greater than 1</h6>';
    }


    if (inputJson["category"] === null) {
        return '<h6  style="color:red;" >Please specify coupon category</h6>';
    }


    if (inputJson["name"] === "") {
        return '<h6  style="color:red;" >Please enter a coupon name</h6>';
    }

    flag = checkDiscountValue(inputJson, context);

    return flag;
}


/*function validates values of discount value*/
function checkDiscountValue(inputJson, context) {
    var returnVal = "";
    if ((inputJson["discountRule"].ruleType == "PERCENTAGE")) {
        if ((inputJson["discountRule"].discountPercentage != "") && (parseInt(inputJson["discountRule"].discountPercentage) < 1) || (parseInt(inputJson["discountRule"].discountPercentage) > 100)) {
            return '<h6  style="color:red;" >Please specify a discount percentage value within the range 1-100</h6>';
        }
    }
    else {
        if ((inputJson["discountRule"].discountPercentage != "") && (parseInt(inputJson["discountRule"].discountFlatAmount) < 1)) {
            return '<h6  style="color:red;" >Please specify a flat discount value greater than 0</h6>';
        }


    }
    return returnVal;
}
/*function to validate application type on selections*/
function validateApplicationType(context, applicationType) {
    var nthTimeId, nthTimeRecurringId, useCountId;
    switch (context) {
        case "create" :
            nthTimeId = "nthTime";
            nthTimeRecurringId = "nthTimeRecurring";
            useCountId = "applicableUseCount";
            break;
        case "edit" :
            nthTimeId = "edit_nthTime";
            nthTimeRecurringId = "edit_nthTimeRecurring";
            useCountId = "edit_applicableUseCount";
            break;
        case "quick" :
            nthTimeId = "quick_nthTime";
            nthTimeRecurringId = "quick_nthTimeRecurring";
            useCountId = "quick_applicableUseCount";
            break;
        case "fast" :
            nthTimeId = "fast_nthTime";
            nthTimeRecurringId = "fast_nthTimeRecurring";
            useCountId = "fast_applicableUseCount";
            break;
    }
    $("#" + nthTimeId).prop('disabled', 'disabled');
    $("#" + nthTimeRecurringId).prop('disabled', 'disabled');
    $("#" + useCountId).prop('disabled', 'disabled');

    if (applicationType == "NTH_TIME_PER_SUBSCRIPTION") {
        $("#" + nthTimeId).prop('disabled', false);
        $("#" + nthTimeRecurringId).prop('disabled', false);
    }
    else if (applicationType == "NTH_TIME_AB_PER_SUBSCRIPTION") {
        $("#" + nthTimeId).prop('disabled', false);
    }
    else if (applicationType == "NTH_TIME") {
        $("#" + nthTimeId).prop('disabled', false);
        $("#" + nthTimeRecurringId).prop('disabled', false);
    }
    else if (applicationType == "ONE_TIME_PER_USER_FIFO") {
        $("#" + useCountId).prop('disabled', false);

    }
}

/*function to give notification on flat discount input*/
function flatInputCheck(context, discountType) {
    var id, maxId, minId;
    switch (context) {
        case "create" :
            id = "createFlatNotice";
            minId = "discountAmountMin";
            maxId = "discountAmountMax";
            break;
        case "edit" :
            id = "editFlatNotice";
            minId = "edit_discountAmountMin";
            maxId = "edit_discountAmountMax";
            break;
        case "quick" :
            id = "quickFlatNotice";
            minId = "quick_discountAmountMin";
            maxId = "quick_discountAmountMax";
            break;
        case "fast" :
            id = "fastFlatNotice";
            minId = "fast_discountAmountMin";
            maxId = "fast_discountAmountMax";
            break;
    }
    if (discountType == "PERCENTAGE") {
        $("#" + id).empty();
    }
    else {
        if (($("#" + minId).val() > 0) || ($("#" + maxId).val() > 0))
            $("#" + id).empty().append("<h5>Please note that max and min discount values will have no effect</h5>");
    }


}

/*function to switch through the JSON input to check whether any value is null*/
function validateIfPublished(inputJson) {
    var flag = "";

    if (inputJson["discountRule"].ruleType == null) {
        return '<h6  style="color:red;" >Please select a rule type</h6>';
    }

    for (var key in inputJson) {
        switch (key) {

            case "name" :
                if (inputJson[key] === "") {
                    return '<h6  style="color:red;" >Invalid name</h6>';
                }
                break;

            case "applicationType" :
                if (inputJson[key] === null) {
                    return '<h6  style="color:red;" >Please specify an application type</h6>';
                }
                break;

            case "actorType" :
                if (inputJson[key] === null) {
                    return '<h6  style="color:red;" >Please specify an actor type</h6>';
                }
                break;

            case "contextType" :
                if (inputJson[key] === null) {
                    return '<h6  style="color:red;" >Please specify a context type</h6>';
                }
                break;

            case "applicableFrom" :
                if (isNaN(parseInt(inputJson[key]))) {
                    return '<h6  style="color:red;" >Please specify a valid application start date</h6>';
                }
                break;

            case "applicableTill" :
                if (isNaN(parseInt(inputJson[key]))) {
                    return '<h6  style="color:red;" >Please specify a valid application end date</h6>';
                }
                break;

            case "productMapping" :

                if (inputJson[key].length === 0 && inputJson["global"] === false) {
                    return '<h6  style="color:red;" >Please select at least one product</h6>';
                }
                break;

            case "couponCategory" :
                if (inputJson[key] === null) {
                    return '<h6  style="color:red;" >Please specify coupon category</h6>';
                }
                break;


            case "discountRule" :
                if (inputJson[key].ruleType === "PERCENTAGE") {
                    if (inputJson[key].discountPercentage === "") {
                        return '<h6  style="color:red;" >Please specify discount percentage</h6>';
                    }
                }
                else {
                    if (inputJson[key].discountFlatAmount === "") {
                        return '<h6  style="color:red;" >Please specify flat discount value</h6>';
                    }
                }
                break;

            case "applicableUseCount" :
                if (inputJson['applicationType'] == "ONE_TIME_PER_USER_FIFO") {
                    if (inputJson[key] === "" || inputJson[key] < 0) {
                        return '<h6  style="color:red;" >Please specify a valid application use count</h6>';
                    }
                }
                break;

            case "nthTime" :
                var appType = inputJson['applicationType'];
                if (appType == "NTH_TIME" || appType == "NTH_TIME_PER_SUBSCRIPTION" || appType == "NTH_TIME_AB_PER_SUBSCRIPTION") {
                    if (inputJson[key] === "" || inputJson[key] < 0) {
                        return '<h6  style="color:red;" >Please specify a valid nth time value</h6>';
                    }
                }
                break;

            case "discountRule" :


                if ((inputJson["discountRule"].ruleType == "PERCENTAGE")) {
                    if ((inputJson["discountRule"].discountPercentage == "") || (parseInt(inputJson["discountRule"].discountPercentage) < 1) || (parseInt(inputJson["discountRule"].discountPercentage) > 100)) {
                        return '<h6  style="color:red;" >Please specify a discount percentage value within the range 1-100</h6>';
                    }
                }
                else {
                    if ((inputJson["discountRule"].discountPercentage == "") || (parseInt(inputJson["discountRule"].discountFlatAmount) < 1)) {
                        return '<h6  style="color:red;" >Please specify a flat discount value greater than 0</h6>';
                    }


                }
                break;
        }
    }
    return flag;

}


/* function to check validity of a coupon with id couponId and publish or show appropriate messages */
function validateAndPublish(JsonResponse, couponId) {
    var response = validateIfPublished(JsonResponse);
    if (response == "") {
        publishCoupon(couponId, JsonResponse.lastUpdatedOn);
    }
    else {
        $("#editStatusBar").empty().append(response);
        $("#edit_publish").prop('checked', true);
        populateEditCouponValues(JsonResponse)
    }
}

function validateEditCouponName(context, name) {
    var storedName = $("#edit_storedName").val();

    if (name == storedName) {
        $("#edit_name_response").empty();
        return;
    }

    validateCouponName(context, name);
}

/*function to get the coupon name and do the ajax call to validate*/
function validateCouponName(context, name) {
    if (name != "") {
        $.ajax({

                type: "GET",

                url: "../rws/coupon?name=" + name,

                success: function (data) {
                    checkForCouponId(context, data);
                },
                error: function (errMsg) {
                    showStatusMessage(errMsg, "errMsg");
                }
            }
        );
    }
    else {
        var statusId;
        switch (context) {
            case 'fast' :
                statusId = 'fast_name_response';
                break;
            case 'create' :
                statusId = 'create_name_response';
                break;
            case 'edit' :
                statusId = 'edit_name_response';
                break;
            case 'quick' :
                statusId = 'quick_name_response';
                break;
        }
        $('#' + statusId).empty();

    }
}

function checkForCouponId(context, response) {
    var statusId;
    switch (context) {
        case 'fast' :
            statusId = 'fast_name_response';
            break;
        case 'create' :
            statusId = 'create_name_response';
            break;
        case 'edit' :
            statusId = 'edit_name_response';
            break;
        case 'quick' :
            statusId = 'quick_name_response';
            break;
    }


    if (response == undefined) {
        $('#' + statusId).empty().append("<p style='font-size:10px;color:blue;margin-bottom: 0px;'>Valid Coupon name</p>");
    }
    else {
        $('#' + statusId).empty().append("<p style='font-size:10px;color:red;margin-bottom: 0px;'>Coupon name already taken!</p>");
    }
}


/*function to get the coupon code and do the ajax call to validate*/
function validateCodeName(context) {
    var codeName
    if (context == 'popup') {
        codeName = $("#codeName").val();
        if (codeName == "") {
            $('#create_code_response').empty().append("<p style='font-size:10px;color:red;margin-bottom: 0px;'>Coupon code is empty</p>");
            return;
        }
    }
    else
        codeName = $("#fast_code").val();
    if (codeName != "") {
        $.ajax({

            type: "GET",

            url: "../rws/couponCode?code=" + codeName,

            success: function (data) {
                checkForCode(data, context);
            },
            error: function (errMsg) {
                showStatusMessage(errMsg, "errMsg");
            }
        });
    }
    else if (context == 'submit') {
        $("#fastStatusBar").empty().append('<h6  style="color:red;" >Please specify a code name</h6>');

    }
}

function checkForCode(response, context) {
    if (response == undefined) {
        if (context != 'popup') {
            $('#fast_code_response').empty().append("<p style='font-size:10px;color:blue;margin-bottom: 0px;'>Valid Coupon code</p>");
            if (context == 'submit')
                submitFastCoupon();
        }
        else {
            submitCreateCouponCode();
            $('#fast_code_response').empty();
            $('#create_code_response').empty();

            $('#createCouponCodeModal').modal('hide');
        }
    }
    else {

        //  $("#fastSubmitId").prop('disabled', true);
        if (context != 'popup')
            $('#fast_code_response').empty().append("<p style='font-size:10px;color:red;margin-bottom: 0px;'>Coupon code already exists!</p>");
        else
            $('#create_code_response').empty().append("<p style='font-size:10px;color:red;margin-bottom: 0px;'>Coupon code already exists!</p>");

    }
}
function clearData() {
    $('#create_code_response').empty();
}


/*function checks if any non alpha numeric value is given as input */
$.fn.regexMask = function () {
    var mask = new RegExp('^[A-Za-z0-9][A-Za-z0-9-]*$');
    $(this).keypress(function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });

    $(this).bind("paste", function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });
};

/*function checks for only numeric integer values*/
$.fn.integerMask = function () {
    var mask = new RegExp('^[0-9]*$');
    $(this).keypress(function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });

    $(this).bind("paste", function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });
};


/*function checks for only alpha-numeric integer values with space and '-' '_' special characters*/
$.fn.couponNameMask = function () {
    var mask = new RegExp('^[A-Za-z0-9][ A-Za-z0-9_-]*$');
    $(this).keypress(function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });

    $(this).bind("paste", function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });
};