<?php
require "conn.php";

if($conn)
{

	#echo "hello";
	$q = "select max(OrderID) from orders";
	$result=mysqli_query($conn, $q);
	if($result)
	{
		while($row=mysqli_fetch_array($result))
		{
			$flag[]=$row;
		}
		print(json_encode($flag));
		
	}
	else
	{
		echo "1";
	}
}
$conn->close();

