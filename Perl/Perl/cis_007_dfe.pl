# cis_007_dfe.pl
# D. Fisher Evans
#
# Requires a ./cis_amoebob_name_shot.pl
#
# Hits the nucleous of an amoebob within 25 tries.

use POSIX;

$lowRange = 0; # Possible range for the nucleous
$highRange = 999999;
$student_name = "dfe";

print " +----+--------+---+--------+--------+\n"; # header text
print " | #  | Shot   | R | Low    | High   |\n";
print " +----+--------+---+--------+--------+\n";

for($i = 0;$i < 25;$i++) # Shoot a max of 25 times
{
	$shot = $lowRange + (($highRange - $lowRange)/2); # if put shot in the middle of the range
	$shotR = ceil($shot); # ceil it for int guess
	
	system ("perl cis_amoebob_name_shot.pl $student_name $shotR"); # call script
	
	open(RESP, 'cis_amoebob_result_dfe.txt'); # get result
	@respArray = <RESP>;
	close RESP;
	$resp = $respArray[$#$respArray];
	chomp($resp);
	
	printf " | %2d | %6d | %1s | %6d | %6d |\n", $i + 1, $shotR, $resp, $lowRange, $highRange; # print shot info
	
	if($resp eq "b") # if result is b
	{
		$lowRange = $shot; # bring range up to shot
	}
	elsif($resp eq "B") # if B
	{
		$highRange = $shot; # bring range down to shot
	}
	else
	{
		$end = "Target destroyed.\n"; # else you win the game!
		last;
	}
}
print " +----+--------+---+--------+--------+\n"; # footer text
print " | #  | Shot   | R | Low    | High   |\n";
print " +----+--------+---+--------+--------+\n\n";

print " One shot guess :\n"; # Bonus point time!

$hidden_number = 0;
for ($i=0;$i<length($student_name);$i++) # loop through each char of "code"
{
	$hidden_number += (ord(substr($student_name,$i,1))**2); # add the square of that letter to total
}
$hidden_number = $hidden_number%1000000; # make sure number is between 0 and 999999
printf " ( (%1s^2) + (%1s^2) + (%1s^2) ) % 1000000 = %d\n",
	substr($student_name,0,1), substr($student_name,1,1), substr($student_name,2,1), $hidden_number; # print how/answer