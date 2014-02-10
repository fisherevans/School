package info.fisherevans.info.vtc.compgfx.raytracing;

import java.awt.Color;
import java.util.ArrayList;

public class Render
{
	private ArrayList<Triangle3D> _triangles;
	private float _ppHeight, _ppWidth, _focalLength;
	private int _pixHeight, _pixWidth;

	private final int CAM_X = 0;
	private final int CAM_Y = 0;
	private final int CAM_Z = 0;
	private Point3D _cameraOrigin;
	
	private ArrayList<Vector3D> _pixelVectors;
	
	private ArrayList<Pixel> _pixels;
	
	public Render(ArrayList<Triangle3D> triangles, float ppWidth, float ppHeight, int pixWidth, int pixHeight, float focalLength)
	{
		_triangles = triangles;
		_ppHeight = ppHeight;
		_ppWidth = ppWidth;
		_pixHeight = pixHeight;
		_pixWidth = pixWidth;
		_focalLength = focalLength;
		
		_cameraOrigin = new Point3D(CAM_X, CAM_Y, CAM_Z);
		
		_pixelVectors = new ArrayList<Vector3D>();
		_pixels = new ArrayList<Pixel>();
		
		for(Triangle3D tempTri:_triangles)
		{
			System.out.println(tempTri.toString());
		}

		System.out.println("Finding Pixel Rays");
		findPixelRays();

		System.out.println("Generating Pixels");
		createRaster();
	}
	
	private void findPixelRays()
	{
		float pixelWidth = _ppWidth/_pixWidth;
		float pixelHeight = _ppHeight/_pixHeight;

		float startX = _ppWidth/(-2f) + pixelWidth/(2f);
		float startY = _ppHeight/(2f) - pixelHeight/(2f);
		
		for(int yIndex = 0;yIndex < _pixHeight;yIndex++)
		{
			for(int xIndex = 0;xIndex < _pixWidth;xIndex++)
			{
				//System.out.println("Getting Ray for " + xIndex + ", " + yIndex + " - " + (startX + (xIndex*pixelWidth)) + "," + (startY - (yIndex*pixelHeight)));
				Point3D pixelCenter = new Point3D(startX + (xIndex*pixelWidth), startY - (yIndex*pixelHeight), _focalLength);
				_pixelVectors.add(new Vector3D(pixelCenter));
			}
		}
	}
	
	private void createRaster()
	{
		float pixelWidth = _ppWidth/_pixWidth;
		float pixelHeight = _ppHeight/_pixHeight;
		
		int pixIndex = 0;
		float minDistance;
		float tempDistance;
		
		for(int yIndex = 0;yIndex < _pixHeight;yIndex++)
		{
			for(int xIndex = 0;xIndex < _pixWidth;xIndex++)
			{
				_pixels.add(new Pixel(xIndex, yIndex, Color.BLACK));
				minDistance = 1000000f;
				
				for(Triangle3D triangle:_triangles)
				{
					tempDistance = triangle.doesIntersect(_pixelVectors.get(pixIndex), _focalLength);
					if(tempDistance >= 0 && tempDistance < minDistance)
					{
						minDistance = tempDistance;
						_pixels.get(pixIndex).setColor(triangle.getColor());
					}
				}
				
				pixIndex++;
			}
		}
	}
	
	public void displayRaster()
	{
		System.out.println("Displaying Raster");
		Display displayRaster = new Display(_pixels, _pixWidth, _pixHeight);
	}
}
