package info.fisherevans.info.vtc.compgfx.raytracing;

import java.awt.Color;

public class Pixel
{
	private int _x, _y;
	private Color _color;
	
	public Pixel(int x, int y, Color color)
	{
		_x = x;
		_y = y;
		_color = color;
	}
	
	public void setColor(Color color)
	{
		_color = color;
	}
	
	public Color getColor()
	{
		return _color;
	}
	
	public int getX()
	{
		return _x;
	}
	
	public int getY()
	{
		return _y;
	}
}
