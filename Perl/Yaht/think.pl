@dice = split(//,$ARGV[0]); # raw list of dice - in order
@diceSort = sort(@dice);
$subScore = $ARGV[1];
$doScore = $ARGV[2];

%scored = (); # what I've scored in already;
for($i = 3;$i <= $#ARGV;$i++)
{ $scored{$ARGV[$i]} = 1; }

%diceCount = (); # count for each number die;
for($i = 0;$i <= $#dice;$i++)
{ $diceCount{$dice[$i]}++; }

%diceFreq = (); # frequency of frequencies (3,3,2,2,2) - ( 2 ->1, 3 -> 1) | (1,2,3,4,4) - (1 -> 3, 2 -> 1)
foreach $freq (values %diceCount)
{ $diceFreq{$freq}++; }

@dicePres = (0,0,0,0,0,0,0); # precence of each die - 1 if true
for($i = 0;$i <= $#dice;$i++)
{ if(exists$diceCount{$i}) { $dicePres[$i] = 1; } }

$diceSum = getSum();

#print "Dice Count:\n"; printHash(*diceCount);
#print "Dice Freqs:\n"; printHash(*diceFreq);

$small1 = $dicePres[1]+$dicePres[2]+$dicePres[3]+$dicePres[4];
$small2 = $dicePres[2]+$dicePres[3]+$dicePres[4]+$dicePres[5];
$small3 = $dicePres[3]+$dicePres[4]+$dicePres[5]+$dicePres[6];

$large1 = $dicePres[1]+$dicePres[2]+$dicePres[3]+$dicePres[4]+$dicePres[5];
$large2 = $dicePres[2]+$dicePres[3]+$dicePres[4]+$dicePres[5]+$dicePres[6];

%scoreScale = ();
$scoreScale{"ones"} = 0.8; ### MULTIPLE's
$scoreScale{"twos"} = 0.9;
$scoreScale{"threes"} = 1.1;
$scoreScale{"fours"} = 1.2;
$scoreScale{"fives"} = 1.25;
$scoreScale{"sixes"} = 1.3;
#$scoreScale{"ones"} = 1.3; ### MULTIPLE's
#$scoreScale{"twos"} = 1.2;
#$scoreScale{"threes"} = 1.1;
#$scoreScale{"fours"} = 1.05;
#$scoreScale{"fives"} = 1;
#$scoreScale{"sixes"} = 0.95;
$scoreScale{"3oak"} = 0.733; ### OAK's
$scoreScale{"4oak"} = 0.766;
$scoreScale{"small"} = 0.9; ### STRAIGHT's
$scoreScale{"large"} = 0.93;
$scoreScale{"full"} = 0.8; ### UNIQUE's
$scoreScale{"chance"} = 0.3;
$scoreScale{"yahtzee"} = 1.01;

%maxScores = ();
$maxScores{"ones"} = 5; ### MULTIPLE's
$maxScores{"twos"} = 10;
$maxScores{"threes"} = 15;
$maxScores{"fours"} = 20;
$maxScores{"fives"} = 25;
$maxScores{"sixes"} = 30;
$maxScores{"3oak"} = 30; ### OAK's
$maxScores{"4oak"} = 30;
$maxScores{"small"} = 30; ### STRAIGHT's
$maxScores{"large"} = 40;
$maxScores{"full"} = 25; ### UNIQUE's
$maxScores{"chance"} = 30;
$maxScores{"yahtzee"} = 40; # lower to give it presedence


open(OUT, ">thought.txt");

if($doScore == 1)
{
	%scores = ();
	$scores{"ones"} = scoreMultiples(1); ### MULTIPLE's
	$scores{"twos"} = scoreMultiples(2);
	$scores{"threes"} = scoreMultiples(3);
	$scores{"fours"} = scoreMultiples(4);
	$scores{"fives"} = scoreMultiples(5);
	$scores{"sixes"} = scoreMultiples(6);
	$scores{"3oak"} = score3OAK(); ### OAK's
	$scores{"4oak"} = score4OAK();
	$scores{"small"} = scoreSmall(); ### STRAIGHT's
	$scores{"large"} = scoreLarge();
	$scores{"full"} = scoreFull(); ### UNIQUE's
	$scores{"chance"} = scoreChance();
	$scores{"yahtzee"} = scoreYahtzee();
	
	
	$max = ""; $maxScore = -1;
	foreach $key (keys(%scores))
	{
		#printf "$key (($scores{$key}/$maxScores{$key})*$scoreScale{$key} = " . ($scores{$key}/$maxScores{$key})*$scoreScale{$key} . "\n";
		if(($scores{$key}/$maxScores{$key})*$scoreScale{$key} > $maxScore && !exists($scored{$key}))
		{ $max = $key; $maxScore = ($scores{$key}/$maxScores{$key})*$scoreScale{$key}; }
	}
	#print "\n\nTurn: " . (keys(%scored)+1) . "\n"; printHash(*scores);
	
	#print "Choose : " . $max . " " . $scores{$max};
	print OUT $max . " " . $scores{$max};
}
else
{
	%weights = ();
	$weights{"ones"} = weighMultiples(1)*$scoreScale{"ones"}; ### MULTIPLE's
	$weights{"twos"} = weighMultiples(2)*$scoreScale{"twos"};
	$weights{"threes"} = weighMultiples(3)*$scoreScale{"threes"};
	$weights{"fours"} = weighMultiples(4)*$scoreScale{"fours"};
	$weights{"fives"} = weighMultiples(5)*$scoreScale{"fives"};
	$weights{"sixes"} = weighMultiples(6)*$scoreScale{"sixes"};
	$weights{"3oak"} = weigh3OAK()*$scoreScale{"3oak"}; ### OAK's
	$weights{"4oak"} = weigh4OAK()*$scoreScale{"4oak"};
	$weights{"small"} = weighSmall()*$scoreScale{"small"}; ### STRAIGHT's
	$weights{"large"} = weighLarge()*$scoreScale{"large"};
	$weights{"full"} = weighFull()*$scoreScale{"full"}; ### UNIQUE's
	$weights{"chance"} = weighChance()*$scoreScale{"chance"};
	$weights{"yahtzee"} = weighYahtzee()*$scoreScale{"yahtzee"};
	
	printHash(*weights);
	
	$maxScore = -1, $max = "";
	while(($key, $value) = each(%weights))
	{ if($value > $maxScore && !exists($scored{$key})) { $max = $key; $maxScore = $value; } }
	
	$result = "";
	if($max eq "ones") { rollMultiples(1); }
	elsif($max eq "twos") { rollMultiples(2); }
	elsif($max eq "threes") { rollMultiples(3); }
	elsif($max eq "fours") { rollMultiples(4); }
	elsif($max eq "fives") { rollMultiples(5); }
	elsif($max eq "sixes") { rollMultiples(6); }
	elsif($max eq "4oak") { rollOAK(3); }
	elsif($max eq "3oak") { rollOAK(4); }
	elsif($max eq "small") { rollSmall(); }
	elsif($max eq "large") { rollLarge(); }
	elsif($max eq "full") { rollFull(); }
	elsif($max eq "chance") { rollChance(); }
	elsif($max eq "yahtzee") { rollYahtzee(); }
	else { $result = "RRRRR"; }
	
	print $result . " " . $max;
	print OUT $result . " " . $max;
}

close(OUT);

### GET ROLL FOR METHODS #########################################################################################################

sub rollMultiples
{
	for($i = 0;$i <= $#dice;$i++)
	{ if($dice[$i] == $_[0]) { $result .= "K"; } else { $result .= "R"; } }
}

sub rollOAK
{
	my $max, $maxCount = -1, $used = 0, $key;
	foreach $key (keys(%diceCount)) { if($diceCount{$key}+($key/10) > $maxCount) { $max = $key; $maxCount = $diceCount{$key}+($key/10); } }
	for($i = 0;$i <= $#dice;$i++)
	{
		if($dice[$i] == $max && $used <= $_[0]) { $result .= "K"; $used++; }
		else
		{
			if($used < $_[0]) { $result .= "R"; }
			elsif($dice[$i] > 3) { $result .= "K"; }
			else { $result .= "R"; }
		}
	}
}

sub rollSmall
{
	my $dontRoll= "";
	if($large1 == 5 || $large2 == 5) { $result = "KKKKK"; return; }
	if($small1 == 4 || $small2 == 4 || $small3 == 4) { rollLarge(); return; }
	elsif($small2 >= $small1 && $small2 >= $small3) { $dontRoll = "16"; }
	elsif($small1 >= $small2 && $small1 >= $small3) { $dontRoll = "56"; }
	else { $dontRoll = "12"; }
	for($i = 0;$i <= $#dice;$i++)
	{
		if($dontRoll =~ /$dice[$i]/) { $result .= "R"; }
		else { $result .= "K"; $dontRoll .= $dice[$i]; }
	}
}

sub rollLarge
{
	my $dontRoll= "";
	if($large1 == 5 || $large2 == 5) { $result = "KKKKK"; return; }
	if($large1 > $large2) { $dontRoll = "6"; }
	else { $dontRoll = "1"; }
	for($i = 0;$i <= $#dice;$i++)
	{
		if($dontRoll =~ /$dice[$i]/) { $result .= "R"; }
		else { $result .= "K"; $dontRoll .= $dice[$i]; }
	}
}

sub rollFull
{
	if($diceFreq{"3"} == 1 && $diceFreq{"2"} == 1) { $result = "KKKKK"; return; }
	elsif($diceFreq{2} == 2)
	{
		for($i = 0;$i <= $#dice;$i++)
		{
			if($diceCount{$dice[$i]} != 2) { $result .= "R"; }
			else { $result .= "K"; }
		}
		return;
	}
	elsif($diceFreq{4} == 1)
	{
		$stop = 0;
		for($i = 0;$i <= $#dice;$i++)
		{
			if($diceCount{$dice[$i]} == 4 && $stop < 3) { $result .= "K"; $stop++; }
			elsif($diceCount{$dice[$i]} == 1) { $result .= "K"; }
			else { $result .= "R"; }
		}
		return;
	}
	elsif($diceFreq{3} == 1 && $diceFreq{1} == 2)
	{
		$stop = 0;
		for($i = 0;$i <= $#dice;$i++)
		{
			if($diceCount{$dice[$i]} == 3) { $result .= "K"; }
			elsif($diceCount{$dice[$i]} == 1 && $stop == 0) { $result .= "K"; $stop++; }
			else { $result .= "R"; }
		}
		return;
	}
	else { $result = "RRRRR"; }
}

sub rollChance
{
	for($i = 0;$i <= $#dice;$i++)
	{
		if($dice[$i] > 3) { $result .= "K"; }
		else { $result .="R"; }
	}
}

sub rollYahtzee
{
	my $max;
	foreach my $key (keys(%diceCount)) { if($diceCount{$key} > $diceCount{$max}) { $max = $key; } }
	for($i = 0;$i <= $#dice;$i++)
	{
		if($dice[$i] == $max) { $result .= "K"; }
		else { $result .= "R"; }
	}
}

### GET WEIGHT METHODS ###########################################################################################################

sub weighMultiples
{
	return $diceCount{$_[0]};
}

sub weigh3OAK
{
	if(exists($diceFreq{3}) || exists($diceFreq{4}) || exists($diceFreq{5})) { return int($diceSum/3); }
	if(exists($diceFreq{2})) { return int($diceSum/3)*0.6; }
	else { return 0; }
}

sub weigh4OAK
{
	if(exists($diceFreq{4}) || exists($diceFreq{5})) { return int($diceSum/3); }
	if(exists($diceFreq{3})) { return int($diceSum/3)*0.6; }
	else { return 0; }
}

sub weighSmall
{
	if($small1 == 4 || $small2 == 4 || $small3 == 4) { return 10; }
	elsif($small1 == 3 || $small2 == 3 || $small3 == 3) { return 4; }
	elsif($small1 == 2 || $small2 == 2 || $small3 == 2) { return 0; }
	else { return 2; }
}

sub weighLarge
{
	if($large1 == 5 || $large2 == 5) { return 10; }
	elsif($large1 == 4 || $large2 == 4) { return 4; }
	elsif($large1 == 3 || $large2 == 3) { return 0; }
	else { return 2; }
}

sub weighFull
{
	if(keys(%diceCount) <= 3)
	{
		if(exists($diceFreq{"2"}) & exists($diceFreq{"3"})) { return 10; }
		else { return 7; }
	}
	else { return 2; }
}

sub weighChance
{
	return int($diceSum/3);
}

sub weighYahtzee
{
	my $max = 0; my $temp;
	for(my $i = 1;$i <= 6;$i++)
	{
		$temp = weighMultiples($i)*(($i+14)/20);
		if($temp > $max) { $max = $temp; }
	}
	return $max;
}

### GET SCORE METHODS ############################################################################################################

sub scoreMultiples
{
	my $diceSum = 0; # loop through dice, if equal to passed var, add that to sum. return sum
	for(my $i = 0;$i <= $#dice;$i++) { if($dice[$i] == $_[0]) { $diceSum += $_[0]; } }
	return $diceSum;
}

sub scoreSmall
{ # if set of conseq 4 die presence, if so 30 points to griffendor
	if($small1 == 4 || $small2 == 4 || $small3 == 4) { return 30; }
	else { return 0; }
}

sub scoreLarge
{ # if set of conseq 5 die presence, if so 40 points to griffendor
	if($large1 == 5 || $large2 == 5) { return 40; }
	else { return 0; }
}

sub scoreChance
{ # just the sum
	return $diceSum;
}

sub scoreFull
{ # if there is a set fo 3 and 2 - return score
	if(exists($diceFreq{"3"}) && exists($diceFreq{"2"})) { return 25; }
	else { return 0; }
}

sub score3OAK
{ # if there are 3 or more of one, return sum
	if(exists($diceFreq{"3"}) || exists($diceFreq{"4"}) || exists($diceFreq{"5"})) { return $diceSum; }
	else { return 0; }
}

sub score4OAK
{ # if there are 4 or more of one, return sum
	if(exists($diceFreq{"4"}) || exists($diceFreq{"5"})) { return getSum(); }
	else { return 0; }
}

sub scoreYahtzee
{ # if there are 5 of one, return 50
	if(exists($diceFreq{"5"})) { return 50; }
	else { return 0; }
}

### GLOBAL FUNCTIONS ###
sub getSum
{
	my $diceSum; # add up each dice and return sum
	for(my $i = 0;$i <= $#dice;$i++) { $diceSum += $dice[$i]; }
	return $diceSum;
}

sub printHash
{
	local (*hash) = @_;
	foreach $key (sort keys %hash)
	{ print "\"$key\" => $hash{$key}\n"; }
}

sub printLogHash
{
	local (*hash) = @_;
	foreach $key (sort keys %hash)
	{ print LOG "\"$key\" => $hash{$key}\n"; }
}