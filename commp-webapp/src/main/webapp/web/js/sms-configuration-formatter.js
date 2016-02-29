"use strict";

/*Formatter function for target type*/
function targetTypeFormatter(value, row, index) {
    return '<div title="' + row['configTargetTypeVo.description'] + '">'
        + dataFormatter(value) + '</div>';
}

/*Formatter function for config parameters*/
function configParamFormatter(value, row, index) {
    return '<div class="hideOverflow" title="' + row['configParamVo.description'] + '">'
        + dataFormatter(value) + '</div>';
}

/*Formatter function for target name*/
function targetNameFormatter(value, row, index) {
    var targetName = (row['targetName'] == null) ? NULL_REPLACEMENT : row.targetName;
    return '<div class="hideOverflow">'
        + dataFormatter(targetName) + '</div>';
}

/*Formatter function for configuration value*/
function configValueFormatter(value, row, index) {
    var dataType = row['configParamVo.valueDataType'];
    if (dataType == "DATE") {
        return '<div title=" Value :' + formatDate(parseInt(row.value)) + '">' + formatDate(parseInt(row.value)) + '</div>';

    }
    return '<div class="hideOverflow" title=" Value :' + row.value + '">' + row.value + '</div>';
}

/*Data event for config params edit*/
window.editConfigParamsEvent = {
    'click .edit': function (e, value, row, index) {

        var dataType = row['configParamVo.valueDataType'];
        var inputTag;
        if (dataType == "NUMBER") {
            inputTag = ' <input class="form-control modalInput" style="width:220px;height:22px"' +
                'type="number" id="configValue"/>';
        }
        else if (dataType == "BOOLEAN") {
            $("#configValueId").removeClass('pull-right');
            $("#configValueId").css("display", " inline");
            inputTag = '<div style="display: inline; margin-left: 110px;"> True<input id="configValueTrue" name="configRadio" type="radio" >&nbsp;&nbsp;&nbsp;&nbsp;'
                + ' False<input id="configValueFalse" name="configRadio" type="radio"></div>';
        }
        else if (dataType == "DATE") {

            inputTag = ' <input class="form-control modalInput" style="width:220px;height:22px" ' +
                'type="text" id="configDateValue"/>';


        }
        else {
            inputTag = ' <input class="form-control modalInput" style="width:220px;height:22px" ' +
                'type="text" id="configValue"/>';
        }
        $("#configValueId").empty().append(inputTag);

        var targetType = row['configTargetTypeVo.targetType'];
        var configParam = row['configParamVo.name'];
        var configValue = row['value'];
        var targetNameValue = row['targetName'];
        var targetId = row['targetId'];
        var configParamId = row['configParamVo.id'];

        editConfigParamsModal(targetType, configParam, configValue, targetNameValue, targetId, configParamId, dataType);
        hideEvent();
        dateTimeInput("configDateValue");
    }
};

/*Event handler on closing of the modal*/
function hideEvent() {
    $('#editSMSConfigModal').on('hidden.bs.modal', function () {
        $("#configValueId").addClass('pull-right');
        $("#configValueId").css("display", " ");
    });
}

/*Function populates current config parameters into the popup*/
function editConfigParamsModal(targetType, configParam, configValue, targetNameValue, targetId, configParamId, dataType) {
    $("#editSMSConfigModal").modal();

    $("#targetType").val(dataFormatter(targetType));
    $("#targetType").prop('title', dataFormatter(targetType));

    $("#configParam").val(dataFormatter(configParam));
    $("#configParam").prop('title', dataFormatter(configParam));

    $("#targetName").val(dataFormatter(targetNameValue));
    $("#targetName").prop('title', dataFormatter(targetNameValue));

    $("#targetId").val(targetId);
    $("#configParamId").val(configParamId);

    $("#dataTypeHolder").val(dataType);

    if ((dataType == "NUMBER") || (dataType == "TEXT")) {
        $("#configValue").val(configValue);
    }
    else if (dataType == "BOOLEAN") {
        if (configValue == "true")
            $("#configValueTrue").prop("checked", true);
        else
            $("#configValueFalse").prop("checked", true);
    }
    else if (dataType == "DATE") {
        $("#configDateValue").val(formatDate(parseInt(configValue)));
    }
}
function smsConfigTableResponseHandler(res) {
    var flatArray = [];
    $.each(res, function (i, element) {
        flatArray.push(JSON.flatten(element));
    });
    return flatArray;
}
