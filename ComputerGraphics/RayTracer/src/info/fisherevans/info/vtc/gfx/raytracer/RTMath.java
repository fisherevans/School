package info.fisherevans.info.vtc.gfx.raytracer;

import info.fisherevans.info.vtc.gfx.raytracer.components.Vector;

public class RTMath
{
	/** Solves a linear equation with 3 unknowns using 3 equations.
	 *  Use form ax + by + cz + CONSTANT = 0
	 *  Math from http://www.cplusplus.happycodings.com/Mathematics/code5.html
	 * @param x1 x1 coefficient
	 * @param y2 y1 coefficient
	 * @param z1 z1 coefficient
	 * @param c1 c1
	 * @param x2 x2 coefficient
	 * @param y2 y2 coefficient
	 * @param z2 z2 coefficient
	 * @param c2 c2 
	 * @param x3 x3 coefficient
	 * @param y3 y3 coefficient
	 * @param z3 z3 coefficient
	 * @param c3 c3
	 * @return
	 */
	public static float[] solveThreeUnknowns(float x1, float y1, float z1, float c1,
		float x2, float y2, float z2, float c2,
		float x3, float y3, float z3, float c3)
	{
		float D = (x1*y2*z3+y1*x3*z2+z1*x2*y3)-(x1*z2*y3+y1*x2*z3+z1*y2*x3);
		float x = ((y1*z3*c2+z1*y2*c3+c1*z2*y3)-(y1*z2*c3+z1*y3*c2+c1*y2*z3))/D;
		float y = ((x1*z2*c3+z1*x3*c2+c1*x2*z3)-(x1*z3*c2+z1*x2*c3+c1*z2*x3))/D;
		float z = ((x1*y3*c2+y1*x2*c3+c1*y2*x3)-(x1*y2*c3+y1*x3*c2+c1*x2*y3))/D;
		float results[] = {x, y, z};
		return results;
	}
	
	/** Returns true if x is within a and b
	 * @param x number in question
	 * @param a lower limit
	 * @param b higher limit
	 * @return true if within range, false otherwise
	 */
	public static boolean numberIsWithin(float x, float a, float b)
	{
		if(x >= a && x <= b)
			return true;
		else
			return false;
	}
	
	/** limits a number by giving it a lower and uper limit
	 * @param number The number to limit
	 * @param low the lowest value it should be
	 * @param high the highest possible value
	 * @return The final number after the limit filter
	 */
	public static float limitFloat(float number, float low, float high)
	{
		if(number < low)
			return low;
		else if(number > high)
			return high;
		else
			return number;
	}
	
	/** Retunrs the dot product of two #D vectors
	 * @param v1 Vector a
	 * @param v2 Vector b
	 * @return The resulting dot product
	 */
	public static float dotProduct(Vector v1, Vector v2)
	{ return (v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ()); }
}
