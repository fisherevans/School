# cis_009_dfe_non-hash.pl
# D. Fisher Evans
#
# Requires a ./cis_009_dfe_data.txt
#
# Counts the number of times a character
# is in a text file, and then prints
# them to the screen:
# ./cis_009_dfe_output.txt
#
# *Uses simple arrays instead of hash*

system("cls"); # Clear the screen

@charArray = ( # Define the character array, and init the count to 0
		"a",0,"b",0,"c",0,"d",0,"e",0,"f",0,"g",0,"h",0,"i",0,"j",0,"k",0,"l",0,"m",0,"n",0,"o",0,"p",0,"q",0,"r",0,"s",0,"t",0,"u",0,"v",0,
		"w",0,"x",0,"y",0,"z",0,"A",0,"B",0,"C",0,"D",0,"E",0,"F",0,"G",0,"H",0,"I",0,"J",0,"K",0,"L",0,"M",0,"N",0,"O",0,"P",0,"Q",0,"R",0,
		"S",0,"T",0,"U",0,"V",0,"W",0,"X",0,"Y",0,"Z",0,",",0,".",0,"/",0,";",0,"'",0,"[",0,"]",0,"\\",0,"`",0,"1",0,"2",0,"3",0,"4",0,"5",0,
		"6",0,"7",0,"8",0,"9",0,"0",0,"-",0,"=",0,"~",0,"!",0,"@",0,"#",0,"\$",0,"%",0,"^",0,"&",0,"*",0,"(",0,")",0,"_",0,"+",0,"|",0,"}",0,
		"{",0,"\"",0,":",0,"?",0,">",0,"<",0,"\" \"",0,
	);

open (INPUTFILE, 'cis_009_dfe_data.txt'); # open input file 
while (<INPUTFILE>) # loop through each line
{
	for($i = 0;$i < length($_);$i++) # each char in that line
	{
		if(substr($_,$i,1) ne " ") { $tempChar = substr($_,$i,1); } # replace the space char with '" "' for readability
		else { $tempChar = "\" \""; }
		
		if($tempChar ne "\n") # if it's not a new line
		{
			for($t = 0;$t <= $#charArray;$t += 2) # go through the char array - up by two to skip the counts
			{
				if($tempChar eq $charArray[$t]) # find its math and incr the corres. # + total # + end inner loop for speed
				{
					$charArray[$t+1]++;
					$totalCharCount++;
					last;
				}
			}
		}
	}
}
close (INPUTFILE); # close input file

print " USING NON-HASH ARRAYS\n"; # Header Text
print " Analysing the frequency of characters in './cis_009_dfe_data.txt'......\n";
printf " +------+-------+-------+\n";
printf " | Char | #     | %     |\n";
printf " +------+-------+-------+\n";

for($i = 0;$i <= $#charArray;$i += 2) # loop through charArray
{
	if($charArray[$i+1] != 0) # if the count is above 0...
	{
		printf " | %5s| %5d | %5.2f |\n", $charArray[$i], $charArray[$i+1], ($charArray[$i+1]/$totalCharCount)*100; # print out the info
	}
}

printf " +------+-------+-------+\n"; # write footer text
printf " | Char | #     | %     |\n";
printf " +------+-------+-------+\n";
print " There were a total of $totalCharCount characters in './cis_009_dfe_data.txt'.\n";
print " Excluding new lines.\n";