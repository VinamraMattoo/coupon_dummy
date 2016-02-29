window.onhashchange = function () {
    var id = null;
    var divsArray = ["createCoupon", "showCoupons", "listCouponCodes", "viewCreatedCoupon", "statusInfo", "showEditCoupon", "createCode", "createCouponCode", "quickCouponCreate", "viewCouponCode", "fastCouponCodeCreate", "dashboard"];
    if (location.hash.length > 0) {
        id = (location.hash.replace('#', ''));
        for (var i = 0; i < divsArray.length; i++) {

            if (i == id) {

                if ($("#" + divsArray[i]).is(':visible')) {
                    continue;
                }
                $("#" + divsArray[i]).show();

                if (divsArray[i] == "createCoupon") {
                    $("#couponAttributes").scrollTop(0);
                    clearCouponDetails();
                    selectedBrandMappings = [];
                    selectedProductMappings = [];
                    selectedAreaMappings = [];
                    selectedReferralMappings = [];
                    for (var i = 0; i < brandJSON.length; i++) {
                        selectedBrandMappings.push({
                            id: brandJSON[i].id
                        });
                    }
                    $('#brandTable').bootstrapTable('checkAll');

                    updateReferralsTable('create');
                    setInfoBar('create');
                }
                else if (divsArray[i] == "quickCouponCreate") {
                    $("#quick_couponAttributes").scrollTop(0);
                    $("#quickStatusBar").empty()
                    couponQuickTabSwitcher(0);
                    updateReferralsTable('quick');
                    setInfoBar('quick');

                }
                else if (divsArray[i] == "showEditCoupon") {
                    $("#edit_couponAttributes").scrollTop(0);
                    $("#editStatusBar").empty();
                    couponEditTabSwitcher(0);
                    updateReferralsTable('edit');
                    setInfoBar('edit');

                }
                else if (divsArray[i] == "fastCouponCodeCreate") {
                    $("#fast_couponAttributes").scrollTop(0);
                    clearFastCouponDetails();
                    selectedBrandMappings = [];
                    selectedProductMappings = [];
                    selectedAreaMappings = [];
                    selectedReferralMappings = [];
                    for (var i = 0; i < brandJSON.length; i++) {
                        selectedBrandMappings.push({
                            id: brandJSON[i].id
                        });
                    }
                    $('#fastBrandTable').bootstrapTable('refresh');
                    updateReferralsTable('fast');
                    setInfoBar('fast');
                    $("#fastStatusBar").empty();
                }
                continue;
            }
            $("#" + divsArray[i]).hide();

        }
        $('#extendValidity').modal('hide');
        $('#advanceSearch').modal('hide');
        $('#createCouponCodeModal').modal('hide');
        $('#advanceSearchCode').modal('hide');
    }

};


window.onbeforeunload = function () {
    if (logoutFlag == true || logoutFlag == false) {
        return;
    }
    else
        return "This action will close or reset this webapp";
};