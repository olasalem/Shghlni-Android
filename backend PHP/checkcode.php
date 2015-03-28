<?php
//this file takes the information of a request (Desired Qualification/deadline date/ place/ rph/ client id) and inserts them in the request table giving the request a new id 
$code=$_POST['code'];
$id=$_POST['id'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

$result=mysqli_query($con,"select * from accounts where ID= '".$id."'");

$array=mysqli_fetch_assoc($result);

if ($array['Code']==$code)
{
	echo"yes" ; 
	$r=mysqli_query($con,"UPDATE accounts SET Flag=1 where ID ='".$id."'");
}
else 
	echo "no";
	

mysqli_close($con);

?>