"use strict";

/*Formatter function for group name */
function groupNameFormatter(value, row, index) {
    var value = row.description;
    if (value == null) {
        value = "-";
    }
    return '<div title ="' + value + '">' + row.name + '</div>';
}

/*Formatter function for group SMS type formatter */
function groupSmsTypeFormatter(value, row, index) {
    var value = "-";
    var name = "";
    if (row.smsTypeData.name != null) {
        value = row.smsTypeData.description;
        name = row.smsTypeData.name;
    }
    return '<div title ="' + value + '">' + name + '</div>';
}

function lastUpdatedOnFormatter(value,row) {
    var val=row.lastUpdatedOn;
    if (val != null) {
        var date = new Date(val); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        return '<div title="Last updated by : ' + row.lastUpdatedBy + '" >' + d + '</div>';

    }
    else {
        return val;
    }
}



/*Formatter function for type match cooling period formatter */
function typeMatchCPFormatter(value, row, index) {

    var value = "N/A";
    if (row.typeMatchCoolingPeriod == null || row.typeMatchCoolingPeriod.value == null) {
        return value;
    }
    if (row.typeMatchCoolingPeriod.unit == "CALENDAR_DAY") {
        return row.typeMatchCoolingPeriod.value
            + '<span title="Calender day" style="top: 4px" class="glyphicon pull-right glyphicon-calendar"></span>';
    } else {
        var absoluteValue = row.typeMatchCoolingPeriod.value + "s";
        return absoluteValue
            + '<span title="Absolute time in seconds" style="top: 4px" class="glyphicon pull-right glyphicon-time"></span>';

    }
}


/*Formatter function for content match cooling period formatter */
function contentMatchCPFormatter(value, row, index) {
    var value = "N/A";
    if (row.contentMatchCoolingPeriod == null || row.contentMatchCoolingPeriod.value == null) {
        return value;
    }
    if (row.contentMatchCoolingPeriod.unit == "CALENDAR_DAY") {
        return row.contentMatchCoolingPeriod.value
            + '<span title="Calender day" style="top: 4px" class="glyphicon pull-right glyphicon-calendar"></span>';
    } else {
        return row.contentMatchCoolingPeriod.value + "s"
            + '<span title="Absolute time in seconds" style="top: 4px" class="glyphicon pull-right glyphicon-time"></span>';
    }
}

/*Data event for SMS type cooling period edit*/
window.typesEditEvents = {
    'click .edit': function (e, value, row, index) {
        var contentMatchCoolingPeriodValue = null;
        var contentMatchCoolingPeriodUnit = null;
        var typeMatchCoolingPeriodValue = null;
        var typeMatchCoolingPeriodUnit = null;

        if (row.contentMatchCoolingPeriod != null) {
            contentMatchCoolingPeriodValue = row.contentMatchCoolingPeriod.value;
            contentMatchCoolingPeriodUnit = row.contentMatchCoolingPeriod.unit;
        }
        if (row.typeMatchCoolingPeriod != null) {
            typeMatchCoolingPeriodValue = row.typeMatchCoolingPeriod.value;
            typeMatchCoolingPeriodUnit = row.typeMatchCoolingPeriod.unit;
        }
        editCPModal(row.name, row.id, contentMatchCoolingPeriodValue, contentMatchCoolingPeriodUnit,
            typeMatchCoolingPeriodValue, typeMatchCoolingPeriodUnit, 'types')
    }
};

/*Data event for SMS content match cooling period edit*/
window.groupEditEvents = {
    'click .edit': function (e, value, row, index) {
        var contentMatchCoolingPeriodValue = null;
        var contentMatchCoolingPeriodUnit = null;
        var typeMatchCoolingPeriodValue = null;
        var typeMatchCoolingPeriodUnit = null;

        if (row.contentMatchCoolingPeriod != null) {
            contentMatchCoolingPeriodValue = row.contentMatchCoolingPeriod.value;
            contentMatchCoolingPeriodUnit = row.contentMatchCoolingPeriod.unit;
        }
        if (row.typeMatchCoolingPeriod != null) {
            typeMatchCoolingPeriodValue = row.typeMatchCoolingPeriod.value;
            typeMatchCoolingPeriodUnit = row.typeMatchCoolingPeriod.unit;
        }
        editCPModal(row.name, row.id, contentMatchCoolingPeriodValue, contentMatchCoolingPeriodUnit,
            typeMatchCoolingPeriodValue, typeMatchCoolingPeriodUnit, 'group')
    }
};


/*Function to populate current cooling period into the modal popups */
function editCPModal(name, id, contentMatchValue, contentMatchUnit, typeMatchValue, typeMatchUnit, context) {

    var modalId, nameId, typeUnitId, contextUnitId, typeValueId, contextValueId, rowId;

    if (context == 'types') {
        modalId = "editTypeCoolingPeriodModal";
        nameId = "typeName";
        typeUnitId = "typeMatchUnit";
        contextUnitId = "contentMatchUnit";
        typeValueId = "typeMatchCoolingPeriod";
        contextValueId = "contentMatchCoolingPeriod";
        rowId = "typesId";
    }
    else {
        modalId = "editGroupCoolingPeriodModal";
        nameId = "groupName";
        typeUnitId = "groupMatchUnit";
        contextUnitId = "groupContentMatchUnit";
        typeValueId = "groupMatchCoolingPeriod";
        contextValueId = "groupContentMatchCoolingPeriod";
        rowId = "groupId";
    }

    $("#" + modalId).modal();

    $("#" + nameId).val(name);

    $("#" + typeUnitId).val(typeMatchUnit);
    $("#" + typeValueId).val(typeMatchValue);

    $("#" + contextUnitId).val(contentMatchUnit);
    $("#" + contextValueId).val(contentMatchValue);

    $("#" + rowId).val(id);
}


