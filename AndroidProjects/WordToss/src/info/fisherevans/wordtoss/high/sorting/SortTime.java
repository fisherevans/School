package info.fisherevans.wordtoss.high.sorting;

import info.fisherevans.wordtoss.high.Score;
import info.fisherevans.wordtoss.views.LetterTile;

import java.util.Comparator;

public class SortTime implements Comparator<Score>
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
