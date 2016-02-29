<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>

<div class="mylist-container" id="page-wrapper" style="width: 950px;">

    <div class=" col-md-12" style="width: 105%;">
        <h4 class="pull-left">SMS Groups</h4>
        <table id="SMSGroupMappingTable"
               data-toggle="table"
               data-pagination="true"
               data-search="true"
               data-show-refresh="true"
               data-page-size="20"
               data-url="../rws/smsGroup/list"
               data-row-style="bootstrapTableRowSizeModifier">
            <thead style="background-color: #3fc1be;">
            <tr>
                <th data-field="name">Groups</th>

                <th data-formatter="groupSmsTypeFormatter">SMS type</th>
                <th data-field="lastUpdatedOn" data-formatter="lastUpdatedOnFormatter">Last Updated On</th>
                <th data-field="priority">Priority</th>

                <th data-formatter="typeMatchCPFormatter">
                    <div title="Type-match Cooling period">TM C-P
                    </div>
                </th>

                <th data-formatter="contentMatchCPFormatter">
                    <div title="Content-match Cooling period">CM C-P
                    </div>
                </th>

                <th data-formatter="editActionColumnFormatter" data-events="groupEditEvents">Actions</th>

            </tr>
            </thead>
        </table>


        <div class="modal fade" id="editGroupCoolingPeriodModal" tabindex="-1" role="dialog"
             data-backdrop="static" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" style="width: 430px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="modalLabel">Edit Group Cooling Period</h4>


                    </div>
                    <div class="modal-body">
                        <form id="groupsEditForm">
                            <div>
                                Group Name
                                <div class="pull-right">
                                    <input class="form-control modalInput" style="width:160px;height:22px"
                                           type="text" id="groupName" readonly/>
                                </div>
                                <br> <br>


                                <input type="hidden" id="groupId">

                                Type match cooling period
                                <div class="pull-right">
                                    <input class="form-control modalInput" style="width:160px;height:22px" type="text"
                                           id="groupMatchCoolingPeriod"/>
                                </div>

                                <br><br>

                                Type match unit
                                <div class="pull-right">
                                    <select class="modalInput" id="groupMatchUnit" style="width:160px;height:22px">
                                        <option value="ABSOLUTE_PERIOD">Absolue period(seconds)</option>
                                        <option value="CALENDAR_DAY">Calendar day</option>
                                    </select>
                                </div>
                                <br><br>

                                Content match cooling period
                                <div class="pull-right">
                                    <input class="form-control modalInput" type="text" style="width:160px;height:22px"
                                           id="groupContentMatchCoolingPeriod"/>
                                </div>
                                <br><br>

                                Content match unit
                                <div class="pull-right">
                                    <select id="groupContentMatchUnit" class="modalInput"
                                            style="width:160px;height:22px">
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
                                onclick="updateSMSGroupCoolingPeriod()">Submit
                        </button>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>