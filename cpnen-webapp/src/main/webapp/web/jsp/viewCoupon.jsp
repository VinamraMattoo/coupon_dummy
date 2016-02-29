<%--page showing all the  arrtributes of a specifc coupon--%>
<div>

    <h2 title="Current coupon details page">Coupon Details
        <div class="pull-right">
            <button style="padding-left: 5px;display:none;" title="Edit this coupon" onclick="viewCouponEdit()"
                    id="editCpn" type="button"
                    class="btn btn-xs btn-info ">Edit
            </button>

            <button style="padding-left: 5px;display:none;" title="Click to create new code for this coupon"
                    data-toggle="modal" data-target="#createCouponCodeModal"
                    id="generateCode"
                    type="button"
                    class="btn btn-xs btn-info ">Create Code
            </button>


            <button style="padding-left: 5px;" onclick="quickCoupon()"
                    title="Create a new coupon using details from this coupon" type="button"
                    id="quickCreateCpn"
                    class="btn btn-xs btn-info ">Copy
            </button>

            <button style="padding-left: 5px;display:none;" type="button" id="extendAppTill"
                    title="Click to extend the validity of coupon"
                    data-toggle="modal" data-target="#extendValidity"
                    class="btn btn-xs btn-info ">Extend validity
            </button>
            <button style="padding-left: 5px;display:none;margin-left: 5px" onclick="deleteThisCoupon()" id="deleteCpn"
                    title="Click to delete this coupon"
                    type="button"
                    class="btn btn-xs btn-danger ">Delete
            </button>
            <button style="padding-left: 5px;display:none;margin-left: 5px" onclick="deactivateThisCoupon()"
                    title="Click to deactivate this coupon"
                    type="button" id="deactivateCpn"
                    class="btn btn-xs btn-danger ">Deactivate
            </button>
        </div>
    </h2>
    <br>


    <div class="col-md-6">
        <div>
            <h3 style="display: inline;" title="Coupon parameters">Coupon Attributes</h3>

            <div id="couponStatus" title="Coupon status" style="display: inline;padding-left: 25px;"></div>
        </div>

        <table class="table table-striped table-bordered" data-row-style="rowStyle">
            <thead>
            <col width="130">
            <col width="130">
            <tr>
                <th>Name</th>
                <th>Value</th>
            </tr>
            </thead>
            <tbody id="viewCurrentCoupon"></tbody>
        </table>
    </div>

    <div class="col-md-6">
        <div><h3 style="display:inline;" title="Discount rules for the coupon">Rule</h3></div>
        <table class="table table-striped table-bordered" data-row-style="rowStyle" style="margin-top: 5px;">
            <thead>
            <col width="130">
            <col width="130">
            <tr>
                <th>Name</th>
                <th>Value</th>
            </tr>
            </thead>
            <tbody id="viewDiscountDetails"></tbody>
        </table>
        <br>

        <div id="mappingTables">
            <h3 title="Products the coupon applies to">Products Chosen</h3>
            <table class="table table-striped table-bordered" data-row-style="rowStyle">
                <thead>
                <col width="130">
                <col width="130">

                <tr>
                    <th>Name</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody id="viewMappingDetails"></tbody>
            </table>
            <br>

            <h3 title="Brands the coupon is applicable to">Applicable Brands</h3>
            <table class="table table-striped table-bordered" data-row-style="rowStyle">
                <thead>

                <tr>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody id="viewBrandsDetails"></tbody>
            </table>
            <br>

            <h3 title="Areas the coupon is applicable to">Applicable Areas</h3>
            <table class="table table-striped table-bordered" data-row-style="rowStyle">
                <thead>

                <tr>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody id="viewAreaDetails"></tbody>
            </table>

            <br>

            <h3 title="Referral sources for this coupon">Referral Brands</h3>
            <table class="table table-striped table-bordered" data-row-style="rowStyle">
                <thead>

                <tr>
                    <th>Name</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody id="viewReferralDetails"></tbody>
            </table>
        </div>
    </div>


    <div hidden>
        <input type="text" id="storedCouponId"/>

        <input type="text" id="viewStoredLastUpdatedOn"/>
    </div>

    <!-- extend validity popup -->
    <div class="modal fade" id="extendValidity" tabindex="-1" role="dialog"
         data-backdrop="static" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Extend coupon validity</h4>

                    <div style="font-size: 12px">
                        &nbsp;Extend coupon application validity. Coupon validity cannot be reduced.
                    </div>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="storedApplicableTill">Applicable till</label>
                        <input type='text' id="storedApplicableTill" title="The current applicable till time for this coupon"
                               class="form-control glyphicon glyphicon-calendar" readonly>

                    </div>
                    <div class="form-group" style="margin-bottom: 8px;">
                        <label for="extendedApplicability">New applicable till</label> <input title="New applicable till time for this coupon will be applicable"
                            type='text' id="extendedApplicability" onclick="$('#extend_validity_response').empty()"
                            class="form-control glyphicon glyphicon-calendar"/>
                    </div>

                    <div id="extend_validity_response"></div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                            data-dismiss="modal">Close
                    </button>

                    <button type="button" class="btn btn-xs btn-success" onclick="extendValidity()"
                            title="Click to extend validity">Submit
                    </button>

                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="createCouponCodeModal" tabindex="-1" role="dialog"
         data-backdrop="static" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" style="width: 400px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="modalLabel">Create Coupon Code</h4>

                    <div style="font-size: 12px">
                        &nbsp;Create code for this coupon
                    </div>

                </div>
                <div class="modal-body">
                    <form id="createCouponCodeForm" style="padding-left: 110px;">
                        <div class="form-group">
                            <label for="codeName">Coupon code <p title="Mandatory field"
                                                                 style="color: red;display: inline;">
                                &#42;</p></label>
                            <input title="Enter a valid code (Alphanumeric characters and Hypen allowed)"
                                   onkeyup="clearData()"
                                   style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                   class="form-control"
                                   type="text" id="codeName"/>

                            <div id="create_code_response"></div>

                        </div>

                        <div class="form-group">
                            <label for="codeChannel">Channel name</label>

                            <input
                                    title="Enter a channel"
                                    style="width:150px;height:22px;font-size: 10px; padding-top: 0px; border-bottom-width: 1px; padding-bottom: 0px;"
                                    class="form-control" type="text" id="codeChannel"/>

                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                            data-dismiss="modal">Close
                    </button>

                    <button type="button" class="btn btn-xs btn-success" onclick="validateCodeName('popup')"
                            title="Click to create code">Submit
                    </button>

                </div>
            </div>
        </div>
    </div>
</div>
