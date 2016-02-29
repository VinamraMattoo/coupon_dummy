"use strict";

/*Formatter function for user name*/
function userNameFormatter(value, row, index) {
    return '<div title="' + row.description + '">' + row.name + '</div>';
}

/*Formatter function for user registration date*/
function userRegisteredOnFormatter(value, row, index) {
    if (value != null) {
        var utcSeconds = value;
        var date = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        return '<div title="Registered On: ' + z + '" style="color:purple">' + d + '</div>';
    }
}
function userLastUpdatedOnFormatter(value, row, index) {
    if (value != null) {
        var utcSeconds = value;
        var date = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        return '<div title="Last Updated On: ' + z + ' by : ' + row.lastUpdatedBy + '" style="color:purple">' + d + '</div>';
    }
}


/*Formatter function for user status*/
function userStatusFormatter(value, row, index) {
    if (value === true) {
        return '<span title="Active" style="color: green"">Active</span>';
    }
    else {
        return '<span title="Inactive" style="color: red" >Inactive</span>';
    }
}

/*Formatter function for user action icons*/
function userConfigActionFormatter(value, row, index) {
    if (row.active === true) {
        return '<a class="deactiveUsr" href="javascript:void(0)" ><span title="Deactivate" ' +
            'style="color:red" class="glyphicon glyphicon-off "></span></a>&nbsp;&nbsp;' +

            '<a class="resetPass" href="javascript:void(0)"  ><span title="Reset Password" ' +
            'class="glyphicon glyphicon-repeat"></span></a>&nbsp;&nbsp;';
    }
    else {
        return '<a class="reactiveUsr" href="javascript:void(0)" ><span title="Reactivate" ' +
            ' style="color:green" class="glyphicon glyphicon-off "></span></a>&nbsp;&nbsp;';
    }
}

/*function to open the add user modal and clearing all the values*/
function addNewUserModal() {
    $('#addNewUser').modal();
    $("#userNameEdit").val('');
    $("#userPasswordEdit").val('');
    $("#targetId").val('');
    $("#userEmailEdit").val('');
}

/*Action handler for user config events*/
window.userConfigActionEvents = {
    //reset pass btn function
    'click .resetPass': function (e, value, row, index) {
        $("#resetPassword").val("");
        $("#resetRePassword").val("");

        var userName = row.name;
        var userId = row.id;
        $("#nameForResetPasswordModal").val(userName);
        $("#userIdForResetPassword").val(userId);
        $("#resetPasswordModal").modal();
    },

//deactivate btn function
    'click .deactiveUsr': function (e, value, row, index) {

        swal({
                title: "Deactivate user?",
                text: row.name + " will be deactivated",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: 'btn-danger',
                confirmButtonText: 'Yes',
                cancelButtonText: "No"
            },
            function (isConfirm) {
                if (isConfirm) {
                    deactivateUserAjaxCall(row.id);
                } else {
                    return;
                }
            });

    },
    'click .reactiveUsr': function (e, value, row, index) {

        swal({
                title: "Reactivate user?",
                text: row.name + " will be reactivated",
                type: "info",
                showCancelButton: true,
                confirmButtonClass: 'btn-danger',
                confirmButtonText: 'Yes',
                cancelButtonText: "No"
            },
            function (isConfirm) {
                if (isConfirm) {
                    reactivateUserAjaxCall(row.id);
                } else {
                    return;
                }
            });

    }
};
/*Generic function to show the password in the given input field
* id = the id of input field whose attribute has to be changed
* open = button or icon on clicking which the password type is changed to text
* close = button or icon on clicking which the text type is changed to password
*/
function showAddUserModalPassword(id,open,close) {
    document.getElementById(id).type = "text";
    $("#"+ open).hide();
    $("#"+ close).show();

}
/*Generic function to Hide the password in the given input field
 * id = the id of input field whose attribute has to be changed
 * open = button or icon on clicking which the password type is changed to text
 * close = button or icon on clicking which the text type is changed to password
 */
function hideAddUserModalPassword(id,open,close) {
    document.getElementById(id).type = "password";
    $("#"+ close).hide();
    $("#"+ open).show();
}
