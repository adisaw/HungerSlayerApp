<?php
require "conn.php";
if($conn)
{
	$q="SELECT * FROM menuitem";
	$result=mysqli_query($conn,$q);
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