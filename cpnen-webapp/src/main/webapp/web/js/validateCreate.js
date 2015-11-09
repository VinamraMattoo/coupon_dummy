function validateOnPublish(arr) {

    var flag = "false";
    for (var key in arr) {
        switch (key) {

            case "name" :
            {
                if (arr[key] === null) {

                    $("#statusBar").empty().append("<h3>Invalid name</h3>");
                    flag = "true";
                    return flag;
                }
            }
                break;

            case "applicationType" :

                if (arr[key] === null) {

                    $("#statusBar").empty().append("<h3>Please specify an application type</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "actorType" :
                if (arr[key] === null) {
                    $("#statusBar").empty().append("<h3>Please specify an Actor type</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "contextType" :
                if (arr[key] === null) {
                    $("#statusBar").empty().append("<h3>Please specify a Context type</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "applicableFrom" :
                if (arr[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a valid application start date</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "applicableTill" :
                if (arr[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a valid application end date</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "transactionValMin" :
                if (arr[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a min transaction value</h3>");
                    flag = "true";
                    return flag;
                }
                break;

            case "transactionValMax" :
                if (arr[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a max transaction value</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "discountAmountMin" :
                if (arr[key] === "") {
                    $("#statusBar").empty().append("<h3>Please specify a min discount amount</h3>");
                    flag = "true";
                    return flag;
                }
                break;


            case "discountAmountMax" :
                if (arr[key] === "") {
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