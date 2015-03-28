<?php
//this file deletes a certain qualification from a certain F.L info given the two ids 
$uid=$_POST['UID'];
$qid=$_POST['pos'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

$r=mysqli_query($con,"Delete from relation where ID='".$uid."' AND QID= '".$qid."'");
mysqli_close($con);

?>