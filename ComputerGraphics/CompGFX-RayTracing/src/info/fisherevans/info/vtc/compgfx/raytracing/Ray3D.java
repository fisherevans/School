package info.fisherevans.info.vtc.compgfx.raytracing;

public class Ray3D
{
	private Point3D _base;
	private Direction _dir;
	
	public Ray3D(Point3D base, Direction dir)
	{
		_base = base;
		_dir = dir;
	}
	
	public Point3D getBase()
	{
		return _base;
	}
	
	public Direction getDirection()
	{
		return _dir;
	}
}
