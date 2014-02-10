for($i = 0;$i < 20000;$i++)
{
	$A[$i] = int(rand(998));
}

for($i = 0;$i <= $#A;$i++)
{
	for($j = 0;$j < 25;$j++)
	{
		printf "%-4d",$A[$i];
		$i++;
	}
	print "\n";
}

for($i = 0;$i < $#A;$i++)
{
	for($j = $i+1;$j <= $#A;$j++)
	{
		if($A[$j] < $A[$i])
		{
			@A[$j, $i] = @A[$i, $j];
		}
	}
}

for($i = 0;$i <= $#A;$i++)
{
	for($j = 0;$j < 25;$j++)
	{
		printf "%-4d",$A[$i];
		$i++;
	}
	print "\n";
}