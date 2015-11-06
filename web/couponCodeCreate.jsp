<br><br>
<h1 align="center">Coupon Code Create</h1>
<div class="col-md-9 col-md-offset-2 panel" style="width: 950px;">
  <div class="panel-body jumbotron" style="width: 350px;">
<form action="">
  <br>
  Coupon Code:*
  <input required style="width: 300px" class="form-control" type="text" />
  <br> Valid from:*
  <input required class="form-control" style="width: 300px" type="date" /> Valid till*
  <input required class="form-control" style="width: 300px" type="date" />
  <br> channel name:
  <select class="btn btn-primary dropdown-toggle">
    <option>None</option>
    <option>Facebook</option>
    <option>Youtube</option>
    <option>Amar Ujjala</option>
    <option>times now</option>
    <option>others</option>
  </select>
  <br>
  <br>

  <div id="registerDiv">
    Reserved for:
    <input class="form-control" type="text" style="width: 300px" />
    <br> Select Event type
    <select class="btn btn-primary dropdown-toggle">
      <option value=""> --select an option--</option>
      <option value="bday">Birthday</option>
      <option value="anniversary">anniversary</option>
      <option value="new year">new year</option>
      <option value="diwali">diwali</option>
      <option value="holi">holi</option>
    </select>
    <br><br><br>
  </div>

  <a id="registerBtn" class="btn btn-info">register user</a>

  <input class="btn btn-success" type="submit" value="submit" />
</form>
    </div>
<script>
  $(document).ready(function() {
    $("#registerDiv").hide();
    $("#registerBtn").click(function() {
      $("#registerDiv").toggle(1000);
    });
  });
</script>
  </form>
  </div>
  </div>