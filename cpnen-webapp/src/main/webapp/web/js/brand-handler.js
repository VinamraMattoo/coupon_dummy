"use strict";

var trimmedJSON = [];

function updateReferralsTable(context) {
    trimmedJSON = [];
    var b2bSelection = $("#" + context + "B2BId").is(':checked');
    var b2cSelection = $("#" + context + "B2CId").is(':checked');
    for (var i = 0; i < referralJSON.length; i++) {
        for (var j = 0; j < selectedBrandMappings.length; j++) {
            if (selectedBrandMappings[j].id == referralJSON[i].brandId) {

                if ((referralJSON[i].type == "B2B") && (b2bSelection == true))
                    trimmedJSON.push(referralJSON[i]);
                if ((referralJSON[i].type == "B2C") && (b2cSelection == true))
                    trimmedJSON.push(referralJSON[i]);
                break;
            }
        }
    }


    /*    for (var i = 0; i < selectedReferralMappings.length; i++) {
     for (var j = 0; j < trimmedJSON.length; j++) {
     if (selectedReferralMappings[i].pid === trimmedJSON[j].id) {
     break;
     }
     }
     console.log(JSON.stringify(selectedReferralMappings));

     selectedReferralMappings.splice(i, 1);
     console.log("after");

     console.log(JSON.stringify(selectedReferralMappings));

     }*/
    for (var index = 0; index < selectedReferralMappings.length; index++) {

        if ((b2bSelection == true ) && (b2cSelection == false )) {
            if (selectedReferralMappings[index].type === 'B2C' ) {
                selectedReferralMappings.splice(index, 1);
            }
        }
        else if ((b2cSelection == true ) && (b2bSelection == false )) {
            if (selectedReferralMappings[index].type === 'B2B' ) {
                selectedReferralMappings.splice(index, 1);
            }
        }
    }

    switch (context) {
        case 'create' :
            $('#referrersTable').bootstrapTable('load', trimmedJSON);
            break;
        case 'edit' :
            $('#editReferrersTable').bootstrapTable('load', trimmedJSON);
            break;
        case 'quick' :
            $('#quickReferrersTable').bootstrapTable('load', trimmedJSON);
            break;
        case 'fast' :
            $('#fastReferrersTable').bootstrapTable('load', trimmedJSON);
            break;
    }

}