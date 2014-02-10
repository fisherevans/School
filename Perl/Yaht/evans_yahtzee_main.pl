%subset = ( "ones" => 1, "twos" => 1, "threes" => 1, "fours" => 1, "fives" => 1, "sixes" );


open(LOG, ">log.txt");

print "Playing now... Please wait...\n";
@scored = ();
for($turn = 1;$turn <= 13;$turn++)
{
	print LOG "Turn $turn\n";
	
	@roll = roll("RRRRR");
	print LOG "   Roll 1: " . join(" ", @roll) . "\n";
	think(join(" ", @roll) . " 0 " . join(" ", @scored));
	@thoughts = split(/ +/, getThoughts());
	print LOG "      Thinking 1: " . $thoughts[0] . " - going for " . $thoughts[1] . "\n";
	@roll = roll($thoughts[0]);
	print LOG "   Roll 2: " . join(" ", @roll) . "\n";
	think(join(" ", @roll) . " 0 " . join(" ", @scored));
	@thoughts = split(/ +/, getThoughts());
	print LOG "      Thinking 2: " . $thoughts[0] . " - going for " . $thoughts[1] . "\n";
	@roll = roll($thoughts[0]);
	print LOG "   Roll 3: " . join(" ", @roll) . "\n";
	think(join(" ", @roll) . " 1 " . join(" ", @scored));
	@thoughtsScore = split(/ +/, getThoughts());
	print LOG "      Scoring into: " . $thoughtsScore[0] . " for " . $thoughtsScore[1] . "\n";
	
	push(@scored, $thoughtsScore[0]);
	
	$score += $thoughtsScore[1];
	
	if(exists($subset{$thoughtsScore[0]}))
	{
		$subscore += $thoughtsScore[1];
		print LOG "         Added $thoughtsScore[1] to the Sub Score. ($subscore)\n";
	}
}
if($subscore >= 63)
{
	print LOG "Got 63 or higher in your subscore, adding 35 points. ($subscore)\n";
	$score += 35;
}
print LOG "Final Score: $score";
print "Got the score: $score ($subscore)\n";

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
