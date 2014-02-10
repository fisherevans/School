# TO DO
# 
# ROLLING ALGOR
# 
# LOOP MANAGEMENT
# 
# GAME SETUP
# 

open(FILE, ">evans_yahtzee_thoughts.txt");

@rawDice = ( $ARGV[0], $ARGV[1], $ARGV[2], $ARGV[3], $ARGV[4] );
$subScore = $ARGV[5];
$doScore = $ARGV[6];

%scored = {};
for($z = 7;$z <= $#ARGV;$z++)
{
	$scored{$ARGV[$z]} = 1;
}

@dice = sort(@rawDice);

#print "  -> GOT ROLL " . join(" ", @ARGV) . "\n";

%diceCount;
for(my $i = 0;$i <= $#dice;$i++)
{ $diceCount{$dice[$i]}++; }

######################
### SET SCALE HASH ###
######################

$rollScale{"ones"} = 0.8;# - (($subScore/63)*1.3);
$rollScale{"twos"} = 0.9;# - (($subScore/63)*1.3);
$rollScale{"threes"} = 1.2;# - (($subScore/63)*1.3);
$rollScale{"fours"} = 1.3;# - (($subScore/63)*1.3);
$rollScale{"fives"} = 1.4;# - (($subScore/63)*1.3);
$rollScale{"sixes"} = 1.5;# - (($subScore/63)*1.3);
$rollScale{"yahtzee"} = 1.01;
$rollScale{"3_of_a_kind"} = 0.733;# + (($subScore/63)*0.2);
$rollScale{"4_of_a_kind"} = 0.766;# + (($subScore/63)*0.2);
$rollScale{"full_house"} = 0.8;
$rollScale{"small_straight"} = 0.9;
$rollScale{"large_straight"} = 0.933;
$rollScale{"chance"} = 0.6;

while(($key, $value) = each(%scored))
{
	$rollScale{$key} = -1;
}

#######################################
### GET WEIGHTS OF WHAT TO ROLL FOR ###
#######################################

$rollFor{"ones"} = getMultWeight(1)*$rollScale{"ones"};
$rollFor{"twos"} = getMultWeight(2)*$rollScale{"twos"};
$rollFor{"threes"} = getMultWeight(3)*$rollScale{"threes"};
$rollFor{"fours"} = getMultWeight(4)*$rollScale{"fours"};
$rollFor{"fives"} = getMultWeight(5)*$rollScale{"fives"};
$rollFor{"sixes"} = getMultWeight(6)*$rollScale{"sixes"};
@mults = ( $rollFor{"ones"}, $rollFor{"twos"}, $rollFor{"threes"}, $rollFor{"fours"}, $rollFor{"fives"}, $rollFor{"sixes"} );

$rollFor{"yahtzee"} = getYWeight()*$rollScale{"yahtzee"};

$rollFor{"3_of_a_kind"} = get3_of_a_kindWeight()*$rollScale{"3_of_a_kind"};
$rollFor{"4_of_a_kind"} = $rollFor{"3_of_a_kind"}*$rollScale{"4_of_a_kind"};

$rollFor{"full_house"} = getFullWeight(@dice)*$rollScale{"full_house"};

$d1, $d2, $d3, $d4, $d5, $d6;
$set = join(' ', @dice);
if($set =~ /1/) { $d1 = 1; }
if($set =~ /2/) { $d2 = 1; }
if($set =~ /3/) { $d3 = 1; }
if($set =~ /4/) { $d4 = 1; }
if($set =~ /5/) { $d5 = 1; }
if($set =~ /6/) { $d6 = 1; }
$rollFor{"small_straight"} = getSSWeight()*$rollScale{"small_straight"};
$rollFor{"large_straight"} = getSLWeight()*$rollScale{"large_straight"};

$rollFor{"chance"} = getchanceWeight()*$rollScale{"chance"};


#foreach $key (sort (keys(%rollFor))) {  print "{ $key } => $rollFor{$key}\n"; }



###################################################
### GET WEIGHTS OF WHAT TO ROLL FOR ### METHODS ###
###################################################

sub getSSWeight
{
	if($d1 + $d2 + $d3 + $d4 == 4 || $d2 + $d3 + $d4 + $d5 == 4 || $d3 + $d4 + $d5 + $d6 == 4) { return 10; }
	if($d1 + $d2 + $d3 + $d4 == 3 || $d2 + $d3 + $d4 + $d5 == 3 || $d3 + $d4 + $d5 + $d6 == 3) { return 4; }
	if($d1 + $d2 + $d3 + $d4 == 2 || $d2 + $d3 + $d4 + $d5 == 2 || $d3 + $d4 + $d5 + $d6 == 2) { return 0; }
	return 2;
}

sub getSLWeight
{
	if($d1 + $d2 + $d3 + $d4 + $d5 == 5 || $d2 + $d3 + $d4 + $d5 + $d6 == 5) { return 10; }
	if($d1 + $d2 + $d3 + $d4 + $d5 == 4 || $d2 + $d3 + $d4 + $d5 + $d6 == 4) { return 4; }
	if($d1 + $d2 + $d3 + $d4 + $d5 == 3 || $d2 + $d3 + $d4 + $d5 + $d6 == 3) { return 0; }
	return 2;
}

sub getchanceWeight
{
	return int(getSum()/3);
}

sub getFullWeight
{
	if(keys(%diceCount) <= 3)
	{
		my %freqs, $temp;
		while (($key, $value) = each(%diceCount))
		{ $freqs{$value}++; }
		if(exists($freqs{"2"}) && exists($freqs{"3"}))
		{ return 10; }
		else
		{ return 7; }
	}
	else
	{ return 2; }
}

sub getMultWeight
{
	my $freq = 0;
	for(my $i = 0;$i <= $#dice;$i++)
	{ if($dice[$i] == $_[0]) { $freq++; } }
	return $freq*2;
}

sub get3_of_a_kindWeight
{
	my $sum = getSum();
	my %freqs, $temp;
	while (($key, $value) = each(%diceCount))
	{ $freqs{$value}++; }
	if(exists($freqs{3}) || exists($freqs{4}) || exists($freqs{5}))
	{ return int($sum/3); }
	if(exists($freqs{2}))
	{ return int($sum/3)*0.6; }
}

sub get4_of_a_kindWeight
{
	my $sum = getSum();
	my %freqs, $temp;
	while (($key, $value) = each(%diceCount))
	{ $freqs{$value}++; }
	if(exists($freqs{4}) || exists($freqs{5}))
	{ return int($sum/3); }
	if(exists($freqs{3}))
	{ return int($sum/3)*0.6; }
}

sub getYWeight
{
	my $max = 0; my $temp;
	for(my $i = 1;$i <= 6;$i++)
	{
		$temp = getMultWeight($i)*(($i+14)/20);
		if($temp > $max) { $max = $temp; }
	}
	return $max;
}

####################
### GET ROLL FOR ###
####################

$max = -1;
foreach $key (sort (keys(%rollFor)))
{ 
	if(!exists($scored{$key}) && $rollFor{$key} > $max)
	{
		$max = $rollFor{$key};
		$maxName = $key;
	}
}

#print "\n\nGoing to roll for $maxName\n\n";


##########################
### GET DICE KEEP ROLL ###
##########################
if($maxName eq "ones") { $keeping = getMultRoll(1); }
elsif($maxName eq "twos") { $keeping = getMultRoll(2); }
elsif($maxName eq "threes") { $keeping = getMultRoll(3); }
elsif($maxName eq "fours") { $keeping = getMultRoll(4); }
elsif($maxName eq "fives") { $keeping = getMultRoll(5); }
elsif($maxName eq "sixes") { $keeping = getMultRoll(6); }

elsif($maxName eq "chance") { $keeping = getchanceRoll(); }

elsif($maxName eq "full_house") { $keeping = getFullRoll(); }

elsif($maxName eq "small_straight") { $keeping = getSSRoll(); }
elsif($maxName eq "large_straight") { $keeping = getSLRoll(); }

elsif($maxName eq "3_of_a_kind") { $keeping = get3_of_a_kindRoll(); }
elsif($maxName eq "4_of_a_kind") { $keeping = get4_of_a_kindRoll(); }

elsif($maxName eq "yahtzee") { $keeping = getYRoll(); }

if($doScore != 1)
{
print FILE "$keeping $maxName\n";
}




######################################
### GET DICE KEEP ROLL ### METHODS ###
######################################
sub getYRoll
{
	my $i, $result = "", %freqs = {}, $max = 0, $maxName = "";
	
	while (($key, $value) = each(%diceCount))
	{
		if($value > $max)
		{
			$max = $value;
			$maxName = $key;
		}
	}
	
	for($i = 0;$i <= $#rawDice;$i++)
	{
		if($rawDice[$i] eq $maxName)
		{ $result .= "K"; }
		else
		{ $result .= "R"; }
	}
	return $result;
}

sub get3_of_a_kindRoll
{
	my $i, $result = "", %freqs = {}, $max = 0, $maxName = "", $saved = 0;
	
	while (($key, $value) = each(%diceCount))
	{
		if($value > $max)
		{
			$max = $value;
			$maxName = $key;
		}
	}
	
	for($i = 0;$i <= $#rawDice;$i++)
	{
		if($rawDice[$i] eq $maxName && $saved < 3)
		{
			$result .= "K";
			$saved++;
		}
		else
		{
			if($saved >= 3)
			{
				if($rawDice[$i] >= 4)
				{ $result .= "K"; }
				else
				{ $result .= "R"; }
			}
			else
			{ $result .= "R"; }
		}
	}
	return $result;
}

sub get4_of_a_kindRoll
{
	my $i, $result = "", %freqs = {}, $max = 0, $maxName = "", $saved = 0;
	
	while (($key, $value) = each(%diceCount))
	{
		if($value > $max)
		{
			$max = $value;
			$maxName = $key;
		}
	}
	
	for($i = 0;$i <= $#rawDice;$i++)
	{
		if($rawDice[$i] eq $maxName && $saved < 4)
		{
			$result .= "K";
			$saved++;
		}
		else
		{
			if($saved >= 4)
			{
				if($rawDice[$i] >= 5)
				{ $result .= "K"; }
				else
				{ $result .= "R"; }
			}
			else
			{ $result .= "R"; }
		}
	}
	return $result;
}

sub getSLRoll
{
	my $result, $i, $roll;
	if($d1 + $d2 + $d3 + $d4 + $d5 == 5 || $d2 + $d3 + $d4 + $d5 + $d6 == 5)
	{ return "KKKKK"; }
	elsif($d1 + $d2 + $d3 + $d4 + $d5 > $d2 + $d3 + $d4 + $d5 + $d6)
	{ $roll = "6"; }
	else { $roll = "1"; }
	$result = "";
	for($i = 0;$i <= $#rawDice;$i++)
	{
		if(!($roll =~ /$rawDice[$i]/))
		{
			$result .= "K";
			$roll .= $rawDice[$i];
		}
		else
		{ $result .= "R"; }
	}
	return $result;
}

sub getSSRoll
{
	my $result, $i, $roll;
	if($d1 + $d2 + $d3 + $d4 == 4 || $d2 + $d3 + $d4 + $d5 == 4 || $d3 + $d4 + $d5 + $d6 == 4)
	{ return getSLRoll(); }
	elsif($d1 + $d2 + $d3 + $d4 > $d2 + $d3 + $d4 + $d5 && $d1 + $d2 + $d3 + $d4 > $d3 + $d4 + $d5 + $d6)
	{ $roll = "56"; }
	elsif($d2 + $d3 + $d4 + $d5 > $d1 + $d2 + $d3 + $d4 && $d2 + $d3 + $d4 + $d5 > $d3 + $d4 + $d5 + $d6)
	{ $roll = "16"; }
	else { $roll = "12"; }
	$result = "";
	for($i = 0;$i <= $#rawDice;$i++)
	{
		if(!($roll =~ /$rawDice[$i]/))
		{
			$result .= "K";
			$roll .= $rawDice[$i];
		}
		else
		{ $result .= "R"; }
	}
	return $result;
}

sub getMultRoll
{
	my $result = "";
	for(my $i = 0;$i <= $#rawDice;$i++)
	{
		if($rawDice[$i] == $_[0]) { $result .= "K"; }
		else { $result .= "R"; }
	}
	return $result;
}

sub getchanceRoll
{
	my $result = "";
	for(my $i = 0;$i <= $#rawDice;$i++)
	{
		if($rawDice[$i] >= 4) { $result .= "K"; }
		else { $result .= "R"; }
	}
	return $result;
}

sub getFullRoll
{
	if(keys(%diceCount) <= 3)
	{
		my %freqs, $temp, $key, $value, $getRid, $result;
		while (($key, $value) = each(%diceCount))
		{ $freqs{$value}++; }
		if($freqs{"2"} == 2 && $freqs{"1"} == 1)
		{
			while (($key, $value) = each(%diceCount))
			{
				if($value == 1)
				{
					$getRid = $key;
					break;
				}
			}
		}
		elsif($freqs{"3"} == 1 && $freqs{"2"} == 1)
		{ $getRid = "7"; }
		elsif($freqs{"4"} == 1 && $freqs{"1"} == 1)
		{
			while (($key, $value) = each(%diceCount))
			{
				if($value == 4)
				{
					$getRid = $key;
					$result = "";
					for(my $i2 = 0;$i2 <= $#rawDice;$i2++)
					{
						if($getRid =~ /$rawDice[$i2]/)
						{
							$result .= "R";
							$getRid = "7";
						}
						else
						{ $result .= "K"; }
					}
					return $result;
				}
			}
		}
		else
		{ $getRid = "123456"; }
		
		$result = "";
		for(my $i3 = 0;$i3 <= $#rawDice;$i3++)
		{
			if($getRid =~ /$rawDice[$i3]/)
			{ $result .= "R"; }
			else
			{ $result .= "K"; }
		}
		return $result;
	}
	else
	{ return "RRRRR"; }
}



if($doScore == 1)
{
##################
### GET SCORES ###
##################
$rollScores{"ones"} = getMultScore(1);
$rollScores{"twos"} = getMultScore(2);
$rollScores{"threes"} = getMultScore(3);
$rollScores{"fours"} = getMultScore(4);
$rollScores{"fives"} = getMultScore(5);
$rollScores{"sixes"} = getMultScore(6);

$rollScores{"3_of_a_kind"} = get3_of_a_kindScore();
$rollScores{"4_of_a_kind"} = get4_of_a_kindScore();

$rollScores{"full_house"} = getFullScore();

$rollScores{"chance"} = getchanceScore();

$rollScores{"small_straight"} = getSSScore();
$rollScores{"large_straight"} = getSLScore();

$rollScores{"yahtzee"} = getYScore();


#foreach $key (sort (keys(%rollScores))) {  print "SCORE { $key } => $rollScores{$key}\n"; }

}


##############################
### GET SCORES ### METHODS ###
##############################
sub getMultScore
{
	my $sum = 0;
	for(my $i = 0;$i <= $#dice;$i++)
	{
		if($dice[$i] == $_[0])
		{ $sum += $_[0]; }
	}
	return $sum;
}

sub getSSScore
{
	if($d1+$d2+$d3+$d4 == 4 || $d2+$d3+$d4+$d5 == 4 || $d3+$d4+$d5+$d6 == 4) { return 30; }
	else { return 0; }
}

sub getSLScore
{
	if($d1+$d2+$d3+$d4+$d5 == 5 || $d2+$d3+$d4+$d5+$d6 == 5) { return 40; }
	else { return 0; }
}

sub getchanceScore
{
	return getSum();
}

sub getFullScore
{
	my %freqs, $temp;
	while (($key, $value) = each(%diceCount))
	{ $freqs{$value}++; }
	if(exists($freqs{3}) && exists($freqs{2})) { return 25; }
	else { return 0; }
}

sub get3_of_a_kindScore
{
	my $sum = getSum();
	my %freqs, $temp;
	while (($key, $value) = each(%diceCount))
	{ $freqs{$value}++; }
	if(exists($freqs{3}) || exists($freqs{4}) || exists($freqs{5})) { return $sum; }
	else { return 0; }
}

sub get4_of_a_kindScore
{
	my $sum = getSum();
	my %freqs, $temp;
	while (($key, $value) = each(%diceCount))
	{ $freqs{$value}++; }
	if(exists($freqs{4}) || exists($freqs{5})) { return $sum; }
	else { return 0; }
}

sub getYScore
{
	my %freqs, $temp;
	while (($key, $value) = each(%diceCount))
	{ $freqs{$value}++; }
	if(exists($freqs{5})) { return 50; }
	else { return 0; }
}

if($doScore == 1)
{
#######################
### FIND BEST SCORE ###
#######################

$max = -1;
foreach $key (sort (keys(%rollScores)))
{ 
	if(!exists($scored{$key}) && $rollScores{$key}*$rollScale{$key} > $max)
	{
		$max = $rollScores{$key}*$rollScale{$key};
		$maxName = $key;
	}
}

	print FILE "$maxName $rollScores{$maxName}\n";
}


######################
### GLOBAL METHODS ###
######################

sub getSign
{
	if($_[0] == 0) { return 1; }
	return int($_[0]/abs($_[0]));
}	

sub getSum
{
	my $sum = 0;
	foreach my $temp (@dice)
	{ $sum += $temp; }
	return $sum;
}

close(FILE);

