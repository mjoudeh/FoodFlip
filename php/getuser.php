<?php
    include_once 'ffdb_functions.php';
	if(isset($_POST["id"]) && !empty($_POST["id"])) {
		$ffdb = new FFDB_Functions();
		$user = $ffdb->getUser($_POST["id"]);
		$a = array();
		if ($user != false && $row = mysql_fetch_array($user)) {
			$a["id"] = $row["id"];
			$a["karma"] = $row["karma"];
			echo json_encode($a);
		} else { ?>
		 <div id="msg">user is false in getuser.php</div>
	<?php }
	} else { ?>
		 <div id="msg">id is not set or empty in getuser.php</div>
	<?php }
?>