//  all infrastructure functions are handled in this  js  file
"use strict";

var referralJSON = [];
var categoryJSON = [];
var brandJSON = [];

/*function gets createCoupon.jsp ,createMapping.jsp,listCoupon.jsp and listCouponCodes.jsp into the divs */
$(document).ready(function () {
    getReferralDetails();
    getBrandsDetails();
    getCategoryDetails();

});

function loadAllJSP() {
    $.get("./createCoupon.jsp", function (data) {

        $("#createCoupon").empty().append(data);
        dateTimeInput("applicableFrom", "applicableTill");

        $("#nthTime").integerMask();

        $("#name").couponNameMask();

        $.get("./createMapping.jsp", function (data) {
            $("#couponMapping").empty().append(data);
            getProductSelections('mappingTable');
            getBrandSelections('brandTable', 'create');
            getAreaSelections('areaTable');
            getReferralSelections('referrersTable');

        });


        populateCategoryOptions('create');

    });

    $.get("./editCoupon.jsp", function (data) {

        $("#showEditCoupon").empty().append(data);
        dateTimeInput("edit_applicableFrom", "edit_applicableTill");
        $("#edit_name").couponNameMask();

        $.get("./editMapping.jsp", function (data) {
            $("#edit_couponMapping").empty().append(data);
            getProductSelections('editMappingTable');
            getBrandSelections('editBrandTable', 'edit');
            getAreaSelections('editAreaTable');
            getReferralSelections('editReferrersTable');
        });
        $("#edit_nthTime").integerMask();
        populateCategoryOptions('edit');
    });
    $.get("./listCoupon.jsp", function (data) {
        $("#showCoupons").empty().append(data);
        $("#advName").couponNameMask();

        editDateTimeInput("advFrom", "advTill");
        editDateTimeInput("advUpdateFrom", "advUpdateTill");
        editDateTimeInput("advDeactivateFrom", "advDeactivateTill");
        editDateTimeInput("advAppDurationFrom", "advAppDurationTill");
        editDateTimeInput("advPublishedFrom", "advPublishedTill");
    });
    $.get("./listCouponCode.jsp", function (data) {
        $("#listCouponCodes").empty().append(data);
        $("#advCodeName").couponNameMask();

        editDateTimeInput("advCodeFrom", "advCodeTill");
        editDateTimeInput("advCodeDeactivateFrom", "advCodeDeactivateTill");
    });

    $.get("./viewCoupon.jsp", function (data) {
        $("#viewCreatedCoupon").empty().append(data);
        $("#codeName").regexMask();

        extendDateTimeInput("storedApplicableTill", "extendedApplicability");

        $('#createCouponCodeModal').on('hidden.bs.modal', function () {
            $(this).find('form')[0].reset();
        });

        $("#extendValidity").on('show.bs.modal', function () {
            $('#extend_validity_response').empty();
        });
        $("#createCouponCodeModal").on('show.bs.modal', function () {
            $('#create_code_response').empty();
        });
    });
    $.get("./quickCouponCreation.jsp", function (data) {
        $("#quickCouponCreate").empty().append(data);
        dateTimeInput("quick_applicableFrom", "quick_applicableTill");
        $("#quick_nthTime").integerMask();
        $("#quick_name").couponNameMask();

        $.get("./quickMapping.jsp", function (data) {
            $("#quick_couponMapping").empty().append(data);
            getProductSelections('quickMappingTable');
            getBrandSelections('quickBrandTable', 'quick');
            getAreaSelections('quickAreaTable');
            getReferralSelections('quickReferrersTable');
        });
        populateCategoryOptions('quick');
    });

    $.get("./viewCode.jsp", function (data) {
        $("#viewCouponCode").empty().append(data);
    });

    $.get("./fastCouponCreation.jsp", function (data) {
        $("#fastCouponCodeCreate").empty().append(data);
        dateTimeInput("fast_applicableFrom", "fast_applicableTill");
        $("#fast_code").regexMask();
        $("#fast_nthTime").integerMask();
        $("#fast_name").couponNameMask();

        $.get("./fastCouponMapping.jsp", function (data) {
            $("#fast_couponMapping").empty().append(data);
            getProductSelections('fastMappingTable');
            getBrandSelections('fastBrandTable', 'fast');
            getAreaSelections('fastAreaTable');
            getReferralSelections('fastReferrersTable');
        });
        populateCategoryOptions('fast');
    });
    $(".bs-checkbox").css("padding-top", "3px !important");
    $(".bs-checkbox").css("padding-bottom", "0px !important");

    hideOtherDivs(11);

}

function setInfoBar(context) {
    var areaHolder, areaBarId, mappingHolder, mappingBarId, brandHolder, brandBarId, referrerHolder, referrerBarId;
    var holderArray = [];
    var barIdArray = [];
    var infoIdArray = [];

    switch (context) {
        case 'create' :
            holderArray = ["areaHolder", "mappingHolder", "brandHolder", "referralHolder"];
            infoIdArray = ["areaTableInfo", "mappingTableInfo", "brandTableInfo", "referrersTableInfo"];
            break;

        case 'edit' :
            holderArray = ["editAreaHolder", "editMappingHolder", "editBrandHolder", "editReferralHolder"];
            infoIdArray = ["editAreaTableInfo", "editMappingTableInfo", "editBrandTableInfo", "editReferrersTableInfo"];
            break;

        case 'quick' :
            holderArray = ["quickAreaHolder", "quickMappingHolder", "quickReferralHolder", "quickBrandHolder"];
            infoIdArray = ["quickAreaTableInfo", "quickMappingTableInfo", "quickReferrersTableInfo", "quickBrandTableInfo"];
            break;
        case 'fast' :
            holderArray = ["fastAreaHolder", "fastMappingHolder", "fastBrandHolder", "fastReferralHolder"];
            infoIdArray = ["fastAreaTableInfo", "fastMappingTableInfo", "fastBrandTableInfo", "fastReferrersTableInfo"];
            break;

    }

    for (var i = 0; i < holderArray.length; i++) {
        var tag = '<h6 class="pull-left" style="margin: 0;padding: 10px" id="' + infoIdArray[i] + '"></h6>';

        $("#" + holderArray[i] + " .clearfix").append(tag);

    }


}

/*function hides all the other divs which are there  in the array*/
function hideOtherDivs(id) {
    location.hash = id;

}


/*function toggles div while editing coupon*/
function couponEditTabSwitcher(id) {
    var arr = ["edit_couponAttributes", "edit_couponMapping", "edit_couponRules"];
    var panels = ["editDetailsPanel", "editMappingsPanel", "editRulesPanel"];

    for (var i = 0; i < arr.length; i++) {
        globalSelected(1);
        if (i == id) {
            $("#" + arr[i]).show();
            $("#" + panels[i]).addClass("active");
            continue;
        }
        $("#" + panels[i]).removeClass("active");
        $("#" + arr[i]).hide();
    }
}


/*function toggles div while fast coupon creation*/
function couponFastTabSwitcher(id) {
    var arr = ["fast_couponAttributes", "fast_couponMapping", "fast_couponRules"];
    var panels = ["fastDetailsPanel", "fastMappingsPanel", "fastRulesPanel"];

    for (var i = 0; i < arr.length; i++) {
        globalSelected(3);
        if (i == id) {
            $("#" + arr[i]).show();
            $("#" + panels[i]).addClass("active");
            continue;
        }
        $("#" + panels[i]).removeClass("active");
        $("#" + arr[i]).hide();
    }
}
/*function toggles div while quick coupon creation*/
function couponQuickTabSwitcher(id) {
    var arr = ["quick_couponAttributes", "quick_couponMapping", "quick_couponRules"];
    var panels = ["quickDetailsPanel", "quickMappingsPanel", "quickRulesPanel"];

    for (var i = 0; i < arr.length; i++) {
        globalSelected(2);
        if (i == id) {
            $("#" + arr[i]).show();
            $("#" + panels[i]).addClass("active");
            continue;
        }
        $("#" + panels[i]).removeClass("active");

        $("#" + arr[i]).hide();
    }
}


/*function toggles div while creating coupon*/
function couponCreateTabSwitcher(id) {
    var arr = ["couponAttributes", "couponMapping", "couponRules"];
    var panels = ["couponDetailsPanel", "couponMappingsPanel", "couponRulesPanel"];
    for (var i = 0; i < arr.length; i++) {
        globalSelected(0);
        if (i == id) {
            $("#" + arr[i]).show();
            $("#" + panels[i]).addClass("active");
            continue;
        }
        $("#" + panels[i]).removeClass("active");


        $("#" + arr[i]).hide();
    }
}


/* function returns the date and time format currently used*/
function getCurrentDateTimeFormat() {
    return 'YYYY-MM-DDTHH:mm:ss';
}


/* function returns the date format currently used*/
function getCurrentDateFormat() {
    return 'YYYY-MM-DD';
}

/* function loops through coupons categories and populates the select options in all coupon pages*/
function populateCategoryOptions(context) {
    var selectInputId;
    switch (context) {
        case 'create'  :
            selectInputId = "couponCategory";
            break;
        case 'edit'  :
            selectInputId = "edit_couponCategory";
            break;
        case 'quick'  :
            selectInputId = "quick_couponCategory";
            break;
        case 'fast'  :
            selectInputId = "fast_couponCategory";
            break;
    }
    var tags = "";
    for (var i = 0; i < categoryJSON['categoryNames'].length; i++) {
        tags += '<option value="' + categoryJSON['categoryNames'][i] + '">' + dataFormatter(categoryJSON['categoryNames'][i]) + '</option>'
    }
    $("#" + selectInputId).append(tags);
}


/*Formatter function which accepts a string and converts it into lower case with '_' replaced with space */
function dataFormatter(inputString) {
    if (inputString == null) {
        return;
    }
    inputString = inputString.replace(/_/g, ' ');
    inputString = inputString.toLowerCase();
    return inputString.charAt(0).toUpperCase() + inputString.slice(1);

}

/*function switches between the links inside the mapping tab*/
function areaReferralMappingSwitcher(flag, context) {

    var areaTab = "AreaProductDiv";
    var referralTab = "BrandReferralDiv";
    var areaLinkId = "BrandsReferralsLink";
    var referralLinkId = "AreasProductsLink";

    if (flag == 'referral') {
        $("#" + context + areaTab).hide(50);
        $("#" + context + referralTab).show(300);
        $("#" + context + referralLinkId).css('text-decoration', '');
        $("#" + context + areaLinkId).css('text-decoration', 'underline');
    }
    else {
        $("#" + context + areaTab).show(300);
        $("#" + context + referralTab).hide(50);
        $("#" + context + referralLinkId).css('text-decoration', 'underline');
        $("#" + context + areaLinkId).css('text-decoration', '');
    }

}

