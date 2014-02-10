package com.fisherevans.ai.slider;

import java.util.ArrayList;

import com.fisherevans.ai.slider.Solver.Direction;

public class Solution
{
	private int _tiles[][];
	private int _emptyX, _emptyY;
	
	private ArrayList<Move> _moves;
	
	public Solution(int[][] tiles, int eX, int eY)
	{
		setTiles(tiles, eX, eY);
		_moves = new ArrayList<Move>();
	}
	
	public void setTiles(int[][] tiles, int eX, int eY)
	{
		_tiles = tiles;
		_emptyX = eX;
		_emptyY = eY;
	}
	
	public boolean move(Direction dir)
	{
		int buffer;
		int dx = 0;
		int dy = 0;
		
		switch(dir)
		{
			case Left:
				if(_emptyY == 0) { return false; }
				dy = -1;
				break;
			case Right:
				if(_emptyY == getWidth()-1) { return false; }
				dy = 1;
				break;
			case Up:
				if(_emptyX == 0) { return false; }
				dx = -1;
				break;
			case Down:
				if(_emptyX == getHeight()-1) { return false; }
				dx = 1;
				break;
			default:
				return false;
		}
		
		buffer = _tiles[_emptyX+dx][_emptyY+dy];
		_tiles[_emptyX+dx][_emptyY+dy] =  _tiles[_emptyX][_emptyY];
		_tiles[_emptyX][_emptyY] = buffer;

		_emptyX += dx;
		_emptyY += dy;
		
		_moves.add(new Move(dir, buffer));
		
		return true;
	}
	
	public void printMoves()
	{
		System.out.println("Printing tracked moves.");
		for(Move move:_moves)
		{
			System.out.println(" > Swap " + move.getSwap() + " with empty. (Pull from " + Solver.getDirectionName(move.getDir()) + ")");
		}
		System.out.println("This moveset has " + _moves.size() + " moves.");
	}
	
	public void clearMoves()
	{
		_moves = new ArrayList<Move>();
	}
	
	public int getWidth()
	{
		return _tiles == null?-1:_tiles.length;
	}
	
	public int getHeight()
	{
		return _tiles == null?-1:_tiles[0].length;
	}
	
	public void printTiles()
	{
		//System.out.println("=== Current Tiles (" + getWidth() + " by " + getHeight() + ") ===");
		System.out.println(">>> Current Tiles");
		
		int max = ("" + (getWidth()*getHeight()-1)).length();
		
		if(_tiles != null)
		{
			for(int x = 0;x < getWidth();x++)
			{
				System.out.print("    ");
				for(int y = 0;y < getHeight();y++)
				{
					System.out.printf("%-" + max + "d ", _tiles[x][y]);
				}
				System.out.println();
			}
		}
	}
}
