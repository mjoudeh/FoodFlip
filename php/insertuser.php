<?php
include_once './ffdb_functions.php';
//Create Object for DB_Functions class
if(isset($_POST["id"]) && !empty($_POST["id"])) {
	$ffdb = new FFDB_Functions(); 
	//Store User into MySQL DB
	$id = $_POST["id"];
	$res = $ffdb->storeUser($id);
	//Based on insertion, create JSON response
	if($res) { ?>
		 <div id="msg">Insertion of user successful</div>
	<?php } else { ?>
		 <div id="msg">Insertion of user failed</div>
	<?php } ?>
<?php }
?>