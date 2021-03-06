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
	
	/** Crates a JFrame and sets default settings (title, exit, size). Sefines GL Canvas to draw with. */
	public Window()
	{
		_frame = new JFrame("Fisher Evans - JOGL Ball");
		_cap = new GLCapabilities(GLProfile.getDefault());
		_cap.setDoubleBuffered(true);
        _canvas = new GLCanvas(_cap);

        _frame.setSize(921*2, 529*2);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.add(_canvas);
        _frame.setVisible(true);
        
        _render = new Render();
        _canvas.addGLEventListener(_render);

        _animator = new FPSAnimator(_canvas, 30);
        _animator.add(_canvas);
        _animator.start();
        
        _canvas.addKeyListener(_render);
        _frame.addKeyListener(_render);
	}
	
	/** Creates a new OpenGL Window 
	 * @throws IOException */
	public static void main(String[] args) throws IOException
	{
		new Window();
	}
}
