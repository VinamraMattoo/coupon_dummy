<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>

<div class="mylist-container" id="page-wrapper" style="width: 950px;">

    <div class=" col-md-12" style="width: 105%;">
        <h4 class="pull-left">Gateway Mappings</h4>
        <table id="gatewayPrioritiesMappingsTable"
               data-toggle="table"
               data-pagination="true"
               data-search="true"
               data-url="../rws/group/gateway/mapping"
               data-response-handler="groupBySmsGroup"
               data-page-size="20"
               data-row-style="bootstrapTableRowSizeModifier"
               data-show-refresh="true">
            <thead id="columnData" style="background-color: #3fc1be">

            </thead>

        </table>

    </div>
</div>


<div class="modal fade" id="editGatewayPriority" tabindex="-1" role="dialog"
     data-backdrop="static" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalLabel">Edit Gateway priority </h4>


            </div>
            <div class="modal-body">
                <form id="priorityMappingForm">
                    <div>


                        <div style="text-align:center">
                            <label for="SMSgroupName">Group name : </label>
                            <input class="form-control modalInput hideOverflow "
                                   style="width:20em;height:22px;margin-left: 20px; display: inline;text-align: center;"
                                   type="text" id="SMSgroupName" readonly/>
                        </div>
                        <hr style="margin-top: 10px;margin-bottom: 0;">

                        <div hidden id="groupId"></div>

                        <div id="priorityResponse"></div>
                        <br>

                        <div id="prioritySelectors"></div>

                    </div>
                </form>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                        data-dismiss="modal">Close
                </button>

                <button type="button" class="btn btn-xs btn-success" onclick="submitGatewayPriority()"
                        >Submit
                </button>

            </div>
        </div>
    </div>
</div>

