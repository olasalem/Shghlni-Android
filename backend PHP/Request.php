<?php
//this file takes the information of a request (Desired Qualification/deadline date/ place/ rph/ client id) and inserts them in the request table giving the request a new id 
$id=$_POST['cid'];
$q=$_POST['desiredq'];
$p=$_POST['place'];
$d= $_POST['date'];
$rp=$_POST['rph'];



$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

//$mysqldate = date($d);


$r=mysqli_query ($con,"INSERT INTO requests ( DesiredQ , Area , Deadline , RatePerHour , CID , Processed ) VALUES ( '$q' , '$p' , '$d' , '$rp' , '$id' , 0 )");

$Id = $con->insert_id;
if($r!= null ) echo $Id;
mysqli_close($con);

?>