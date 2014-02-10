package com.fisherevans.vtc.rubiks;

public class Block
{
	public enum Color { White, Red, Blue, Green, Orange, Yellow };
	
	private Color _color;
	private float[][] _colored, _black;
	
	public Block(Color color, float[][] black, float[][] colored)
	{
		_color = color;
		_colored = colored;
		_black = black;
	}

	public float[][] getColored()
	{
		return _colored;
	}
	
	public float[][] getBlack()
	{
		return _black;
	}
	
	public float[] getColor()
	{
		switch(_color)
		{
		case Orange:
			return new float[] { 1, 0.5f, 0 };
		case White:
			return new float[] { 1, 1, 1 };
		case Yellow:
			return new float[] { 1, 1, 0 };
		case Red:
			return new float[] { 1, 0, 0 };
		case Green:
			return new float[] { 0, 1, 0 };
		case Blue:
			return new float[] { 0, 0, 1 };
		default:
			return new float[] { 0, 0, 0 };
		}
	}
}
