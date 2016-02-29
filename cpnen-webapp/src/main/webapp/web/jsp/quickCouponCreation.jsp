<%--divs to accept the coupon parameters while creating  quick  coupon--%>

<div class="container" style="padding-left: 25px;">

    <h3 title="Coupon creation page" style="width: 750px; border-bottom: thin solid white;margin-bottom: 2px;">Create
        Coupon</h3>
    <h6 style="margin-top: 2px;">Create a new coupon by specifying its attributes, mappings and discount rules. Once coupon has been published no further updates can be done.</h6>



    <ul class="list-inline nav nav-pills">
        <%--coupon details  panel--%>
        <li id="quickDetailsPanel" class="active" role="presentation"><a style=" height: 32px;     padding-top: 6px;"
                                                                         title="Click to add coupon details"
                                                                         href="javascript:void(0)" onclick="couponQuickTabSwitcher(0)">Coupon
            Details</a></li>
        <li id="quickMappingsPanel" role="presentation"><a style=" height: 32px;     padding-top: 6px;"
                                                           title="Click to select mappings"
                                                           href="javascript:void(0)" onclick="couponQuickTabSwitcher(1)">Mapping</a></li>
        <li id="quickRulesPanel" role="presentation"><a style=" height: 32px;     padding-top: 6px;"
                                                        title="Click to define discount rule"
                                                        href="javascript:void(0)" onclick="couponQuickTabSwitcher(2)">Rules</a></li>


    </ul>


    <div id="quickStatusBar"></div>

    <div class="panel panel-default" id="quickCouponForm" style="width: 900px;margin-top: 10px;">
        <div class="panel-body" id="quick_couponAttributes"
             style="height: 400px; width: 900px; overflow: auto;">
            <%--coupoon detail form--%>
            <form class="form-horizontal" role="form">
                <div class="col-md-4 col-md-offset-1">

                    <div class="form-group">
                        <label style="font-size: 12px" for="quick_name">Coupon Name <p title="Mandatory field"
                                                                                       style="color: red;display: inline;">
                            <span style="font-size: 0.8em;">&#8224;</span><span> &#42;</span></p></label>


                        <input
                                title="Enter a valid coupon name" onblur="validateCouponName('quick',this.value)"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control" type="text" id="quick_name"/>


                        <div id="quick_name_response"></div>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="quick_applicableFrom">Applicable From <p
                                title="Mandatory field"
                                style="color: red;display: inline;">
                            &#42;</p></label> <input
                            title="Enter the date and time from which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            type='text' id="quick_applicableFrom"
                            class="form-control glyphicon glyphicon-calendar"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="quick_applicableTill">Applicable Till <p
                                title="Mandatory field"
                                style="color: red;display: inline;">
                            &#42;</p></label> <input
                            title="Enter the date and time till which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            type='text' id="quick_applicableTill"
                            class="form-control glyphicon glyphicon-calendar"/>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_transactionValMax">Transaction Max Amount</label>
                        <input title="Enter maximum transaction amount below which this coupon will be applicable"

                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="number" id="quick_transactionValMax"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_transactionValMin">Transaction Min Amount</label>
                        <input title="Enter minimum transaction amount above which this coupon will be applicable"
                               min="0"

                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="number" id="quick_transactionValMin"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_discountAmountMax">Max Discount</label> <input
                            title="Enter maximum discount below which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="quick_discountAmountMax"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_discountAmountMin">Min Discount</label> <input
                            title="Enter minimum discount below which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="quick_discountAmountMin"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_contextType">Context type <p title="Mandatory field"
                                                                                                style="color: red;display: inline;">
                            &#42;</p></label>
                        <select title="Select appropriate context in which this coupon will be applicable"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class=" form-control" id="quick_contextType">
                            <option>SUBSCRIPTION</option>
                            <option>APPOINTMENT</option>
                        </select>
                    </div>

                </div>
                <div class="col-md-4 col-md-offset-2">
                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_description">Description</label>
                        <textarea title="Enter Description" class="form-control" rows="3"
                                  id="quick_description" maxlength="512"></textarea>
                    </div>

               <div class="form-group">
                        <label style="font-size: 12px;" for="quick_couponCategory">Coupon Category <p title="Mandatory field"
                                                                                                      style="color: red;display: inline;">
                            <span style="font-size: 0.8em;">&#8224;</span><span> &#42;</span></p></label>
                        <select
                                title="Select type of category this coupon can be applied "
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control form-control" id="quick_couponCategory">


                        </select>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_inclusive">Exclusive</label> <input
                            title="More than one exclusive coupons cannot be applied together"
                            type="checkbox" name="inclusive" id="quick_inclusive">

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quickGlobal">Global</label> <input
                            title="Check this to make this a global coupon"
                            type="checkbox" name="Global" id="quickGlobal">
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_actorType">Actor Type <p title="Mandatory field"
                                                                                            style="color: red;display: inline;">
                            &#42;</p></label> <select
                            title="Select type of user who can apply this coupon"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class=" form-control" id="quick_actorType">
                        <option>STAFF</option>
                        <option>CUSTOMER</option>
                    </select>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_applicationType">Application type <p
                                title="Mandatory field" style="color: red;display: inline;">&#42;</p></label>
                        <select
                                title="Select an appropriate application type"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class=" form-control" id="quick_applicationType"
                                onchange="validateApplicationType('quick',this.value)">
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
                        <label style="font-size: 12px;" for="quick_nthTimeRecurring">nth time recurring</label> <input
                            title="If checked, the coupon will be applicable for every nth transaction"
                            type="checkbox" name="nthtime" id="quick_nthTimeRecurring"
                            disabled/>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_nthTime">nth time </label>
                        <input title="Enter the nth-time value. Applicable for all nth-time* application types"
                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="text"
                               id="quick_nthTime" disabled/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="quick_applicableUseCount">Applicable Use Count</label>
                        <input title="Enter maximum number of times this coupon can be used. Applicable for 'ONE TIME PER USER FIFO' application type"
                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="number"
                               id="quick_applicableUseCount" disabled/>
                    </div>
                </div>
            </form>

        </div>
        <%--coupon mproducts and brands listing--%>
        <div id="quick_couponMapping"
             style="display: none; height: 400px; width: 900px; overflow: hidden;">

        </div>

        <%--coupon rules definition input   style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;" form--%>
        <div id="quick_couponRules"
             style="display: none; height: 400px; width: 900px; overflow: auto;">
            <br> <br>

            <div class="col-md-4 col-md-offset-1">

                <div class="form-group">
                    <label style="font-size: 12px;" for="quick_ruleType">RULE TYPE <p title="Mandatory field"
                                                                                      style="color: red;display: inline;">
                        &#42;</p></label> <select
                        title="Select a rule type"
                        style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                        class=" form-control" onchange="flatInputCheck('quick',this.value)"
                        id="quick_ruleType">

                    <option>PERCENTAGE</option>
                    <option>FLAT</option>
                </select>
                </div>


                <div class="form-group">
                    <label style="font-size: 12px;" for="quick_discountValue">Value <p title="Mandatory field"
                                                                                       style="color: red;display: inline;">
                        &#42;</p></label><input
                        title="Enter percentage value or flat amount"
                        style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                        class="form-control" type="number" id="quick_discountValue"/>
                </div>
                <div id="quickFlatNotice"></div>

            </div>
            <div class="col-md-4 col-md-offset-2">
                <div class="form-group">
                    <label style="font-size: 12px;" for="quick_ruleDesc">RULE DESCRIPTION</label>
                    <textarea title="Enter rule description" class="form-control" rows="3"
                              id="quick_ruleDesc" maxlength="512"></textarea>
                </div>
            </div>



        </div>
    </div>
    <div class="col-md-4">
        <p style="color: red;display: inline;">&#8224;</p>

        <p style='font-size:10px; display: inline;'>

            &nbsp;Mandatory field for draft</p> &nbsp;&nbsp;&nbsp;

        <p style="color: red;display: inline;margin-left: 2px;">&#42;</p>

        <p style='font-size:10px;display: inline;'>

            &nbsp;Mandatory field for publishing</p>
    </div>
    <div class="col-md-7">

        <submit title="Click to save as draft" class="btn btn-xs btn-primary " style="margin-left: 320px;"
                onclick="submitQuickCoupon('draft')"
                >Save as Draft
        </submit>

        <submit title="Click to publish the coupon" onclick="submitQuickCoupon('publish')"
                class="btn btn-xs btn-primary">
            Publish Coupon
        </submit>

    </div>


</div>