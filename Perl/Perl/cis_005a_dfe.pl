# cis_005a_dfe.pl
# D. Fisher Evans
#
# Figures out which lights are on. User inputs N. N lights.
# N iterations, Person inverts every Nth light.

do
{
	system("cls"); # Clear the screen

	print "Please enter the amount of lights (above 0): "; # Ask for input
	$numLights = <>;
	chomp($numLights); # Chomp the input (remove '\0')
}
while($numLights < 1);

system("cls"); # Clear the screen

print " Light # | On/Off\n"; # Header Text
print "---------+--------\n";

$on = 0; # Lights that are on counter

$nextOn = 1; # Predict next perfect square
$nextSkip = 3; # Amount to jump to next perfect square

for($loop = 1;$loop <= $numLights;$loop++) # loop through all lights
{
	printf " %-7d | ", $loop; # Print index
	if($loop == $nextOn) # If the light index = perfect square
	{
		print "On\n"; # It's on
		$nextOn += $nextSkip; #Jump to th enext perfect square
		$nextSkip += 2; # Incr. jump amount
		$on++;  # Incr amont of lights on
	}
	else
	{
		print "Off\n"; # else it's off
	}
}

### Another, slower way to solve this problem.
#  
#  for($outLoop = 0;$outLoop < $numLights;$outLoop++)
#  {
#  	$light = 1;
#  	$curLight = $outLoop + 1;
#  	
#  	for($inLoop = 2;$inLoop <= $outLoop + 1;$inLoop++)
#  	{
#  		if($curLight % $inLoop == 0) { $light++; }
#  	}
#  	
#  	printf " %-7d | ", $curLight;
#  	if($light % 2 == 0) { print "Off\n"; }
#  	else { print "On\n"; $on++; }
#  }

print "---------+--------\n"; # footer text
print " Light # | On/Off\n\n";
printf "There are %d light(s) on out of %d. (%%%.2f)\n", $on, $numLights, 100 * ($on/$numLights); # Print light stats