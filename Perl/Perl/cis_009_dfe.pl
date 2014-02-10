# cis_009_dfe.pl
# D. Fisher Evans
#
# Requires a ./cis_009_dfe_data.txt
#
# Counts the number of times a character
# is in a text file, and then prints
# them to the screen, and to an ouput file:
# ./cis_009_dfe_output.txt
#
# uses hash arrays

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

printf "USING HASH ARRAYS\n"; # Header Text
print " Analysing the frequency of characters in './cis_009_dfe_data.txt'......\n";
printf " +------+-------+-------+ +------+-------+-------+ +------+-------+-------+\n";
printf " | Char | #     | %     | | Char | #     | %     | | Char | #     | %     |\n";
printf " +------+-------+-------+ +------+-------+-------+ +------+-------+-------+\n";

open (OUTPUTFILE, '>cis_009_dfe_output.txt'); # Open the output file and CLEAR ITS CONTENTS
print OUTPUTFILE "The most recent data collection results for './cis_009_dfe_data.txt' which was parsed by './cis_009_dfe.pl'.\n\n";
print OUTPUTFILE "Char | #     | %     \n";
print OUTPUTFILE "-----+-------+-------\n";
foreach $charName (sort keys %charCount) # Loop through each hash array element after sorting
{
	$newLineCounter++; # formatting counter
	printf " | %-4s | %-5d | %-5.2f |",$charName,$charCount{$charName},100*($charCount{$charName}/$totalCharCount); # write to screen
	printf OUTPUTFILE "%-4s | %-5d | %-5.2f\n",$charName,$charCount{$charName},100*($charCount{$charName}/$totalCharCount); # write to output file
	if($newLineCounter % 3 == 0) { print "\n"; } # formatting - new line
}
print OUTPUTFILE "-----+-------+-------\n"; # print footer text for output file before closing
print OUTPUTFILE "Char | #     | %     \n\n";
print OUTPUTFILE "There were a total of $totalCharCount characters in 'cis_009_dfe_data.txt'. Excluding new lines.\n";
close (OUTPUTFILE); # close the output file

while($newLineCounter % 3 != 0) # some final work for screen formatting. print empty columns 
{
	print " |      |       |       |"; # Emtpty column
	$newLineCounter++;
	if($newLineCounter % 3 == 0) { print "\n"; } # at the end, new line.
}

printf " +------+-------+-------+ +------+-------+-------+ +------+-------+-------+\n"; # write footer text
printf " | Char | #     | %     | | Char | #     | %     | | Char | #     | %     |\n";
printf " +------+-------+-------+ +------+-------+-------+ +------+-------+-------+\n";
print " There were a total of $totalCharCount characters in './cis_009_dfe_data.txt'.\n";
print " Excluding new lines.\n";
print " This info can also be found in the file './cis_009_dfe_output.txt'.\n";