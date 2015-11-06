function edit_coupon_submit()
{

    var data = $("#edit_coupon").map(function(){


        var JsonData = {
            "coupon": {

                name: $("#edit_name").val(),

                applicableFrom: $("#edit_applicableFrom").val(),

                applicableTill: $("#edit_applicableTill").val(),

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


    $.ajax({

        type: "PATCH",
        url: "/cpnen/web/rws/coupon/"+couponId,
        data: JSON.stringify(data[0]),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            showEditedView();},
        error: function(errMsg) {
            alert(errMsg);
        }
    });
}
//untreated code starts
function showEditedView()
{

    $.get("./info.html", function(data) {
        $("#currentShownDiv").empty().append(data);
        $("#messageStatus").empty().append("modified");
    });
    return false;
}