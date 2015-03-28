<?php
//checks if the request has an offer and returns one values(0 in case the deadline has passed/ 1 if there is no result but there is still time/ 2 if there is an offer 
$rid=$_POST ['RID'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

$r=mysqli_query($con, "select * from requests where RID ='".$rid."'");
$row=mysqli_fetch_assoc($r);


if ( strtotime($row['Deadline']) > time() )
{
	if ($row['Offer'] == "1")   //there is an offer before dealine 
	{
		echo"2";
		
	}
	else
		echo "1";            //no offer & checklater
}
else 
{
     echo "0";                //deadline passed
}


mysqli_close($con);

?>