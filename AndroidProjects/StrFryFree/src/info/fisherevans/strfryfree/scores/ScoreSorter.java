package info.fisherevans.strfryfree.scores;

import info.fisherevans.strfryfree.views.LetterTile;

import java.util.Comparator;

public class ScoreSorter implements Comparator<Score>
{
	@Override
	public int compare(Score score1, Score score2)
	{
		if(score1.getTime() < score2.getTime())
		{
			return -1;
		}
		else if(score1.getTime() < score2.getTime())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}