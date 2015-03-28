<?php
    include_once 'ffdb_functions.php';
	if(isset($_POST["id"]) && !empty($_POST["id"]) &&
		isset($_POST["comment"]) && !empty($_POST["comment"])) {
		$ffdb = new FFDB_Functions(); 
		$id = $_POST["id"];
		$comment = $_POST["comment"];
		$res = $ffdb->addEntryComment($id, $comment);

		if($res) { ?>
		 <div id="msg">Insertion of comment successful</div>
	<?php } else { ?>
		 <div id="msg">Insertion of comment failed</div>
	<?php } ?>
<?php }
?>