<%--tabular to list the  coupon codes for all or a particular coupon with all related attrbutes--%>

<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-en-US.js"></script>


<div class="col-md-9" style="width: 950px;">
    <h3 class="pull-left" title="Code listing page ">Code Listing</h3><br>

    <div>
        <a href="javascript:void(0)" onclick="refreshOnUpdate()" title="Click here to show complete code listing"
           style="margin-left: 577px;"> Reset Listing</a>

        <button type="button" class="pull-right btn btn-info btn-xs" data-toggle="modal"
                title="Click here to specify search filters"
                data-target="#advanceSearchCode">
            Advanced Search
        </button>
    </div>


    <table id="codeListTable"
           data-toggle="table"
           data-url="../rws/coupon/codes"
           data-pagination="true"
           data-page-size="20"
           data-sort-order="desc"
           data-row-style="bootstrapTableRowSizeModifier"
           data-side-pagination="server"
           data-page-list="[1, 10, 50, 100, ALL]"
            >
        <thead style="background-color: #3fc1be;">
        <tr>
            <th data-field="id" class="hidden">id</th>
            <th data-field="couponId" class="hidden">c_id</th>
            <th data-field="code" data-sortable="true" data-align="left" data-formatter="codeFormatter">Code</th>
            <th data-field="couponName" data-formatter="nameForCodeListingFormatter">Coupon</th>
            <th data-field="createdOn" data-align="left" data-formatter="codeCreateOnFormatter" data-sortable="true">
                Created on
            </th>
            <th data-field="createdBy" data-sortable="true" class="hidden">created by</th>
            <th data-field="applicableFrom" data-align="left" data-formatter="fromFormatter">Applicable From</th>
            <th data-field="applicableTill" data-align="left" data-formatter="tillFormatter">Applicable Till</th>
            <th data-field="deactivatedOn" class="hidden" data-formatter="codeDeactivateOnFormatter">Deactivated on</th>
            <th data-field="deactivatedBy" class="hidden">Deactivated by</th>
            <th data-field="categoryName" data-formatter="categoryFormatter">Category</th>
            <th data-field="codeStatus" data-align="left" data-formatter="codeStatusFormatter">Status</th>
            <th data-field="operate" data-align="left" data-formatter="codeOptionsFormatter">
                Actions
            </th>
        </tr>
        </thead>
    </table>
</div>


<%-- advanced search modal--%>
<div id="advanceSearchCode" class="modal fade" tabindex="-1" data-backdrop="static" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Advanced Search</h4>

                <div style="font-size: 12px">Advanced search for coupon listing</div>
            </div>


            <!--column one here-->
            <div class=" modal-body row">
                <div class="col-md-8">
                    Code Name
                    <div class="pull-right">
                        <input style="width:213px;height:22px" id="advCodeName" type="text" placeholder="Coupon name"/>
                    </div>
                    <br> <br>
                    Creation Date
                    <div class="pull-right"><input style="width:100px;height:22px" id="advCodeFrom" placeholder="From"
                                                   type="text"> -
                        <input style="width:100px;height:22px" placeholder="Till" id="advCodeTill" type="text"></div>
                    <br><br>
                    Deactivate Date
                    <div class="pull-right"><input style="width:100px;height:22px" placeholder="From"
                                                   id="advCodeDeactivateFrom" type="text"> -
                        <input style="width:100px;height:22px" id="advCodeDeactivateTill" placeholder="Till"
                               type="text">
                    </div>
                    <br><br>

                </div>
                <div class="col-md-3 ">

                    Status<br>

                    <div class="btn-group" data-toggle="buttons">
                        <label style="width:150px;height:22px" class="btn btn-xs btn-default">
                            <input id="advCodeActive" type="checkbox">Active
                        </label><br>
                        <label style="width:150px;height:22px" class="btn btn-xs btn-default">
                            <input id="advCodeDeactivated" type="checkbox">Deactivated
                        </label>
                    </div>
                    <br><br>
                    Channel<br>
                    <input id="advCodeChannel" style="width:150px;height:22px" type="text">
                    <br>

                </div>
            </div>
            <div class="modal-footer row">
                <div style="padding-right: 10px" class="pull-right">
                    <button type="button" class="btn btn-xs btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-xs btn-success" onclick="advCodeSearchSubmit()"
                            data-dismiss="modal">Submit
                    </button>
                </div>
            </div>


        </div>
    </div>
</div>