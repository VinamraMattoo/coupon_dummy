/*
 *file that gets the current data of the coupon and populates the quick coupon creation div
 */
"use strict";

function populateQuickCreation(response) {
    hideOtherDivs(8);
    getPreviousSelections(response);
    $('#quickMappingTable').bootstrapTable('refresh');
    $('#quickBrandTable').bootstrapTable('refresh');
    $('#quickAreaTable').bootstrapTable('refresh');
    $('#quickReferrersTable').bootstrapTable('refresh');
    couponQuickTabSwitcher(0);
    for (var arr in response) {
        switch (arr) {
            case "name" :
                $("#quick_name").empty().val("");
                $("#quick_name_response").empty();
                break;

            case "description" :
                $("#quick_description").val("");
                break;

            case "applicableFrom" :
                $("#quick_applicableFrom").val("");
                break;

            case "applicableTill" :
                $("#quick_applicableTill").val("");
                break;

            case "inclusive" :
                var exclusive = !(response[arr]);
                $("#quick_inclusive").prop('checked', exclusive);
                break;

            case "applicationType" :
                $("#quick_applicationType").val(response[arr]);
                validateApplicationType('quick', response[arr]);
                break;

            case "actorType" :
                $("#quick_actorType").val(response[arr]);
                break;

            case "contextType" :
                $("#quick_contextType").val(response[arr]);
                break;


            case "transactionValMin" :
                $("#quick_transactionValMin").val(response[arr]);
                break;

            case "transactionValMax" :
                $("#quick_transactionValMax").val(response[arr]);
                break;


            case "discountAmountMin" :
                $("#quick_discountAmountMin").val(response[arr]);
                break;


            case "discountAmountMax" :
                $("#quick_discountAmountMax").val(response[arr]);
                break;


            case "applicableUseCount" :
                $("#quick_applicableUseCount").val(response[arr]);
                break;

            case "global" :
                $("#quickGlobal").prop('checked', response[arr]);
                if (response[arr] == true) {
                    globalSelected(2);
                }
                break;

            case "nthTime" :
                $("#quick_nthTime").val(response[arr]);
                break;


            case "nthTimeRecurring" :
                $("#quick_nthTimeRecurring").prop('checked', response[arr]);
                break;


            case "discountRule" :
                $("#quick_ruleType").val(response[arr].ruleType);

                if (response[arr].ruleType === "PERCENTAGE") {
                    $("#quick_discountValue").val(response[arr].discountPercentage);
                }
                else
                    $("#quick_discountValue").val(response[arr].discountFlatAmount);


                $("#quick_ruleDesc").val(response[arr].description);

                break;

            case "isB2B" :
                $("#quickB2BId").prop('checked', response[arr]);
                break;

            case "isB2C" :
                $("#quickB2CId").prop('checked', response[arr]);
                break;

            case "isAllAreas" :
                $("#quickMappingAreasGlobal").prop('checked', response[arr]);
                tableGlobalSelector('quick', 'area', 0);
                break;

            case "isAllBrands" :
                $("#quickMappingBrandsGlobal").prop('checked', response[arr]);
                tableGlobalSelector('quick','brand', 0);
                break;

            case "isAllProducts" :
                $("#quickMappingProductsGlobal").prop('checked', response[arr]);
                tableGlobalSelector('quick','product', 0);
                break;

        }
    }
    updateReferralsTable('quick');

}

