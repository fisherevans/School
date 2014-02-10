package info.fisherevans.school.compgfx.matrixtranslation.comps;

import java.util.ArrayList;

public class Shape
{
	public World _world;
	private ArrayList<Point> _points;
	private int _curPointId = -1;
	
	public Shape(World world)
	{
		_world = world;
		_points = new ArrayList<Point>();
	}

	public void addPoint(Point newPoint)
	{
		_points.add(newPoint);
		_curPointId = _points.size()-1;
		_world._controler.log("Point[" + _curPointId + "] added AND selected. " + _points.get(_curPointId).toString());
	}
	
	public void selectPoint(int id)
	{
		if(checkPointId(id))
		{
			_curPointId = id;
			_world._controler.log("Point[" + _curPointId + "] selected. " + _points.get(_curPointId).toString());
		}
	}
	
	public void matixMult(float[][] fourFour)
	{
		if(checkPointId(_curPointId))
		{
			float[] oneFour = _points.get(_curPointId).getMatrix();
			oneFour = MatrixMath.FourFourTimesOneFour(fourFour, oneFour);
			_points.get(_curPointId).setPoint(oneFour);
			_world._controler.log("Point[" + _curPointId + "] has been adjusted. Result: " + _points.get(_curPointId).toString());
		}
		else
		{
			_world._controler.log("Have you added a shape yet?");
		}
	}
	
	public void matrixMultShape(float[][] fourFour)
	{
		if(_points.size() == 0)
			_world._controler.log("There are no points to rotate.");
		else
		{
			for(int i = 0;i < _points.size();i++)
			{
				float[] oneFour = _points.get(i).getMatrix();
				oneFour = MatrixMath.FourFourTimesOneFour(fourFour, oneFour);
				_points.get(i).setPoint(oneFour);
				_world._controler.log("Point[" + i + "] has been rotated. Result: " + _points.get(i).toString());
			}
		}
	}
	
	public void printPoints()
	{
		if(_points.size() == 0)
			_world._controler.log("There are no points defined yet for this shape.");
		else
		{
			for(int index = 0;index < _points.size();index++)
			{
				_world._controler.log("  -> Point[" + index + "] : " + _points.get(index).toString());
			}
		}
	}
	
	public Point getCurrentPoint()
	{
		return _points.get(_curPointId);
	}
	
	public int getPointSize()
	{
		return _points.size();
	}
	
	public void selected()
	{
		if(checkPointId(_curPointId))
		{
			_world._controler.log("      -> Point[" + _curPointId + "] : " + _points.get(_curPointId).toString());
		}
		else
		{
			_world._controler.log("Have you added a point yet?");
		}
	}
	
	private boolean checkPointId(int id)
	{
		if(id >= 0 && id < _points.size())
			return true;
		else
		{
			_world._controler.log("There is no point defined the ID: " + id);
			return false;
		}
	}
}
