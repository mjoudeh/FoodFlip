<?php
include_once './ffdb_functions.php';
//Create Object for DB_Functions class
if(isset($_POST["Building"]) && !empty($_POST["Building"])
	&& isset($_POST["Location"]) && !empty($_POST["Location"])
	&& isset($_POST["FoodCategory"]) && !empty($_POST["FoodCategory"])
	&& isset ($_POST["FoodType"]) && !empty($_POST["FoodType"])){
$ffdb = new FFDB_Functions(); 
//Store User into MySQL DB
$building = $_POST["Building"];
$location = $_POST["Location"];
$foodCategory = $_POST["FoodCategory"];
$foodType = $_POST["FoodType"];
$foodDescription = $_POST["FoodDescription"];
$res = $ffdb->storeEntry($building, $location, $foodCategory, $foodType, $foodDescription);
	//Based on insertion, create JSON response
	if($res){ ?>
		 <div id="msg">Insertion successful</div>
	<?php }else{ ?>
		 <div id="msg">Insertion failed</div>
	<?php } ?>
}<?php }
?>