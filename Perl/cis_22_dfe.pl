$name = "dfe";

newGame();




$name = "dfe";
sub newGame
{
	system ("perl cis_yahtzee.pl $name new_game");
}
sub endGame
{
	system ("perl cis_yahtzee.pl $name end_game");
}
sub rollDice
{
	system ("perl cis__yahtzee.pl $name roll_dice " . $_);
}
sub score
{
	system ("perl cis__yahtzee.pl $name score_into " . $scoreType[$_]);
}