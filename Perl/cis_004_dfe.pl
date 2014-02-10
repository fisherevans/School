# cis_004_dfe.pl
# D. Fisher Evans
#
# Displays the Fibonacci seq.m the ratio and index
#
# cis_004_dfe.pl <max number>
#
# max number : Highest number to check in the seq. (2 - 92)

system("cls"); # Clears the screen

$max = $ARGV[0]; # Grab the first argument

if(($max == "") || ($max > 92) || ($max < 2)) # If there is no argument supplied
{
	print "Please use the syntax:\n\n"; # Tell them how to use the program
	print "cis_004_dfe.pl <Max number>\n\n";
	print "Max number must be within 2 and 92.\n";
}
else # If there is an argument given
{
	print "+-------+---------------------+-------------------+\n";
	print "| Index | Fibonacci Number    | Ratio             |\n"; ## Header text
	print "+-------+---------------------+-------------------+\n";
	
	$f_first = 0; # Auto fill first 2 numbers
	$f_second = 1;
	
	for($loop = 2;$loop <= $max;$loop++) # start loop from 3 and increment by 1
	{
		$f_third = $f_first + $f_second; # Find new mnumber
		$f_ratio = $f_third / $f_second; # Find ration
		$f_first = $f_second; # Push the numbers back
		$f_second = $f_third;
	
		printf "| %-5d | %-19d | %-1.15f |\n", $loop, $f_third, $f_ratio; #Print results
		print "+-------+---------------------+-------------------+\n";
	}
	
	print "| Index | Fibonacci Number    | Ratio             |\n"; ## Footer text
	print "+-------+---------------------+-------------------+\n";
}