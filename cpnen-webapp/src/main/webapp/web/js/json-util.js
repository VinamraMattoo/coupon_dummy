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

    if (checkForPublish(data) == "true") {
        return;
    }
    else {
        createCouponAjax(data);
    }
}

function getMillSec(date) {
    return moment(date).valueOf();
}

function edit_coupon_submit() {

    var data = $("#edit_coupon").map(function () {
        var JsonData = {
            "coupon": {

                name: $("#edit_name").val(),

                applicableFrom: getMillSec($("#edit_applicableFrom").val()),

                applicableTill: getMillSec($("#edit_applicableTill").val()),

                transactionMaxVal: $("#edit_transactionMaxVal").val(),

                transactionMinVal: $("#edit_transactionMinVal").val(),

                maxDiscount: $("#edit_maxDiscount").val(),

                applicationType: $("#edit_applicationType").val(),

                applicationContext: $("#edit_applicationContext").val(),

                description: $("#edit_description").val(),

                inclusive: $("#edit_inclusive").is(':checked'),

                applicableUseCount: $("#edit_applicableUseCount").val(),

                couponType: $("#edit_type").val(),

                channelName: $("#edit_channelName").val()
            },
            "global": $("#edit_global").is(':checked'),

            "publish": $("#edit_publish").is(':checked'),

            //look into this
            "mappings": getMappings(),

            "rules": {

                ruleType: $("#edit_ruleType").val(),

                percent: $("#edit_percent").val(),

                flatAmount: $("#edit_flatAmount").val(),

                ruleDesc: $("#edit_ruleDesc").val()
            },
            //look into this also
            "brands": getBrands()
        };
        return JsonData;
    }).get();

    if (checkForPublish(data) == "true") {
        return;
    }
    else {
        editCouponAjax(data);
    }

}