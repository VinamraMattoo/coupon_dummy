<%--divs to accept the coupon parameters while creating  new  coupon--%>

<div class="container" style="padding-left: 25px;">


    <h3 title="Coupon creation page " style="width: 750px; border-bottom: thin solid white;margin-bottom: 2px;">Create
        Coupon</h3>
    <h6 style="margin-top: 2px;">Create a new coupon by specifying its attributes, mappings and discount rules. Once
        coupon has been published no further updates can be done.</h6>
    <ul class="list-inline nav nav-pills">
        <%--coupon details  panel--%>
        <li id="couponDetailsPanel" class="active" role="presentation"><a style=" height: 32px;padding-top: 6px;"
                                                                          title="Click to add coupon details"
                                                                          href="javascript:void(0)"
                                                                          onclick="couponCreateTabSwitcher(0)">Coupon
            Details</a></li>
        <%--coupon mapping panel--%>
        <li id="couponMappingsPanel" role="presentation"><a style=" height: 32px;padding-top: 6px;"
                                                            title="Click to select mappings"
                                                            href="javascript:void(0)"
                                                            onclick="couponCreateTabSwitcher(1)">Mappings</a>
        </li>

        <%--coupon rules panel--%>
        <li id="couponRulesPanel" role="presentation"><a style=" height: 32px;padding-top: 6px;"
                                                         title="Click to define discount rule"
                                                         href="javascript:void(0)"
                                                         onclick="couponCreateTabSwitcher(2)">Rules</a></li>

    </ul>


    <div id="statusBar" style="margin-top: 10px;"></div>
    <div class="panel panel-default" style="width: 900px; margin-top: 2px;">
        <div class="panel-body" id="couponAttributes"
             style="height: 400px; width: 900px; overflow: auto;">
            <%--coupoon detail form--%>
            <form class="form-horizontal" role="form">
                <div class="col-md-4 col-md-offset-1">
                    <div class="form-group">
                        <label style="font-size: 12px" for="name">Coupon Name <p title="Mandatory field"
                                                                                 style="color: red;display: inline;">
                            <span style="font-size: 0.8em;">&#8224;</span><span> &#42;</span></p></label>


                        <input title="Enter a valid coupon name"
                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" onblur="validateCouponName('create',this.value)"
                               type="text" id="name"/>

                        <div id="create_name_response"></div>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="applicableFrom">Applicable From <p title="Mandatory field"
                                                                                               style="color: red;display: inline;">
                            &#42;</p></label> <input
                            title="Enter the date and time from which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            type='text' id="applicableFrom"
                            class="form-control glyphicon glyphicon-calendar"/>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="applicableTill">Applicable Till <p title="Mandatory field"
                                                                                               style="color: red;display: inline;">
                            &#42;</p></label> <input
                            title="Enter the date and time till which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            type='text' id="applicableTill"
                            class="form-control glyphicon glyphicon-calendar"/>

                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="transactionValMax">Transaction Max Amount</label> <input
                            title="Enter maximum transaction amount below which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="transactionValMax"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="transactionValMin">Transaction Min Amount</label> <input
                            title="Enter minimum transaction amount above which this coupon will be applicable" min="0"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="transactionValMin"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="discountAmountMax">Max Discount</label> <input
                            title="Enter maximum discount below which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="discountAmountMax"/>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px" for="discountAmountMin">Min Discount</label> <input
                            title="Enter minimum discount below which this coupon will be applicable"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control" type="number" id="discountAmountMin"/>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="contextType">Context type <p title="Mandatory field"
                                                                                          style="color: red;display: inline;">
                            &#42;</p></label>
                        <select title="Select appropriate context in which this coupon will be applicable"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control" id="contextType">
                            <option>SUBSCRIPTION</option>
                            <option>APPOINTMENT</option>
                        </select>
                    </div>

                </div>
                <div class="col-md-4 col-md-offset-2">
                    <div class="form-group">
                        <label style="font-size: 12px;" for="description">Description</label>
                        <textarea title="Enter description" class="form-control" rows="3" id="description"
                                  maxlength="512"></textarea>
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="couponCategory">Coupon Category
                            <p title="Mandatory field"
                               style="color: red;display: inline;">
                                <span style="font-size: 0.8em;">&#8224;</span><span> &#42;</span></p></label>
                        <select
                                title="Select type of category this coupon can be applied "
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control form-control" id="couponCategory">


                        </select>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="inclusive">Exclusive</label> <input
                            title="More than one exclusive coupons cannot be applied together"
                            type="checkbox" name="inclusive" id="inclusive">
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="createGlobal">Global</label> <input
                            title="Check this to make this a global coupon"
                            type="checkbox" name="Global" id="createGlobal">
                    </div>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="actorType">Actor Type <p title="Mandatory field"
                                                                                      style="color: red;display: inline;">
                            &#42;</p></label>
                        <select
                                title="Select type of user who can apply this coupon"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control form-control" id="actorType">
                            <option>STAFF</option>
                            <option>CUSTOMER</option>
                        </select>
                    </div>


                    <div class="form-group">
                        <label style="font-size: 12px;" for="applicationType">Application type <p
                                title="Mandatory field" style="color: red;display: inline;">&#42;</p></label>
                        <select
                                title="Select an appropriate application type"
                                style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                class="form-control form-control" id="applicationType"
                                onchange="validateApplicationType('create',this.value)">
                            <option value='NTH_TIME'>NTH TIME</option>
                            <option value='ONE_TIME'>ONE TIME</option>
                            <option value='ONE_TIME_PER_USER'>ONE TIME PER USER</option>
                            <option value='ONE_TIME_PER_USER_FIFO'>ONE TIME PER USER FIFO</option>
                            <option value='MANY_TIMES'>MANY TIMES</option>
                            <option value='NTH_TIME_PER_SUBSCRIPTION'>NTH TIME PER SUBSCRIPTION</option>
                            <option value='NTH_TIME_AB_PER_SUBSCRIPTION'>NTH TIME AB PER SUBSCRIPTION</option>


                        </select>
                    </div>


                    <label class="form-group"><label style="font-size: 12px;" for="nthTimeRecurring">nth time
                        recurring</label> <input
                            title="If checked, the coupon will be applicable for every nth transaction"
                            type="checkbox" name="nthtime" id="nthTimeRecurring" disabled>

                    </label>

                    <div class="form-group">
                        <label style="font-size: 12px;" for="nthTime">nth time </label>
                        <input title="Enter the nth-time value. Applicable for all nth-time* application types"
                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="text"
                               id="nthTime" disabled/>
                    </div>
                    <div class="form-group">
                        <label style="font-size: 12px" for="applicableUseCount">Applicable Use Count</label>
                        <input title="Enter maximum number of times this coupon can be used. Applicable for 'ONE TIME PER USER FIFO' application type"
                               style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                               class="form-control" type="number"
                               id="applicableUseCount" disabled/>
                    </div>
                </div>
            </form>

        </div>
        <%--coupon mproducts and brands listing--%>
        <div id="couponMapping"
             style="display: none; height: 400px; width: 900px; overflow:hidden;">

        </div>

        <%--coupon rules definition input form--%>
        <div id="couponRules"
             style="display: none; height: 400px; width: 900px; overflow: auto;">
            <br> <br>

            <div class="col-md-4 col-md-offset-1">

                <div class="form-group">
                    <label style="font-size: 12px;" for="ruleType">RULE TYPE <p title="Mandatory field"
                                                                                style="color: red;display: inline;">
                        &#42;</p></label>
                    <select title="Select a rule type" onchange="flatInputCheck('create',this.value)"
                            style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                            class="form-control form-control" id="ruleType">

                        <option>PERCENTAGE</option>
                        <option>FLAT</option>
                    </select>
                </div>


                <div class="form-group">
                    <label style="font-size: 12px;" for="discountValue">Value <p title="Mandatory field"
                                                                                 style="color: red;display: inline;">
                        &#42;</p></label> <input
                        title="Enter percentage value or flat amount"
                        style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                        class="form-control" type="number" id="discountValue"/>
                </div>
                <div id="createFlatNotice"></div>


            </div>
            <div class="col-md-4 col-md-offset-2">
                <div class="form-group">
                    <label style="font-size: 12px;" for="ruleDesc">RULE DESCRIPTION</label>
                    <textarea title="Enter rule description" class="form-control" rows="4"
                              id="ruleDesc" maxlength="512"></textarea>
                </div>
            </div>

        </div>
    </div>
    <%--checkbox to publish coupon--%>
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
                onclick="submitCreateCoupon('draft')"
                >Save as Draft
        </submit>

        <submit title="Click to publish the coupon" onclick="submitCreateCoupon('publish')"
                class="btn btn-xs btn-primary">
            Publish Coupon
        </submit>

    </div>

</div>
