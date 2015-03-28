<?php
//this file updates the freelancer info in the table or add them if they are not there 
$id=$_POST['ID'];
$area=$_POST['Area'];
$phone1=$_POST['ph1'];
$phone2=$_POST['ph2'];
$rph=$_POST['RPH'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

//get the freelancer account
$result=mysqli_query($con,"UPDATE accounts SET Area='".$area."' ,Phone1= '".$phone1."',Phone2='".$phone2."', RatePerHour='".$rph."'  Where ID='".$id."'");
mysqli_close($con);
?>