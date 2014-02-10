package info.fisherevans.vtc.cgfx.simpleraytracer;

import java.awt.Color;
import java.util.ArrayList;

public class Main
{
	private ArrayList<Triangle> _triangles;
	private ArrayList<Point> _ppPoints;
	private Raster _ppRaster;
	
	/** Picture pan width */
	private final float PP_WIDTH = 2f;
	/** Picture pan height */
	private final float PP_HEIGHT = 2f;
	/** Number of pixels for the pane width wise */
	private final int PIX_WIDTH = 5;
	/** Number of pixels for the pane height wise */
	private final int PIX_HEIGHT = 5;
	/** distance from 0,0 (camera) to picture pane */
	private final float FOCAL_LENGTH = 5f;
	/** depth of anti-aliasing (number squared samples) */
	private final int AA_DEPTH = 4;
	
	/** The object that builds and displays the raster using ray tracing  */
	public Main()
	{
		_triangles = new ArrayList<Triangle>();
		addTestTriangles();
		
		_ppPoints = new ArrayList<Point>();
		generatePixelRays();
		
		_ppRaster = new Raster(PIX_WIDTH, PIX_HEIGHT);
		
		traceRays();
		
		new Display(_ppRaster);
	}
	
	/** Adds triangles to the world for testing purposes.  */
	private void addTestTriangles()
	{
		Point tempBase, tempBA, tempBC;
		Triangle tempTriangel;
		
		/* homework 5x5 - 20px20 - 10f
		tempBase = new Point(-1.75f, -0.5f, -15f);
		tempBA = new Point(3.25f, 0f, 0f);
		tempBC = new Point(3.25f, 4.25f, 0f);
		Triangle tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.RED);
		_triangles.add(tempTriangel);

		tempBase = new Point(5.1f, 0f, -17f);
		tempBA = new Point(-8.1f, 0f, 0f);
		tempBC = new Point(-8.1f, -4.25f, 0f);
		tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.GREEN);
		_triangles.add(tempTriangel);

		tempBase = new Point(0f, -2f, -20f);
		tempBA = new Point(0f, 7f, 0f);
		tempBC = new Point(-5f, 0f, 0f);
		tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.BLUE);
		_triangles.add(tempTriangel);
		*/

		tempBase = new Point(-4.5f, -3f, -20f);
		tempBA = new Point(0f, 0f, -20f);
		tempBC = new Point(9f, 0f, 0f);
		tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.WHITE  );
		_triangles.add(tempTriangel);
		
		tempBase = new Point(4.5f, -3f, -40f);
		tempBA = new Point(0f, 0f, 20f);
		tempBC = new Point(-9f, 0f, 0f);
		tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.WHITE  );
		_triangles.add(tempTriangel);
		
		tempBase = new Point(-2.5f, -3f, -30f);
		tempBA = new Point(2.5f, 0f, 5f);
		tempBC = new Point(2.5f, 8f, 0f);
		tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.BLUE  );
		_triangles.add(tempTriangel);
		
		tempBase = new Point(2.5f, -3f, -30f);
		tempBA = new Point(-2.5f, 0f, 5f);
		tempBC = new Point(-2.5f, 8f, 0f);
		tempTriangel = new Triangle(tempBase, tempBA, tempBC, Color.BLUE  );
		_triangles.add(tempTriangel);
	}
	
	/** Generates a list of the center of all pixels in the picture pane based on stated finals. */
	private void generatePixelRays()
	{
		double pixelWidth = PP_WIDTH/PIX_WIDTH;
		double pixelHeight = PP_HEIGHT/PIX_HEIGHT;

		double startX = PP_WIDTH/(-2f) + pixelWidth/(2f);
		double startY = PP_HEIGHT/(2f) - pixelHeight/(2f);

		for(int xIndex = 0;xIndex < PIX_WIDTH;xIndex++)
		{
			for(int yIndex = 0;yIndex < PIX_HEIGHT;yIndex++)
			{
				Point pixelCenter = new Point((float)(startX + (xIndex*pixelWidth)), (float)(startY - (yIndex*pixelHeight)), FOCAL_LENGTH);
				//System.out.println("[" + xIndex + ", " + yIndex + "] Got Center: " + pixelCenter.toString());
				_ppPoints.add(pixelCenter);
			}
		}
	}
	
	/** Checks intersection for each point in _ppPoints and _triangles and sets color accordingly in _ppRaster */
	private void traceRays()
	{
		for(int xIndex = 0;xIndex < PIX_WIDTH;xIndex++)
		{
			for(int yIndex = 0;yIndex < PIX_HEIGHT;yIndex++)
			{
				float minDistance = 1000;
				float tempDistance;
				//System.out.println("Figuring intersectiosn for [" + xIndex + ", " + yIndex + "]");
				for(Triangle tempTriangel:_triangles)
				{
					tempDistance = tempTriangel.doesIntersect(_ppPoints.get(xIndex + yIndex*PIX_WIDTH));
					if(tempDistance >= 0 && tempDistance < minDistance)
					{
						minDistance = tempDistance;
						_ppRaster.setPixel(xIndex,  yIndex, tempTriangel.getColor());
					}
				}
			}
		}
	}
	
	/** creates the raster and displays it
	 * @param args There are no arguments to use
	 */
	public static void main(String[] args)
	{
		new Main();
	}
}
