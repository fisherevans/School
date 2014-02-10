package info.fisherevans.vtc.cgfx.simpleraytracer;

public class Point
{
	private float _x, _y, _z;
	
	/** generates a 3D point
	 * @param x The x value
	 * @param y the y value
	 * @param z the z value
	 */
	public Point(float x, float y, float z)
	{
		_x = x;
		_y = y;
		_z = z;
	}
	
	/** Adds a vector to the point
	 * @param point to vector to add
	 */
	public void addPoint(Point point)
	{
		_x += point.getX();
		_y += point.getY();
		_z += point.getY();
	}

	/** subtracts a vector to the point
	 * @param point to vector to subtract
	 */
	public void subPoint(Point point)
	{
		_x -= point.getX();
		_y -= point.getY();
		_z -= point.getY();
	}

	/** Multply the point as a vector
	 * @param mag the amount to multiply by
	 */
	public void magPoint(float mag)
	{
		_x *= mag;
		_y *= mag;
		_z *= mag;
	}
	
	/** gets the x value of the point
	 * @return The x value
	 */
	public float getX()
	{
		return _x;
	}

	/** gets the y value of the point
	 * @return The y value
	 */
	public float getY()
	{
		return _y;
	}

	/** gets the z value of the point
	 * @return The z value
	 */
	public float getZ()
	{
		return _z;
	}
	
	/** sets the x value of the point
	 * @param x The new x value
	 */
	public void setX(float x)
	{
		_x = x;
	}

	/** sets the y value of the point
	 * @param y The new y value
	 */
	public void setY(float y)
	{
		_y = y;
	}

	/** sets the z value of the point
	 * @param z The new z value
	 */
	public void setZ(float z)
	{
		_z = z;
	}
	
	/** gets the distance between this point and a given point
	 * @param point The point to get distance from
	 * @return the distance
	 */
	public float getDistance(Point point)
	{
		float xd = point.getX() - _x;
		float yd = point.getY() - _y;
		float zd = point.getZ() - _z;
		
		return (float)Math.sqrt(xd*xd + yd*yd + zd*zd);
	}
	
	@Override
	/** creates a readable string describing the point
	 * @return the string that describes the point
	 */
	public String toString()
	{
		return _x + ", " + _y + ", " + _z;
	}
}
