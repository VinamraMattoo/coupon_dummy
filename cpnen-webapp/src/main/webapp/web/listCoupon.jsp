<link rel="stylesheet"
      href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.0/bootstrap-table.css">

<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-en-US.js"></script>


<br><br>
<h1 align="center"><code>Coupon listings</code></h1>

<div class="col-md-9" style="width: 950px;">

    <table id="serverTable"
           data-toggle="table"
           data-url="./rws/coupon/list"
           data-pagination="true"
           data-side-pagination="server"
           data-page-list="[1, 5, 10, 100, ALL]"
           data-height="600">
        <thead>
        <tr>
            <th class="hidden" data-field="description" data-formatter="descriptionFormatter">desc</th>
            <th data-field="id" data-formatter="idFormatter" data-align="right" data-sortable="true">ID</th>
            <th data-field="name" data-formatter="nameFormatter" data-align="center" data-sortable="true">Name</th>
            <th data-field="from" data-formatter="fromFormatter">From</th>
            <th data-field="till"data-formatter="tillFormatter">Till</th>
            <th data-field="brandVos" data-formatter="brandFormatter">Brand</th>
            <th data-field="inclusive">Inclusive</th>
            <th data-field="createdBy" data-formatter="createdByFormatter" class="hidden"></th>
            <th data-field="createdOn" data-formatter="createdFormatter">Created On</th>
            <th data-field="global">Global</th>
            <th data-field="publishedOn" data-formatter="publishedOnFormatter" class="hidden">published on dummy date test</th>
            <th data-field="publishedBy" data-formatter="publishedByFormatter" class="hidden"></th>
            <th data-field="lastUpdatedOn" data-formatter="updatedOnFormatter" class="hidden"></th>
            <th data-field="lastUpdatedBy" data-formatter="updatedByFormatter" class="hidden"></th>
            <th data-field="deactivatedOn" data-formatter="deactivatedOnFormatter" class="hidden"></th>
            <th data-field="deactivatedBy" data-formatter="deactivatedByFormatter" class="hidden"></th>
            <th data-field="Channel" class="hidden" data-align="center">Channel</th>
            <th data-field="applicationType">App-type</th>
            <th data-field="appUseCount">UseCount</th>
            <th  data-formatter="statusFormat" data-align="center" data-events="operateEvents">Status</th>
            <th data-field="codes" data-formatter="codesFormatter" data-sortable="true" data-align="center">Codes</th>
            <th data-field="operate" data-formatter="operateFormatter" data-events="operateEvents">Item Operate</th>
        </tr>
        </thead>
    </table>

    <br>

</div>

