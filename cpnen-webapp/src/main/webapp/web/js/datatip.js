/* ======datatip pluugin function=====
* on mouse over on the  DOM with class datatip the attribute datatip data  will be pushed into the  datatip div
* the function mousemove returns the  present location of mouse  adding a  padding of 10 px on top and left the
* div is set with aabsolute position making the  div come up like  a datatip z- index may have to be set for navbars and such
*/
$(document).ready(function() {
    $(".datatip").mousemove(function(e) {
        var dataTip = $(this).attr("datatip");
        $("#datatipDiv").text(dataTip).fadeIn(400);
        $("#datatipDiv").css("top",e.clientY+10).css("left",e.clientX+10);
    }).mouseout(function(){
        $("#datatipDiv").hide();
    });
});