package info.fisherevans.info.vtc.compgfx.raytracing;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JPanel
{
	private static final long serialVersionUID = -8991990059989773205L;

	private JFrame _frame;
	
	private ArrayList<Pixel> _pixels;
	
	private int _width, _height;
	
	public Display(ArrayList<Pixel> pixels, int width, int height)
	{
		_pixels = pixels;
		_height = height;
		_width = width;
		
		
		_frame = new JFrame("Raster Display");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setBounds(20, 20, _width+20, _height+50);
		_frame.add(this);
		_frame.setVisible(true);
	}

	public void paint(Graphics g)
	{
		for(Pixel pix:_pixels)
		{
			g.setColor(pix.getColor());
			g.fillRect(pix.getX(), pix.getY(), 1, 1);
		}
	}
}
