//To show the coupon values just created
function showCoupon(Id) {
    hideOthers(3);
    $.get("./view_coupon.jsp", function (data) {
        $("#viewCreatedCoupon").empty().append(data);
        getCouponDetails(Id, "view");
    });

    return false;
}

function onEditClick() {
    var id = couponId;
    editCoupon(id);
}


function editCoupon(cid) {
    hideOthers(5);
    $.get("./editCoupon.jsp", function (data) {
        $("#editCoupon").empty().append(data);
        $.get("./mapping.jsp", function (data) {
            $("#edit_couponMapping").append(data);
        });
    });
    getCouponDetails(cid, "edit");


}


$(document).ready(function () {
    $.get("./create.jsp", function (data) {
        $("#createCoupon").append(data);
        $.get("./mapping.jsp", function (data) {
            $("#couponMapping").append(data);
        });
    });
    $.get("./listCoupon.jsp", function (data) {
        $("#showCoupons").append(data);
    });
    $.get("./listCouponCode.jsp", function (data) {
        $("#listCouponCodes").append(data);
    });


});