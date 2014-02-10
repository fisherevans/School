package info.fisherevans.vtc.cgfx.simpleraytracer;

import java.awt.Color;

public class Triangle
{
	private Point _base, _ba, _bc;
	private Color _color;
	
	/** Generates a 3D triangle based on a base point, 2 vectors and color
	 * @param base The base point
	 * @param ba The vector from point b to a
	 * @param bc The vector from point b to c
	 * @param color The color of the Triangle
	 */
	public Triangle(Point base, Point ba, Point bc, Color color)
	{
		_base = base;
		_ba = ba;
		_bc = bc;
		_color = color;
	}
	
	/** Computes if a ray will intersect with this triangle. -1 return for no.
	 * @param ray The ray to test against
	 * @return The distance of the intersection. -1 means does not intersect.
	 */
	public float doesIntersect(Point ray)
	{
		// u + v + -t + c = 0
			// u: BA
			// v: BC
			// t: Ray
			// c: Triangle base
		float UVT[] = RayMath.solveThreeUnknowns(_ba.getX(), _bc.getX(), -ray.getX(), _base.getX(),
												  _ba.getY(), _bc.getY(), -ray.getY(), _base.getY(),
												  _ba.getZ(), _bc.getZ(), -ray.getZ(), _base.getZ());
		float u = UVT[0];
		float v = UVT[1];
		float t = UVT[2];

		//System.out.print("	Got [U = " + u +"] [V = " + v + "] [T = " + t + "] from > ");
		//System.out.println("(" + _base.toString() + ") + u(" + _ba.toString() + ") + v(" + _bc.toString() + ") = t(" + ray.toString() + ")");

		if(RayMath.numberIsWithin(u, 0, 1) && RayMath.numberIsWithin(v, 0, 1) && RayMath.numberIsWithin(u+v, 0, 1))
		{
			Point tempPoint = new Point(ray.getX(), ray.getY(), ray.getZ());
			tempPoint.magPoint(t);
			return (new Point(0,0,0)).getDistance(tempPoint);
		}
		else
			return -1f;
	}
	
	/** Set the base point
	 * @param base the new base point
	 */
	public void setBase(Point base)
	{
		_base = base;
	}
	
	/** Set the BA vector
	 * @param ba the new BA vector
	 */
	public void setBA(Point ba)
	{
		_ba = ba;
	}

	/** Set the BC vector
	 * @param bc the new BC vector
	 */
	public void setBC(Point bc)
	{
		_bc = bc;
	}
	
	/** gets the base point
	 * @return The base point
	 */
	public Point getBase()
	{
		return _base;
	}
	
	/** gets the BA vector
	 * @return The BA vector
	 */
	public Point getBA()
	{
		return _ba;
	}

	/** gets the BC vector
	 * @return The BC vector
	 */
	public Point getBC()
	{
		return _bc;
	}
	
	/** set the color of the triangle
	 * @param color The new color
	 */
	public void setColor(Color color)
	{
		_color = color;
	}
	
	/** get the color of the triangle
	 * @return The color of the triangle
	 */
	public Color getColor()
	{
		return _color;
	}
	
	@Override
	/** creates a readable string describing the triangle
	 * @return the string that describes the triangle
	 */
	public String toString()
	{
		return "Triangel [Base: " + _base.toString() + "] [BA: " + _ba.toString() + "] [BC: " + _bc.toString() + "] [" + _color.toString() + "]";
	}
}
