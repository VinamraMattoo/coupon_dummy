$(document).ready(function(){
   divAdd();
   logIn();
});

/*adds the navigation bars in the web page*/
function divAdd(){
    $("#navbarDiv").load("../html/navbar.html");
}


/*on clicking the log in button redirects the web page to main page*/
function logIn(){
    $("#loginButton").click(function(){
    //window.open("./html/main.html","_self");
    });
}