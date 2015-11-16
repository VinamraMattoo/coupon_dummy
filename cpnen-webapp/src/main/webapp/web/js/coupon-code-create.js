/*
* functions of coupon code creation  are in this js file.
* */

/*function to load the code creation page into the div */
function createCouponCode() {
    hideOtherDivs(7);
    $.get("./couponCodeCreate.jsp", function (data) {
        $("#createCouponCode").empty().append(data);
        addCodeRegistrations();
    });
}
/*function dynamically generates registration forms
 * max_fields defines the max no of registrations that can be dynamically generated
 * */
function addCodeRegistrations() {

    var max_fields = 100; //maximum input boxes allowed
    var wrapper = $(".input_fields_wrap"); //Fields wrapper
    var add_button = $(".add_field_button"); //Add button ID

    var x = 1; //initlal text box count
    $(add_button).click(function (e) { //on add input button click
        e.preventDefault();
        if (x < max_fields) { //max input box allowed
            x++; //text box increment
            $(wrapper).append('<div class="removeMe" >Reserved for: <input class="form-control" type="text"  /><br> validity from: <input class="form-control" type="date"  /><br> till : <input class="form-control" type="date" /> <br> Select Event type:   <select class="btn btn-primary dropdown-toggle"> <option value=""> --select an option--</option> <option value="bday">Birthday</option> <option value="anniversary">anniversary</option> <option value="new year">new year</option> <option value="diwali">diwali</option> <option value="holi">holi</option> </select> <br><br><br><a href="#" class="remove_field btn btn-danger"><span title="remove  this user" class="glyphicon glyphicon-remove-sign" > Delete user</span></a><br><br></div>'); //add input box
        }
    });

    $(wrapper).on("click", ".remove_field", function (e) { //user click on remove text
        e.preventDefault();
        $(this).parent('.removeMe').remove();
        x--;
    });

    $("#registerDiv").hide();
    $("#registerBtn").click(function () {
        $("#registerDiv").toggle(1000);
    });

}