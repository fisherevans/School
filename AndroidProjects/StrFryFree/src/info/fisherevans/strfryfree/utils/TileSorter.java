package info.fisherevans.strfryfree.utils;

import info.fisherevans.strfryfree.views.LetterTile;

import java.util.Comparator;

public class TileSorter implements Comparator<LetterTile>
{
	@Override
	public int compare(LetterTile tile1, LetterTile tile2)
	{
		if(tile1.getLeft() < tile2.getLeft())
		{
			return -1;
		}
		else if(tile1.getLeft() < tile2.getLeft())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
