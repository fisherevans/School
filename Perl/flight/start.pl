# Fisher Evans - start.pl
#
# simple interface program for flight_paths.pl
#
# perl start.pl

print "Please enter a SOURCE FILE: "; $file = <>; chomp($file); # get the file
while(1) # loop indef
{
	print "Enter '!q' to quit at any time.\n"; # tell how to quit
	print "Please enter a FROM: "; $from = <>; chomp($from); # get from
	if($from eq "!q") { die(); } # if !q quit
	print "Please enter a TO: "; $to = <>; chomp($to); # get to
	if($to eq "!q") { die(); } # if !q quit
	system("perl flight_paths.pl $file $from $to"); # call flight_paths.pl
	print "\n\n---=== New Run ===---\n"; # indicate new run
}