<%-- tabular listing of all present coupon with their status and  attributes--%>
<script type="text/javascript">
    $('.bootstrap-multiselect').multiselect();
</script>
<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-en-US.js"></script>

<div class="col-md-9" style="width: 950px;">
    <!-- Trigger the advance search modal with this button -->

    <h3 class="pull-left" title="Coupon listing page ">Coupon Listing</h3><br>

    <div>
        <a href="javascript:void(0)" onclick="refreshOnUpdate()" title="Click here to show complete coupon listing"
           style="margin-left: 550px;"> Reset Listing</a>
        <button type="button" class=" pull-right btn btn-info btn-xs" data-toggle="modal"
                title="Click here to specify search filters"
                data-target="#advanceSearch">
            Advanced Search
        </button>
    </div>
    <%--table to display coupons and various attributes--%>
    <table id="serverTable"
           data-toggle="table"
           data-row-style="bootstrapTableRowSizeModifier"
           data-url="../rws/coupon/list"
           data-pagination="true"
           data-side-pagination="server"
           data-sort-order="desc"
           data-page-size="20"
           data-page-list="[1, 10, 50, 100, ALL]"
           data-response-handler="firstPageSwitch">
        <thead style="background-color: #3fc1be;">
        <tr>
            <th class="hidden" data-field="description">desc</th>
            <th data-field="id" class="hidden" data-align="right" data-sortable="true">ID</th>
            <th data-field="name" data-formatter="nameFormatter" data-sortable="true">Name</th>
            <th data-field="from" data-formatter="fromFormatter">From</th>
            <th data-field="till" data-formatter="tillFormatter">Till</th>
            <th data-field="brandVos" data-formatter="brandFormatter">Brand</th>
            <th class="hidden" data-field="inclusive">Inclusive</th>
            <th data-field="createdBy" class="hidden"></th>
            <th data-field="createdOn" data-sortable="true" data-formatter="createdFormatter">Created On</th>
            <th data-field="global">Global</th>
            <th data-field="publishedOn" class="hidden"></th>
            <th data-field="publishedBy" class="hidden"></th>
            <th data-field="lastUpdatedOn" class="hidden"></th>
            <th data-field="lastUpdatedBy" class="hidden"></th>
            <th data-field="deactivatedOn" class="hidden"></th>
            <th data-field="deactivatedBy" class="hidden"></th>
            <th data-field="Channel" class="hidden">Channel</th>
            <th class="hidden" data-field="applicationType">App-type</th>
            <th data-field="appUseCount">UseCount</th>
            <th data-formatter="statusFormat" >Status</th>
            <th data-field="codes" data-formatter="codesFormatter">Codes</th>
            <th data-field="operate" data-formatter="operateFormatter" data-events="operateEvents"
                >Actions
            </th>
        </tr>
        </thead>
    </table>

    <br>

</div>

<%-- advanced search modal--%>
<div id="advanceSearch" class="modal fade" tabindex="-1" data-backdrop="static" role="dialog">
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
                    Coupon Name
                    <div class="pull-right">
                        <input style="width:213px;height:22px" id="advName" type="text" placeholder="Coupon name"/>
                    </div>
                    <br> <br>
                    Creation Date
                    <div class="pull-right">
                        <input style="width:100px;height:22px;font-size:11px;" id="advFrom"
                               placeholder="From"
                               type="text"> -
                        <input style="width:100px;font-size:11px;height:22px" placeholder="Till" id="advTill"
                               type="text"></div>
                    <br><br>

                    Update Date
                    <div class="pull-right"><input style="width:100px;font-size:11px;height:22px" placeholder="From"
                                                   id="advUpdateFrom"
                                                   type="text"> -
                        <input style="width:100px;font-size:11px;height:22px" placeholder="Till" id="advUpdateTill"
                               type="text"></div>
                    <br><br>

                    Deactivation Date
                    <div class="pull-right"><input style="width:100px;font-size:11px;height:22px" placeholder="From"
                                                   id="advDeactivateFrom" type="text"> -
                        <input style="width:100px;font-size:11px;height:22px" id="advDeactivateTill" placeholder="Till"
                               type="text">
                    </div>
                    <br><br>

                    Applicability Duration
                    <div class="pull-right"><input style="width:100px;font-size:11px;height:22px" placeholder="From"
                                                   id="advAppDurationFrom" type="text">
                        -
                        <input style="width:100px;font-size:11px;height:22px" id="advAppDurationTill" placeholder="Till"
                               type="text">
                    </div>
                    <br><br>

                    Published Date
                    <div class="pull-right"><input style="width:100px;height:22px" placeholder="From"
                                                   id="advPublishedFrom" type="text"> -
                        <input style="width:100px;height:22px" id="advPublishedTill" placeholder="Till" type="text">
                    </div>
                    <br><br><br>

                    Transaction Amount
                    <div class="pull-right"><input style="width:100px;height:22px" placeholder="From"
                                                   id="advTransactionFrom"
                                                   type="number"> -
                        <input style="width:100px;height:22px" id="advTransactionTill" placeholder="Till" type="number">
                    </div>


                </div>
                <div class="col-md-3 ">

                    Status<br>

                    <div class="btn-group" data-toggle="buttons">
                        <label style="width:150px;height:22px" class="btn btn-xs btn-default">
                            <input id="advDraft" type="checkbox">Draft
                        </label><br>
                        <label style="width:150px;height:22px" class="btn btn-xs btn-default">
                            <input width="100px" id="advPublished" type="checkbox">Published
                        </label><br>
                        <label style="width:150px;height:22px" class="btn btn-xs btn-default">
                            <input width="100px" id="advActive" type="checkbox">Active
                        </label><br>
                        <label style="width:150px;height:22px" class="btn btn-xs btn-default">
                            <input id="advDeactivated" type="checkbox">Deactivated
                        </label>
                    </div>
                    <br><br>


                    Global

                    <br> True<input id="advGlobalTrue" type="checkbox" onchange="trueSelector('global',true)" checked>

                    False<input id="advGlobalFalse" type="checkbox" onchange="trueSelector('global',false)" checked>
                    <br><br>


                    Inclusive
                    <br> True<input id="advInclusiveTrue" type="checkbox" onchange="trueSelector('inclusive',true)"
                                    checked>
                    False<input id="advInclusiveFalse" type="checkbox" onchange="trueSelector('inclusive',false)"
                                checked> <br><br>


                    Coupon App Type
                    <select id="advCouponAppType" class="bootstrap-multiselect" multiple="multiple">
                        <option value="NTH_TIME">nth time</option>
                        <option value="ONE_TIME">one time</option>
                        <option value="ONE_TIME_PER_USER">one time per user</option>
                        <option value="ONE_TIME_PER_USER_FIFO">one time per user fifo</option>
                        <option value="MANY_TIMES">many times</option>
                        <option value="NTH_TIME_PER_SUBSCRIPTION">n time per subsciption</option>
                        <option value="NTH_TIME_AB_PER_SUBSCRIPTION">n time per ab subscription</option>
                    </select>

                    <br>
                    Actor type<select id="advActor" class="bootstrap-multiselect" multiple="multiple">
                    <option value="STAFF">Staff</option>
                    <option value="CUSTOMER">Customer</option>
                </select>


                    <br>
                    Context type<select id="advContextType" class="bootstrap-multiselect" multiple="multiple">
                    <option value="APPOINTMENT">Appointment</option>
                    <option value="SUBSCRIPTION">Subscription</option>

                </select>
                </div>
            </div>
            <div class="modal-footer">
                <div style="padding-right: 10px" class="pull-right">
                    <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                            data-dismiss="modal">Close
                    </button>

                    <button type="button" class="btn btn-xs btn-success" onclick="advSearchSubmit()"
                            data-dismiss="modal">Submit
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>