"use strict";

/*Checkbox toggle function to make sure atleast one of the checkbox is checked*/
function trueSelector(context, eventType) {
    var trueId, falseId;
    switch (context) {
        case 'global' :
            trueId = 'advGlobalTrue';
            falseId = 'advGlobalFalse';
            break;
        case 'inclusive' :
            trueId = 'advInclusiveTrue';
            falseId = 'advInclusiveFalse';
            break;
        case 'create' :
            trueId = "createB2BId";
            falseId = "createB2CId";
            break;
        case 'edit' :
            trueId = "editB2BId";
            falseId = "editB2CId";
            break;
        case 'quick' :
            trueId = "quickB2BId";
            falseId = "quickB2CId";
            break;
        case 'fast' :
            trueId = "fastB2BId";
            falseId = "fastB2CId";
            break;


    }
    if (eventType) {
        if (!($("#" + trueId).is(':checked')))
            $("#" + falseId).prop('checked', true);
    }
    else {
        if (!($("#" + falseId).is(':checked')))
            $("#" + trueId).prop('checked', true);
    }
    if ((context == 'create') || (context == 'edit') || (context == 'quick') || (context == 'fast')) {
        updateReferralsTable(context);
    }
}


//from js  convert the epoch time to readable time format function
function fromFormatter(value) {
    if (value != null) {
        var utcSeconds = value;
        var date = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        return '<div title="Applicable from : ' + z + '" style="color:purple">' + d + '</div>';

    }
    else {
        return value;
    }
}
//till js  and conversion off epoch
function tillFormatter(value) {
    if (value != null) {
        var utcSeconds = value;
        var date = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        return '<div title="Applicable till : ' + z + '" style="color:purple">' + d + '</div>';

    }
    else {
        return value;
    }
}

//createdOn js the tooltip takes data from createdy variable and  is shown on mouse hover
function createdFormatter(value, row) {
    if (value != null) {
        var utcSeconds = value;
        var date = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
        var d = (moment(date).format("DD/MM/YY"));
        var z = (moment(date).format(getCurrentDateTimeFormat()));
        var createdBy = "NA";
        if (row.createdBy != null)
            createdBy = row.createdBy;
        return '<div title="Created on : ' + z + ' by :' + createdBy + '" style="color:purple">' + d + '</div>';

    }
    else {
        return value;
    }
}

/*Returns the time in date format*/
function getUTCTime(value) {
    if (value != null) {
        var date = new Date(value);
        return date;
    }
}

// brandFormatter js
/*
 ===========brand display function=========

 checks if the length is 1 then display the name else go in a loop and check each element in the array
 eachBrandObj variable is user to get the  array element as whole i.e. value[0], value[1] ...etc
 brandNames variable then using another for loop searches  for the  key "name" and takes the value
 by using eachBrandObj[key] and saves + appends it to the brandNames Variable
 */

function brandFormatter(value) {

    if (value.length == 1) {
        return value[0].name;
    }

    var brandNames = "";
    for (var index = 0; index < value.length; index++) {
        var eachBrandObj = value[index];
        for (var key in eachBrandObj) {
            if (key == "name") {
                brandNames += eachBrandObj[key] + "    ";
            }
        }
    }

    return '<a title="' + brandNames + '"style="style:none;">' + value.length + '</a>';
}


//name js   display name of coupon with description as datatip
function nameFormatter(value, row, index) {
    return '<div ><a style="color: black" title="Description : ' + row.description + '" onclick="showCoupon(' + row.id + ')" href="javascript:void(0)">' + value + '</a></div>';
}

//name js   display name of coupon with description as datatip
function nameForCodeListingFormatter(value, row, index) {
    return '<div ><a style="color: black" title="Click to view coupon" onclick="showCoupon(' + row.couponId + ')" href="javascript:void(0)">' + value + '</a></div>';
}


//code js  display number of coupon codes and on click calling the  function openListing
function codesFormatter(value, row, index) {
    if (value !== 0) {
        return '<div title="Click to view coupon codes listing"><a onclick="openListing(' + row.id + ')" href="javascript:void(0)"><strong>' + value + '</strong></a></div>';
    }
    else {
        return value;
    }
}
//open coupon listing   function called on clicking the  codes number
/*
 =====bootstrap table refresh function====
 .bootstrapTable  removeAll  clears the table
 the  refresh calls the  url as given and reloads the data which it gets  from the  json after hitting that  url
 cause we are using rest API the id of the  coupon comes into the  url itself and  not as a query parameter
 hideother()  function hides other divs and shows  the coupon codes listing div only
 */

function openListing(id) {
    $('#codeListTable').bootstrapTable('removeAll');
    $('#codeListTable').bootstrapTable('refresh', {url: '../rws/coupon/' + id + '/codes'});
    hideOtherDivs(2);
}


//status js
/*
 *function to format the status field of the table
 *checks if  data has a published on value if false status = draft
 *if true checks if there is a deactivated by value if false then status is published
 *else the status is deactivated
 *
 * based on if status is draft , published  or deactivated the icon particular to
 * that is shown under the status  row
 * */


function statusFormat(value, row) {
    var status;
    if (row.publishedOn == null) {
        status = "draft";
    }
    else if (row.deactivatedOn != null) {
        status = "deactivated";
    }
    else if (row.deactivatedOn == null) {
        status = "published";
    }


    if (status == "published") {
        return '<span title="Published on : ' + getUTCTime(row.publishedOn) + '  by : ' + row.publishedBy + ' " style="color: green">Published</span>';

        return '<a title="Published on : ' + getUTCTime(row.publishedOn) + '  by : ' + row.publishedBy + '" style="border-radius:20px" class="btn btn-xs btn-success">P</a>';
    }
    else if (status == "draft") {
        return '<span title="Updated on : ' + getUTCTime(row.lastUpdatedOn) + '  by : ' + row.lastUpdatedBy + '"style="color: blue">Draft</span>';

        return '<a title="Updated on : ' + getUTCTime(row.lastUpdatedOn) + '  by : ' + row.lastUpdatedBy + '" style="border-radius:20px" class="btn btn-xs btn-info">D</a>';
    }
    else if (status == "deactivated") {
        return '<span title="Deactivated on : ' + getUTCTime(row.deactivatedOn) + '  by : ' + row.deactivatedBy + ' "style="color: red">Inactive</span>';

        return '<a title="Deactivated on : ' + getUTCTime(row.deactivatedOn) + '  by : ' + row.deactivatedBy + '" style="border-radius:20px" class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-off"></span></a>';
    }
    return value;
}


//options js

/*  ===== the  options formatter function =====
 *
 *  according  to the  current row status the set of options change
 *  eg if status  is deactivated there wont be any clickable options
 *
 */

function operateFormatter(value, row, index) {
    var status;
    if (row.publishedOn == null) {
        status = "draft";
    }
    else if (row.deactivatedBy == null) {
        status = "published";
    }
    else if (row.deactivatedBy != null) {
        status = "deactivated";
    }
    if (status == "draft") {
        return [
            '<a class="like" href="javascript:void(0)" >',
            '<span title="Edit" class="glyphicon glyphicon-edit"></span>',
            '</a>&nbsp;&nbsp;',
            '  <a class="removebtn ml10" href="javascript:void(0)" title="Edit">',
            '<span title="Delete"  class="glyphicon glyphicon-remove"></span>',
            '</a>&nbsp;&nbsp;',
            '  <a class="publishbtn ml10" href="javascript:void(0)" title="Remove">',
            '<span title="Publish" class="glyphicon glyphicon-floppy-saved"></span>',
            '</a>'
        ].join('');
    }
    else if (status == "published") {

        return [
            '<div class="deactivatebtn" style="color:green"><strong><span title="Click to deactivate coupon" ' +
            'class="glyphicon glyphicon-remove-sign"> </span></strong></div>'
        ].join('');
    }
    else {
        return [
            ' <div style="color:red"><strong><span class="glyphicon glyphicon-ban-circle" title="Deactivated"></span></strong></div>'
        ].join('');
    }

}

// events function that defines  how the  buttons shown in the table will act upon clicking


window.operateEvents = {
    'click .like': function (e, value, row, index) {
        showEditCoupon(row.id);
    },
    'click .removebtn': function (e, value, row, index) {
        deletingThisCoupon(row.id, "table", row.lastUpdatedOn);
    },
    'click .deactivatebtn': function (e, value, row, index) {
        deactivatingThisCoupon(row.id, "table", row.lastUpdatedOn);
    },
    'click .publishbtn': function (e, value, row, index) {
        var confirmation = confirm("Do you really want to Publish this coupon?");
        if (confirmation == true) {
            getCouponDetails(row.id, "publish");
        }
    }
};

/*function to control advance search*/

function advSearchSubmit() {
    var str = "advSearch=true";

    str += "&name=" + $("#advName").val();

    str += "&draft=" + getNull($("#advDraft").is(':checked'));
    str += "&published=" + getNull($("#advPublished").is(':checked'));
    str += "&active=" + getNull($("#advActive").is(':checked'));
    str += "&deactivated=" + getNull($("#advDeactivated").is(':checked'));

    str += "&from=" + checkForNull($("#advFrom").val(), 1);
    str += "&till=" + checkForNull($("#advTill").val(), 1);

    str += "&updateFrom=" + checkForNull($("#advUpdateFrom").val(), 1);
    str += "&updateTill=" + checkForNull($("#advUpdateTill").val(), 1);


    str += "&global=" + getGlobal();

    str += "&inclusive=" + getInclusive();

    str += "&deactivateFrom=" + checkForNull($("#advDeactivateFrom").val(), 1);
    str += "&deactivateTill=" + checkForNull($("#advDeactivateTill").val(), 1);

    str += "&appDurationFrom=" + checkForNull($("#advAppDurationFrom").val(), 1);
    str += "&appDurationTill=" + checkForNull($("#advAppDurationTill").val(), 1);
    str += "&publishedFrom=" + checkForNull($("#advPublishedFrom").val(), 1);
    str += "&publishedTill=" + checkForNull($("#advPublishedTill").val(), 1);

    str += "&transactionFrom=" + $("#advTransactionFrom").val();
    str += "&transactionTill=" + $("#advTransactionTill").val();
    str += "&channel=" + $("#advChannel").val();

    str += "&couponAppType=" + checkForNull($("#advCouponAppType").val(), 2);
    str += "&actor=" + checkForNull($("#advActor").val(), 2);
    str += "&contextType=" + checkForNull($("#advContextType").val(), 2);

    var url = "../rws/coupon/list?" + str;
    $('#serverTable').bootstrapTable('removeAll');
    $('#serverTable').bootstrapTable('refresh', {url: url});

}

function getNull(input) {
    if (input == false) {
        return "";
    }
    return true;
}

/**/
function checkForNull(value, flag) {
    if (isNaN(value) && flag == 1) {
        return getMilliSec(value);
    }
    else if (value != null && flag == 2) {
        return value;
    }
    else
        return "";
}


/*checks the value of global selections*/
function getGlobal() {
    if (($("#advGlobalTrue").is(':checked')) && ($("#advGlobalFalse").is(':checked')))
        return "";
    if ($("#advGlobalTrue").is(':checked'))
        return true;
    else if ($("#advGlobalFalse").is(':checked'))
        return false;

}
/*checks the value of inclusive selections*/
function getInclusive() {
    if (($("#advInclusiveTrue").is(':checked')) && ($("#advInclusiveFalse").is(':checked')))
        return "";
    if ($("#advInclusiveTrue").is(':checked'))
        return true;
    else if ($("#advInclusiveFalse").is(':checked'))
        return false;
}

/*function for page control for table when server sends first page*/
function firstPageSwitch(res) {
    var x = (res.flag);
    if (x == 1) {
        /*refresh table */
    }
    return res;
}
/*advanced search for code submit*/
function advCodeSearchSubmit() {
    var str = "advCodeSearch=true";

    str += "&name=" + checkForNull($("#advCodeName").val(), 2);

    str += "&from=" + checkForNull($("#advCodeFrom").val(), 1);
    str += "&till=" + checkForNull($("#advCodeTill").val(), 1);

    str += "&deactivateFrom=" + checkForNull($("#advCodeDeactivateFrom").val(), 1);
    str += "&deactivateTill=" + checkForNull($("#advCodeDeactivateTill").val(), 1);

    str += "&active=" + getNull($("#advCodeActive").is(':checked'));
    str += "&deactivated=" + getNull($("#advCodeDeactivated").is(':checked'));

    str += "&channel=" + checkForNull($("#advCodeChannel").val(), 2);

    var url = "../rws/coupon/codes?" + str;

    $('#codeListTable').bootstrapTable('removeAll');
    $('#codeListTable').bootstrapTable('refresh', {url: url});

}
