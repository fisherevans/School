package info.fisherevans.info.vtc.gfx.raytracer.components;

public class Vector extends Point
{
	/** The vector objects holds a 3d vector
	 * @param x The X magnitude
	 * @param y The X magnitude
	 * @param z The X magnitude
	 */
	public Vector(float x, float y, float z)
	{ super(x, y, z); }

	/** Multiply the vector
	 * @param mult the amount to multiply by
	 */
	public void multiplyVector(float mult)
	{
		setX(getX()*mult);
		setY(getY()*mult);
		setZ(getZ()*mult);
	}

	/** Multiply the vector and return a new instance
	 * @param mult the amount to multiply by
	 * @return 
	 */
	public Vector getMultiplyVector(float mult)
	{ return new Vector(getX()*mult, getY()*mult, getZ()*mult); }
	
	/** get the length of the full vector
	 * @return The total length traveled byt eh vector
	 */
	public float getLength()
	{ return (float)Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ()); }
	
	/** Return a new vector that is this vector scaled to be between 0 and 1
	 * @return The scaled vector between 0 and 1
	 */
	public Vector getNormalaized()
	{
		float length = Math.abs(getLength());
		return new Vector(getX()/length, getY()/length, getZ()/length);
	}
}
