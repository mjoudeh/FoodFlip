<?php
include_once './ffdb_functions.php';
//Create Object for DB_Functions class
if(isset($_POST["FoodType"]) && !empty($_POST["FoodType"])){
$ffdb = new FFDB_Functions(); 
//Store User into MySQL DB
$food = $_POST["FoodType"];
$res = $ffdb->storeEntry($food);
	//Based on insertion, create JSON response
	if($res){ ?>
		 <div id="msg">Insertion successful</div>
	<?php }else{ ?>
		 <div id="msg">Insertion failed</div>
	<?php } ?>
}<?php }
?>