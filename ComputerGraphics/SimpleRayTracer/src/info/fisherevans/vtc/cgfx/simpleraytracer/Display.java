package info.fisherevans.vtc.cgfx.simpleraytracer;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JPanel
{
	private static final long serialVersionUID = -4147453367344608955L;
	
	private JFrame _frame;
	private Raster _raster;
	
	private final int SCALE = 100;
	
	/** Draws a raster given ina  new window
	 * @param raster the raster to draw
	 */
	public Display(Raster raster)
	{
		_raster = raster;
		
		_frame = new JFrame("Raster Display");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setBounds(20, 20, _raster.getWidth()*SCALE+18, _raster.getHeight()*SCALE+45);
		_frame.add(this);
		_frame.setVisible(true);
	}
	
	@Override
	/** Draws the raster given in this jpanel */
	public void paint(Graphics g)
	{
		int width = _raster.getWidth();
		int height = _raster.getHeight();

		for(int xIndex = 0;xIndex < width;xIndex++)
		{
			for(int yIndex = 0;yIndex < height;yIndex++)
			{
				g.setColor(_raster.getPixel(xIndex, yIndex));
				g.fillRect(yIndex*SCALE, (width - xIndex)*SCALE, SCALE, SCALE);
			}
		}
	}
}
