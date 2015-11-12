//untreated code starts
function showEditedView()
{

    $.get("./info.html", function(data) {
        $("#currentShownDiv").empty().append(data);
        $("#messageStatus").empty().append("modified");
    });
    return false;
}