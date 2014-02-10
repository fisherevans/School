%subset = ( "ones" => 1, "twos" => 1, "threes" => 1, "fours" => 1, "fives" => 1, "sixes" );

$n = $ARGV[0];
$maxScore = -1;
$minScore = 1000;
$maxSubScore = -1;
$minSubScore = 1000;
open(STATS, ">stats.csv");
for($g = 1;$g <= $n;$g++)
{
	print "Game $g: ";
	@game = playGame();
	$totalScore += $game[0];
	$totalSubScore += $game[1];
	print "$game[0] ($game[1])\n";
	print STATS "$game[0],";
	
	if($game[0] > $maxScore) { $maxScore = $game[0]; }
	if($game[1] > $maxSubScore) { $maxSubScore = $game[1]; }
	
	if($game[0] < $minScore) { $minScore = $game[0]; }
	if($game[1] < $minSubScore) { $minSubScore = $game[1]; }
}
close(STATS);

$avgScore = $totalScore/$n;
$avgSubScore = $totalSubScore/$n;
print "\nResults for $n games:\n\n";
print  " Sub Score | Min | Avg | Max\n";
print  "-----------+-----+-----+-----\n";
printf " Score     | %-3d | %-3d | %-3d\n", $minScore, $avgScore, $maxScore;
print  "-----------+-----+-----+-----\n";
printf " Sub Score | %-3d | %-3d | %-3d\n", $minSubScore, $avgSubScore, $maxSubScore;

sub playGame
{
	open(LOG, ">log.txt");
	$score = 0;
	$subscore = 0;
	@scored = ();
	for($turn = 1;$turn <= 13;$turn++)
	{
print LOG "Turn $turn:\n";
		@roll = roll("RRRRR");
print LOG "	Rolled -> @roll\n";
		think(join("", @roll) . " $subscore 0 " . join(" ", @scored));
		@thoughts = split(/ +/, getThoughts());
print LOG "		Thought -> @thoughts\n";
		@roll = roll($thoughts[0]);
print LOG "	Rolled -> @roll\n";
		think(join("", @roll) . " $subscore 0 " . join(" ", @scored));
		@thoughts = split(/ +/, getThoughts());
print LOG "		Thought -> @thoughts\n";
		@roll = roll($thoughts[0]);
print LOG "	Rolled -> @roll\n";
		think(join("", @roll) . " $subscore 1 " . join(" ", @scored));
		@thoughts = split(/ +/, getThoughts());
print LOG "	Scored $thoughts[1] for $thoughts[0]\n";
		push(@scored, $thoughts[0]);
		$score += $thoughts[1];
		if(exists($subset{$thoughts[0]}))
		{
			$subscore += $thoughts[1];
print LOG "		Putting $thoughts[1] into Sub Score ($subscore)\n";
		}
	}
	if($subscore >= 63)
	{ $score += 35; }

print LOG "\n\nGame Total: $score\n";
print LOG "Game Sub Total: $subscore\n";

	close(LOG);
	return ($score, $subscore);
}

sub think { system("perl think.pl $_[0]"); }

sub getThoughts
{
	open(FILE, "thought.txt");
	my $last_line;
	while(<FILE>) { $last_line = $_ if eof; }
	chomp($last_line);
	close(FILE);
	return $last_line;
}

sub roll
{
	my $toDo = $_[0];
	@newRoll = ();
	for($i = 0;$i <= 4;$i++)
	{
		if(substr($toDo, $i, 1) eq "K") { push(@newRoll, $roll[$i]); }
		else { push(@newRoll, int(rand(6))+1); }
	}
	return @newRoll;
}
