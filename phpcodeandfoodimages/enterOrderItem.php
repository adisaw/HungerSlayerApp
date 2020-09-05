<?php
require "conn.php";
$orderID=$_POST["orderID"];
$ID=$_POST["ID"];
$Quantity=$_POST["Quantity"];

if($conn)
{
	$q="insert into orderitem (OrderID,ID,Quantity) values ($orderID,$ID,$Quantity)";
	if($conn->query($q) === TRUE)
	{
		#echo "Your food will be delivered shortly. Thank you!!";	
	}
	else
	{
		echo "Error: " .$q . "<br>" .$conn->error;
	}

}
$conn->close();
?>