"use strict";

/*Ajax call for updating group cooling period*/
function updateGroupCP(updateJSON, groupId) {
    $.ajax({

        type: "PUT",
        url: "../rws/smsGroup/" + groupId,
        data: JSON.stringify(updateJSON[0]),
        contentType: "application/json; charset=utf-8",

        success: function () {
            successSweetAlert("updateGroupCP");
            $("#SMSGroupMappingTable").bootstrapTable('refresh');

        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }
    });
}

/*Ajax call for updating type cooling period*/
function updateTypeCP(updateJSON, typeId) {
    $.ajax({

        type: "PUT",
        url: "../rws/smsType/" + typeId,
        data: JSON.stringify(updateJSON[0]),
        contentType: "application/json; charset=utf-8",

        success: function () {
            successSweetAlert("updateTypeCP");

            $("#SMSTypeMappingTable").bootstrapTable('refresh');
        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }

    });
}
/*Ajax call for updating config parameters*/
function updateConfigParameters(updateJSON) {
    $.ajax({

        type: "PUT",
        url: "../rws/targetConfigValue",
        data: JSON.stringify(updateJSON[0]),
        contentType: "application/json; charset=utf-8",

        success: function () {
            successSweetAlert("updateConfigParams");

            $("#SMSConfigTable").bootstrapTable('refresh');
        }, error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }
    });
}
/*Ajax call for adding a new user*/
function addNewSMSSender(newUserJSON) {
    $.ajax({

        type: "POST",
        url: "../rws/smsSender",
        data: JSON.stringify(newUserJSON[0]),
        contentType: "application/json; charset=utf-8",

        success: function () {
            successSweetAlert("registerUser");
            $("#resetPasswordModal").modal('hide');
            $("#userConfigurationTable").bootstrapTable('refresh');
        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }


    });

}

/*Ajax call for deactivating user*/
function deactivateUserAjaxCall(id) {
    $.ajax({

        type: "PUT",
        url: "../rws/smsSender/" + id + "/deactivate",

        success: function () {
            successSweetAlert("deactivateUser");
            $("#userConfigurationTable").bootstrapTable('refresh');
        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }
    });
}

/*Ajax call for resetting password */
function resetUserPassword() {
    var id = ($("#userIdForResetPassword").val());
    var password = ($("#resetPassword").val());
    var rePassword = ($("#resetRePassword").val());
    if (password == rePassword) {
        $.ajax({

            type: "PUT",
            url: "../rws/smsSender/" + id + "/resetPassword/" + password,

            success: function () {
                successSweetAlert("editPassword");
                $("#resetPasswordModal").modal('hide');
            },
            error: function (xhr, err) {
                displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
            }
        });
    }
    else {
        swal("Passwords didn't match please retry");
    }
}

/*Ajax call for getting gateway list*/
function getGatewayList() {
    $.ajax({

        type: "GET",
        url: "../rws/gateway/list",

        success: function (data) {
            gatewayMapping = data;
            loadAllFiles();
        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }
    });
}

/*Ajax call for updating gateway priority*/
function updateGroupPriority(updatePriorityJSON) {

    $.ajax({

        type: "POST",
        url: "../rws/group/gateway",
        data: JSON.stringify(updatePriorityJSON[0]),
        contentType: "application/json; charset=utf-8",

        success: function () {
            successSweetAlert("updatePriority");

            $("#gatewayPrioritiesMappingsTable").bootstrapTable('refresh');
            $("#editGatewayPriority").modal('hide');
        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }
    });
}


/*Ajax call for reactivate user*/
function reactivateUserAjaxCall(id) {
    $.ajax({

        type: "PUT",
        url: "../rws/smsSender/" + id + "/reactivate",

        success: function () {
            successSweetAlert("reactivateUser");
            $("#userConfigurationTable").bootstrapTable('refresh');
        },
        error: function (xhr, err) {
            displayErrorDetails(err, xhr.status, xhr.statusText, xhr.getResponseHeader('X-Commp-web-Error-Detail'));
        }
    });
}

/*Function maps error cases to their corresponding swals */
function displayErrorDetails(errorType, statusCode, statusMessage, customMessage) {
    switch (statusCode) {
        case 409:
            swal(errorType, customMessage);
            break;
        default :
            swal("Error!", statusMessage);
            break;
    }
}