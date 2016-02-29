<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>


<div class="mylist-container" id="page-wrapper" style="width: 950px;">

    <div class=" col-md-12" style="width: 105%;">
        <h4 class="pull-left" >SMS Configurations</h4>

        <%--Advance search Maybe implemented in near future--%>

        <%--<button class="btn btn-info btn-xs pull-right">Advance Search</button>--%>
        <table id="SMSConfigTable"
               class="tableOverflow"
               data-toggle="table"
               data-row-style="bootstrapTableRowSizeModifier"
               data-page-size="20"
               data-search="true"
               data-response-handler="smsConfigTableResponseHandler"
               data-show-refresh="true"
               data-url="../rws/targetConfigParam/list"
               data-pagination="true">


            <%--

<thead style="background-color: #3fc1be">
<tr>
    <th  data-search-formatter="true" data-formatter="targetTypeFormatter">Target Type</th>
    <th data-formatter="configParamFormatter">Config Param</th>
    <th data-field="targetName" data-formatter="targetNameFormatter">Target Name</th>
    <th data-field="value" data-formatter="configValueFormatter">Value</th>
    <th data-formatter="editActionColumnFormatter" data-width="80" data-events="editConfigParamsEvent">
        Actions
    </th>
</tr>
</thead>--%>
            <thead style="background-color: #3fc1be">
            <tr>
                <th  data-field="configTargetTypeVo.targetType" data-formatter="targetTypeFormatter">Target Type</th>
                <th data-field="configParamVo.name" data-formatter="configParamFormatter">Config Param</th>
                <th data-field="targetName" ata-formatter="targetNameFormatter">Target Name</th>
                <th class="hideOverflow" data-field="value"  data-formatter="configValueFormatter">Value</th>
                <th data-formatter="editActionColumnFormatter" data-width="80" data-events="editConfigParamsEvent">
                    Actions
                </th>
            </tr>
            </thead>
        </table>

    </div>
</div>

<div class="modal fade" id="editSMSConfigModal" tabindex="-1" role="dialog"
     data-backdrop="static" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalLabel">Edit SMS configuration parameters</h4>


            </div>
            <div class="modal-body">
                <form id="configParamsForm">
                    <div>
                        Target type
                        <div class="pull-right">
                            <input class="form-control modalInput hideOverflow" style="width:220px;height:22px"
                                   type="text" id="targetType" readonly/>
                        </div>
                        <br> <br>

                        Config parameter
                        <div class="pull-right ">
                            <input class="form-control modalInput hideOverflow" style="width:220px;height:22px"
                                   type="text" id="configParam" readonly/>
                        </div>
                        <br> <br>
                        Target name
                        <div class="pull-right ">
                            <input class="form-control modalInput hideOverflow" style="width:220px;height:22px"
                                   type="text" id="targetName" readonly/>
                        </div>
                        <br> <br>

                        Config value
                        <div class="pull-right" id="configValueId">


                        </div>
                        <div id="targetId"></div>
                        <div id="configParamId"></div>

                        <input id="dataTypeHolder" hidden/>
                        <br><br>

                    </div>
                </form>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                        data-dismiss="modal">Close
                </button>


                <button type="button" class="btn btn-xs btn-success" data-dismiss="modal"
                        onclick="updateTargetConfigParameters()">Submit
                </button>

            </div>
        </div>
    </div>
</div>



