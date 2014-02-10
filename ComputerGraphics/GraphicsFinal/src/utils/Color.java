/**
 * @author 		Philip Lipman
 * @date 		Nov 29, 2012
 *
 * @class		Color
 * @comments	Insert your comments here.
 */

package utils;

/**
 * 
 */

public class Color
{
	private float _red;
	private float _green;
	private float _blue;
	private float _alpha;
	
	/**
	 * 
	 */
	public Color(float red, float green, float blue, float alpha)
	{
		// TODO Auto-generated constructor stub
		_red = clamp(red, 0.0f, 1.0f);
		_green = clamp(green, 0.0f, 1.0f);
		_blue = clamp(blue, 0.0f, 1.0f);
		_alpha = clamp(alpha, 0.0f, 1.0f);

	}
	
	public float R()
	{
		return _red;
	}
	
	public float G()
	{
		return _green;
	}
	
	public float B()
	{
		return _blue;
	}
	
	public float Alpha()
	{
		return _alpha;
	}
	
	public float clamp(float x, float high, float low)
	{
		return Math.min(Math.max(x, high), low);
	}

}
