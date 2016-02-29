"use strict";

/*function handles all table global selections*/
function tableGlobalSelector(context, table, flag) {
    var globalId, divToBeHidden;
    switch (context) {
        case 'create' :
            switch (table) {
                case 'area' :
                    globalId = "createMappingAreaGlobal";
                    divToBeHidden = "areaTable";
                    break;
                case 'product':
                    globalId = "createMappingProductsGlobal";
                    divToBeHidden = "mappingTable";
                    break;
                case 'brand':
                    globalId = "createMappingBrandsGlobal";
                    divToBeHidden = "brandTable";
                    break;
                case 'referral':
                    globalId = "createMappingReferrerSourcesGlobal";
                    divToBeHidden = "referrersTable";
                    break;

            }
            break;

        case 'edit' :
            switch (table) {
                case 'area' :
                    globalId = "editMappingAreasGlobal";
                    divToBeHidden = "editAreaTable";
                    break;
                case 'product':
                    globalId = "editMappingProductsGlobal";
                    divToBeHidden = "editMappingTable";
                    break;
                case 'brand':
                    globalId = "editMappingBrandsGlobal";
                    divToBeHidden = "editBrandTable";
                    break;
                case 'referral':
                    globalId = "editMappingReferrerSourcesGlobal";
                    divToBeHidden = "editReferrersTable";
                    break;

            }
            break;

        case 'quick' :
            switch (table) {
                case 'area' :
                    globalId = "quickMappingAreasGlobal";
                    divToBeHidden = "quickAreaTable";
                    break;
                case 'product':
                    globalId = "quickMappingProductsGlobal";
                    divToBeHidden = "quickMappingTable";
                    break;
                case 'brand':
                    globalId = "quickMappingBrandsGlobal";
                    divToBeHidden = "quickBrandTable";
                    break;
                case 'referral':
                    globalId = "quickMappingReferrerSourcesGlobal";
                    divToBeHidden = "quickReferrersTable";
                    break;

            }
            break;

        case 'fast' :
            switch (table) {
                case 'area' :
                    globalId = "fastMappingAreasGlobal";
                    divToBeHidden = "fastAreaTable";
                    break;
                case 'product':
                    globalId = "fastMappingProductsGlobal";
                    divToBeHidden = "fastMappingTable";
                    break;
                case 'brand':
                    globalId = "fastMappingBrandsGlobal";
                    divToBeHidden = "fastBrandTable";
                    break;
                case 'referral':
                    globalId = "fastMappingReferrerSourcesGlobal";
                    divToBeHidden = "fastReferrersTable";
                    break;


            }
            break;
    }
    if ((($("#" + globalId).is(':checked')) == true) || (flag == 1)) {
        $("#" + divToBeHidden).find('input').not("#" + globalId).attr('disabled', 'disabled');
        var message = "All " + table + "s selected.";
        $("#" + divToBeHidden + "Info").empty().append(message);

    }
    else if ((($("#" + globalId).is(':checked')) == false) || (flag == 1)) {
        $("#" + divToBeHidden).find('input').removeAttr('disabled');
        $("#" + divToBeHidden + "Info").empty();
    }


    $("#" + divToBeHidden).on('page-change.bs.table', function (number, size) {

        if ((($("#" + globalId).is(':checked')) == true) || (flag == 1)) {
            $("#" + divToBeHidden).find('input').not("#" + globalId).attr('disabled', 'disabled');
            var message = "All " + table + "s selected."
            $("#" + divToBeHidden + "Info").empty().append(message);
        }
        else {
            $("#" + divToBeHidden).find('input').removeAttr('disabled');
            $("#" + divToBeHidden + "Info").empty();
        }
    });
}

