<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>

<div class="mylist-container" id="page-wrapper" style="width: 950px;">


    <div class=" col-md-12" style="width: 105%;">
        <h4 class="pull-left">SMS Types</h4>
        <table id="SMSTypeMappingTable"
               data-toggle="table"
               data-pagination="true"
               data-search="true"
               data-row-style="bootstrapTableRowSizeModifier"
               data-show-refresh="true"
               data-row-style="test"
               data-page-size="20"
               data-url="../rws/smsType/list">
            <thead style="background-color: #3fc1be">
            <tr>
                <th data-field="name">Types</th>
                <th data-formatter="lastUpdatedOnFormatter">Last Updated On</th>
                <th data-formatter="typeMatchCPFormatter">
                    <div title="Type-match Cooling period">TM C-P</div>
                </th>
                <th data-formatter="contentMatchCPFormatter">
                    <div title="Content-match Cooling period">CM C-P</div>
                </th>
                <th data-formatter="editActionColumnFormatter" data-events="typesEditEvents">Actions</th>
            </tr>
            </thead>
        </table>
        <div class="modal fade" id="editTypeCoolingPeriodModal" tabindex="-1" role="dialog"
             data-backdrop="static" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" style="width: 430px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Edit Type Cooling Period</h4>


                    </div>
                    <div class="modal-body">
                        <form id="typesEditForm">
                            <div>
                                Type Name
                                <div class="pull-right">
                                    <input class="form-control modalInput" style="width:160px;height:22px"
                                           type="text" id="typeName" readonly/>
                                </div>
                                <br> <br>


                                <input type="hidden" id="typesId">

                                Type match cooling period
                                <div class="pull-right">
                                    <input class="form-control modalInput" style="width:160px;height:22px" type="text"
                                           id="typeMatchCoolingPeriod"/>
                                </div>

                                <br><br>

                                Type match unit
                                <div class="pull-right">
                                    <select class="modalInput" id="typeMatchUnit" style="width:160px;height:22px">
                                        <option value="ABSOLUTE_PERIOD">Absolue period(seconds)</option>
                                        <option value="CALENDAR_DAY">Calendar day</option>
                                    </select>
                                </div>
                                <br><br>

                                Content match cooling period
                                <div class="pull-right">
                                    <input class="form-control modalInput" type="text" style="width:160px;height:22px"
                                           id="contentMatchCoolingPeriod"/>
                                </div>
                                <br><br>

                                Content match unit
                                <div class="pull-right">
                                    <select class="modalInput" id="contentMatchUnit" style="width:160px;height:22px">
                                        <option value="ABSOLUTE_PERIOD">Absolue period(seconds)</option>
                                        <option value="CALENDAR_DAY">Calendar day</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                                data-dismiss="modal">Close
                        </button>

                        <button type="button" class="btn btn-xs btn-success" data-dismiss="modal"
                                onclick="updateSMSTypeCoolingPeriod()">Submit
                        </button>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>