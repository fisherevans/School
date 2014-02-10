package info.fisherevans.info.vtc.compgfx.raytracing;

public class Direction
{
	private Point3D _base, _unit;
	
	public Direction(Point3D base)
	{
		_base = base;
		_unit = getUnit(_base);
	}
	
	private Point3D getUnit(Point3D base)
	{
		float x = base.getX();
		float y = base.getY();
		float z = base.getZ();
		float a = (float) Math.sqrt(x*x + y*y + z*z);
		
		if(a == 1f)
			return base;
		
		a = 1f/a;
		return new Point3D(x*a, y*a, z*a);
	}
	
	public float getAngle(Direction d)
	{
		float x1 = _base.getX();
		float y1 = _base.getY();
		float z1 = _base.getZ();
		float x2 = d.getBase().getX();
		float y2 = d.getBase().getY();
		float z2 = d.getBase().getZ();
		
		double dot = x1*x2 + y1*y2 + z1*z2;
		dot /= Math.sqrt(x1*x1+y1*y1+z1*z1) + Math.sqrt(x2*x2+y2*y2+z2*z2);
		return (float) Math.acos(dot);
	}
	
	public boolean equals(Direction d)
	{
		return _unit.equals(d.getUnit());
	}
	  
	
	public Point3D getBase()
	{
		return _base;
	}
	
	public Point3D getUnit()
	{
		return _unit;
	}
}
