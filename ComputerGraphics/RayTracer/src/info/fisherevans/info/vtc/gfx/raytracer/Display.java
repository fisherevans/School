package info.fisherevans.info.vtc.gfx.raytracer;

import info.fisherevans.info.vtc.gfx.raytracer.components.Pixel;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JPanel
{
	private static final long serialVersionUID = -4147453367344608955L;
	
	private JFrame _frame;
	private ArrayList<Pixel> _pixels;
	
	private int _scale;
	
	/** Draws a raster given in a new window
	 * @param raster the raster to draw
	 */
	public Display(ArrayList<Pixel> pixels, int width, int height, int scale)
	{
		_pixels = pixels;
		_scale = scale;
		
		_frame = new JFrame("Raster Display");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.add(this);
		_frame.setBounds(20, 20, width*_scale+18, height*_scale+45);
		_frame.setVisible(true);
		
		_frame.toFront();
	}
	
	@Override
	/** Draws the raster given in this jpanel
	 * @param g The graphics element to draw to.
	 */
	public void paint(Graphics g)
	{
		for(Pixel pixel:_pixels)
		{
			g.setColor(pixel.getColor());
			g.fillRect(pixel.getDisplayX()*_scale, pixel.getDisplayY()*_scale, _scale, _scale);
		}
	}
}
