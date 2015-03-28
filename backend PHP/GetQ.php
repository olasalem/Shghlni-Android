<?php
//this file takes the freelancer ID and the qualification position and inserts them in the relation table / made alone to make it flexible to enter as many qualifications 
$id=$_POST['ID'];
$q=$_POST['pos'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");
$result=mysqli_query($con,"UPDATE accounts SET qualifications='".$q."'  Where ID='".$id."'");
$decoded = json_decode($q,true);


for ($i=0 ; $i <count($decoded); $i++) //get the qualifications one by the other 
{
	//insert in the relation table
	$t=$decoded[$i]['q'];
$r= mysqli_query ($con,"INSERT INTO relation ( ID , QID ) VALUES ( '$id' , '$t' )");

//cheking an unprocessed requests
$wv= mysqli_query ($con,"UPDATE requests SET Offer=1 where DesiredQ='".$t."' AND Processed= 0");		

}
mysqli_close($con);

?>