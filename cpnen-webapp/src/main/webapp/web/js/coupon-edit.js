/*
 functions related to edit operation are here
 */
"use strict";
/*function to get the new validity entered and call the function which does the ajax call*/
function extendValidity() {
    var newValidity = getMilliSec($("#extendedApplicability").val());
    var couponId = $("#storedCouponId").val();
    var lastUpdatedOn = $("#viewStoredLastUpdatedOn").val();
    var oldApplicableTill = getMilliSec($("#storedApplicableTill").val());

    $('#extendedApplicability').empty();
    $('#storedApplicableTill').empty();


    if (newValidity < oldApplicableTill) {
        $('#extend_validity_response').empty().append("<p style='font-size:10px;color:red;margin-bottom: 0px;'>New applicable till should be greater than old value</p>");
        return;
    }
    $('#extend_validity_response').empty();
    extendValidityOperation(newValidity, couponId, lastUpdatedOn);
    $('#extendValidity').modal('hide');

}

/*gets all the selections*/
function getPreviousSelections(response) {
    selectedProductMappings = [];
    selectedBrandMappings = [];
    selectedAreaMappings = [];
    selectedReferralMappings = [];

    for (var values in response) {
        var selectedReferrals = [];
        if (values == "productMapping") {
            selectedReferrals = response[values];
            for (var selectionKey = 0; selectionKey < selectedReferrals.length; selectionKey++) {
                selectedProductMappings.push({
                    pid: selectedReferrals[selectionKey].productId,
                    type: selectedReferrals[selectionKey].type
                });
            }
        }
        if (values == "brandMapping") {
            var selectedBrands = response[values];
            for (var selectionKey = 0; selectionKey < selectedBrands.length; selectionKey++) {
                selectedBrandMappings.push({
                    id: selectedBrands[selectionKey].brandId
                });
            }
        }

        if (values == "referrersMapping") {
            selectedReferrals = response[values];
            for (var selectionKey = 0; selectionKey < selectedReferrals.length; selectionKey++) {
                selectedReferralMappings.push({
                    pid: selectedReferrals[selectionKey].referrerId,
                    type: selectedReferrals[selectionKey].type
                });
            }
        }
        if (values == "areaMapping") {
            var selectedAreas = response[values];
            for (var selectionKey = 0; selectionKey < selectedAreas.length; selectionKey++) {
                selectedAreaMappings.push({
                    id: selectedAreas[selectionKey].areaId
                });
            }
        }

    }
}