//this js file controls the  creation of json from create coupon page and edit coupon page

/*=======create coupon json function=======
 * the function takes data  from the  various input fields and creates a key value pair json structure
 * for the checkbox is :checked is used to get a boolean value
 *for date creation getMillSec function is called and value is converted to milliseconds and passed into the  json
 */

function createCouponSubmit() {

    var data = $("#createCoupon").map(function () {

        var JsonData = {

            "name": $("#name").val(),

            "description": $("#description").val(),

            "inclusive": $("#inclusive").is(':checked'),

            "applicationType": $("#applicationType").val(),

            "actorType": $("#actorType").val(),

            "contextType": $("#contextType").val(),

            "applicableFrom": getMillSec($("#applicableFrom").val()),

            "applicableTill": getMillSec($("#applicableTill").val()),

            "applicableUseCount": $("#applicableUseCount").val(),

            "transactionValMin": $("#transactionValMin").val(),

            "transactionValMax": $("#transactionValMax").val(),

            "discountAmountMin": $("#discountAmountMin").val(),

            "discountAmountMax": $("#discountAmountMax").val(),

            "global": $("#global").is(':checked'),

            "nthTime": $("#nthTime").val(),

            "published": $("#publish").is(':checked'),

            "nthTimeRecurring": $("#nthTimeReccuring").is(':checked'),

            "productMapping": getMappings(),

            "brandMapping": getBrands(),

            "discountRule": {

                "description": $("#ruleDesc").val(),

                "ruleType": $("#ruleType").val(),

                "discountFlatAmount": $("#flatAmount").val(),

                "discountPercentage": $("#percent").val()

            }

        };
        return JsonData;
    }).get();

//check if status is published

    if (checkForPublish(data) == "true") {
        return;
    }
    else {
        createCouponAjax(data);
    }
}
//function to convert the  given date data into milliseconds

function getMillSec(date) {
    return moment(date).valueOf();
}

/* ============ edit coupon json function ==============
 * function creates the  json data for the  edit coupon forms
 * similar functionality as create coupon json function
 */

function edit_coupon_submit() {
       var editJson = $("#editCouponForm").map(function () {
        var editArray = {

            "name": $("#edit_name").val(),

            "description": $("#edit_description").val(),

            "inclusive": $("#edit_inclusive").is(':checked'),

            "applicationType": $("#edit_applicationType").val(),

            "actorType": $("#edit_actorType").val(),

            "contextType": $("#edit_contextType").val(),

            "applicableFrom": getMillSec($("#edit_applicableFrom").val()),

            "applicableTill": getMillSec($("#edit_applicableTill").val()),

            "applicableUseCount": $("#edit_applicableUseCount").val(),

            "transactionValMin": $("#edit_transactionValMin").val(),

            "transactionValMax": $("#edit_transactionValMax").val(),

            "discountAmountMin": $("#edit_discountAmountMin").val(),

            "discountAmountMax": $("#edit_discountAmountMax").val(),

            "global": $("#edit_global").is(':checked'),

            "nthTime": $("#edit_nthTime").val(),

            "published": $("#edit_publish").is(':checked'),

            "nthTimeRecurring": $("#edit_nthTimeReccuring").is(':checked'),

            //"productMapping": getMappings(),

            // "brandMapping": getBrands(),

            "discountRule": {

                "description": $("#edit_ruleDesc").val(),

                "ruleType": $("#edit_ruleType").val(),

                "discountFlatAmount": $("#edit_flatAmount").val(),

                "discountPercentage": $("#edit_percent").val()

            }

        };
        return  editArray;
    }).get();

    var couponId=$("#couponId").val();

    alert(JSON.stringify(editJson[0]));

    if (checkForPublish(editJson) == "true") {
        return;
    }
    else {
        editCouponAjax(editJson,couponId);
    }

}