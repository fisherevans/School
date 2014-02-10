# cis_012_dfe.pl
# D. Fisher Evans
#
# Displays data hierarchy from a file
# take file as arg
# defaults to cis_009a_dfe.txt

system("cls"); # clear screen

if($ARGV[0] ne "") { $readFileName = $ARGV[0]; } # Grab file to read
else { $readFileName = 'cis_009a_dfe.txt'; } # else default to 9a.txt

print "Please enter your search term: "; #ask for the search term
$search = <>; chomp($search);

open(INPUT, $readFileName); # open file

while(<INPUT>) # loop through file lines and make the hash array
{
	chomp($_); @tempLineArray = split(" : ",$_); # get rid of new line, and split it up
	$hash{$tempLineArray[0]} = $tempLineArray[1]; # first term -> Second
}

$total = 0; # create var to track total subs

if($hash{$search} ne "")
{
	printControl($search,""); # run first itir of sub rout
	print "$search controls a total of $total other position.\n"; # tell total
}
else { print "There is no data for $search.\n"; } # Else say no data

sub printControl
{
	my @subs = split(" ",$hash{$_[0]}); # get controlees
	$total++;
	for(my $loop = 0;$loop <= $#subs;$loop++) # loop through subs
	{
		print $_[1] . $subs[$loop] . "\n"; # print sub with indent
		printControl($subs[$loop],$_[1]."  "); # and call same routine on them
	}
}