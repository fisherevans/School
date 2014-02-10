# cis_008_dfe.pl
# D. Fisher Evans
#
# Converts a database of cars, their odeometer readings and their estimated values from Km to Mi.

use POSIX;

if($ARGV[0] == "") # grab possible argument for db file
{
	$readFileName = 'cis_008_dfe_data.txt';
} else {
	$readFileName = $ARGV[0];
}

system("cls"); # clear screen

$writeFileName = '>cis_008_dfe_output.txt'; # file name for output

$saved = 0; # total
@outputLines;
open(INPUT, $readFileName); # open files
open(OUTPUT, $writeFileName);

print " +-----------+-----------+------+---------+----------+---------+---------+---------+\n"; # header texts
print " | Brand     | Make      | Year | Ode(mi) | New \$    | Ode(km) | Old \$   | Saved   |\n";
print " +-----------+-----------+------+---------+----------+---------+---------+---------+\n";

print OUTPUT " +-----------+-----------+------+---------+----------+---------+---------+---------+\n";
print OUTPUT " | Brand     | Make      | Year | Ode(mi) | New \$    | Ode(km) | Old \$   | Saved   |\n";
print OUTPUT " +-----------+-----------+------+---------+----------+---------+---------+---------+\n";

for($i = 0;<INPUT>;$i++) # loop through each line
{
	if($i > 1) # only work with lines after the first two
	{
		@tempLineArray = split(/ +/); # split the line with spaces using regex
		
		$tempLineArray[6] = $tempLineArray[4]; # save old info
		$tempLineArray[7] = $tempLineArray[5];
		
		$tempLineArray[4] = $tempLineArray[4]*0.62; # update milage and price
		$tempLineArray[5] = 20000 - 2000*(2012-$tempLineArray[3]) - 1000*int($tempLineArray[4]/20000);
		
		printf " | %-9s | %-9s | %-4d | %-7d | %-7d  | %-7d | %-7d | %-7d |\n", # print info for that line
				$tempLineArray[1], $tempLineArray[2], $tempLineArray[3], $tempLineArray[4],
				$tempLineArray[5], $tempLineArray[6], $tempLineArray[7], $tempLineArray[5]-$tempLineArray[7];
		printf OUTPUT " | %-9s | %-9s | %-4d | %-7d | %-7d  | %-7d | %-7d | %-7d |\n",
				$tempLineArray[1], $tempLineArray[2], $tempLineArray[3], $tempLineArray[4],
				$tempLineArray[5], $tempLineArray[6], $tempLineArray[7], $tempLineArray[5]-$tempLineArray[7];
				
		$saved += $tempLineArray[5]-$tempLineArray[7]; # incr saved amount
	}
}

print " +-----------+-----------+------+---------+---------+---------+---------+---------+\n"; # footer texts
print "\n You saved a total of \$$saved.\n\n";

print OUTPUT " +-----------+-----------+------+---------+----------+---------+---------+---------+\n";
print OUTPUT "\n You saved a total of \$$saved.\n\n";

close (OUTPUT);
close (INPUT);

print " This info can be found in './cis_008_dfe_output.txt'.\n\n";