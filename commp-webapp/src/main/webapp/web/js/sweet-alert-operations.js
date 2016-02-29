/**
 * Created by user on 1/2/16.
 */

/*Function maps appropriate events to sweet alert messages*/
function successSweetAlert(context) {
    var messageString;

    switch (context) {
        case "updateGroupCP" :
            messageString = "SMS group cooling period has been updated successfully";
            break;
        case "updateTypeCP":
            messageString = "SMS type cooling period has been updated successfully";
            break;
        case "updateConfigParams":
            messageString = "Config parameter has been updated successfully";
            break;
        case "registerUser" :
            messageString = "New user successfully added";
            break;
        case "deactivateUser":
            messageString = "User deactivated successfully";
            break;
        case "editPassword" :
            messageString = "Password updated successfully";
            break;
        case "updatePriority" :
            messageString = "Priorities updated successfully";
            break;
        case "reactivateUser":
            messageString = "User reactivated successfully";
            break;
    }
    swal(messageString);
}