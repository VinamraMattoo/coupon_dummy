function populateEdit(response) {
    alert(JSON.stringify(response));
    for (var arr in response) {
        switch (arr) {

            case "name" :
                $("#edit_name").val(response[arr]);
                break;

            case "description" :
                $("#edit_description").val(response[arr]);
                break;

            case "inclusive" :
                $("#edit_inclusive").prop('checked', response[arr]);
                break;

            case "applicationType" :
                $("#edit_applicationType").val(response[arr]);
                break;

            case "actorType" :
                $("#edit_actorType").val(response[arr]);
                break;

            case "contextType" :
                $("#edit_contextType").val(response[arr]);
                break;

            case "applicableFrom" :
                $("#edit_applicableFrom").val(getDateInFormat(response[arr]));
                break;
            case "applicableTill" :
                $("#edit_applicableTill").val(getDateInFormat(response[arr]));
                break;

            case "transactionValMin" :
                $("#edit_transactionValMin").val(response[arr]);
                break;

            case "transactionValMax" :
                $("#edit_transactionValMax").val(response[arr]);
                break;


            case "discountAmountMin" :
                $("#edit_discountAmountMin").val(response[arr]);
                break;


            case "discountAmountMax" :
                $("#edit_discountAmountMax").val(response[arr]);
                break;


            case "applicableUseCount" :
                $("#edit_applicableUseCount").val(response[arr]);
                break;

            case "global" :
                $("#edit_global").prop('checked', response[arr]);
                break;

            case "nthTime" :
                $("#edit_nthTime").val(response[arr]);
                break;


            case "nthTimeReccuring" :
                $("#edit_nthTimeReccuring").prop('checked', response[key]);
                break;


            case "discountRule" :
                for (var value in arr) {
                    switch (value) {
                        case "ruleType" :
                            $("#edit_ruleType").val(arr[value]);
                            break;

                        case "discountPercentage" :
                            $("#edit_discountPercentage").val(arr[value]);
                            break;

                        case "discountFlatAmount" :
                            $("#edit_discountFlatAmount").val(arr[value]);
                            break;

                        case "description" :
                            $("#edit_ruleDesc").val(arr[value]);
                            break;
                    }

                }
                break;
            case "productMapping" :
                for (var val in arr) {
                    if (val == "productId") var prodId = arr[val];
                }
                break;
            case "brandMapping" :
                for (var va in arr) {
                    if (va == "brandId") var brandId = arr[va];
                }
                break;
        }
    }
}
function getDateInFormat(date) {
    return moment(date).format();
}