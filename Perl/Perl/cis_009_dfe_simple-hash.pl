# cis_009_dfe.pl
# D. Fisher Evans
#
# Requires a ./cis_009_dfe_data.txt
#
# Counts the number of times a character
# is in a text file, and then prints
# them to the screen:
# ./cis_009_dfe_output.txt
#
# Uses hash arrays
#
# Removed external file writing and super fancy formatting for easy of reading

system("cls"); # Clear the screen

open (INPUTFILE, 'cis_009_dfe_data.txt'); # Open the input file
while (<INPUTFILE>) # loop through each line
{
	for($i = 0;$i < length($_);$i++) # for each char in the line/string
	{
		if(substr($_,$i,1) ne " ") { $tempChar = substr($_,$i,1); } # Repleace the space char with '" "' to make it visible.
		else { $tempChar = "\" \""; }
		if($tempChar ne "\n") # if it's not a new line or a space
		{
			$charCount{$tempChar}++; # incr count for that char
			$totalCharCount++; # incr total count
		}
	} # incr. the number of that char
}
close (INPUTFILE); # close the file

print " USING HASH ARRAYS\n"; # Header Text
print " Analysing the frequency of characters in './cis_009_dfe_data.txt'......\n";
printf " +------+-------+-------+\n";
printf " | Char | #     | %     |\n";
printf " +------+-------+-------+\n";

foreach $charName (sort keys %charCount) # Loop through each hash array element after sorting
{
	printf " | %-4s | %-5d | %-5.2f |\n",$charName,$charCount{$charName},100*($charCount{$charName}/$totalCharCount); # write to screen
}

printf " +------+-------+-------+\n"; # write footer text
printf " | Char | #     | %     |\n";
printf " +------+-------+-------+\n";
print " There were a total of $totalCharCount characters in './cis_009_dfe_data.txt'.\n";
print " Excluding new lines.\n";