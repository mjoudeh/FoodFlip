<?php
    include_once 'ffdb_functions.php';
	if(isset($_POST["id"]) && !empty($_POST["id"])) {
		$ffdb = new FFDB_Functions();
		$comments = $ffdb->getEntryComments($_POST["id"]);
		$a = array();
		$b = array();
		if ($comments != false) {
			$no_of_comments = mysql_num_rows($comments);
			while ($row = mysql_fetch_array($comments)) {		
				$b["comment"] = $row["comment"];
				array_push($a,$b);
			}
			echo json_encode($a);
		}
	}
?>