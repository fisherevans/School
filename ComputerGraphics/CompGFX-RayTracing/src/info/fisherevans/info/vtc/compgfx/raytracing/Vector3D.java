package info.fisherevans.info.vtc.compgfx.raytracing;

public class Vector3D
{
	private Point3D _point;
	
	public Vector3D(Point3D point)
	{
		_point = point;
	}
	
	public Point3D getPoint()
	{
		return _point;
	}
	
	public Vector3D multiply(float times)
	{
		return new Vector3D(new Point3D(_point.getX()*times, _point.getY()*times, _point.getZ()*times));
	}
	
	public float getX()
	{
		return _point.getX();
	}
	
	public float getY()
	{
		return _point.getY();
	}
	
	public float getZ()
	{
		return _point.getZ();
	}
}
