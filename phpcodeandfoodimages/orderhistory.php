<?php
require "conn.php";
if($conn)
{
	$q="select u.emailID as emailID,temp.OrderID as OrderID, temp.TotalPrice as TotalPrice, temp.OrderStatus as OrderStatus, temp.Date as Date, men.Name as Name,
	item.Quantity as Quantity from orders as temp cross join orderitem as item cross 
	join menuitem as men cross join user as u where temp.OrderID=item.OrderID and item.ID=men.ID and temp.emailID=u.emailID order by orderID desc";
	
	$result=mysqli_query($conn,$q);
	if($result)
	{
		while($row=mysqli_fetch_array($result))
		{
			$flag[]=$row;
			
		}
		print(json_encode($flag));
		echo "$flag";
	}
	else
	{
		echo "No Record";
	}
}
mysqli_close($conn);
?>