<h1 align="center">Select the Mapping</h1>

<div class="col-md-offset-1 col-md-5">
    <div style="border:solid;width:70px"><input type="checkbox"><span> Global</span></div>

    <br>
    <hr>
    <table disabled
           id="mappingTable"
           data-toggle="table"
           data-search="true"
           data-url="temp.txt"
           data-pagination="true"
           data-striped="true"
           data-height="400"
           data-response-handler="extractRows">
        <thead>
        <tr>
            <th data-field="state" data-formatter="checkfun" data-checkbox="true"></th>
            <th class="hidden" data-formatter="getMappingId" data-field="id">Id</th>
            <th data-formatter="getMappingType" data-field="type">Type</th>
            <th data-formatter="getMappingName" data-field="name">Name</th>
        </tr>
        </thead>
    </table>
</div>
<script>
    var mapId, mapType, mapName;
    //get id function
    function getMappingId(value) {
        mapId = value;
        return value;
    }
    //get type  function
    function getMappingType(value) {
        mapType = value;
        return value;
    }
    //get name function
    function getMappingName(value) {
        mapName = value;
        return value;
    }
    //checkbox funtion
    function checkfun(value) {

        console.log(mapId + "+" + mapName + "+" + mapType);
    }
</script>
<%--//vertical row--%>
<hr width="2" size="400">

<div class="col-md-offset-1 col-md-5">
    <br>
    <hr>
    <table disabled
           id="brandTable"
           data-toggle="table"
           data-search="true"
           data-url="/cpnen/web/rws/brands"
           data-height="400">
        <thead>
        <tr>
            <th data-field="state" data-checkbox="true"></th>
            <th class="hidden" data-field="id">Id</th>
            <th data-field="type">Type</th>
            <th data-field="name">Name</th>
        </tr>
        </thead>
    </table>
</div>
<script>
    function extractRows(res) {
        return res["rows"];
    }
</script>
