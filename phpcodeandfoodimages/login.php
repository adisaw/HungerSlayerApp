<?php
require "conn.php";
$email=$_POST["email"];
$pass=$_POST["pass"];

if($conn)
{

	$q="select * from user where EmailID like '$email' and Password like '$pass'";
	$result=mysqli_query($conn, $q);
	if(mysqli_num_rows($result)>0)
	{
		echo "login successful...!";
	}
	else
	{
		echo "User does not exist.";
	}
}
else
{
	echo "Not Connected.....!";
}
?>