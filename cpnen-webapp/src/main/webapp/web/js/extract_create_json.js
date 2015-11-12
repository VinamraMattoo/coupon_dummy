var couponId;
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

            populateView(data, cpnId);
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}


function populateView(response, id) {
    couponId = id;
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


function onEditClick() {
    var id = couponId;
    editCoupon(id);
}


function editCoupon(cid) {
    $.get("./editCoupon.jsp", function (data) {
        $("#editCoupon").empty().append(data);
        getCouponDetails(cid);
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
                            $("#edit_applicableFrom").val(getDateInFormat(arr[key]));
                            break;
                        case "applicableTill" :
                            $("#edit_applicableTill").val(getDateInFormat(arr[key]));
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
                            $("#edit_nthTime").val(arr[key]);
                            break;


                        case "nthTimeReccuring" :
                            $("#edit_nthTimeReccuring").prop('checked', arr[key]);
                            break;
                    }
                }
                break;

            case "discountRule" :
                for (var value in arr) {
                    switch (value) {
                        case "ruleType" :
                            $("#edit_ruleType").val(arr[value]);
                            break;

                        case "discountPercentage" :
                            $("#edit_discountPercentage").val(arr[value]);
                            break;

                        case "discountFlatAmount" :
                            $("#edit_discountFlatAmount").val(arr[value]);
                            break;

                        case "description" :
                            $("#edit_ruleDesc").val(arr[value]);
                            break;
                    }

                }
                break;
            case "productMapping" :
                for (var val in arr) {
                    if (val == "productId") var prodId = arr[val];
                }
                break;
            case "brandMapping" :
                for (var va in arr) {
                    if (va == "brandId") var brandId = arr[va];
                }

                break;
        }
    }
}
function getDateInFormat(date) {
    return moment(date).format();
}

//untreated code starts
function onDeactivationClick() {
    confirm("Do you really want to deactivate?");
}

function onDeleteClick() {
    confirm("Do you really want to delete?");
}
function onGenerateCodeClick() {
    confirm("showing couponcodes");
}