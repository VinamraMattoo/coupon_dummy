/*
functions related to edit operation are here
 */

/*edit date time picker for calender input*/
function editDateTimePicker() {
    $('#edit_applicableFrom').datetimepicker({
        format: getCurrentDateTimeFormat()
    });

    $('#edit_applicableTill').datetimepicker({
        useCurrent: false,
        format: getCurrentDateTimeFormat()
    });

    $("#edit_applicableFrom").on("dp.change", function (e) {
        $('#edit_applicableTill').data("DateTimePicker").minDate(e.date);
    });

    $("#edit_applicableTill").on("dp.change", function (e) {
        $('#edit_applicableFrom').data("DateTimePicker").maxDate(e.date);
    });

}