<?php
require "conn.php";
$orderID=$_POST["orderID"];
$email=$_POST["email"];
$price=$_POST["price"];
$orderStatus=$_POST["orderStatus"];
$date=$_POST["date"];
$address=$_POST["address"]; 

if($conn)
{
	$q="insert into orders (OrderID,EmailID,TotalPrice,OrderStatus,Date,Address) values ($orderID,'$email', $price,'$orderStatus','$date','$address')";
	if($conn->query($q) === TRUE)
	{
		echo "Your food will be delivered shortly. Thank you!!";	
	}
	else
	{
		echo "Error: " .$q . "<br>" .$conn->error;
	}

}
$conn->close();
?>