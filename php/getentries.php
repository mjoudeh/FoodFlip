<?php
    include_once 'ffdb_functions.php';
    $ffdb = new FFDB_Functions();
    $entries = $db->getRowCount();
	$a = array();
	$b = array();
    if ($entries != false){
        $no_of_entries = mysql_num_rows($entries);
		while ($row = mysql_fetch_array($entries)) {		
			$b["foodType"] = $row["FoodType"];
			$b["foodDescription"] = $row["FoodDescription"];
			array_push($a,$b);
		}
		echo json_encode($a);
	}
?>