"use strict";
/*function stores the products selected into an array and modified in the formatter function */
function getProductSelections(id) {

    $('#' + id).on('check.bs.table', function (e, row) {
        var flag = 0;
        for (var val in selectedProductMappings) {
            if ((selectedProductMappings.length > 0) && (selectedProductMappings[val].pid == row.id) && (selectedProductMappings[val].type == row.type)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            selectedProductMappings.push({
                pid: row.id,
                type: row.type
            });
        }


            $("#" + id + "Info").empty().append(selectedProductMappings.length + " product(s) selected");

    });


    $('#' + id).on('uncheck.bs.table', function (e, row) {

        for (var val in selectedProductMappings) {
            if ((selectedProductMappings.length > 0) && (selectedProductMappings[val].pid == row.id) && (selectedProductMappings[val].type == row.type)) {
                selectedProductMappings.splice(val, 1);
            }

        }
        $("#" + id + "Info").empty().append(selectedProductMappings.length + " product(s) selected");

    });


    $('#' + id).on('check-all.bs.table', function (e, row) {


        for (var key in row) {


            // var flag = "false";
            if (selectedProductMappings.length > 0) {
                for (var i = 0; i < selectedProductMappings.length; i++) {
                    if ((selectedProductMappings[i].pid == row[key].id) &&
                        (selectedProductMappings[i].type == row[key].type)) {
                        selectedProductMappings.splice(i, 1);
                    }
                }
            }

            selectedProductMappings.push({
                pid: row[key].id,
                type: row[key].type
            });

        }
        $("#" + id + "Info").empty().append(selectedProductMappings.length + " product(s) selected");

    });


    $('#' + id).on('uncheck-all.bs.table', function (e, row) {


        for (var key in row) {
            if (selectedProductMappings.length > 0)
                for (var val in selectedProductMappings) {

                    if ((selectedProductMappings[val].pid == row[key].id) && (selectedProductMappings[val].type == row[key].type)) {
                        selectedProductMappings.splice(val, 1);
                    }
                }

        }
        $("#" + id + "Info").empty().append(selectedProductMappings.length + " product(s) selected");

    });
}

/*function stores the brands selected into an array and modified in the formatter function */
function getBrandSelections(id, context) {

    $('#' + id).on('check.bs.table', function (e, row) {
        var flag = 0;
        for (var val in selectedBrandMappings) {
            if ((selectedBrandMappings.length > 0) && (selectedBrandMappings[val].id == row.id)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            selectedBrandMappings.push({
                id: row.id
            });
        }
        updateReferralsTable(context);
        $("#" + id + "Info").empty().append(selectedBrandMappings.length + " brand(s) selected");

    });


    $('#' + id).on('uncheck.bs.table', function (e, row) {

        for (var val in selectedBrandMappings) {
            if ((selectedBrandMappings.length > 0) && (selectedBrandMappings[val].id == row.id)) {
                selectedBrandMappings.splice(val, 1);
            }

        }
        updateReferralsTable(context);
        $("#" + id + "Info").empty().append(selectedBrandMappings.length + " brand(s) selected");

    });


    $('#' + id).on('check-all.bs.table', function (e, row) {


        for (var key in row) {
            if (selectedBrandMappings.length > 0) {
                for (var i = 0; i < selectedBrandMappings.length; i++) {
                    if (selectedBrandMappings[i].id == row[key].id) {
                        selectedBrandMappings.splice(i, 1);
                    }
                }
            }

            selectedBrandMappings.push({
                id: row[key].id

            });

        }

        updateReferralsTable(context);
        $("#" + id + "Info").empty().append(selectedBrandMappings.length + " brand(s) selected");

    });


    $('#' + id).on('uncheck-all.bs.table', function (e, row) {


        for (var key in row) {
            for (var val in selectedBrandMappings) {
                if ((selectedBrandMappings.length > 0) && (selectedBrandMappings[val].id == row[key].id)) {
                    selectedBrandMappings.splice(val, 1);
                }
            }

        }
        updateReferralsTable(context);
        $("#" + id + "Info").empty().append(selectedBrandMappings.length + " brand(s) selected");

    });


}


/*function stores the areas selected into an array and modified in the formatter function */
function getReferralSelections(id) {

    $('#' + id).on('check.bs.table', function (e, row) {
        var flag = 0;
        for (var val in selectedReferralMappings) {
            if ((selectedReferralMappings.length > 0) && (selectedReferralMappings[val].pid == row.id) && (selectedReferralMappings[val].type == row.type)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            selectedReferralMappings.push({
                pid: row.id,
                type: row.type
            });
        }
        $("#" + id + "Info").empty().append(selectedReferralMappings.length + " referral(s) selected");

    });


    $('#' + id).on('uncheck.bs.table', function (e, row) {

        for (var val in selectedReferralMappings) {
            if ((selectedReferralMappings.length > 0) && (selectedReferralMappings[val].pid == row.id) && (selectedReferralMappings[val].type == row.type)) {
                selectedReferralMappings.splice(val, 1);
            }

        }

        $("#" + id + "Info").empty().append(selectedReferralMappings.length + " referral(s) selected");

    });


    $('#' + id).on('check-all.bs.table', function (e, row) {


        for (var key in row) {


            // var flag = "false";
            if (selectedReferralMappings.length > 0) {
                for (var i = 0; i < selectedReferralMappings.length; i++) {
                    if ((selectedReferralMappings[i].pid == row[key].id) &&
                        (selectedReferralMappings[i].type == row[key].type)) {
                        selectedReferralMappings.splice(i, 1);
                    }
                }
            }

            selectedReferralMappings.push({
                pid: row[key].id,
                type: row[key].type
            });

        }
        $("#" + id + "Info").empty().append(selectedReferralMappings.length + " referral(s) selected");

    });


    $('#' + id).on('uncheck-all.bs.table', function (e, row) {


        for (var key in row) {
            if (selectedReferralMappings.length > 0)
                for (var val in selectedReferralMappings) {

                    if ((selectedReferralMappings[val].pid == row[key].id) && (selectedReferralMappings[val].type == row[key].type)) {
                        selectedReferralMappings.splice(val, 1);
                    }
                }

        }
        $("#" + id + "Info").empty().append(selectedReferralMappings.length + " referral(s) selected");

    });
}

/*function stores the referrals selected into an array and modified in the formatter function */
function getAreaSelections(id) {

    $('#' + id).on('check.bs.table', function (e, row) {
        var flag = 0;
        for (var val in selectedAreaMappings) {
            if ((selectedAreaMappings.length > 0) && (selectedAreaMappings[val].id == row.id)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            selectedAreaMappings.push({
                id: row.id
            });
        }

        $("#" + id + "Info").empty().append(selectedAreaMappings.length + " area(s) selected");
    });


    $('#' + id).on('uncheck.bs.table', function (e, row) {

        for (var val in selectedAreaMappings) {
            if ((selectedAreaMappings.length > 0) && (selectedAreaMappings[val].id == row.id)) {
                selectedAreaMappings.splice(val, 1);
            }

        }
        $("#" + id + "Info").empty().append(selectedAreaMappings.length + " area(s) selected");

    });


    $('#' + id).on('check-all.bs.table', function (e, row) {


        for (var key in row) {
            if (selectedAreaMappings.length > 0) {
                for (var i = 0; i < selectedAreaMappings.length; i++) {
                    if (selectedAreaMappings[i].id == row[key].id) {
                        selectedAreaMappings.splice(i, 1);
                    }
                }
            }

            selectedAreaMappings.push({
                id: row[key].id

            });

        }

        $("#" + id + "Info").empty().append(selectedAreaMappings.length + " area(s) selected");

    });


    $('#' + id).on('uncheck-all.bs.table', function (e, row) {


        for (var key in row) {
            for (var val in selectedAreaMappings) {
                if ((selectedAreaMappings.length > 0) && (selectedAreaMappings[val].id == row[key].id)) {
                    selectedAreaMappings.splice(val, 1);
                }
            }

        }
        $("#" + id + "Info").empty().append(selectedAreaMappings.length + " area(s) selected");

    });


}