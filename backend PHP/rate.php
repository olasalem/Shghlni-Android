<?php

$id =$_POST['ID'];
$r= $_POST['Rating'];


$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

//get the freelancer account
$result=mysqli_query($con,"select * from accounts where ID= '".$id."'");

//get the existing rate and number of raters 
$result2=mysqli_fetch_assoc($result);

$erate=json_encode($result2['Ratings']);
$nraters=json_encode($result2['Nrates']);
$x=(float) ltrim(rtrim($nraters,'"'),'"'); 


$newr= $x *(float) $erate [1]+ (float) $r;

$n=($x+1) ; 
$n2=$newr/$n;

mysqli_query($con,"UPDATE accounts SET Ratings='".$n2."',Nrates='".$n."' Where ID='".$id."' ");


mysqli_close($con);

?>