package com.fisherevans.ai.slider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver
{
	public enum Direction { Left, Right, Up, Down };
	private Direction[] _dirs = { Direction.Left, Direction.Right, Direction.Up, Direction.Down };
	
	private Solution _baseSolution;

	public void solve()
	{
		long start = System.currentTimeMillis();
		
		System.out.print("Solving the current tileset...");
		
		Main.printTime(start);
	}
	
	public void printBaseTiles()
	{
		_baseSolution.printTiles();
	}
	
	public void generateSolvedTiles(int size)
	{
		long start = System.currentTimeMillis();

		System.out.print("Generating new tiles. (" + size + " by " + size + ")");
		
		int[][] tiles = new int[size][size];
		int tile = 0;
		for(int x = 0;x < size;x++)
		{
			for(int y = 0;y < size;y++)
			{
				tiles[x][y] = tile++;
			}
		}

		_baseSolution = new Solution(tiles, 0, 0);

		Main.printTime(start);
	}
	
	public void moveTest()
	{
		_baseSolution.move(Direction.Right);
		_baseSolution.move(Direction.Right);
		_baseSolution.move(Direction.Down);
		//_baseSolution.move(Direction.Left);
	}
	
	public void scrambleTiles(int moves)
	{
		long start = System.currentTimeMillis();
		
		System.out.print("Scrambling tiles with " + moves + " random moves.");
		for(int times = 0;times < moves;times++)
		{
			while(!_baseSolution.move(_dirs[(int) ((Math.random()*4))]));
		}

		Main.printTime(start);
	}
	
	public void setTiles(int[][] newTiles)
	{
		System.out.println("The imported tileset is " + newTiles.length + " by " + newTiles.length + ".");
		
		int[][] tiles = newTiles;
		int size = tiles.length;
		int emptyX = 0;
		int emptyY = 0;

		for(int x = 0;x < size;x++)
		{
			for(int y = 0;y < size;y++)
			{
				if(tiles[x][y] == 0)
				{
					emptyX = x;
					emptyY = y;
				}
			}
		}
		
		_baseSolution = new Solution(tiles, emptyX, emptyY);
	}
	
	public void setTiles(String file)
	{
		System.out.println("Importing tiles from the file: " + file);
		
		setTiles(getTilesFromFile(file));
	}
	
	public int[][] getTilesFromFile(String file)
	{
		Scanner in = null;
		try
		{
			in = new Scanner(new File(file));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("There was an error opening the file... Does it exist? Quitting!");
			e.printStackTrace();
			System.exit(7);
		}
		
		String line;
		String[][] cols = new String[50][50];
		int row = 0;
		
		while(in.hasNextLine())
		{
			line = in.nextLine();
			cols[row] = line.split(" +");
			row++;
		}
		
		int[][] tiles = new int[row][row];
		for(int x = 0;x < row;x++)
		{
			for(int y = 0;y < row;y++)
			{
				tiles[x][y] = Integer.parseInt(cols[x][y]);
			}
		}
		
		return tiles;
	}
	
	public Solution getBaseSolution()
	{
		return _baseSolution;
	}

	public void setBaseSolution(Solution baseSolution)
	{
		_baseSolution = baseSolution;
	}

	public static String getDirectionName(Direction dir)
	{
		switch(dir)
		{
			case Left:
				return "the Left";
			case Right:
				return "the Right";
			case Up:
				return "Above";
			case Down:
				return "Below";
			default:
				return "";
		}
	}
}
