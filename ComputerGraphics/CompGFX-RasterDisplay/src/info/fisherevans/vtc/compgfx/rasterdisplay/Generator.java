package info.fisherevans.vtc.compgfx.rasterdisplay;

import java.awt.image.Raster;

public class Generator
{
	private Raster _raster;
	
	public Generator()
	{
		_raster = new Raster();
	}
	
	public static void main(String[] args)
	{
		Generator gen = new Generator();
	}
}
