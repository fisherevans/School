/**
 * RenderWindow.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package vtc.cd.jogl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * RenderWindow - description
 * @author Craig A. Damon
 *
 */
abstract public class RenderWindow extends JFrame implements GLEventListener
{
	protected RenderWindow() throws Exception
	{
		this("Sample window");
	}
	protected RenderWindow(String name) throws Exception
	{
		this(name,1500);
	}
  protected RenderWindow(String name,double distance) throws Exception
 	 {
 		 super(name);
 		 _distance = distance;
 		 createCanvas();
 		GLCapabilities capabilities = new GLCapabilities(null);
    capabilities.setRedBits(8);
    capabilities.setBlueBits(8);
    capabilities.setGreenBits(8);
    capabilities.setAlphaBits(8);
 	   getContentPane().setLayout(new BorderLayout());
 	   getContentPane().add(_canvas,BorderLayout.CENTER);
 	   //_image = new ImagePanel();
 	   //getContentPane().add(_image,BorderLayout.EAST);
 	   addWindowListener(new WindowAdapter(){
 				@Override
 				public void windowClosing(WindowEvent e)
 				{
 	        System.exit(0);				
 				}
 	      });
 	 }

	/** description
	 * 
	 */
	private void createCanvas()
	{
		_canvas = new GLJPanel();
		_canvas.setPreferredSize(new Dimension(1000,1000));
		_canvas.addGLEventListener(this);
	}
	
	/**
	 * @param arg0
	 * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	abstract public void display(GLAutoDrawable canvas);

	/**
	 * @param arg0
	 * @see javax.media.opengl.GLEventListener#dispose(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void dispose(GLAutoDrawable canvas)
	{
		// nothing to do here
	}

	/**
	 * @param arg0
	 * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void init(GLAutoDrawable canvas)
	{
		GL2 gl = (GL2) _canvas.getGL();
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LEQUAL);
	  GLU glu = new GLU();
    gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
    gl.glLoadIdentity();

    localInit(gl);
    
    // Perspective.
    float widthHeightRatio = (float) getWidth() / (float) getHeight();
    glu.gluPerspective(45, widthHeightRatio, 1, 2000);
    glu.gluLookAt(0, 0, _distance, 0, 0, 0, 0, 1, 0);
	}
	
	protected void localInit(GL2 gl)
	{
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable, int, int, int, int)
	 */
	@Override
	public void reshape(GLAutoDrawable canvas, int arg1, int arg2, int arg3,
			int arg4)
	{
		// TODO Auto-generated method stub
		
	}
  
  private GLJPanel _canvas;
  private double _distance;
 }
