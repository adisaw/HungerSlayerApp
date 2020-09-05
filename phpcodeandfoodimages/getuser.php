<?php
require "conn.php";
$email=$_POST["email"];
$pass=$_POST["pass"];

if($conn)
{

	$q="select * from user where EmailID like '$email' and Password like '$pass'";
	$result=mysqli_query($conn, $q);
	if($result)
	{
		while($row=mysqli_fetch_array($result))
		{
			$flag[]=$row;
		}
		print(json_encode($flag));
	}
	
}
mysqli_close($conn);
?>