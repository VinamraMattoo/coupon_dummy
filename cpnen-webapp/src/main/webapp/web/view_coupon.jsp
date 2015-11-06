<div>
    <br><br>
    <br><br>
    <h2>Coupon details
        <button onclick="showEdit()" style="display:none;" id="editCpn"  type="button" class="btn btn-info pull-right">Edit</button>
        <button onclick="showDelete()" style="display:none;" id="deleteCpn"  type="button" class="btn btn-danger pull-right">Delete</button>

        <button onclick="showCouponCode()" style="display:none;" id="generateCode"  type="button" class="btn btn-info pull-right">Show Coupon Codes</button>

        <button onclick="showDeactivation()" style="display:none;" type="button" id="deactivateCpn" class="btn btn-danger pull-right">Deactivate</button>
        <a onclick="createCouponCode()" style="display:none;" type="button" id="createCodebtn" class="btn btn-success pull-right">Create Code</a>
    </h2>
    <table class="table table-striped" data-row-style="rowStyle">
        <thead>
        <tr>
            <th>Name</th>
            <th>Value</th>
        </tr>
        </thead>
        <tbody id="viewCurrentCoupon"></tbody>
    </table>


</div>