package info.fisherevans.info.vtc.gfx.raytracer.components;


import java.awt.Color;
import java.util.ArrayList;

public class Pixel
{
	private ArrayList<Vector> _vectors;
	private int _displayX, _displayY;
	private Point _a, _b;
	private int _aaDepth;
	private Color _color;
	
	/** Creates a pixel object which holds the x and y of the display field, the rays that define the pixel in 3d space and the color is represents in a raster
	 * @param x the x corid int he display plane
	 * @param y the y corid int he display plane
	 * @param a The top left corner of the pixel's ray plane
	 * @param b The bottom right corner of the pixel's ray plane
	 * @param aaDepth The amount of anti aliasing the rays should calcualte.
	 */
	public Pixel(int x, int y, Point a, Point b, int aaDepth)
	{
		_vectors = new ArrayList<Vector>();
		_displayX = x;
		_displayY = y;
		_a = a;
		_b = b;
		_aaDepth = aaDepth;
		
		getAAVectors();
	}
	
	/** Calculates the rays that descibes the pixel's color. */
	private void getAAVectors()
	{
		float unitWide = _b.getX() - _a.getX();
		float unitHigh = _a.getY() - _b.getY();
		
		float aaWidth = unitWide/_aaDepth;
		float aaHeight = unitHigh/_aaDepth;
		
		Point startPoint = new Point((float)(_a.getX() + aaWidth/2.0), (float)(_a.getY() - aaHeight/2.0), _a.getZ());
		
		for(int x = 0;x < _aaDepth;x++)
		{
			for(int y = 0;y < _aaDepth;y++)
				{
					_vectors.add(new Vector((float)(startPoint.getX() + aaWidth*((float)x)), (float)(startPoint.getY() + aaHeight*((float)y)), _a.getZ()));
				}
		}
	}
	
	/** Averages the a list of colors handed to it and sets the result as the color of the pixel.
	 * @param colors The colors to be averaged
	 */
	public void setColor(ArrayList<Color> colors)
	{
		float rTotal = 0;
		float gTotal = 0;
		float bTotal = 0;
		
		for(Color tempColor:colors)
		{
			rTotal += (float)(tempColor.getRed()/255.0);
			gTotal += (float)(tempColor.getGreen()/255.0);
			bTotal += (float)(tempColor.getBlue()/255.0);
		}
		
		_color = new Color((float)(rTotal/((float)colors.size())),
						   (float)(gTotal/((float)colors.size())),
						   (float)(bTotal/((float)colors.size())));
	}
	
	/** @return the color of the pixel */
	public Color getColor() { return _color; }
	
	/** @return a list of rays that define the pixel int he 3d space */
	public ArrayList<Vector> getVectors() { return _vectors; }
	
	/** @return The x display corrid of this pixel */
	public int getDisplayX() { return _displayX; }

	/** @return The y display corrid of this pixel */
	public int getDisplayY() { return _displayY; }
}