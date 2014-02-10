package com.fisherevans.vtc.terrain;

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

public class Window
{
	private JFrame _frame;
	private GLCanvas _canvas;
	private GLCapabilities _cap;
	private FPSAnimator _animator;
	
	private Render _render;
	
	/** Crates a JFrame and sets default settings (title, exit, size). Sefines GL Canvas to draw with. 
	 * @throws IOException */
	public Window() throws IOException
	{
		_frame = new JFrame("Fisher Evans - JOGL Ball");
		_cap = new GLCapabilities(GLProfile.getDefault());
		_cap.setDoubleBuffered(true);
        _canvas = new GLCanvas(_cap);

        _frame.setSize(1000, 1000);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.add(_canvas);
        _frame.setVisible(true);
        
        BufferedImage map = ImageIO.read(new File("res/map.bmp"));
        _render = new Render(map);
        _canvas.addGLEventListener(_render);

        _animator = new FPSAnimator(_canvas, 30);
        _animator.add(_canvas);
        _animator.start();
	}
	
	/** Creates a new OpenGL Window 
	 * @throws IOException */
	public static void main(String[] args) throws IOException
	{
		new Window();
	}
}
