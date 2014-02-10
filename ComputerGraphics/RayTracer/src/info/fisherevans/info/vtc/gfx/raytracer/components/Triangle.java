package info.fisherevans.info.vtc.gfx.raytracer.components;

import info.fisherevans.info.vtc.gfx.raytracer.RTMath;

import java.awt.Color;

public class Triangle
{
	private Point _base;
	private Vector _ba, _bc, _normal;
	private Color _color;
	private int _shinny;
	
	/** Generates a 3D triangle based on a base point, 2 vectors and color
	 * @param base The base point
	 * @param ba The vector from point b to a
	 * @param bc The vector from point b to c
	 * @param color The color of the Triangle
	 */
	public Triangle(Point base, Vector ba, Vector bc, Color color, int shinny)
	{
		_base = base;
		_ba = ba;
		_bc = bc;
		_color = color;
		_normal = new Vector(_ba.getY()*_bc.getZ() - _ba.getZ()*_bc.getY(), _ba.getZ()*_bc.getX() - _ba.getX()*_bc.getZ(), _ba.getX()*_bc.getY() - _ba.getY()*_bc.getX());
		_shinny = shinny;
	}
	
	/** Set the base point
	 * @param base the new base point
	 */ public void setBase(Point base) { _base = base; }
	
	/** Set the BA vector
	 * @param ba the new BA vector
	 */
	public void setBA(Vector ba) { _ba = ba; }

	/** Set the BC vector
	 * @param bc the new BC vector
	 */
	public void setBC(Vector bc) { _bc = bc; }
	
	/** gets the base point
	 * @return The base point
	 */
	public Point getBase() { return _base; }
	
	/** gets the BA vector
	 * @return The BA vector
	 */
	public Vector getBA() { return _ba; }

	/** gets the BC vector
	 * @return The BC vector
	 */
	public Vector getBC() { return _bc; }

	/** gets the normal of the shape
	 * @return The normal vector
	 */
	public Vector getNormal() { return _normal; }

	/** gets the normal of the shape
	 * @return The normal vector
	 */
	public int getShinny() { return _shinny; }
	
	/** set the color of the triangle
	 * @param color The new color
	 */
	public void setColor(Color color) { _color = color; }
	
	/** get the color of the triangle
	 * @return The color of the triangle
	 */
	public Color getColor() { return _color; }
	
	/** Cacluataes if this triangle is between the two given points
	 * @param a Pooint 1
	 * @param b Point b
	 * @return true if the triangle is between the two points, false otherwise.
	 */
	public boolean doesIntersect(Point a, Point b)
	{
		float UVT[] = RTMath.solveThreeUnknowns(_ba.getX(), _bc.getX(), -(b.getX() - a.getX()), _base.getX()-a.getX(),
				_ba.getY(), _bc.getY(), -(b.getY() - a.getY()), _base.getY()-a.getY(),
				_ba.getZ(), _bc.getZ(), -(b.getZ() - a.getZ()), _base.getZ()-a.getZ());
		float u = UVT[0];
		float v = UVT[1];
		float t = UVT[2];

		if(RTMath.numberIsWithin(u, 0, 1) && RTMath.numberIsWithin(v, 0, 1) && RTMath.numberIsWithin(u+v, 0, 1) && RTMath.numberIsWithin(t, 0, 1))
			return true;
		else
			return false;
	}
	
	@Override
	/** creates a readable string describing the triangle
	 * @return the string that describes the triangle
	 */
	public String toString()
	{
		return "Triangle [Base: " + _base.toString() + "] [BA: " + _ba.toString() + "] [BC: " + _bc.toString() + "] [RGB(" + _color.getRed() + ", " + _color.getGreen() + ", " + _color.getBlue() + ")]";
	}
}
