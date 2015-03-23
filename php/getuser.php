<?php
    include_once 'ffdb_functions.php';
	if(isset($_POST["id"]) && !empty($_POST["id"])) {
		$ffdb = new FFDB_Functions();
		$id = $_POST["id"];
		$user = $ffdb->getUser($id);
		$a = array();
		if ($user != false) {
			$a["id"] = $user["id"];
			$a["karma"] = $user["karma"];
			echo json_encode($a);
		} else { ?>
		 <div id="msg">user is false in getuser.php</div>
	<?php }
	} else { ?>
		 <div id="msg">id is not set or empty in getuser.php</div>
	<?php }
?>