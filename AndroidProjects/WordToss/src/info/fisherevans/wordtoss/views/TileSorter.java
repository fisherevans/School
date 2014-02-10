package info.fisherevans.wordtoss.views;

import java.util.Comparator;

public class TileSorter implements Comparator<LetterTile>
{
	@Override
	public int compare(LetterTile tile1, LetterTile tile2)
	{
		if(tile1.getX() < tile2.getX())
		{
			return -1;
		}
		else if(tile1.getX() < tile2.getX())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
