$(function(){
    // sample data
    var filepaths = ["/Nursing/Wound Care", "/Nursing/12 Hr Nursing", "/Nursing/Followup Visit",
        "/Nursing/Corporate Visit", "/Physiotherapy", "/Physiotherapy/GroupOn Physio", "/Physiotherapy/MAX PHYSIO",
        "/doctor visit", "/doctor visit/RA Aide- 4hrs", "/doctor visit/Doctor home visits on call"];

// attach the plugin to an element
// select multiple items paramater is true
    $('#directoryTree_container').directoryTree([filepaths, true]);

//register event handlers
    $('.directoryTree_expand').click(function (e) {
        $('#directoryTree_container').data('directoryTree').expandAll();
    });

    $('.directoryTree_collapse').click(function (e) {
        $('#directoryTree_container').data('directoryTree').collapseAll();
    });


});
