$(document).ready(function() {
	checkForLogOut();
});

function checkForLogOut() {

	var prodId = getParameterByName('logout');

	if (prodId == "true") {
		prodId = "You have successfully logged out";
	} else {
		prodId = "";
	}
	document.getElementById("status").innerHTML = prodId;
}

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
			.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g,
			" "));
}
