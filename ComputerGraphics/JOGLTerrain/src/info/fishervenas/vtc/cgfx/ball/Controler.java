package info.fishervenas.vtc.cgfx.ball;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class Controler
{
	private JFrame _frame;
	private GLCanvas _canvas;
	private FPSAnimator _animator;
	
	public static BufferedImage map;
	
	private Display _display;
	
	/** Crates a JFrame and sets default settings (title, exit, size). Sefines GL Canvas to draw with. */
	public Controler()
	{
		_frame = new JFrame("Fisher Evans - JOGL Ball");
		GLCapabilities caps = new GLCapabilities(GLProfile.getDefault());
		caps.setDoubleBuffered(true);
        _canvas = new GLCanvas(caps);

        _frame.setSize(600, 600);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.add(_canvas);
        _frame.setVisible(true);
        
        _display = new Display();
        _canvas.addGLEventListener(_display);

        _animator = new FPSAnimator(_canvas, 30);
        _animator.add(_canvas);
        _animator.start();
		
	}
	
	/** Creates a new OpenGL Window 
	 * @throws IOException */
	public static void main(String[] args) throws IOException
	{
		map = ImageIO.read(new File("res/map.bmp"));
		new Controler();
	}
}
