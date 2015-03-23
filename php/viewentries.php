<html>
<head>
<script>
var val= setInterval(function(){
location.reload();
},2000);
</script>
</head>
<body>
<center>
<div class="header">
FoodFlip - View Entries
</div>
</center>
<?php
    include_once 'ffdb_functions.php';
    $ffdb = new FFDB_Functions();
    $entries = $ffdb->getAllEntries();
    if ($entries != false)
        $no_of_entries = mysql_num_rows($entries);
    else
        $no_of_entries = 0;
		
?>
<?php
    if ($no_of_entries > 0) {
?>
<table>
<?php
    while ($row = mysql_fetch_array($entries)) {
?> 
<tr>
<td><span><?php echo $row["FoodType"] ?></span></td>
<td><span><?php echo $row["FoodDescription"] ?></span></td>
<td id="sync"><span>
<?php } ?>
</body>
</html>
                          
    