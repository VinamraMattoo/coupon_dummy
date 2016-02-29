/**
 * Created by user on 3/2/16.
 */



/*function to create JSON on submission of target config edit form  */
function updateTargetConfigParameters() {
    var configValue;
    var dataType = $("#dataTypeHolder").val();
    if ((dataType == "NUMBER") || (dataType == "TEXT")) {
        configValue = $("#configValue").val();
    }
    else if (dataType == "BOOLEAN") {
        if ($("#configValueTrue").is(':checked'))
            configValue = "true";
        else
            configValue = "false";
    }
    else if (dataType == "DATE") {
        configValue = getMilliSec($("#configDateValue").val());
    }
    var configParamJson = $("#configParamsForm").map(function () {
        var configJson = {
            "targetId": $("#targetId").val(),
            "configParamId": $("#configParamId").val(),
            "value": configValue
        };
        return configJson;
    }).get();
    updateConfigParameters(configParamJson);
}

/*function to create JSON on submission of add user form  */
function addNewUser() {
    var addUserJson = $("#addNewUser").map(function () {
        var name = $("#userNameEdit").val();
        var password = $("#userPasswordEdit").val();
        var description = $("#targetId").val();
        var email = $("#userEmailEdit").val();


        var newUserJson = {
            "name": name,
            "password": password,
            "description": description,
            "email": email
        };
        return newUserJson;
    }).get();
    addNewSMSSender(addUserJson);
}