// time formatting function
function getCurrentDateTimeFormat() {
    return 'YYYY-MM-DDTHH:mm:ss';
}
var typeData;
function getSMSTypeMetaData() {
    $.ajax({
        url: "../rws/smsType/list",
        dataType: 'json',
        success: function (resp) {
            typeData = [];
            var i;
            for (i = 0; i < resp.length; i++) {
                var currentObj = resp[i];
                typeData.push(currentObj.name);
            }
        }

    });
}
getSMSTypeMetaData();