"use strict";
/*function to check value of the status field  */
function checkForPublished(status) {

    if (status == null) {
        $("#editCpn").show();
        $("#deleteCpn").show();
        $("#deactivateCpn").hide();
        $("#generateCode").hide();
        $("#extendAppTill").hide();
    }
    else {
        $("#deactivateCpn").show();
        $("#generateCode").show();
        $("#extendAppTill").show();
        $("#editCpn").hide();
        $("#deleteCpn").hide();
    }

    return;
}
/*function to check value of the deactivation field  */
function checkForDeactivation(status) {
    if (status != null) {

        $("#deactivateCpn").hide();
        $("#generateCode").hide();
        $("#editCpn").hide();
        $("#deleteCpn").hide();
        $("#extendAppTill").hide();
    }

    return;

}

/*function to map the data returned into the view coupon table*/
function populateCouponDetailsTable(response, id) {

    var tags = "";
    var NOT_AVAILABLE = "N/A";
    var str = "";
    for (var key in response) {

        switch (key) {
            case "couponId" :
                $("#storedCouponId").val(response[key]);
                break;
            case "discountRule" :
                populateDiscount(response[key]);
                break;
            case "brandMapping" :
                populateMappings(response[key], "brand");
                break;
            case "productMapping" :
                populateMappings(response[key], "product");
                break;

            case "areaMapping" :
                populateMappings(response[key], "area");
                break;

            case "referrersMapping" :
                populateMappings(response[key], "referral");
                break;


            case "applicableFrom" :

                if (response[key] != null)
                    tags += "<tr><td>" + 'Applicable from' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Applicable from' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";

                break;

            case "applicableTill" :
                if (response[key] != null)
                    tags += "<tr><td>" + 'Applicable till' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Applicable till' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";

                $("#storedApplicableTill").empty().val(formatDate(response[key]));
                $("#extendedApplicability").empty().val(formatDate(response[key]));
                break;


            case "createdOn" :
                if (response[key] != null)
                    tags += "<tr><td>" + 'Created on' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Created on' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";
                break;

            case "publishedOn" :
                checkForPublished(response[key]);
                checkForDeactivation(response["deactivatedOn"]);

                if (response["publishedOn"] == null) {
                    str += "<label title='Coupon is in draft mode' class=\"pull-right\" style=\"border: solid thin ; padding: 2px; margin-left: 2px; \" > Draft </label>";
                }
                else {
                    str += "<label title=\"Coupon is published\" class=\"pull-right\" style=\"border: solid thin ; padding: 2px; margin-left: 2px; \"> Published </label>&nbsp;&nbsp;";
                    tags += "<tr><td>" + 'Published on' + " </td><td>" + formatDate(response[key]) + "</td></tr>";

                    if (response["deactivatedOn"] == null) {
                        str += "<label title='Coupon is active'class=\"pull-right\" style=\"border: solid thin ; padding: 2px;margin-left: 2px; \" > Active </label>&nbsp;&nbsp;";
                    }
                    else {
                        str += "<label title='Coupon is deactivated' class=\"pull-right\" style=\"border: solid thin ; padding: 2px; margin-left: 2px;  \"  > Deactivated </label>&nbsp;&nbsp;";
                    }
                }
                break;

            case "deactivatedOn" :
                if (response[key] != null)
                    tags += "<tr><td>" + 'Deactivated on' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Deactivated on' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";
                break;

            case "lastUpdatedOn" :
                if (response[key] != null)
                    tags += "<tr><td>" + 'Last updated on' + " </td><td>" + formatDate(response[key]) + "</td></tr>";
                else
                    tags += "<tr><td>" + 'Last updated on' + " </td><td>" + NOT_AVAILABLE + "</td></tr>";
                $("#viewStoredLastUpdatedOn").val(response[key]);
                break;

            case "name" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Coupon name' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "category" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Category' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "description" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != "")
                    responseValue = response[key];
                tags += "<tr><td>" + 'Description' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "createdBy" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Created by' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "lastUpdatedBy" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Last updated by' + " </td><td>" + responseValue + "</td></tr>";
                break;


            case "deactivatedBy" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Deactivated by' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "inclusive" :
                var exclusive = !(response[key]);
                tags += "<tr><td>" + 'Exclusive' + " </td><td>" + exclusive + "</td></tr>";
                break;


            case "applicationType" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Application type' + " </td><td>" + responseValue + "</td></tr>";
                break;


            case "actorType" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Actor type' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "contextType" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Context type' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "applicableUseCount" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Applicable use count	' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "transactionValMin" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Min transaction value' + " </td><td>" + responseValue + "</td></tr>";
                break;
            case "transactionValMax" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Max transaction value' + " </td><td>" + responseValue + "</td></tr>";
                break;


            case "discountAmountMin" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Min discount amount' + " </td><td>" + responseValue + "</td></tr>";
                break;


            case "discountAmountMax" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Max discount amount' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "global" :
                tags += "<tr><td>" + 'Global' + " </td><td>" + response[key] + "</td></tr>";
                if (key == "global" && response[key] == true)
                    $("#mappingTables").attr('hidden', 'hidden');

                else if (key == "global" && response[key] == false)
                    $("#mappingTables").removeAttr('hidden', 'hidden');
                break;

            case "nthTime" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'nth time interval' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "nthTimeRecurring" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'nth time recurring' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "publishedBy" :
                var responseValue = NOT_AVAILABLE;
                if (response[key] != null)
                    responseValue = response[key];
                tags += "<tr><td>" + 'Published by' + " </td><td>" + responseValue + "</td></tr>";
                break;
        }
    }
    $("#couponStatus").empty().append(str);

    $('#viewCurrentCoupon').empty().append(tags);

    hideOtherDivs(3);
}


/*function populates the discount rules from the json array into the view table*/
function populateDiscount(discountArray) {
    var tags = "";
    var NOT_AVAILABLE = "N/A";
    for (var key in discountArray) {
        switch (key) {
            case "description" :
                var responseValue = NOT_AVAILABLE;
                if (discountArray[key] != "")
                    responseValue = discountArray[key];
                tags += "<tr><td>" + 'Description' + " </td><td>" + responseValue + "</td></tr>";
                break;
            case "ruleType" :
                var responseValue = NOT_AVAILABLE;
                if (discountArray[key] != null)
                    responseValue = discountArray[key];
                tags += "<tr><td>" + 'Rule type' + " </td><td>" + responseValue + "</td></tr>";
                break;
            case "createdOn" :
                var responseValue = NOT_AVAILABLE;
                if (discountArray[key] != null)
                    responseValue = discountArray[key];
                tags += "<tr><td>" + 'Created on' + " </td><td>" + formatDate(responseValue) + "</td></tr>";
                break;

            case "createdBy" :
                var responseValue = NOT_AVAILABLE;
                if (discountArray[key] != null)
                    responseValue = discountArray[key];
                tags += "<tr><td>" + 'Created by' + " </td><td>" + responseValue + "</td></tr>";
                break;

            case "discountFlatAmount" :
            case "discountPercentage" :
                var responseValue = NOT_AVAILABLE;
                if (discountArray[key] != null)
                    responseValue = discountArray[key];
                if (discountArray[key] != null) {
                    if (discountArray["ruleType"] == "PERCENTAGE")
                        tags += "<tr><td>" + 'Discount percentage' + " </td><td>" + responseValue + "</td></tr>";
                    else
                        tags += "<tr><td>" + 'Flat discount amount' + " </td><td>" + responseValue + "</td></tr>";
                }
                break;
        }

    }
    $('#viewDiscountDetails').empty().append(tags);
}

/*function populates products and brands table*/
function populateMappings(mappings, flag) {
    var tags = "";
    if (mappings.length > 0) {
        for (var value in mappings) {
            if (flag == "brand")
                tags += "<tr><td>" + mappings[value].name + "</td></tr>";
            else if (flag == "product")
                tags += "<tr><td>" + mappings[value].name + " </td><td>" + mappings[value].type + "</td></tr>";
            else if (flag == "area")
                tags += "<tr><td>" + mappings[value].name + " </td></tr>";
            else
                tags += "<tr><td>" + mappings[value].name + " </td><td>" + mappings[value].type + "</td></tr>";

        }
    }
    else {
        if (flag == "brand")
            tags += "<tr><td>No brand selected</td></tr>";
        else if (flag == "product")
            tags += "<tr><td colspan=\"2\">No product selected</td></tr>";
        else if (flag == "area")
            tags += "<tr><td colspan=\"2\">No area selected</td></tr>";
        else
            tags += "<tr><td colspan=\"2\">No referrals selected</td></tr>";

    }

    if (flag == "brand")
        $('#viewBrandsDetails').empty().append(tags);
    else if (flag == "product")
        $('#viewMappingDetails').empty().append(tags);
    else if (flag == "area")
        $('#viewAreaDetails').empty().append(tags);
    else
        $('#viewReferralDetails').empty().append(tags);

}
