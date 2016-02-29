/*all coupon creation related function are defined here*/

"use strict";

/*date time picker for calender input */
function dateTimeInput(startDate, endDate) {
    $('#' + startDate).datetimepicker({
        format: getCurrentDateTimeFormat(),
        widgetPositioning: {horizontal: 'left', vertical: 'bottom'},
        minDate: moment().startOf('day')
    });
    $('#' + endDate).datetimepicker({
        useCurrent: false,
        format: getCurrentDateTimeFormat(),
        widgetPositioning: {horizontal: 'left', vertical: 'bottom'}

    });
    $('#' + startDate).on("dp.change", function (e) {
        $('#' + endDate).data("DateTimePicker").minDate(e.date);
    });
    $("#" + endDate).on("dp.change", function (e) {
        $('#' + startDate).data("DateTimePicker").maxDate(e.date);
    });
    $("#" + startDate).integerMask();
    $("#" + endDate).integerMask();
}
/*edit date time picker for calender input */
function editDateTimeInput(startDate, endDate) {
    $('#' + startDate).datetimepicker({
        format: getCurrentDateFormat(),
        widgetPositioning: {horizontal: 'left', vertical: 'bottom'}

    });
    $('#' + endDate).datetimepicker({
        useCurrent: false,
        format: getCurrentDateFormat(),
        widgetPositioning: {horizontal: 'left', vertical: 'bottom'}

    });
    $('#' + startDate).on("dp.change", function (e) {
        $('#' + endDate).data("DateTimePicker").minDate(e.date);
    });
    $("#" + endDate).on("dp.change", function (e) {
        $('#' + startDate).data("DateTimePicker").maxDate(e.date);
    });
    $("#" + startDate).integerMask();
    $("#" + endDate).integerMask();

}
/*extend date time picker for calender input*/
function extendDateTimeInput(startDate, endDate) {
    $('#' + startDate).datetimepicker({
        format: getCurrentDateTimeFormat()
    });
    $('#' + endDate).datetimepicker({
        useCurrent: false,
        format: getCurrentDateTimeFormat()
    });

    $("#" + startDate).integerMask();
    $("#" + endDate).integerMask();

}


/*data formatter of listCouponCode.jsp to add a delete option*/
function codeOptionsFormatter(value, row, index) {
    var cid = row.couponId;
    var codeId = row.id;
    var deactivation = '';
    if (row.deactivatedOn != null) {

  deactivation += ' <div style="color:red"><strong><span class="glyphicon glyphicon-ban-circle" title="Deactivated"></span></strong></div>';
    }
    else {

        deactivation += '<div style="color:green"><strong><span title="Click to deactivate code" onclick="deactivatingThisCode(' + cid + ',' + codeId +
            ')" class="glyphicon glyphicon-remove-sign"> </span></strong></div>' ;

    }

    return [
        deactivation
    ].join('');
}
/*returns the brands checked*/
function getBrands(flag) {

    var brands = [];
    if (flag == "create") {
        if (($("#createGlobal").is(':checked')) == false)
            brands = $('#brandTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "edit") {
        if (($("#editGlobal").is(':checked')) == false)
            brands = $('#editBrandTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "quick") {
        if (($("#quickGlobal").is(':checked')) == false)
            brands = $('#quickBrandTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "fast") {
        if (($("#fastGlobal").is(':checked')) == false)
            brands = $('#fastBrandTable').bootstrapTable('getAllSelections');
    }
    var brandsJson = [];
    for (var i = 0; i < brands.length; i++) {
        brandsJson.push({
            brandId: brands[i].id
        });
    }

    return brandsJson;

}
/*returns the mappings checked*/
function getMappings(flag) {
    var mappings = [];
    if (flag == "create") {
        if (($("#createGlobal").is(':checked')) == false)
            mappings = $('#mappingTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "edit") {
        if (($("#editGlobal").is(':checked')) == false)
            mappings = $('#editMappingTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "quick") {
        if (($("#quickGlobal").is(':checked')) == false)
            mappings = $('#quickMappingTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "fast") {
        if (($("#fastGlobal").is(':checked')) == false)
            mappings = $('#fastMappingTable').bootstrapTable('getAllSelections');
    }
    var mappingsJson = [];

    for (var i = 0; i < mappings.length; i++) {
        mappingsJson.push({
            productId: mappings[i].id,
            name: mappings[i].name,
            type: mappings[i].type
        });
    }

    return mappingsJson;

}

/*returns the referrals checked*/
function getReferrals(flag) {
    var referrals = [];
    if (flag == "create") {
        if (($("#createGlobal").is(':checked')) == false)
            referrals = $('#referrersTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "edit") {
        if (($("#editGlobal").is(':checked')) == false)
            referrals = $('#editReferrersTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "quick") {
        if (($("#quickGlobal").is(':checked')) == false)
            referrals = $('#quickReferrersTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "fast") {
        if (($("#fastGlobal").is(':checked')) == false)
            referrals = $('#fastReferrersTable').bootstrapTable('getAllSelections');
    }
    var referralJson = [];

    for (var i = 0; i < referrals.length; i++) {
        referralJson.push({
            referrerId: referrals[i].id,
            name: referrals[i].name,
            type: referrals[i].type

        });
    }

    return referralJson;
}

/*returns the areas checked*/
function getAreaMappings(flag) {

    var area = [];
    if (flag == "create") {
        if (($("#createGlobal").is(':checked')) == false)
            area = $('#areaTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "edit") {
        if (($("#editGlobal").is(':checked')) == false)
            area = $('#editAreaTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "quick") {
        if (($("#quickGlobal").is(':checked')) == false)
            area = $('#quickAreaTable').bootstrapTable('getAllSelections');
    }
    else if (flag == "fast") {
        if (($("#fastGlobal").is(':checked')) == false)
            area = $('#fastAreaTable').bootstrapTable('getAllSelections');
    }
    var areasJson = [];
    for (var i = 0; i < area.length; i++) {
        areasJson.push({
            areaId: area[i].id
        });
    }

    return areasJson;

}