//all the utility functions to handle edit coupon handling
/*
 * function to map current values for the particular couponId into the edit form input
 * */
"use strict";

var selectedProductMappings = [];
var selectedBrandMappings = [];
var selectedAreaMappings = [];
var selectedReferralMappings = [];


function populateEditCouponValues(response) {
    hideOtherDivs(5);
    getPreviousSelections(response);
    $('#editMappingTable').bootstrapTable('refresh');
    $('#editBrandTable').bootstrapTable('refresh');
    $('#editAreaTable').bootstrapTable('refresh');
    $('#editReferrersTable').bootstrapTable('refresh');
    couponEditTabSwitcher(0);

    for (var arr in response) {
        switch (arr) {

            case "name" :
                $("#edit_name").val(response[arr]);
                $("#edit_name_response").empty();
                $("#edit_storedName").val(response[arr]);
                break;

            case "couponId" :
                $("#couponId").val(response[arr]);
                break;

            case "description" :
                $("#edit_description").val(response[arr]);
                break;

            case "isAllAreas" :
                $("#editMappingAreasGlobal").prop('checked', response[arr]);
                break;

            case "isAllBrands" :
                $("#editMappingBrandsGlobal").prop('checked', response[arr]);
                break;

            case "isAllProducts" :
                $("#editMappingProductsGlobal").prop('checked', response[arr]);
                break;

            case "inclusive" :
                var exclusive = !(response[arr]);
                $("#edit_inclusive").prop('checked', exclusive);
                break;

            case "applicationType" :
                $("#edit_applicationType").val(response[arr]);
                validateApplicationType('edit', response[arr]);
                break;

            case "actorType" :
                $("#edit_actorType").val(response[arr]);
                break;

            case "contextType" :
                $("#edit_contextType").val(response[arr]);
                break;

            case "applicableFrom" :
                $("#edit_applicableFrom").val(formatDate(response[arr]));
                break;
            case "applicableTill" :
                $("#edit_applicableTill").val(formatDate(response[arr]));
                break;

            case "transactionValMin" :
                $("#edit_transactionValMin").val(response[arr]);
                break;

            case "transactionValMax" :
                $("#edit_transactionValMax").val(response[arr]);
                break;


            case "discountAmountMin" :
                $("#edit_discountAmountMin").val(response[arr]);
                break;


            case "discountAmountMax" :
                $("#edit_discountAmountMax").val(response[arr]);
                break;


            case "applicableUseCount" :
                $("#edit_applicableUseCount").val(response[arr]);
                break;

            case "global" :
                $("#editGlobal").prop('checked', response[arr]);
                break;

            case "nthTime" :
                $("#edit_nthTime").val(response[arr]);
                break;


            case "nthTimeRecurring" :
                $("#edit_nthTimeRecurring").prop('checked', response[arr]);
                break;


            case "discountRule" :
                $("#edit_ruleType").val(response[arr].ruleType);

                if (response[arr].ruleType === "PERCENTAGE") {
                    $("#edit_discountValue").val(response[arr].discountPercentage);
                }
                else
                    $("#edit_discountValue").val(response[arr].discountFlatAmount);


                $("#edit_ruleDesc").val(response[arr].description);

                break;

            case "lastUpdatedOn" :
                $("#editStoredLastUpdatedOn").val(response[arr]);
                break;

            case "isB2B" :
                $("#editB2BId").prop('checked', response[arr]);
                break;

            case "isB2C" :
                $("#editB2CId").prop('checked', response[arr]);
                break

        }
    }
    updateReferralsTable('edit');
    tableGlobalSelector('edit','brand', 0);
    tableGlobalSelector('edit','product', 0);
    tableGlobalSelector('edit','area', 0);


}
/*returns milliseconds input into standard YYYY-MM-DDTHH:mm:ss format*/
function formatDate(date) {
    if (date == null)
        return "";

    return moment(date).format(getCurrentDateTimeFormat());
    /* var localDate = UTCtoLocal(date);
     console.log("to local" + moment(localDate).format(getCurrentDateTimeFormat()));
     return moment(localDate).format(getCurrentDateTimeFormat());*/
}

//checks the row if it was previously checked
function getBrandsChecked(value, row) {
    var bool = false;
    if (selectedBrandMappings.length > 0) {
        for (var key in selectedBrandMappings) {

            if (row.id === selectedBrandMappings[key].id) {

                bool = true;
                return bool;

            }
        }
    }
    return bool;

}


//data -formatter for checking for previous values
function getState(value, row) {
    var bool = false;
    var mapId = row.id;
    var mapType = row.type;
    if (selectedProductMappings.length > 0) {
        for (var key in selectedProductMappings) {

            if (mapId === selectedProductMappings[key].pid && mapType === selectedProductMappings[key].type) {

                bool = true;
                return bool;

            }
        }
    }
    return bool;
}


//data -formatter for checking for previous values
function getAreaState(value, row) {
    var bool = false;
    if (selectedAreaMappings.length > 0) {
        for (var key in selectedAreaMappings) {

            if (row.id === selectedAreaMappings[key].id) {

                bool = true;
                return bool;

            }
        }
    }
    return bool;
}

//data -formatter for checking for previous values
function getReferralState(value, row) {
    var bool = false;
    var mapId = row.id;
    var mapType = row.type;
    if (selectedReferralMappings.length > 0) {
        for (var key in selectedReferralMappings) {

            if (mapId === selectedReferralMappings[key].pid && mapType === selectedReferralMappings[key].type) {

                bool = true;
                return bool;

            }
        }
    }
    return bool;
}