package info.fisherevans.vtc.cgfx.simpleraytracer;

import java.awt.Color;
import java.util.ArrayList;

public class Pixel
{
	private ArrayList<Point> _rays;
	private int _displayX, _displayY;
	private Color _color;
	
	public Pixel(int x, int y)
	{
		_rays = new ArrayList<Point>();
		_displayX = x;
		_displayY = y;
	}
	
	public void setColor(ArrayList<Color> colors)
	{
		float rTotal = 0;
		float gTotal = 0;
		float bTotal = 0;
		
		for(Color tempColor:colors)
		{
			rTotal += tempColor.getRed();
			gTotal += tempColor.getGreen();
			bTotal += tempColor.getBlue();
		}
		
		_color = new Color((float)(rTotal/((float)colors.size())),
						   (float)(gTotal/((float)colors.size())),
						   (float)(bTotal/((float)colors.size())));
	}
	
	public void addRay(Point newRay)
	{
		_rays.add(newRay);
	}
	
	public Color getColor()
	{
		return _color;
	}
	
	public ArrayList<Point> getRays()
	{
		return _rays;
	}
	
	public int getDisplayX()
	{
		return _displayX;
	}
	
	public int getDisplayY()
	{
		return _displayY;
	}
}