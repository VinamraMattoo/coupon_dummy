<%--tabular to list the  coupon codes for all or a particular coupon with all related attrbutes--%>
<link rel="stylesheet"
      href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.0/bootstrap-table.css">

<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-en-US.js"></script>
<br><br>
<h1 align="center">Coupon Codes listings</h1>

<div class="col-md-9" style="width: 950px;">

  <table id="codeListTable"
         data-toggle="table"
         data-url="./code.txt"
         data-pagination="true"
         data-side-pagination="server"
         data-page-list="[1, 5, 10, 100, ALL]"
         data-height="400">
    <thead>
    <tr>
      <th data-field="id" class="hidden">id</th>
     <th data-field="couponId" class="hidden">c_id</th>
      <th data-field="code">code</th>
      <th data-field="createdOn">created on</th>
      <th data-field="createdBy">created by</th>
      <th data-field="deactivatedOn">deactivated on</th>
      <th data-field="deactivatedBy">deactivated by</th>
      <th data-field="channelName">channel name</th>
      <th data-field="operate" data-formatter="codeOptionsFormatter" data-events="codeOptionsEvents">Item Operate</th>
    </tr>
    </thead>
  </table>
</div>