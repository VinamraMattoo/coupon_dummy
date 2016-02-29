<div>

    <div hidden id="couponFromCode"></div>
    <div class="col-md-6" style="width: 800px;margin-left: 70px;">
        <h3>Code details
            <div class="pull-right">
                <button onclick="showCouponFromCode()" title="Click to view coupon for this code" id="showCouponBtn"
                        type="button"
                        class="btn btn-info pull-right btn-xs">View coupon
                </button>
            </div>
        </h3>

        <table class="table table-striped table-bordered" data-row-style="rowStyle">
            <thead>
            <tr>
                <th>Name</th>
                <th>Value</th>
            </tr>
            </thead>
            <tbody id="viewCurrentCode"></tbody>
        </table>
    </div>
</div>