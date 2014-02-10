package info.fisherevans.vtc.cgfx.simpleraytracer;

import java.awt.Color;
import java.util.ArrayList;

public class Raster
{
	private int _width, _height;
	private ArrayList<Color> _pixels;
	
	/** A class used to keep track of pixels for diplaying a raster based on raytracing.
	 * @param width The number of pixels in the raster width wise
	 * @param height The number of pixels in the raster height wise
	 */
	public Raster(int width, int height)
	{
		_width = width;
		_height = height;
		_pixels = new ArrayList<Color>();
		for(int i = 0;i < _width*_height;i++)
		{
			_pixels.add(Color.BLACK);
		}
	}
	
	/** set the color of pixel at specified x and y
	 * @param x The x corrid of the pixel
	 * @param y The y corrid of the pixel
	 * @param color the new color
	 */
	public void setPixel(int x, int y, Color color)
	{
		_pixels.set(x + y*_width, color);
	}
	
	/** get the color of a pixel at a certain x and y
	 * @param x The desired x
	 * @param y the desired y
	 * @return the color of the pixel
	 */
	public Color getPixel(int x, int y)
	{
		return _pixels.get(x + y*_width);
	}
	
	/** Gets the number of pixels width wise in the raster
	 * @return The number of pixels width-wise in this raster
	 */
	public int getWidth()
	{
		return _width;
	}

	/** Gets the number of pixels height wise in the raster
	 * @return The number of pixels height-wise in this raster
	 */
	public int getHeight()
	{
		return _height;
	}
}
