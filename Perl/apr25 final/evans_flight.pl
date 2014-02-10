# D. Fisher Evans
# evans_flight.pl
#   Take an old, and new flight list. It then returns 4 lists:
#     1) Old Flights
#     2) New Flights
#     3) Plane size changes
#     4) Flight Number Changes
# perl evans_flight.pl <oldList> <newList>

system("cls"); # clear the screen

$oldFile = $ARGV[0]; chomp(); # grab and chom input
$newFile = $ARGV[1]; chomp();
if(!(-f $oldFile)) { print " " . $oldFile . " is an invalid file. Quitting..."; exit 0; } # check for real files
if(!(-f $newFile)) { print " " . $newFile . " is an invalid file. Quitting..."; exit 0; }

print "Getting old flights from $oldFile...\n"; # put old file into array
open(OLDIN, $oldFile);
for($i = 0;<OLDIN>;$i++)
{
	if($_ =~ /^[a-zA-Z]/) # make sure it's not a header line
	{
		push(@oldFlights, formatFlightInput($_));
		push(@oldTouched, 0); # create an array checking for flight's that aren't cross checked
	}
}
close OLDIN;

print "Getting new flights from $newFile...\n"; # put new file into array
open(NEWIN, $newFile);
for($i = 0;<NEWIN>;$i++)
{
	if($_ =~ /^[a-zA-Z]/) # make sure it's not a header line
	{ push(@newFlights, formatFlightInput($_)); }
}
close NEWIN;

print "Finding new and modified flights...\n"; # loop through new flights and check against old ones
for($i = 0;$i <= $#newFlights;$i++)
{ crossCheck($newFlights[$i]); } # pass the flight

print "Finding old flights...\n"; # after all cross checking, look for old flights that weren't cross checked
for($i = 0;$i <= $#oldTouched;$i++)
{
	if($oldTouched[$i] == 0) { push(@listOld, $oldFlights[$i]); } # if not touched is still 0, push it to the list
}

printList(@listOld, "Old Flights", 1); # print the results
printList(@listNew, "New Flights", 2);
printList(@listSize, "New Plane Size", 3);
printList(@listNumber, "New Flight Numbers", 4);

sub formatFlightInput # takes string, and formats it for easier comparison (important data in front)
{
	chomp();
	my @temp = split(/ +/, $_[0]); # split it up by white space
	return sprintf("%s,%s,%s,%s,%s,%s", $temp[0], $temp[2], $temp[3], $temp[4], $temp[1], $temp[5]); # reorder and put it the order: car, from, to, time, id, size
}

sub crossCheck # takes a flight in "formatFlightInput" format - uses return to break the checks.
{
	my $flight = $_[0]; # gett he flight
	my $flightMain = $flight; $flightMain =~ s/,[a-zA-Z0-9]+,[a-zA-Z0-9]+$//; # get the portion that makes it unique (car, from, to, time)
	for(my $i = 0;$i <= $#oldFlights;$i++) # loop through old flights
	{
		if($oldFlights[$i] =~ /$flightMain/) # if the main part is in the old flight, it's either the same or modfied
		{
			$oldTouched[$i] = 1; # unique flight is in both, adjust the not touched array for old flights
			if($oldFlights[$i] eq $flight) { return; } # no change to flight
			else
			{
				my $size = $flight; $size =~ s/.*,//; # get the size of the new flight
				if($newFlights[$i] =~ /,$size\z/) { push(@listSize, $flight); return; } # if it matches, has to be a new size
				else { push(@listNumber, $flight); return; } # else the number has changed
			}
		}
	}
	push(@listNew, $flight); # if no matches, it's a new flight
}

sub printList # takes a list, 2nd to last being title, last being list id
{
	print "\n" . $_[$#_-1] . " (List " . $_[$#_] . ")\n"; # print out the list title and id
	pop(@_); pop(@_); # then get rid of those elements
	for($i = 0;$i <= $#_;$i++) # loop through remaining array
	{
		print "  " . $_[$i] . "\n"; # print out the flight (indented)
	}
	print "Total: " . ($#_+1) . "\n"; # after list is printed, print the total
}