package info.fisherevans.school.compgfx.matrixtranslation.comps;


public class Point
{
	private float _x, _y, _z, _w;
	
	/** generates a 3D point
	 * @param x The x value
	 * @param y the y value
	 * @param z the z value
	 */
	public Point(float x, float y, float z, float w)
	{
		_x = x;
		_y = y;
		_z = z;
		_w = w;
	}
	
	/** gets the x value of the point
	 * @return The x value
	 */
	public float getX() { return _x/_w; }

	/** gets the y value of the point
	 * @return The y value
	 */
	public float getY() { return _y/_w; }

	/** gets the z value of the point
	 * @return The z value
	 */
	public float getZ() { return _z/_w; }
	
	public float getW() { return _w; }
	
	/** sets the x value of the point
	 * @param x The new x value
	 */
	public void setX(float x) { _x = x; }

	/** sets the y value of the point
	 * @param y The new y value
	 */
	public void setY(float y) { _y = y; }

	/** sets the z value of the point
	 * @param z The new z value
	 */
	public void setZ(float z) { _z = z; }
	
	public void setW(float w) { _w = w; }
	
	public void setPoint(float[] matrix)
	{
		_x = matrix[0];
		_y = matrix[1];
		_z = matrix[2];
		_w = matrix[3];
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
	
	public float[] getMatrix()
	{
		float[] matrix =  {_x, _y, _z, _w };
		return matrix;
	}
	
	public void scale(float[][] scaleMatrix)
	{
		
	}
	
	@Override
	/** creates a readable string describing the point
	 * @return the string that describes the point
	 */
	public String toString() { return "(" + _x + ", " + _y + ", " + _z + ")"; }
}
