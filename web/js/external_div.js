$("#create_new_coupon").click(function() {
	$.get("./create.jsp", function(data) {
		$("#currentShownDiv").empty().append(data);
	});
	return false;
});
