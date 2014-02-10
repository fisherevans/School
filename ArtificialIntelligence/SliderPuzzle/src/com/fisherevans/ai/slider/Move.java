package com.fisherevans.ai.slider;

import com.fisherevans.ai.slider.Solver.Direction;

public class Move
{
	private Direction _dir;
	private int _swap;
	
	public Move(Direction dir, int swap)
	{
		_dir = dir;
		_swap = swap;
	}

	public Direction getDir()
	{
		return _dir;
	}

	public void setDir(Direction dir)
	{
		_dir = dir;
	}

	public int getSwap()
	{
		return _swap;
	}

	public void setSwap(int swap)
	{
		_swap = swap;
	}
}
