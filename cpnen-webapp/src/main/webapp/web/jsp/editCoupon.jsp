<script>
    $(document).ready(function () {
        $('#edit_applicableFrom').datetimepicker({
            format: 'YYYY-MM-DDHH:mm:ss'
        });

        $('#edit_applicableTill').datetimepicker({
            useCurrent: false,
            format: 'YYYY-MM-DDHH:mm:ss'
        });
        $("#edit_applicableFrom").on("dp.change", function (e) {
            $('#edit_applicableTill').data("DateTimePicker").minDate(e.date);
        });
        $("#edit_applicableTill").on("dp.change", function (e) {
            $('#edit_applicableFrom').data("DateTimePicker").maxDate(e.date);
        });

    });
</script>

<div class="container">
    <br> <br>

    <h1 class="text-center" style="width: 750px;">Edit Coupon</h1>
    <br>
    <ul class="list-inline">
        <li style="width: 330px; height: 20px; font-size: x-large;"><a
                href="#" onclick="editOthers(0)">Coupon Details</a></li>
        <li style="width: 260px; height: 20px; font-size: x-large;"><a
                href="#" onclick="editOthers(1)">Mapping</a></li>
        <li style="font-size: x-large;"><a href="#"
                                           onclick="editOthers(2)">Rules</a></li>

    </ul>
    
    <div class="panel panel-default" style="width: 753px;">
        <div class="panel-body" id="edit_couponAttributes"
             style="height: 400px; width: 750px; overflow: auto;">
            <form class="form-horizontal" role="form">


                <div class="col-md-4 col-md-offset-1">
                    <div class="form-group">
                        <label for="edit_name">Coupon Name</label> <input
                            class="form-control" type="text" id="edit_name"/>
                    </div>

                    <div class="form-group">
                        <label for="edit_applicableFrom">Applicable From</label> <input
                            type='text' id="edit_applicableFrom"
                            class="form-control glyphicon glyphicon-calendar"/>


                    </div>

                    <div class="form-group">
                        <label for="edit_applicableTill">Applicable Till</label> <input
                            type='text' id="edit_applicableTill"
                            class="form-control glyphicon glyphicon-calendar"/>

                    </div>

                    <div class="form-group">
                        <label for="edit_transactionValMax">Transaction Max Value</label> <input
                            class="form-control" type="number" id="edit_transactionValMax"/>
                    </div>

                    <div class="form-group">
                        <label for="edit_transactionValMin">Transaction Min Value</label> <input
                            class="form-control" type="number" id="edit_transactionValMin"/>
                    </div>

                    <div class="form-group">
                        <label for="edit_discountAmountMax">Max Discount</label> <input
                            class="form-control" type="number" id="edit_discountAmountMax"/>
                    </div>

                    <div class="form-group">
                        <label for="edit_discountAmountMin">Min Discount</label> <input
                            class="form-control" type="number" id="edit_discountAmountMin"/>
                    </div>

                    <div class="form-group">
                        <label for="edit_applicableUseCount">Application Use Count</label>
                        <input class="form-control" type="number"
                               id="edit_applicableUseCount" value="transValMax"/>
                    </div>

                </div>
                <div class="col-md-4 col-md-offset-2">
                    <div class="form-group">
                        <label for="edit_description">Description</label>
                        <textarea class="form-control" rows="4" id="edit_description"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="edit_inclusive">Inclusive?</label> <input
                            type="checkbox" name="inclusive" id="edit_inclusive">


                        <label for="edit_nthTimeReccuring">nth time recurring?</label> <input
                            type="checkbox" name="nthtime" id="edit_nthTimeReccuring">

                    </div>

                    <div class="form-group">
                        <label for="edit_nthTime">nth time </label>
                        <input class="form-control" type="number"
                               id="edit_nthTime"/>
                    </div>

                    <div class="form-group">
                        <label for="edit_actorType">Actor Type</label> <select
                            class="form-control" id="edit_actorType">
                        <option selected disabled>Select type</option>
                        <option>Customer</option>
                        <option>Employee</option>
                    </select>
                    </div>


                    <div class="form-group">
                        <label for="edit_channelName">Channel name</label> <select
                            class="form-control" id="edit_channelName" required>
                        <option selected disabled>Select Channel</option>
                        <option>Facebook</option>
                        <option>Andriod app</option>
                        <option>Mail</option>
                    </select>
                    </div>

                    <div class="form-group">
                        <label for="edit_applicationType">Application type</label> <select
                            class="form-control" id="edit_applicationType">
                        <option selected disabled>Select App Type</option>
                        <option>FIRST_TIME</option>
                        <option>ONE_TIME</option>
                        <option>ONE_TIME_PER_USER</option>
                        <option>ONE_TIME_PER_USER_FIFO</option>
                        <option>MANY_TIMES</option>

                    </select>
                    </div>


                    <div class="form-group">
                        <label for="edit_contextType">Context type</label>
                        <select class="form-control" id="edit_contextType">
                            <option selected disabled>Select Context type</option>
                            <option>Subscription</option>
                            <option>Appointment</option>
                        </select>
                    </div>

                </div>

            </form>

        </div>
        <div id="edit_couponMapping"
             style="display: none; height: 400px; width: 750px; overflow: auto;">

        </div>


        <div id="edit_couponRules"
             style="display: none; height: 400px; width: 750px; overflow: auto;">
            <br> <br>

            <div class="col-md-4 col-md-offset-1">

                <div class="form-group">
                    <label for="edit_ruleType">RULE TYPE</label> <select
                        onchange="checkDicountType()" class="form-control"
                        id="edit_ruleType">

                    <option>PERCENT</option>
                    <option>FLAT</option>
                </select>
                </div>


                <div class="form-group">
                    <label for="edit_percent">PERCENT</label> <input
                        class="form-control" type="number" id="edit_percent"
                        value="percent"/>
                </div>

                <div class="form-group">
                    <label for="edit_flatAmount">FLAT AMOUNT</label> <input
                        class="form-control" type="number" id="edit_flatAmount"
                        value="flatAmount" disabled/>
                </div>

            </div>
            <div class="col-md-4 col-md-offset-2">
                <div class="form-group">
                    <label for="edit_ruleDesc">RULE DESCRIPTION</label>
                    <textarea class="form-control" rows="5" id="edit_ruleDesc"></textarea>
                </div>
            </div>


        </div>
    </div>
    <%--    onchange="publishChanged()" add this to handle publish--%>
    <div>
        <input type="checkbox" style="margin-left: 300px;"
               id="edit_publish"><label for="edit_publish"> Publish</label>
        <submit class="btn btn-primary " onclick="edit_coupon_submit()" style="margin-left: 300px;">Submit</submit>
    </div>

</div>