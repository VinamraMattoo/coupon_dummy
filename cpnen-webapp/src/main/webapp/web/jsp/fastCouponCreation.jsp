<%--similar to create coupon with preddefined values for a specific coupon--%>

<div class="container" style="padding-left: 25px;">


    <h3 title="Quick coupon and code creation page "
        style="width: 750px; border-bottom: thin solid white;margin-bottom: 2px;">Quick Coupon and Code Creation</h3>
    <h6 style="margin-top: 2px;">Create a new coupon and its associated code together by specifying important
        attributes. This coupon will be published upon submission.</h6>

    <%--panel to accept fast  input  parameters--%>
    <ul class="list-inline nav nav-pills">
        <li id="fastDetailsPanel" class="active" role="presentation"><a title="Click to add coupon details" style=" height: 32px;
    padding-top: 6px;" href="javascript:void(0)" onclick="couponFastTabSwitcher(0)">Coupon
            Details</a></li>
        <li id="fastMappingsPanel" role="presentation"><a title="Click to select mappings" style=" height: 32px;
    padding-top: 6px;" href="javascript:void(0)" onclick="couponFastTabSwitcher(1)">Mapping</a></li>
        <li id="fastRulesPanel" role="presentation"><a title="Click to define discount rule" style=" height: 32px;
    padding-top: 6px;" href="javascript:void(0)" onclick="couponFastTabSwitcher(2)">Rules</a></li>

    </ul>


    <div id="fastStatusBar" style="margin-top: 10px;"></div>

    <div class="panel panel-default" id="fastCouponForm" style="width: 900px;margin-top: 10px;">
        <div class="panel-body" id="fast_couponAttributes"
             style="height: 400px; width: 900px; overflow: auto;">

            <form class="form-horizontal" role="form">
                <div class="col-md-4 col-md-offset-1">

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_code">Coupon Code <p title="Mandatory field"
                                                                                       style="color: red;display: inline;">
                            &#42;</p></label> <input
                            title="Enter an alphanumeric code name "
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" onblur="validateCodeName('input')" type="text" id="fast_code"/>

                        <div id="fast_code_response"></div>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_name">Coupon Name <p title="Mandatory field"
                                                                                       style="color: red;display: inline;">
                            &#42;</p></label> <input
                            title="Enter a valid coupon name"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" onblur="validateCouponName('fast',this.value)" type="text"
                            id="fast_name"/>

                        <div id="fast_name_response"></div>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_applicableFrom">Applicable From <p
                                title="Mandatory field"
                                style="color: red;display: inline;">
                            &#42;</p></label></label> <input
                            title="Enter the date and time from which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            type='text' id="fast_applicableFrom"
                            class="form-control  "/>


                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_applicableTill">Applicable Till <p
                                title="Mandatory field"
                                style="color: red;display: inline;">
                            &#42;</p></label></label> <input
                            title="Enter the date and time till which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            type='text' id="fast_applicableTill"
                            class="form-control  "/>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_discountAmountMax">Max Discount</label> <input
                            title="Enter maximum transaction amount below which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="fast_discountAmountMax"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_discountAmountMin">Min Discount</label>
                        <input
                                title="Enter minimum transaction amount above which this coupon will be applicable"
                                min="0"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control" type="number" id="fast_discountAmountMin"/>
                    </div>

                     <div class="form-group">
                              <label style="font-size: 12px;" for="fast_couponCategory">Coupon Category <p title="Mandatory field"
                                                                                                           style="color: red;display: inline;">
                                  <span style="font-size: 0.8em;">&#8224;</span><span> &#42;</span></p></label>
                              <select
                                      title="Select type of category this coupon can be applied "
                                      style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                      class="form-control form-control" id="fast_couponCategory">


                              </select>
                          </div>


                </div>
                <div class="col-md-4 col-md-offset-2">

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_inclusive">Exclusive</label> <input
                            title="More than one exclusive coupons cannot be applied together"
                            type="checkbox" name="inclusive" id="fast_inclusive">
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fastGlobal">Global</label> <input
                            title="Check this to make this a global coupon"
                            type="checkbox" name="Global" id="fastGlobal">
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_actorType">Actor Type <p title="Mandatory field"
                                                                                           style="color: red;display: inline;">
                            &#42;</p></label></label>
                        <select
                                title="Select type of user who can apply this coupon"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class=" form-control" id="fast_actorType">
                            <option>CUSTOMER</option>
                            <option>STAFF</option>
                        </select>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_channel">Channel Name</label></label>
                        <input
                                title="Enter a channel"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control" type="text" id="fast_channel"/>


                    </div>
                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_contextType">Context type <p title="Mandatory field"
                                                                                               style="color: red;display: inline;">
                            &#42;</p></label></label>
                        <select title="Select appropriate context in which this coupon will be applicable"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class=" form-control" id="fast_contextType">
                            <option>SUBSCRIPTION</option>
                            <option>APPOINTMENT</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_applicationType">Application type <p
                                title="Mandatory field"
                                style="color: red;display: inline;">
                            &#42;</p></label></label>
                        <select
                                title="Select an appropriate application type"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class=" form-control" id="fast_applicationType"
                                onchange="validateApplicationType('fast',this.value)">
                            <option value='NTH_TIME'>NTH TIME</option>
                            <option value='ONE_TIME'>ONE TIME</option>
                            <option value='ONE_TIME_PER_USER'>ONE TIME PER USER</option>
                            <option value='ONE_TIME_PER_USER_FIFO'>ONE TIME PER USER FIFO</option>
                            <option value='MANY_TIMES'>MANY TIMES</option>
                            <option value='NTH_TIME_PER_SUBSCRIPTION'>NTH TIME PER SUBSCRIPTION</option>
                            <option value='NTH_TIME_AB_PER_SUBSCRIPTION'>NTH TIME AB PER SUBSCRIPTION</option>

                        </select>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_nthTimeRecurring">nth time recurring</label> <input
                            title="If checked, the coupon will be applicable for every nth transaction"
                            type="checkbox" name="nthtime" id="fast_nthTimeRecurring">

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_nthTime">nth time </label>
                        <input title="Enter the nth-time value. Applicable for all nth-time* application types"

                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="text"
                               id="fast_nthTime" disabled/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="fast_applicableUseCount">Applicable Use Count</label>
                        <input title="Enter maximum number of times this coupon can be used. Applicable for 'ONE TIME PER USER FIFO' application type"
                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="number"
                               id="fast_applicableUseCount" disabled/>
                    </div>

                </div>
            </form>
        </div>


        <div id="fast_couponMapping"
             style="display: none; height: 400px; width: 900px; overflow: hidden; ">

        </div>


        <div id="fast_couponRules"
             style="display: none; height: 400px; width: 900px; overflow: auto;">
            <br> <br>

            <div class="col-md-4 col-md-offset-1">

                <div class="form-group">
                    <label style="font-size: 12px;" for="fast_ruleType">RULE TYPE<p title="Mandatory field"
                                                                                    style="color: red;display: inline;">
                        &#42;</p></label>
                    <select
                            title="Select a rule type"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class=" form-control" onchange="flatInputCheck('fast',this.value)"
                            id="fast_ruleType">

                        <option>PERCENTAGE</option>
                        <option>FLAT</option>
                    </select>
                </div>


                <div class="form-group">
                    <label style="font-size: 12px;" for="fast_discountValue">Value<p title="Mandatory field"
                                                                                     style="color: red;display: inline;">
                        &#42;</p></label> <input
                        style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                        class="form-control" type="number" id="fast_discountValue"
                        title="Enter percentage value or flat amount"
                        value="percent"/>
                </div>

                <div id="fastFlatNotice"></div>

            </div>
        </div>
    </div>


    <div class="col-md-4">
        <p style="color: red;display: inline;margin-left: 2px;">&#42;</p>

        <p style='font-size:10px;display: inline;'>

            &nbsp;Mandatory field for publishing</p>
    </div>
    <div class="col-md-7">

        <submit title="Click to create this coupon and code" id="fastSubmitId"
                onclick="validateCodeName('submit')"
                class="btn btn-xs btn-primary"
                style="margin-left: 320px;">
            Submit
        </submit>

    </div>

</div>

