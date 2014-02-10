package info.fisherevans.vtc.cgfx.simpleraytracer;

public class RayMath
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
}
