function create_coupon_submit() {

    var data = $("#createCoupon").map(function () {


        var JsonData = {
            "coupon": {

                name: $("#name").val(),

                description: $("#description").val(),

                inclusive: $("#inclusive").is(':checked'),

                applicationType: $("#applicationType").val(),

                actorType: $("actorType").val(),

                contextType: $("contextType").val(),

                applicableFrom: $("#applicableFrom").val(),

                applicableTill: $("#applicableTill").val(),

                applicableUseCount: $("#applicableUseCount").val(),

                transactionValMin: $("#transactionValMin").val(),

                transactionValMax: $("#transactionValMax").val(),

                discountAmountMin: $("#discountAmountMin").val(),

                discountAmountMax: $("#discountAmountMax").val(),

                "global": $("#global").is(':checked'),

                "nthTime": $("#nthTime").is(':checked'),

                "nthTimeReccuring": $("#nthTimeReccuring").val()

            },

            "productMapping": getMappings(),

            "brandMapping": getBrands(),

            "rule": {

                description: $("#ruleDesc").val(),

                ruleType: $("#ruleType").val(),

                discountFlatAmount: $("#flatAmount").val(),

                discountPercentage: $("#percent").val()

            }

        };
        return JsonData;
    }).get();

    alert(JSON.stringify(data[0]));

    $.ajax({

        type: "POST",
        url: "/cpnen/web/rws/coupon",
        data: JSON.stringify(data[0]),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            showView(data, "create");
        },
        failure: function (errMsg) {
            showView(errMsg, "create");
        }
    });
}


var couponId;
function showView(Id, eventType) {

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


//To show the coupon values just created
function showCoupon(Id) {
    hideOthers(3);
    $.get("view_coupon.jsp", function (data) {
        $("#viewCreatedCoupon").empty().append(data);
        populateValues(Id);
    });

    return false;
}

function populateValues(cpnId) {

    $.ajax({

        type: "GET",
        url: "/cpnen/web/rws/coupon/" + cpnId,

        success: function (data) {

            populateView(data);
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}


var currentCoupon;
function populateView(response) {

    currentCoupon = response;
    var tags = "";
    for (var key in response) {

        tags += "<tr><td>" + key + " </td><td>" + response[key] + "</td></tr>";
        if (key == "publishedOn") {
            checkStatus(response[key]);
        }
        if (key == "deactivatedOn") {
            checkDeactivation(response[key]);
        }

        /*   for (var key2 in key) {
         tags += "<tr><td>" + key2 + " </td><td>" + key[key2] + "</td></tr>";
         if (key2 == "publishedOn") {
         checkStatus([key2]);
         }
         }*/


    }

    $('#viewCurrentCoupon').append(tags);


}

function checkStatus(status) {

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

function checkDeactivation(status) {

    if (status != null) {

        $("#deactivateCpn").css('display', 'none');
        $("#generateCode").css('display', 'none');
        $("#editCpn").css('display', 'none');
        $("#deleteCpn").css('display', 'none');

    }

    return;

}


function showEdit() {
    var id = couponId;
    editCoupon(id);
}


function editCoupon(cid) {
    $.get("./editCoupon.jsp", function (data) {
        $("#editCoupon").empty().append(data);
        showCurrentVal(cid);
    });

}

function showCurrentVal(couponId) {

    $.ajax({

        type: "GET",
        url: "/cpnen/web/rws/coupon/" + couponId,

        success: function (data) {

            populateEdit(data);
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });
}
function populateEdit(response) {
    for (var arr in response) {
        switch (arr) {
            case "coupon" :
                for (var key in arr) {
                    switch (key) {
                        case "name" :
                            $("#edit_name").val(arr[key]);
                            break;

                        case "description" :
                            $("#edit_description").val(arr[key]);
                            break;

                        case "inclusive" :
                            $("#edit_inclusive").prop('checked', arr[key]);
                            break;

                        case "applicationType" :
                            $("#edit_applicationType").val(arr[key]);
                            break;

                        case "actorType" :
                            $("#edit_actorType").val(arr[key]);
                            break;

                        case "contextType" :
                            $("#edit_contextType").val(arr[key]);
                            break;

                        case "applicableFrom" :
                            $("#edit_applicableFrom").val(arr[key]);
                            break;
                        case "applicableTill" :
                            $("#edit_applicableTill").val(arr[key]);
                            break;


                        case "transactionValMin" :
                            $("#edit_transactionValMin").val(arr[key]);
                            break;

                        case "transactionValMax" :
                            $("#edit_transactionValMax").val(arr[key]);
                            break;


                        case "discountAmountMin" :
                            $("#edit_discountAmountMin").val(arr[key]);
                            break;


                        case "discountAmountMax" :
                            $("#edit_discountAmountMax").val(arr[key]);
                            break;


                        case "applicableUseCount" :
                            $("#edit_applicableUseCount").val(arr[key]);
                            break;

                        case "global" :
                            $("#edit_global").prop('checked', arr[key]);
                            break;

                        case "nthTime" :
                            $("#edit_nthTime").prop('checked', arr[key]);
                            break;


                        case "nthTimeReccuring" :
                            $("#edit_nthTimeReccuring").val(arr[key]);
                            break;
                    }
                }
                break;

            case "rule" :
                for (var key in arr) {
                    switch (key) {
                        case "ruleType" :
                            $("#edit_ruleType").val(arr[key]);
                            break;

                        case "discountPercentage" :
                            $("#edit_discountPercentage").val(arr[key]);
                            break;

                        case "discountFlatAmount" :
                            $("#edit_discountFlatAmount").val(arr[key]);
                            break;

                        case "description" :
                            $("#edit_ruleDesc").val(arr[key]);
                            break;
                    }

                }
                break;
            case "productMapping" :
                for (var key in arr) {
                    if(key=="productId") var prodId=arr[key];
                }
                break;
            case "brandMapping" :
                for (var key in arr) {
                    if(key=="brandId") var brandId=arr[key];
                }

                break;
        }
    }
}
//untreated code starts
function showDeactivation() {
    confirm("Do you really want to deactivate?");
}

function showDelete() {
    confirm("Do you really want to delete?");
}
function showCouponCode() {
    warning("showing couponcodes");
}