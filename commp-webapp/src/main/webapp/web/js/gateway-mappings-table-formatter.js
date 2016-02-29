"use strict";

var rowCount = 0;

/*Formatter functions to extract the gateway priority*/
function priorityMappingFormatter(value, row, index) {
    var priority = getGatewayPriority(gatewayMapping[rowCount].name, row.gateways);
    rowCount++;
    if (gatewayMapping.length == rowCount)
        rowCount = 0;
    return priority;
};


/*Formatter function to extract the group mapping name*/
function mappingGroupsFormatter(value, row, index) {
    var descriptionValue = "-";
    if (row.description != null)
        descriptionValue = row.description;
    return '<div title ="' + descriptionValue + '">' + row.name + '</div>';
}

/*Formatter function to extract the gateway priority*/
function getGatewayPriority(gatewayName, gatewayMappings) {
    var priority = NULL_REPLACEMENT;
    gatewayMappings.forEach(function (gateway) {
        if (gateway.name === gatewayName) {
            priority = gateway.priority;
        }
    });
    return priority;
}


/**
 * Groups similar Sms groups in a single object with gateway mappings and
 * returns a list of this sms groups.
 */
function groupBySmsGroup(data) {
    var requiredResponse = {"groups": [], "modifiedResponse": []};
    for (var index in data) {
        var row = data[index];
        var groupId = row.smsGroupVo.id;

        var found = requiredResponse.groups.indexOf(groupId) > -1;

        var gatewayData = {};
        gatewayData["mappingId"] = row.id;
        gatewayData["id"] = row.smsGatewayVo.id;
        gatewayData["name"] = row.smsGatewayVo.name;
        gatewayData["status"] = row.smsGatewayVo.status;
        gatewayData["priority"] = row.priority;

        var groupMapping;
        if (found) {
            groupMapping = requiredResponse.modifiedResponse[requiredResponse.groups.indexOf(groupId)];
            groupMapping["gateways"].push(gatewayData);
        }
        else {
            requiredResponse.groups.push(groupId);
            groupMapping = {};
            groupMapping["groupId"] = groupId;
            groupMapping["name"] = row.smsGroupVo.name;
            groupMapping["description"] = row.smsGroupVo.description;
            groupMapping["gateways"] = [];

            groupMapping["gateways"].push(gatewayData);
            requiredResponse.modifiedResponse.push(groupMapping);
        }

    }
    return requiredResponse.modifiedResponse;
}

/*Window event handler for getting the current priorities
 * and populate the priorities in order to the modal popup
 * */
window.editGatewayPriorityEvent = {
    'click .edit': function (e, value, row, index) {

        var tags = "";
        for (var priorityId = 0; priorityId < gatewayMapping.length; priorityId++) {
            var temp = priorityId + 1;
            tags += ' Priority ' + temp + ' :' +
                '<div class="pull-right" style="margin-right: 70px;">' +
                '<select class="modalInput" id="prioritySelect' + priorityId + '" style="width:150px;height:22px">' +
                '<option selected>N/A</option>';
            for (var gatewayName = 0; gatewayName < gatewayMapping.length; gatewayName++) {
                tags += '<option value="' + gatewayMapping[gatewayName].name + '">' + dataFormatter(gatewayMapping[gatewayName].name) + '</option>';
            }
            tags += ' </select></div><br> <br>';
        }
        $("#prioritySelectors").empty().append(tags);

        $('#priorityResponse').empty();
        $("#groupId").val(row.groupId);
        $("#SMSgroupName").val(row.name);
        $("#SMSgroupName").prop('title', row.name);


        var priorityValue;
        for (var selectId = 0; selectId < gatewayMapping.length; selectId++) {
            priorityValue = "N/A";
            for (var priority = 0; priority < gatewayMapping.length; priority++) {
                var currentPriority = getGatewayPriority(gatewayMapping[priority].name, row.gateways);
                if (selectId + 1 == currentPriority) {
                    priorityValue = gatewayMapping[priority].name;
                    break;
                }
            }

            $("#prioritySelect" + selectId).val(priorityValue);
        }
        $("#editGatewayPriority").modal();
    }
}


/*Function that gets the updated priority and forms a JSON object */
function submitGatewayPriority() {
    var gwPriorityMapping = [];

    $('#priorityResponse').empty();


    var count = 0;
    for (var i = 0; i < gatewayMapping.length; i++) {

        if ($("#prioritySelect" + i).val() != "N/A") {
            for (var index = 0; index < gatewayMapping.length; index++) {

                if ($("#prioritySelect" + i).val() === gatewayMapping[index].name) {
                    gwPriorityMapping.push({
                        "gatewayId": gatewayMapping[index].id,
                        "priority": ++count
                    });
                    break;
                }

            }
        }
    }

    var flag = true;
    loop1 : for (var m = 0; m < gwPriorityMapping.length; m++) {
        for (var n = m + 1; n < gwPriorityMapping.length; n++) {
            if (gwPriorityMapping[m].gatewayId == gwPriorityMapping[n].gatewayId) {
                flag = false;
                break loop1;
            }

        }

    }
    if (flag == false) {
        $('#priorityResponse').empty().append("<p style='font-size:12px;color:red;margin-bottom: 0px;'>Duplicate gateways selected</p>");
    }
    else {

        for (var k = 0; k < gatewayMapping.length; k++) {
            flag = true;
            for (var l = 0; l < gwPriorityMapping.length; l++) {
                if (gatewayMapping[k].id == gwPriorityMapping[l].gatewayId) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                gwPriorityMapping.push({
                    "gatewayId": gatewayMapping[k].id,
                    "priority": null
                });
            }

        }
        var updatedPriorityJson = $("#priorityMappingForm").map(function () {
            var priorityJson = {
                "groupId": $("#groupId").val(),
                "gatewayPriorityList": gwPriorityMapping
            };
            return priorityJson;
        }).get();

        updateGroupPriority(updatedPriorityJson);
    }
}