<?php

$num=$_POST['phnum'];
$id=$_POST['id'];

require('twilio-php/Services/Twilio.php'); 
 
$account_sid = 'AC3aec2f7a6bd42ee4ce6520fdc778d517'; 
$auth_token = 'cae118495d43617aef143ba97d65748b'; 
$client = new Services_Twilio($account_sid, $auth_token); 

$t=rand(100000,999999);

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

$r=mysqli_query($con," UPDATE accounts SET Code = '".$t."' Where ID ='".$id."' ");
		 
$client->account->messages->create(array( 
	'To' => $num,
	'From' => "+13213381168",
	 'Body' => $t,    
));

mysqli_close($con);

?>