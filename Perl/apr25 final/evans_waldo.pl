# D. Fisher Evans
# evans_waldo.pl
#    Take text file, and finds the word "WALDO" within it and print off all strings
#    see evans_search.txt for example
# perl evans_waldo.pl <filename>

system("clear");

$file = $ARGV[0]; # grab user input
chomp();
if(!(-f $file)) { print " " . $file . " is an invalid file. Quitting..."; exit 0; } # check for real file

@search = ( "W", "A", "L", "D", "O" ); # what to search for
@hex = ( "0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f" ); # int to hex
@shift = ( -17, -16, -15, -1, 1, 15, 16, 17 ); # top left, top, top right, left, right, bot left, bot, bot right from index

system("cls"); # clear screen
print " Using the data from: " . $file . "\n\n"; # return the file we're searching

@matrix = ();

open(IN, $file); # open the input file # open file handle
for($i = 0;<IN>;$i++) # loop through each line
{
	if($_ =~ s/^[0-9a-f]\| //) # if it starts with a row id, remove the row id
	{
		@lineArray = split(/ /); # and split if with a " "'
		for($j = 0;$j <= 15;$j++) # store each value in the matrix array
		{ push(@matrix, substr($lineArray[$j],0,1)); }
	}
}
close IN;

print " Begining branch search...\n\n"; # notify the start of the search

for($i = 0;$i <= $#matrix;$i++) # loop through the matrix
{
	if($matrix[$i] eq $search[0]) # look for W's to start wtih
	{ search($i); } # start branch search from that index
}
print " Found the following strings...\n\n"; # result header
print "  W     A     L     D     O\n";
for($i = 0;$i <= $#finds;$i++) { print $finds[$i] . "\n"; } # loop through strings found and print em' out
print "\n There were " . ($#finds + 1) . " 'Waldo's found in " . $file . ".\n"; # say how many

sub search # branch search take array of leading positions, last value the matrix index of the last letter found
{
	my @path = @_; # grab the passed path
	my $index = $path[$#path]; # get the current inde
	if($#path == $#search) # if the current path and search path are same length, found solution
	{
		my $tempSolution = "";
		foreach $elem (@path) { $tempSolution .= getXY($elem) . " "; }
		push(@finds, $tempSolution);
	}
	else # else look for adjacent letters
	{
		for(my $j = 0;$j <= $#shift;$j++) # loop through the index shift to find adj.
		{
			my $tempIndex = $index + $shift[$j]; # temp var for adj index
			if(inRange($index, $shift[$j]) == 1 && $matrix[$tempIndex] eq $search[$#path+1]) # if it's a valid index, and it's the next needed letter
			{ search(@path, $tempIndex); } # countinue the search branch by adding the new found letter and its index
		}
	}
}

sub getXY # pass int, get string with hex x and y
{
	my $id = $_[0]; # grab id
	my $row = int($id/16); # get the row
	my $col = $id % 16; # and the column
	return "[" . $hex[$row] . "," . $hex[$col] . "]"; # return the formatted string '(x, y)'
}

sub getString # takes array, returns concatenated string
{
	my @temp = @_; # grab array
	my $string = ""; # temp line buffer
	for(my $i = 0;$i <= $#temp;$i++) { $string .= " " . $temp[$i] . " "; } # loop through array, add elem to buffer
	return $string; # returnt he buffer
}

sub inRange # takes id and shift, returns 1 if within 0 and max id of matrix. returns 0 if looping around
{
	my $id = $_[0]; # grab id and shift
	my $shift = $_[1];
	my $col = $id % 16; # get the col
	my $inRange = 1; # range starts at 1
	if($col == 0) { if($shift == -1 || $shift == -17 || $shift == 15) { $inRange *= 0; } } # eep it from wrapping
	if($col == 15) { if($shift == 1 || $shift == 17 || $shift == -15) { $inRange *= 0; } }
	
	if($id+$shift < 0 || $id+$shift > $#matrix) { $inRange *= 0; } # return true if in range
	
	#print "ID: $id ($matrix[$id]) - SHIFT: $shift - COL: $col - RANGE: $inRange\n";
	return $inRange;
}
