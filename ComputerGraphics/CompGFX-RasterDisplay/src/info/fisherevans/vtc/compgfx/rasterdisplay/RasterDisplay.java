/**
 * RasterDisplay.java
 * Copyright 2010, Craig A. Damon
 * all rights reserved
 */
package info.fisherevans.vtc.compgfx.rasterdisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;


/**
 * RasterDisplay - display a raster on the screen
 * @author Craig A. Damon
 *
 */
public class RasterDisplay extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2963986640023140053L;


	public RasterDisplay(Raster raster)
	{
		_raster = raster;
	}
	
	public Dimension getPreferredSize()
	{
		int height = _raster.getColumns()*64;
		int width = _raster.getRows()*64;
		while (height > _raster.getColumns() && (height > 800 || width > 800))
			{
				height /= 2;
				width /= 2;
			}
		return new Dimension(height,width);
	}
	
	public Dimension getMinimumSize()
	{
		return new Dimension(_raster.getColumns(),_raster.getRows());
	}
	
	/**
	 * description
	 * @param g
	 */
	protected void paintComponent(Graphics g)
	{
		int pixelWidth = getWidth()/_raster.getColumns();
		int pixelHeight = getHeight()/_raster.getRows();
		for (int i = 0; i < _raster.getColumns(); i++)
			{
				for (int j = 0; j < _raster.getRows(); j++)
					{
						Pixel pixel = _raster.getPixel(i,j);
						g.setColor(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue()));
						g.fillRect(i*pixelWidth, j*pixelHeight, pixelWidth, pixelHeight);
					}
			}
	}
	

	private Raster _raster;
}