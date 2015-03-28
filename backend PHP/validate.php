<?php
$UserName = $_POST['UserName'];
$sql="select * from accounts where accounts.Username='".$UserName."'"; 

//echo $sql;

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");
//echo "connected!";

mysqli_query($con, $sql);
$encodable = array(); 
$result = mysqli_query($con, $sql);

while($obj = mysqli_fetch_object($result)) {   
	$encodable[] = $obj; 
} 
$encoded = json_encode($encodable); 
echo $encoded;
mysqli_close($con);

?>