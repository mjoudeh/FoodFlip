<html>
<head><title>View Entries</title>
<style>
body {
  font: normal medium/1.4 sans-serif;
}
table {
  border-collapse: collapse;
  width: 20%;
  margin-left: auto;
  margin-right: auto;
}
tr > td {
  padding: 0.25rem;
  text-align: center;
  border: 1px solid #ccc;
}
tr:nth-child(even) {
  background: #FAE1EE;
}
tr:nth-child(odd) {
  background: #edd3ff;
}
tr#header{
background: #c1e2ff;
}
td#sync{
background: #fff;
}
div.header{
padding: 10px;
background: #e0ffc1;
width:30%;
color: #008000;
margin:5px;
}
div.refresh{
margin-top:10px;
width: 5%;
margin-left: auto;
margin-right: auto;
}
div#norecord{
margin-top:10px;
width: 15%;
margin-left: auto;
margin-right: auto;
}
</style>
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
                          
    