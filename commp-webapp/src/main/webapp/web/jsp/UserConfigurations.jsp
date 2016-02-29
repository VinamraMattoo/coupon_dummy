<script
        src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>


<div class="mylist-container" id="page-wrapper" style="width: 950px;">

    <div class=" col-md-12" style="width: 105%;">
        <h4 class="pull-left">User
            Management</h4>
        <button title="Click here to add a new user" onclick="addNewUserModal()"
                style="margin-left:530px;margin-top:9px"
                class="btn btn-xs btn-success pull-left ">
            <span class="glyphicon glyphicon-plus"></span> Add user
        </button>
        <table
                id="userConfigurationTable"
                data-toggle="table" data-pagination="true" data-search="true"
                data-show-refresh="true"
                data-row-style="bootstrapTableRowSizeModifier"
                data-page-size="20"
                data-url="../rws/smsSender/list"
                data-classes="table table-bordered table-hover"
                data-striped="true">
            <thead style="background-color: #3fc1be">
            <tr>
                <th data-field="name"
                    data-formatter="userNameFormatter">Name
                </th>
                <th data-field="email">Email</th>
                <th data-field="registeredOn" data-formatter="userRegisteredOnFormatter">Registered On</th>
                <th data-field="lastUpdatedOn" data-formatter="userLastUpdatedOnFormatter">Updated On</th>
                <th data-field="active" data-formatter="userStatusFormatter">Status</th>
                <th data-formatter="userConfigActionFormatter" data-events="userConfigActionEvents">Actions</th>
            </tr>
            </thead>

        </table>

    </div>
</div>


<div class="modal fade" id="addNewUser" tabindex="-1" role="dialog"
     data-backdrop="static" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header" style="padding-bottom:0;">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="addUserModelTitle">Add User</h4>
                <h6>Enter the following details to register a new user.</h6>

            </div>
            <div class="modal-body">
                <form id="addUserForm">
                    <div>
                        Username:
                        <div class="pull-right">
                            <div  class="right-inner-addon">
                                <span style="pointer-events: none;top: 2px;" class="glyphicon glyphicon-user"></span>
                                <input placeholder="Username"  title="Enter the username for the user to be added" class="form-control modalInput"
                                   style="width:250px;height:22px"
                                   type="text" id="userNameEdit"/>
                                </div>
                        </div>
                        <br> <br>
                        Password:
                        <div class="pull-right">
                            <div class="right-inner-addon">
                                <span id="addUserEyeOpen" title="Show Password" onclick="showAddUserModalPassword('userPasswordEdit','addUserEyeOpen','addUserEyeClose')" class="glyphicon glyphicon-eye-open"></span>
                                <span  id="addUserEyeClose" title="Hide Password" style="display: none" onclick="hideAddUserModalPassword('userPasswordEdit','addUserEyeOpen','addUserEyeClose')" class="glyphicon glyphicon-eye-close "></span>
                                <input placeholder="Password" title="Enter the password for the user to be created" class="form-control modalInput"
                                   style="width:250px;height:22px"
                                   maxlength="32" name="addUserPasswordEdit" type="password" id="userPasswordEdit"/>
                                </div>

                        </div>
                        <br> <br>
                        Email:
                        <div class="pull-right">
                            <input placeholder="Email" title="Enter the email for the user to be created" class="form-control modalInput"
                                   style="width:250px;height:22px"
                                   type="text" id="userEmailEdit"/>
                        </div>
                        <br> <br>
                        Description
                            <textarea placeholder="Enter user description and additional details here."
                                      maxlength="250" rows="7" cols="50" class="form-control"
                                      id="userDescriptionEdit"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                        data-dismiss="modal">Close
                </button>

                <button type="button" class="btn btn-xs btn-success" data-dismiss="modal" onclick="addNewUser()"

                        >Submit
                </button>

            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="resetPasswordModal" tabindex="-1" role="dialog"
     data-backdrop="static" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header" style="padding-bottom:0;">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="resetPasswordModalTitle">Reset Password</h4>
                <h6>Reset password for this user</h6>

            </div>
            <div class="modal-body">
                <form id="resetPasswordModalForm">
                    <div>
                        Name:
                        <div class="pull-right">
                            <input class="form-control modalInput" style="width:150px;height:22px"
                                   type="text" id="nameForResetPasswordModal" readonly/>
                        </div>
                        <input class="hidden" id="userIdForResetPassword" type="text"/>
                        <br> <br>
                        Password:
                        <div class="pull-right">
                            <div class="right-inner-addon">
                                <span id="resetUserEyeOpen" title="Show Password" onclick="showAddUserModalPassword('resetPassword','resetUserEyeOpen','resetUserEyeClose')" class="glyphicon glyphicon-eye-open"></span>
                                <span  id="resetUserEyeClose" title="Hide Password" style="display: none" onclick="hideAddUserModalPassword('resetPassword','resetUserEyeOpen','resetUserEyeClose')" class="glyphicon glyphicon-eye-close "></span>

                                <input class="form-control modalInput" style="width:150px;height:22px"
                                   type="password" id="resetPassword"/>
                                </div>
                        </div>
                        <br> <br>
                        Re enter Password:
                        <div class="pull-right">
                            <div class="right-inner-addon">
                                <span id="resetReUserEyeOpen" title="Show Password" onclick="showAddUserModalPassword('resetRePassword','resetReUserEyeOpen','resetReUserEyeClose')" class="glyphicon glyphicon-eye-open"></span>
                                <span  id="resetReUserEyeClose" title="Hide Password" style="display: none" onclick="hideAddUserModalPassword('resetRePassword','resetReUserEyeOpen','resetReUserEyeClose')" class="glyphicon glyphicon-eye-close "></span>


                                <input class="form-control modalInput" style="width:150px;height:22px"
                                   type="password" id="resetRePassword"/>
                                </div>
                        </div>
                        <br> <br>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-xs btn-default pull-left" title="Click to close"
                        data-dismiss="modal">Close
                </button>

                <button type="button" class="btn btn-xs btn-success" onclick="resetUserPassword()">Submit
                </button>

            </div>
        </div>
    </div>
</div>


