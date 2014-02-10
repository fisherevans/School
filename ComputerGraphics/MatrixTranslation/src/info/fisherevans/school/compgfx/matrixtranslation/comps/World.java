package info.fisherevans.school.compgfx.matrixtranslation.comps;

import info.fisherevans.school.compgfx.matrixtranslation.Controler;

import java.util.ArrayList;

public class World
{
	private ArrayList<Shape> _shapes;
	public Controler _controler;
	
	private int _curShapeId = -1;
	
	public World(Controler controler)
	{
		_controler = controler;
		_shapes = new ArrayList<Shape>();
	}

	
	public void addShape()
	{
		_shapes.add(new Shape(this));
		_curShapeId = _shapes.size()-1;
		_controler.log("Shape[" + _curShapeId + "] added AND selected.");
	}
	
	public void addPoint(Point newPoint)
	{
		if(checkShapeId(_curShapeId))
		{
			_shapes.get(_curShapeId).addPoint(newPoint);
		}
		else
		{
			_controler.log("Have you added a shape yet?");
		}
	}
	
	public void selectShape(int id)
	{
		if(checkShapeId(id))
		{
			_curShapeId = id;
			_controler.log("Shape[" + _curShapeId + "] selected. (There are " + _shapes.get(_curShapeId).getPointSize() + " points defined in this shape.)");
		}
	}
	
	public void selectPoint(int id)
	{
		_shapes.get(_curShapeId).selectPoint(id);
	}
	
	public void matixMult(float[][] fourFour)
	{
		if(checkShapeId(_curShapeId))
		{
			_shapes.get(_curShapeId).matixMult(fourFour);
		}
		else
		{
			_controler.log("Have you added a shape yet?");
		}
	}
	
	public void matrixMultShape(float[][] fourFour)
	{
		if(checkShapeId(_curShapeId))
		{
			_shapes.get(_curShapeId).matrixMultShape(fourFour);
		}
		else
		{
			_controler.log("Have you added a shape yet?");
		}
	}
	
	public void printPoints()
	{
		printPoints(_curShapeId);
	}
	
	public void printPoints(int id)
	{
		if(checkShapeId(id))
		{
			_controler.log("Listing points defined for Shape[" + id + "]:");
			_shapes.get(id).printPoints();
		}
	}
	
	public void printShapes()
	{
		if(_shapes.size() == 0)
			_controler.log("There are no shapes defined yet");
		else
		{
			for(int index = 0;index < _shapes.size();index++)
			{
				_controler.log("  -> Shape[" + index + "] : " + _shapes.get(index).getPointSize() + " points.");
			}
		}
	}
	
	public void selected()
	{
		if(checkShapeId(_curShapeId))
		{
			_controler.log("Currently Selected:");
			_controler.log("  -> Shape[" + _curShapeId + "] : " + _shapes.get(_curShapeId).getPointSize() + " points.");
			_shapes.get(_curShapeId).selected();
		}
		else
		{
			_controler.log("Have you added a shape yet?");
		}
	}
	
	private boolean checkShapeId(int id)
	{
		if(id >= 0 && id < _shapes.size())
			return true;
		else
		{
			_controler.log("There is no shape defined the ID: " + id);
			return false;
		}
	}
}
