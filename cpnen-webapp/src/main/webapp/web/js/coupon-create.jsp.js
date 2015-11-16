/*date time picker for calender input */
function dateTimeInput() {
    $('#applicableFrom').datetimepicker({
        format: getCurrentDateTimeFormat()
    });

    $('#applicableTill').datetimepicker({
        useCurrent: false,
        format: getCurrentDateTimeFormat()
    });
    $("#applicableFrom").on("dp.change", function (e) {
        $('#applicableTill').data("DateTimePicker").minDate(e.date);
    });
    $("#applicableTill").on("dp.change", function (e) {
        $('#applicableFrom').data("DateTimePicker").maxDate(e.date);
    });

}


/*data formatter of listCouponCode.jsp to add a delete option*/
function codeOptionsFormatter(value, row, index) {
    return [

        '  <a class="edit ml10" href="javascript:void(0)" title="deactivate">',
        '<span title="deactivate"  class="glyphicon glyphicon-remove"></span>',
        '</a>'
    ].join('');
}

window.codeOptionsEvents = {
    'click .remove': function (value, row, index) {
        alert(c_id);
        //call delete here
        console.log(value, row, index);
    }
};

/*returns the brands checked*/
function getBrands() {

    var brands = [];
    if ($("#global").is(':checked') === false) {
        $.each(checkedBrands, function (index, value) {

            brands.push(value);

        });
    }
    return brands;

}
/*returns the mappings checked*/
function getMappings() {

    var mappings = [];

    if ($("#global").is(':checked') === false) {
        $.each(checkedMapping, function (index, value) {

            mappings.push(value);

        });
    }
    return mappings;

}