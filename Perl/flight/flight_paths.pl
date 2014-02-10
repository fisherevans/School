# Fisher Evans - flight_paths.pl
#
# take flight list in format
#   "FlightId <whitespace> From <whitespace> To"
# Finds all possible paths from give from to given to.
# One flight path may go through one airport only once or zero times
#
# ALL GOOD PATHS ARE STORED IN @paths - THIS ARRAY IS NEVER USED
#
# perl flight_paths.pl flight_file.txt from to

$file = $ARGV[0]; # grab arguments
$from = $ARGV[1];
$to = $ARGV[2];

open(IN, $file); # open the input file
for($i = 0;<IN>;$i++) # loop through each line
{
	chomp(); # chomp \n
	@tempA = split(/ +/); # split the line with spaces using regex
	push(@flights, $tempA[0] . ":" . $tempA[1] . ":" . $tempA[2]); # replace it with eaiser formattion id:from:to
}

for($i = 0;$i <= $#flights;$i++) # loop through flights
{
	if($flights[$i] =~ /:$from:/) # if the flight is from the supplied from
	{
		@goneOn = ( $flights[$i] ); # create the beginning of the flight list
		track(@goneOn); # begin possibility branch
	}
}

print "\nThere were " . ($#paths+1) . " path(s) for: $from -> $to\n"; # resulting information 

sub track
{
	my @goneOn = @_; # grab flight path - local array because It gets modified
	my $curPath = getString(@goneOn); # get string version of the flight path
	if($curPath =~ /:$to -> $/) # if this branch is at the destination
	{
		push(@paths, $curPath); # add it to paths
		$curPath =~ s/ -> $//; # just for formatting, get rid of the last arrow
		print $curPath . "\n"; # print out the path
	}
	else # else look for more branches
	{
		for(my $i = 0;$i <= $#flights;$i++) # loop through flights
		{
			my @thisArray = split(/:/, $flights[$i]); # grab current flight info
			if($curPath =~ /$thisArray[1] -> $/ && !($curPath =~ /$thisArray[2]/)) # if this flight is from current location && it's not going to a previous location
			{
				my @newGoneOn = @goneOn; # create a new flight path array
				push(@newGoneOn, $flights[$i]); # add this flight to it
				track(@newGoneOn); # start a new possibility branch
			}
		}
	}
}

sub getString # takes an array, makes a string from it "element1 -> element2 -> "
{
	my $tempLine; # create line buffer
	foreach $ele (@_) # loop through array
	{ $tempLine .= $ele . " -> "; } #tag array element to the end of the buffer with a " -> "
	return $tempLine; # return the buffer
}