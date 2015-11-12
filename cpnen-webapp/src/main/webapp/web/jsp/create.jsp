<div class="container">
    <br> <br>

    <h1 class="text-center" style="width: 750px;">Create Coupon</h1>
    <br>
    <ul class="list-inline">
        <li style="width: 330px; height: 20px; font-size: x-large;"><a
                href="#" onclick="showOthers(0)">Coupon Details</a></li>
        <li style="width: 260px; height: 20px; font-size: x-large;"><a
                href="#" onclick="showOthers(1)">Mapping</a></li>
        <li style="font-size: x-large;"><a href="#"
                                           onclick="showOthers(2)">Rules</a></li>

    </ul>
    <div class="panel panel-default" style="width: 753px;">
        <div class="panel-body" id="couponAttributes"
             style="height: 400px; width: 750px; overflow: auto;">
            <form class="form-horizontal" role="form">


                <div class="col-md-4 col-md-offset-1">
                    <div class="form-group">
                        <label for="name">Coupon Name</label> <input
                            class="form-control" type="text" id="name"/>
                    </div>

                    <div class="form-group">
                        <label for="applicableFrom">Applicable From</label> <input
                            type='text' id="applicableFrom"
                            class="form-control glyphicon glyphicon-calendar"/>


                    </div>

                    <div class="form-group">
                        <label for="applicableTill">Applicable Till</label> <input
                            type='text' id="applicableTill"
                            class="form-control glyphicon glyphicon-calendar"/>

                    </div>

                    <div class="form-group">
                        <label for="transactionValMax">Transaction Max Value</label> <input
                            class="form-control" type="number" id="transactionValMax"/>
                    </div>

                    <div class="form-group">
                        <label for="transactionValMin">Transaction Min Value</label> <input
                            class="form-control" type="number" id="transactionValMin"/>
                    </div>

                    <div class="form-group">
                        <label for="discountAmountMax">Max Discount</label> <input
                            class="form-control" type="number" id="discountAmountMax"/>
                    </div>

                    <div class="form-group">
                        <label for="discountAmountMin">Min Discount</label> <input
                            class="form-control" type="number" id="discountAmountMin"/>
                    </div>

                    <div class="form-group">
                        <label for="applicableUseCount">Application Use Count</label>
                        <input class="form-control" type="number"
                               id="applicableUseCount" value="transValMax"/>
                    </div>

                </div>
                <div class="col-md-4 col-md-offset-2">
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" rows="4" id="description"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="inclusive">Inclusive?</label> <input
                            type="checkbox" name="inclusive" id="inclusive">


                        <label for="nthTimeReccuring">nth time recurring?</label> <input
                            type="checkbox" name="nthtime" id="nthTimeReccuring">

                    </div>

                    <div class="form-group">
                        <label for="nthTime">nth time </label>
                        <input class="form-control" type="number"
                               id="nthTime"/>
                    </div>

                    <div class="form-group">
                        <label for="actorType">Actor Type</label> <select
                            class="form-control" id="actorType">
                        <option selected disabled>Select type</option>
                        <option>STAFF</option>
                        <option>CUSTOMER</option>
                    </select>
                    </div>


                    <div class="form-group">
                        <label for="channelName">Channel name</label> <select
                            class="form-control" id="channelName" required>
                        <option selected disabled>Select Channel</option>
                        <option>Facebook</option>
                        <option>Andriod app</option>
                        <option>Mail</option>
                    </select>
                    </div>

                    <div class="form-group">
                        <label for="applicationType">Application type</label> <select
                            class="form-control" id="applicationType">
                        <option selected disabled>Select App Type</option>
                        <option>NTH_TIME</option>
                        <option>ONE_TIME</option>
                        <option>ONE_TIME_PER_USER</option>
                        <option>ONE_TIME_PER_USER_FIFO</option>
                        <option>MANY_TIMES</option>
                        <option>NTH_TIME_PER_SUBSCRIPTION</option>
                        <option>NTH_TIME_AB_PER_SUBSCRIPTION</option>

                    </select>
                    </div>


                    <div class="form-group">
                        <label for="contextType">Context type</label>
                        <select class="form-control" id="contextType">
                            <option selected disabled>Select Context type</option>
                            <option>SUBSCRIPTION</option>
                            <option>APPOINTMENT</option>
                        </select>
                    </div>

                </div>

            </form>

        </div>
        <div id="couponMapping"
             style="display: none; height: 400px; width: 750px; overflow: auto;">

        </div>


        <div id="couponRules"
             style="display: none; height: 400px; width: 750px; overflow: auto;">
            <br> <br>

            <div class="col-md-4 col-md-offset-1">

                <div class="form-group">
                    <label for="ruleType">RULE TYPE</label> <select
                        onchange="checkDicountType('create')" class="form-control"
                        id="ruleType">

                    <option>PERCENTAGE</option>
                    <option>FLAT</option>
                </select>
                </div>


                <div class="form-group">
                    <label for="percent">PERCENT</label> <input
                        class="form-control" type="number" id="percent"
                        value="percent"/>
                </div>

                <div class="form-group">
                    <label for="flatAmount">FLAT AMOUNT</label> <input
                        class="form-control" type="number" id="flatAmount"
                        value="flatAmount" disabled/>
                </div>

            </div>
            <div class="col-md-4 col-md-offset-2">
                <div class="form-group">
                    <label for="ruleDesc">RULE DESCRIPTION</label>
                    <textarea class="form-control" rows="5" id="ruleDesc"></textarea>
                </div>
            </div>


        </div>
    </div>

    <div>
        <input type="checkbox" style="margin-left: 300px;"
               id="publish"><label for="publish"> Publish</label>
        <submit class="btn btn-primary " onclick="createCouponSubmit()" style="margin-left: 300px;">Submit</submit>
    </div>

</div>