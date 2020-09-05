<?php
require "conn.php";
$email=$_POST["email"];
$pass=$_POST["pass"];
$name=$_POST["name"];
$mobile=$_POST["mobile"];
$q = "select * from user where EmailID='$email'";
$result = $conn->query($q);
if($result->num_rows > 0) {
	echo "User already exists";
} else {
	$q="insert into user (EmailID, Name, Password,MobileNo) values ('$email','$name','$pass','$mobile')";
	if($conn->query($q) === TRUE)
	{
		echo "User Created";	
	}
	else
	{
		echo "Error: " .$q . "<br>" .$conn->error;
	}
}
$conn->close();
?>