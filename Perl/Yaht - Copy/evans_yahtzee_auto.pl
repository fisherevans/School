%subset = ( "ones" => 1, "twos" => 1, "threes" => 1, "fours" => 1, "fives" => 1, "sixes" );

for($g = 1;$g <= 5000;$g++)
{
	print "Game $g: ";
	@game = playGame();
	$totalScore += $game[0];
	$totalSubScore += $game[1];
	print "$game[0] ($game[1])\n";
}

$avgScore = $totalScore/5000;
$avgSubScore = $totalSubScore/5000;
print "\nAverge Score for 5000: $avgScore ($avgSubScore)\n";

sub playGame
{
	$score = 0;
	$subscore = 0;
	@scored = ();
	for($turn = 1;$turn <= 13;$turn++)
	{
		@roll = roll("RRRRR");
		think(join(" ", @roll) . " 0 " . join(" ", @scored));
		@thoughts = split(/ +/, getThoughts());
		@roll = roll($thoughts[0]);
		think(join(" ", @roll) . " 0 " . join(" ", @scored));
		@thoughts = split(/ +/, getThoughts());
		@roll = roll($thoughts[0]);
		think(join(" ", @roll) . " 1 " . join(" ", @scored));
		@thoughtsScore = split(/ +/, getThoughts());
		push(@scored, $thoughtsScore[0]);
		$score += $thoughtsScore[1];
		if(exists($subset{$thoughtsScore[0]}))
		{ $subscore += $thoughtsScore[1]; }
	}
	if($subscore >= 63)
	{ $score += 35; }

	return ($score, $subscore);
}

sub think { system("perl evans_yahtzee.pl $_[0]"); }

sub getThoughts
{
	open(FILE, "evans_yahtzee_thoughts.txt");
	my $last_line;
	while(<FILE>) { $last_line = $_ if eof; }
	chomp($last_line);
	return $last_line;
	close(FILE);
}

sub roll
{
	my $toDo = $_[0];
	@newRoll = ();
	for($i = 0;$i <= 4;$i++)
	{
		if(substr($toDo, $i, 1) eq "K")
		{
			push(@newRoll, $roll[$i]);
		}
		else
		{
			push(@newRoll, int(rand(6))+1);
		}
	}
	return @newRoll;
}
