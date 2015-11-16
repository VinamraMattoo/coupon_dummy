// validation of compulsory elements are done in this  js  file


/* ========= check For Publish function ===========
 *  checks whether publish is selected
 */


function checkIfPublished(data) {
    if ($("#publish").is(':checked')) {
        var status = validateIfPublished(data[0]);
        if (status == "true") {
            return true;
        }
        else {
            $("#statusBar").empty();
            return false;
        }
    }
    else
        return false;
}
/*function to switch through the JSON input to check whether any value is null*/
function validateIfPublished(inputJson) {

    var flag = "false";
    for (var key in inputJson) {
        switch (key) {

            case "name" :
            {
                if (inputJson[key] === null) {

                    $("#statusBar").empty().append("<h3>Invalid name</h3>");
                    flag = "true";
                    return flag;
                }
            }
                break;

            case "applicationType" :

                if (inputJson[key] === null) {

                    $("#statusBar").empty().append("<h3>Please specify an application type</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "actorType" :
                if (inputJson[key] === null) {
                    $("#statusBar").empty().append("<h3>Please specify an Actor type</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "contextType" :
                if (inputJson[key] === null) {
                    $("#statusBar").empty().append("<h3>Please specify a Context type</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "applicableFrom" :
                if (inputJson[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a valid application start date</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "applicableTill" :
                if (inputJson[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a valid application end date</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "transactionValMin" :
                if (inputJson[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a min transaction value</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "transactionValMax" :
                if (inputJson[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a max transaction value</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "discountAmountMin" :
                if (inputJson[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a min discount amount</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "discountAmountMax" :
                if (inputJson[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a max discount amount</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "rule" :
                for (var rl in key) {
                    switch (rl) {
                        case "ruleType" :
                            if (key[rl] === "PERCENT") {
                                if (key["PERCENT"] === "") {
                                    $("#statusBar").empty().append("<h3>Please specify percentage discount percentage</h3>");
                                    flag = "true";
                                    return flag;
                                }
                            }
                            else {
                                if (key["FLAT"] === "") {
                                    $("#statusBar").empty().append("<h3>Please specify flat discount percentage</h3>");
                                    flag = "true";
                                    return flag;
                                }
                                break;
                            }
                    }
                }

        }
    }
    return flag;

}