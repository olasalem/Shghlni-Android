<?php
$Name = $_POST['Name'];
$UserName = $_POST['UserName'];
$Password=$_POST['Password'];
$Type = $_POST['Type'];
$sql="insert into accounts (Name,Username, Password,Type) values ('".$Name."','".$UserName."','".$Password."','".$Type."')";
//echo $sql;
$con=mysqli_connect("localhost","g5","abc123","Shghalni");
// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");
//echo "connected!";
$query = mysqli_query($con, $sql);
$Id = $con->insert_id;
if($query!= null ) echo $Id;
else {
	echo "Error";
}
mysqli_close($con);
?>