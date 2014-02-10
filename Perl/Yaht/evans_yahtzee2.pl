@d = split(//,$ARGV[0]); # raw list of dice - in order
$subScore = $ARGV[1];
$doScore = $ARGV[2];

%s = (); # what I've scored in already;
for($i = 3;$i <= $#ARGV;$i++)
{ $s{$ARGV[$i]} = 1; }

%dC = (); # count for each number die;
for($i = 0;$i <= $#dice;$i++)
{ $dC{$dice[$i]}++; }

%dF = (); # frequency of frequencies (3,3,2,2,2) - ( 2 ->, 3 -> 1) | (1,2,3,4,4) - (1 -> 3, 2 -> 1)
foreach $freq (%dC)
{ $dF{$freq}++; }

@dP = (0,0,0,0,0,0,0); # precence of each die - 1 if true
for($i = 0;$i <= $#dice;$i++)
{ if(exists$dC{$i}) { $dP[$i] = 1; } }

open(OUT, ">evans_yahtzee_thought.txt");

if($doScore == 1)
{
	%scores = ();
	$scores{"ones"} = coreMultiples(1); ### MULTIPLE's
	$scores{"twos"} = coreMultiples(2);
	$scores{"threes"} = coreMultiples(3);
	$scores{"fours"} = coreMultiples(4);
	$scores{"fives"} = coreMultiples(5);
	$scores{"sixes"} = coreMultiples(6);
	$scores{"3oak"} = score3OAK(); ### OAK's
	$scores{"4oak"} = score4OAK();
	$scores{"small"} = scoreSmall(); ### STRAIGHT's
	$scores{"large"} = scoreLarge();
	$scores{"full"} = scoreFull(); ### UNIQUE's
	$scores{"chance"} = scoreChance();
	$scores{"yahtzee"} = scoreYahtzee();
	
	foreach $scoreType (keys($scores))
	{ if($scores{$scoreType} > $scores{$max} && !exists($s{$scoreType})) { $max = $scoreType; } }
}
else
{

}

### GET SCORE METHODS ############################################################################################################

sub scoreMultiples
{
	my $sum = 0; # loop through dice, if equal to passed var, add that to sum. return sum
	for(my $i = 0;$i <= $#dice;$i++) { if($dice[$i] == $_[0]) { $sum += $_[0]; } }
	return $sum;
}

sub scoreSmall
{ # if set of conseq 4 die presence, if so 30 points to griffendor
	if($dP[1]+$dP[2]+$dP[3]+$dP[4] == 4 || $dP[2]+$dP[3]+$dP[4]+$dP[5] == 4 || $dP[3]+$dP[4]+$dP[5]+$dP[6] == 4) { return 30; }
	else { return 0; }
}

sub scoreLarge
{ # if set of conseq 5 die presence, if so 40 points to griffendor
	if($dP[1]+$dP[2]+$dP[3]+$dP[4]+$dP[5] == 5 || $dP[2]+$dP[3]+$dP[4]+$dP[5]+$dP[6] == 5) { return 40; }
	else { return 0; }
}

sub scoreChance
{ # just the sum
	return getSum();
}

sub scoreFull
{ # if there is a set fo 3 and 2 - return score
	if(exists($dF{3}) && exists($dF{2})) { return 25; }
	else { return 0; }
}

sub score3OAK
{ # if there are 3 or more of one, return sum
	if(exists($dF{3}) || exists($dF{4}) || exists($dF{5})) { return getSum(); }
	else { return 0; }
}

sub score4OAK
{ # if there are 4 or more of one, return sum
	if(exists($dF{4}) || exists($dF{5})) { return getSum(); }
	else { return 0; }
}

sub scoreYahtzee
{ # if there are 5 of one, return 50
	if(exists($dF{5})) { return 50; }
	else { return 0; }
}

### GLOBAL FUNCTIONS ###
sub getSum
{
	my $sum; # add up each dice and return sum
	for(my $i = 0;$i <= $#dice;$i++) { $sum += $dice[$i]; }
	return $sum;
}