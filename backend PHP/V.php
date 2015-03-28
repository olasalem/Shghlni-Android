<?php
$un= $_POST['UserName'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

$r=mysqli_query($con,"select * from accounts Where Username='".$un."'");
$result=mysqli_fetch_assoc($r);


if(json_encode($result) == "null")
{echo "Yes" ;} 
else 
{echo "NO" ;}

mysqli_close($con);


?>