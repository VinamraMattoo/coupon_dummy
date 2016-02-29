"use strict";

/*function to create JSON on submission of cooling period edit form  */
function updateSMSGroupCoolingPeriod() {


    var groupCPJSON = $("#groupsEditForm").map(function () {
        var groupCP = {

            "contentMatchCoolingPeriod": {
                "unit": $("#groupContentMatchUnit").val(),

                "value": $("#groupContentMatchCoolingPeriod").val()
            },
            "typeMatchCoolingPeriod": {
                "unit": $("#groupMatchUnit").val(),

                "value": $("#groupMatchCoolingPeriod").val()
            }


        };
        return groupCP;
    }).get();

    var groupId = $("#groupId").val();
    updateGroupCP(groupCPJSON,groupId);

}
/*function to create JSON on submission of SMS type edit form  */
function updateSMSTypeCoolingPeriod() {


    var typeCPJSON = $("#typesEditForm").map(function () {
        var typeCP = {

            "contentMatchCoolingPeriod": {
                "unit": $("#contentMatchUnit").val(),

                "value": $("#contentMatchCoolingPeriod").val()
            },
            "typeMatchCoolingPeriod": {
                "unit": $("#typeMatchUnit").val(),

                "value": $("#typeMatchCoolingPeriod").val()
            }


        };
        return typeCP;
    }).get();
    var typeId = $("#typesId").val();
    updateTypeCP(typeCPJSON,typeId);
}
