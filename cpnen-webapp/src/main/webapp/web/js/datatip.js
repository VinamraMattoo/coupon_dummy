$(document).ready(function() {
$(".datatip").mousemove(function(e) {
    var dataTip = $(this).attr("datatip");
    $("#datatipDiv").text(dataTip).fadeIn(400);
    $("#datatipDiv").css("top",e.clientY+10).css("left",e.clientX+10);
}).mouseout(function(){
    $("#datatipDiv").hide();
});
});