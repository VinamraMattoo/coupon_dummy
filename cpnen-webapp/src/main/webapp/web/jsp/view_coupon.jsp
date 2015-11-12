<div>
    <br><br>

    <h2>Coupon details
        <button onclick="onEditClick()" style="display:none;" id="editCpn" type="button"
                class="btn btn-info pull-right">Edit
        </button>

        <button onclick="onDeleteClick()" style="display:none;" id="deleteCpn" type="button"
                class="btn btn-danger pull-right">Delete
        </button>

        <button onclick="onGenerateCodeClick()" style="display:none;" id="generateCode" type="button"
                class="btn btn-info pull-right">Show Coupon Codes
        </button>

        <button onclick="onDeactivationClick()" style="display:none;" type="button" id="deactivateCpn"
                class="btn btn-danger pull-right">Deactivate
        </button>

        <button onclick="onCreateCodeClick()" style="display:none;" type="button" id="createCodeCpn"
                class="btn btn-success pull-right">Create Code
        </button>
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