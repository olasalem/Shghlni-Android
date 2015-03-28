<?php
$QID=$_POST['pos'];
$A =$_POST['area'];

$con=mysqli_connect("localhost","g5","abc123","Shghalni");

// Check connection
if (mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// select the database
mysqli_select_db($con,"Shghalni") or die( "Unable to select database");

//get the Ids of the freelancers with this qualification
$result=mysqli_query($con,"select * from  relation where QID= '".$QID."'");

$encodedable=array();

if ($A==null)
{
	while ($obj = mysqli_fetch_assoc($result))
	{
		$x =$obj['ID'];
		$t=mysqli_query($con,"select * from accounts where ID='".$x."' ");
		while($obj1=mysqli_fetch_assoc($t))
		{$encodable[]=$obj1;}
	}
}
elseif ($A==21)
{
	while ($obj = mysqli_fetch_assoc($result))
	{
		$x =$obj['ID'];
		$t=mysqli_query($con,"select * from accounts where ID='".$x."' AND (Area < 21 OR Area = 21) ");
		while($obj2=mysqli_fetch_assoc($t))
		{$encodable[]=$obj2;}
	}
	
}
elseif($A <21)
{
	while ($obj = mysqli_fetch_assoc($result))
	{
		$x =$obj['ID'];
		$t=mysqli_query($con,"select * from accounts where ID='".$x."' AND (Area='".$A."' OR Area =21) ");
		while($obj1=mysqli_fetch_assoc($t))
		{$encodable[]=$obj1;}
	}
}
else 
{
	while ($obj = mysqli_fetch_assoc($result))
	{
		
		$x =$obj['ID'];
		$t=mysqli_query($con,"select * from accounts where ID='".$x."' AND Area='".$A."'");
		while($obj2=mysqli_fetch_assoc($t))
		{$encodable[]=$obj2;}
	}
	

}
echo json_encode($encodable);
mysqli_close($con);
?>