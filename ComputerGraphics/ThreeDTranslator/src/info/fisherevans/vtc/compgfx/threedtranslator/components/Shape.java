package info.fisherevans.vtc.compgfx.threedtranslator.components;

import java.util.ArrayList;

public class Shape
{
	private ArrayList<Point> _points;
	private String _name;
	
	public Shape(ArrayList<Point> points, String name)
	{
		_points = points;
		_name = name;
	}
}
