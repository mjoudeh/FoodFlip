<?php
    include_once 'ffdb_functions.php';
    $ffdb = new FFDB_Functions();
    $entries = $ffdb->getAllEntries();
	$a = array();
	$b = array();
    if ($entries != false){
        $no_of_entries = mysql_num_rows($entries);
		while ($row = mysql_fetch_array($entries)) {		
			$b["building"] = $row["Building"];
			$b["location"] = $row["Location"];
			$b["foodCategory"] = $row["FoodCategory"];
			$b["foodType"] = $row["FoodType"];
			$b["foodDescription"] = $row["FoodDescription"];
			$b["votes"] = $row["Votes"];
			array_push($a,$b);
		}
		echo json_encode($a);
	}
?>