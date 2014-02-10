package info.fisherevans.info.vtc.compgfx.raytracing;

import java.awt.Color;

public class Triangle3D
{
	private Point3D _base;
	private Vector3D _a, _b;
	private Color _color;

	public Triangle3D(Point3D base, Vector3D a, Vector3D b)
	{
		_base = base;
		_a = a;
		_b = b;
		_color = new Color(0f, 0f, 0f);
	}
	
	public Triangle3D(Point3D a, Point3D b, Point3D c)
	{
		_base = a;
		a.minusPoint(new Vector3D(_base));
		b.minusPoint(new Vector3D(_base));
		_a = new Vector3D(a);
		_b = new Vector3D(b);
		_color = new Color(0f, 0f, 0f);
	}
	
	public Triangle3D(Point3D base, Vector3D a, Vector3D b, Color color)
	{
		_base = base;
		_a = a;
		_b = b;
		_color = color;
	}
	
	// -1 for no.
	public float doesIntersect(Vector3D vector, float focalLength)
	{
		// +u +v -t +c = 0
		float results[] = Unknowns.solveThreeUnknowns(_a.getX(), _b.getX(), -vector.getX(), _base.getX(),
													  _a.getY(), _b.getY(), -vector.getY(), _base.getY(),
													  _a.getZ(), _b.getZ(), -vector.getZ(), _base.getZ());
		
		float u = results[0]; // a
		float v = results[1]; // b
		
		//System.out.printf("[U: %f] [V: %f]\n", u, v);
		
		if(v >= 0 && u >= 0 && v <= 1 && u <= 1)
		{
			Point3D tempBase = _base;
			tempBase.addPoint(_a.multiply(u));
			tempBase.addPoint(_a.multiply(v));
			float xd = tempBase.getX();
			float yd = tempBase.getY();
			float zd = tempBase.getZ();
			return (float) Math.sqrt(xd*xd + yd*yd + zd*zd);
		}
		else
			return -1f;
	}
	
	public Color getColor()
	{
		return _color;
	}
	
	public void setColor(Color color)
	{
		_color = color;
	}
	
	public String toString()
	{
		return "[Base: "+_base.getX()+","+_base.getY()+","+_base.getZ()+"] [Vector A:"+_a.getX()+","+_a.getY()+","+_a.getZ()+"] [Vector B: "+_b.getX()+","+_b.getY()+","+_b.getZ()+"]";
	}
}
