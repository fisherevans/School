package info.fisherevans.info.vtc.compgfx.raytracing;

public class Point3D
{
	private float _x,_y,_z;
	
	public Point3D(float x, float y, float z)
	{
		_x = x;
		_y = y;
		_z = z;
	}
	
	public Point3D()
	{
		_x = 0;
		_y = 0;
		_z = 0;
	}
	
	public boolean equals(Point3D p)
	{
		if(p.getX() == _x && p.getY() == _y && p.getZ() == _z)
			return true;
		else
			return false;
	}
	
	public float getX()
	{
		return _x;
	}
	
	public float getY()
	{
		return _y;
	}
	
	public float getZ()
	{
		return _z;
	}
	
	public void setX(float x)
	{
		_x = x;
	}
	
	public void setY(float y)
	{
		_y = y;
	}
	
	public void setZ(float z)
	{
		_z = z;
	}
	
	public void setPoint(float x, float y, float z)
	{
		_x = x;
		_y = y;
		_z = z;
	}

	public void addPoint(Vector3D shift)
	{
		_x += shift.getPoint().getX();
		_y += shift.getPoint().getY();
		_y += shift.getPoint().getZ();
	}
	
	public void minusPoint(Vector3D shift)
	{
		_x -= shift.getPoint().getX();
		_y -= shift.getPoint().getY();
		_y -= shift.getPoint().getZ();
	}
}
