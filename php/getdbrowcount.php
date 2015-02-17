<?php
    include_once 'ffdb_functions.php';
    $ffdb = new FFDB_Functions();
    $entries = $db->getRowCount();
	$a = array();
	$b = array();
    if ($entries != false){
        $no_of_entries = mysql_num_rows($entries);		
		$b["count"] = $no_of_entries;
		echo json_encode($b);
	}
    else{
        $no_of_entries = 0;
		$b["count"] = $no_of_entries;
		echo json_encode($b);
	}
?>