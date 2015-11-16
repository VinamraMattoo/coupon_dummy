//defining variables
var c_id, status, description, createdBy, publishedOn, publishedBy, lastUpdatedOn, lastUpdatedBy, deactivatedOn, deactivatedBy;


//coupon formatter js
function idFormatter(value) {
    c_id = value;
    return value;
}


//from js  convert the epoch time to readable time format function
function fromFormatter(value) {
    var utcSeconds = value;
    var d = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
    d = d.toDateString();           // make the date  readable
    return d;

}
//till js  and conversion off epoch
function tillFormatter(value) {
    var utcSeconds = value;
    var d = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
    d = d.toDateString();          // make the date  readable
    return d;

}


//description js ==   storing the  description coming from the  json and saving it into the  description variable
function descriptionFormatter(value, row, index) {
    description = value;
    return [value];
}

//createdBy js storing the  createdby coming from the  json and saving it into the  createdby variable
function createdByFormatter(value) {
    createdBy = value;
    return value;
}

//createdOn js the tooltip takes data from createdy variable and  is shown on mouse hover
function createdFormatter(value) {
    var utcSeconds = value;
    var d = new Date(utcSeconds); // The utcSeconds there is the key, which sets the date to the epoch
    d = d.toDateString();
    return '<a title=" created by : ' + createdBy + '" style="color:purple">' + d + '</a>';

}

//publishedOn js  saving the  data  in publishedon variable
function publishedOnFormatter(value) {
    publishedOn = value;
    return value;
}

//publishedBy js    saving the data  in the  published by variable
function publishedByFormatter(value) {
    publishedBy = value;
    return value;
}

//updatedOn js saving the data  in createdon variable
function updatedOnFormatter(value) {
    lastUpdatedOn = value;
    return value;
}

//updatedBy js  saving he data  in updatedon variable
function updatedByFormatter(value) {
    lastUpdatedBy = value;
    return value;
}

//deactivatedOn js saving the data in deactivatedon variable
function deactivatedOnFormatter(value) {
    deactivatedOn = value;
    return value;

}
//deactivatedBy js saving the data in deractivatedby variable
function deactivatedByFormatter(value) {
    deactivatedBy = value;
    return value;
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
    return '<div ><a title="' + description + '" onclick="showCoupon(' + c_id + ')" href="#"><strong>' + value + '</strong></a></div>';
}


//code js  display number of coupon codes and on click calling the  function openListing
function codesFormatter(value, row, index) {
    if (value !== 0) {
        return '<div title="click to view coupon codes listing"><a onclick="openListing(' + c_id + ')" href="#"><strong>' + value + '</strong></a></div>';
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
    $('#codeListTable').bootstrapTable('refresh', {url: './rws/coupon/' + id + '/codes'});
    hideOtherDivs(2);
}


//status js
/*
 *function to format the status field of the table
 *checks if  data has a publishedon value if false status = draft
 *if true checks if there is a deactivatedby value if false then status is published
 *else the status is deactivated
 *
 * based on if status is draft , published  or deactivated the icon particular to
 * that is shown under the status  row
 * */


function statusFormat() {
    if (publishedOn == null) {
        status = "draft";
    }
    else if (deactivatedBy == null) {
        status = "published";
    }
    else {
        status = "deactivated";
    }

    if (status == "published") {
        return '<a title="published on : ' + publishedOn + '  published by: ' + publishedBy + '" style="border-radius:20px" class="btn btn-sm btn-success">P</a>';
    }
    else if (status == "draft") {

        return '<a title="updated on : ' + lastUpdatedOn + '  updated by: ' + lastUpdatedBy + '" style="border-radius:20px" class="btn btn-sm btn-info">D</a>';
    }
    else {
        return '<a title="deactivated on : ' + deactivatedOn + '  deactivated by: ' + deactivatedBy + '" style="border-radius:20px" class="btn btn-sm btn-danger"><span class="glyphicon glyphicon-off"></span></a>';
    }

}


//options js

/*  ===== the  options formatter function =====
 *
 *  according  to the  current row status the set of options change
 *  eg if status  is deactivated there wont be any clickable options
 *
 */

function operateFormatter(value, row, index) {
    if (status == "draft") {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<span title="edit" class="glyphicon glyphicon-edit"></span>',
            '</a>',
            '  <a class="edit ml10" href="javascript:void(0)" title="Edit">',
            '<span title="delete"  class="glyphicon glyphicon-remove"></span>',
            '</a>',
            '  <a class="remove ml10" href="javascript:void(0)" title="Remove">',
            '<span title="Publish" class="glyphicon glyphicon-floppy-saved"></span>',
            '</a>'
        ].join('');
    }
    else if (status == "published") {
        return [
            '  <a class="edit ml10" href="javascript:void(0)" title="delete">',
            '<span title="deactivate"  class="glyphicon glyphicon-remove-circle"></span>  ',
            '<span ><a title="Generate coupon codes" style="border-radius:20px;" class="btn btn-xs btn-default">G</a></span>',
            '</a>',
        ].join('');
    }
    else {
        return [
            ' <div style="color:red"><strong><span class="glyphicon glyphicon-ban-circle"></span></strong></div>'
        ].join('');
    }

}

// events function that defines  how the  buttons shown in the table will act upon clicking


window.operateEvents = {
    'click .like': function (e, value, row, index) {
        showEditCoupon(c_id);
        console.log(value, row, index);
    },
    'click .edit': function (e, value, row, index) {
        alert("edit" + c_id);
        //delete from here
        console.log(value, row, index);
    },
    'click .remove': function (e, value, row, index) {
        alert("publish" + c_id);
        console.log(value, row, index);
    }
};


