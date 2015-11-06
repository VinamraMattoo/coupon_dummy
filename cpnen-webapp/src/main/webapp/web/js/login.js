function logout(){
$.ajax({url:'logout', success:function(result){
    alert("login success"+ result);
}

});
}
/*
function login(){
    var username = $("#username").val();
    var password = $("#psswd").val();

    $.ajax({url:'login', method : 'POST', data : {"username" : username, "password" : password}, success:function(result){
        alert("logout success"+ result);
}

});
}*/
