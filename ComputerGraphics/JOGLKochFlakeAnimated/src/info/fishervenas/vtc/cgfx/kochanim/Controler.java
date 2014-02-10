package info.fishervenas.vtc.cgfx.kochanim;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class Controler
{
	private JFrame _frame;
	private GLCanvas _canvas;
	private FPSAnimator _animator;
	
	private Display _display;

	/** Crates a JFrame and sets default settings (title, exit, size). Sefines GL Canvas to draw with. */
	public Controler()
	{
		_frame = new JFrame("Fisher Evans - Koch Flake");
        _canvas = new GLCanvas(new GLCapabilities(GLProfile.getDefault()));

        _frame.setSize(600, 600);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.add(_canvas);
        _frame.setVisible(true);
        
        _display = new Display();
        _canvas.addGLEventListener(_display);

        _animator = new FPSAnimator(_canvas, 40);
        _animator.add(_canvas);
        _animator.start();
		
	}

	/** Creates a new OpenGL Window */
	public static void main(String[] args)
	{
		new Controler();
	}
}
