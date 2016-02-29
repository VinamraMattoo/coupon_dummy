"use strict";

/*Function called when an url is changed*/
window.onhashchange = function () {
    var id = null;
    var divsArray = ["dashboard", "smsGroupings", "gatewayMappings", "smsConfigurations", "userConfigurations","smsTypes"];
    if (location.hash.length > 0) {
        id = (location.hash.replace('#', ''));
        for (var i = 0; i < divsArray.length; i++) {

            if (i == id) {

                if ($("#" + divsArray[i]).is(':visible')) {
                    continue;
                }
                $("#" + divsArray[i]).show();
                continue;
            }
            $("#" + divsArray[i]).hide();

        }
    }
};

/*function called when logout is pressed asking for user confirmation*/
function logoutFunction() {
    $("#logoutForm").attr('action', "../logout");
}
