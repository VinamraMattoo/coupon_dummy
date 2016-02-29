"use strict";

var gatewayMapping = [];
var NULL_REPLACEMENT = "-";

/*Function called when the document is first loaded*/
$(document).ready(function () {
    getGatewayList();
});

/*Function gets all the jsp files and populates it appropriate jsp files*/
function loadAllFiles() {

    $.get("../jsp/SMSGroupMappings.jsp", function (data) {

        $("#smsGroupings").empty().append(data);

        $("#typeMatchCoolingPeriod").integerMask();
        $("#contentMatchCoolingPeriod").integerMask();


    });

    $.get("../jsp/SMSTypeMappings.jsp", function (data) {

        $("#smsTypes").empty().append(data);
        $("#groupMatchCoolingPeriod").integerMask();
        $("#groupContentMatchCoolingPeriod").integerMask();


    });


    $.get("../jsp/GatewayMappings.jsp", function (data) {

        $("#gatewayMappings").empty().append(data);

        var tag = '<tr>' +
            '<th data-field="name" data-formatter="mappingGroupsFormatter">Groups</th>';

        for (var i = 0; i < gatewayMapping.length; i++) {
            tag += ' <th data-formatter="priorityMappingFormatter">' +
                dataFormatter(gatewayMapping[i].name) + '</th>';
        }
        tag += '<th data-formatter="editActionColumnFormatter" data-events="editGatewayPriorityEvent">Actions</th>';

        $("#columnData").append(tag);

    });

    $.get("../jsp/SMSConfigurations.jsp", function (data) {

        $("#smsConfigurations").empty().append(data);

    });
    $.get("../jsp/UserConfigurations.jsp", function (data) {

        $("#userConfigurations").empty().append(data);

        $("#userNameEdit").alphaNumericMask();


    });
}

/*Function checks for only numeric integer values*/
$.fn.integerMask = function () {
    var mask = new RegExp('^[0-9]*$');
    $(this).keypress(function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });

    $(this).bind("paste", function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });
};


/*Function checks for only numeric integer values*/
$.fn.alphaNumericMask = function () {
    var mask = new RegExp('^[0-9a-zA-z ]*$');
    $(this).keypress(function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });

    $(this).bind("paste", function (event) {
        if (!event.charCode) return true;
        var part1 = this.value.substring(0, this.selectionStart);
        var part2 = this.value.substring(this.selectionEnd, this.value.length);
        if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
            return false;
    });
};

/*Function called when other div has to be shown*/
function loadThisDiv(id) {
    location.hash = id;
}


/*Common bootstrap css formatter */
function bootstrapTableRowSizeModifier(value, row, index) {

    return {
        css: {
            "font-size": "12px !important",
            "padding-top": "0px !important",
            "padding-bottom": "0px !important"
        }
    };
}

/*Formatter function to add edit glyphicon*/
function editActionColumnFormatter(value, row, index) {
    return ['<a class="edit" href="javascript:void(0)" >',
        '<span title="Edit" class="glyphicon glyphicon-edit"></span>',
        '</a>'].join('');
}


/*jquery event handler to show manage coupon glyphicon*/
$('#navBarToggle').click(function () {
    if ($('#glyphiconId').hasClass('glyphicon-chevron-down')) {
        $('#glyphiconId').removeClass('glyphicon-chevron-down');
        $('#glyphiconId').addClass('glyphicon-chevron-up');
    }
    else {
        $('#glyphiconId').removeClass('glyphicon-chevron-up');
        $('#glyphiconId').addClass('glyphicon-chevron-down');
    }
});

/*Formatter function which accepts a string and converts it into lower case with '_' replaced with space */
function dataFormatter(inputString) {
    if (inputString == null) {
        return;
    }
    inputString = inputString.replace(/_/g, ' ');
    inputString = inputString.toLowerCase();
    return inputString.charAt(0).toUpperCase() + inputString.slice(1);

}

/*function converts given epoch time to current date time format */
function formatDate(date) {
    if (date == null)
        return "";

    return moment(date).format(getCurrentDateTimeFormat());
}

/*function converts date time format time to epoch time*/
function getMilliSec(date) {
    if (date == "")
        return "";
    return moment(date).valueOf();
}

function dateTimeInput(id)
{
    $('#'+id).datetimepicker({
        format: getCurrentDateTimeFormat(),
        widgetPositioning: {horizontal: 'left', vertical: 'bottom'}
    });
}