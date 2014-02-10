package com.fisherevans.ai.slider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.fisherevans.ai.slider.Solver.Direction;

public class Main
{
	public static void main(String args[]) throws FileNotFoundException
	{
		//args = new String[] { "C:/slide.txt" };
		
		Solver solver = new Solver();
		
		if(args.length > 0)
		{
			solver.setTiles(args[0]);
		}
		else
		{
			solver.generateSolvedTiles(3);
			//solver.moveTest();
			solver.scrambleTiles(5);
			solver.getBaseSolution().clearMoves();
		}
		

		solver.printBaseTiles();
		solver.solve();
		solver.getBaseSolution().printMoves();
		solver.printBaseTiles();
	}
	
	public static void printTime(long start)
	{
		System.out.println(" (" + (System.currentTimeMillis()-start) + "ms)");
	}
}
