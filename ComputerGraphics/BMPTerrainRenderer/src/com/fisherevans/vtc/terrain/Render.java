package com.fisherevans.vtc.terrain;

import java.awt.image.BufferedImage;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class Render implements GLEventListener 
{
	
	private GLUT _glut;
	private GLU _glu;
	private GL2 _gl;
	
	private float _rotation = 0;
	private float _sin, _cos;
	
	private final float R_SCALE = 2f,
						G_SCALE = 0.5f,
						B_SCALE = 0.05f;
	
	private float[][] _map;
	private int _mapW, _mapH, _mapHW, _mapHH, _avgH;
	
	private float _cX, _cY, _cZ;
	
	public Render(BufferedImage map)
	{
		_mapW = map.getWidth();
		_mapH = map.getHeight();
		_mapHW = _mapW/2;
		_mapHH = _mapH/2;
		
		_map = new float[_mapW][_mapH];
		
		double total = 0;
		for(int w = 0;w < _mapW;w++)
		{
			for(int h = 0;h < _mapH;h++)
			{
				int rgb = map.getRGB(w, h);
				int red = (rgb >> 16) & 0x000000FF;
				int green = (rgb >>8 ) & 0x000000FF;
				int blue = (rgb) & 0x000000FF;
				_map[w][h] = red*R_SCALE + green*G_SCALE + blue*B_SCALE;
				total += _map[w][h];
			}
		}
		_avgH = (int) (total/((double)(_mapW*_mapH)));
		
		System.out.println("Avg Height: " + _avgH);
	}
	
	public void init(GLAutoDrawable drawable)
	{
		_gl = drawable.getGL().getGL2();
		_glu = new GLU();
		_glut = new GLUT();

		_gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glEnable(_gl.GL_DEPTH_TEST);

		_gl.glEnable(_gl.GL_LIGHTING);
		
		_gl.glEnable(_gl.GL_LIGHT0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_AMBIENT, new float[]{ 0.2f, 0.2f, 0.2f, 1 }, 0);
		//_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_SPECULAR,  new float[]{ 0.8f, 0.8f, 0.8f, 1 }, 0);
		//_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_DIFFUSE,  new float[]{ 0.4f, 0.4f, 0.4f, 1 }, 0);

		_gl.glEnable(_gl.GL_COLOR_MATERIAL);
		//_gl.glColorMaterial(_gl.GL_FRONT_AND_BACK, _gl.GL_AMBIENT_AND_DIFFUSE);
		//_gl.glMaterialfv(_gl.GL_FRONT, _gl.GL_SPECULAR, new float[]{ 0.8f, 0.8f, 0.8f, 1 }, 0); // set materials for ball and cube
		//_gl.glMaterialf(_gl.GL_FRONT, _gl.GL_SHININESS, 100f);
	}
	
	public void display(GLAutoDrawable drawable)
	{
		_gl.glClear(_gl.GL_COLOR_BUFFER_BIT);
		_gl.glClear(_gl.GL_DEPTH_BUFFER_BIT);
		_gl.glLoadIdentity();
		
		update();
		draw(drawable);
	}
	
	private void update()
	{
		_rotation += 0.25;
		_rotation %= 360;
		
		float rotR = (float) Math.toRadians(_rotation);
		_sin = (float) Math.sin(rotR);
		_cos = (float) Math.cos(rotR);
		
		//System.out.println("Angle: " + _rotation + " - Sin: " + _sin + " - Cos: " + _cos);
	}
	
	private void draw(GLAutoDrawable drawable)
	{
		float[] lPos = { _mapHW*_cos, 152, _mapHH*_sin, 1 };
		_glu.gluPerspective(55, 1, 1, 500);
		_glu.gluLookAt(_mapHW*_sin, 500, _mapHH*_cos, 0, _avgH, 0, 0, 1, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_POSITION, lPos, 0);
		
		_gl.glColor3f(0.4f, 0.7f, 0.2f);
		for(int w = 0;w < _mapW - 1;w++)
		{
			_gl.glBegin(_gl.GL_TRIANGLE_STRIP);
			
			for(int h = 0;h < _mapH;h++)
			{
				float h1 = _map[w][h],
					  h2 = _map[w+1][h];
				_gl.glVertex3f(w-_mapHW, h1, h-_mapHH);
				_gl.glVertex3f(w+1-_mapHW, h2, h-_mapHH);
			}
			
			_gl.glEnd();
		}
		
		_gl.glColor3f(0.1f, 0.2f, 0.9f);
		for(int x = 0;x < _mapW - 1;x++)
		{
			_gl.glBegin(_gl.GL_TRIANGLE_STRIP);
			for(int z = 0;z < _mapH;z++)
			{
				_gl.glVertex3f(x - _mapHW, _avgH, z - _mapHH);
				_gl.glVertex3f(x+1 - _mapHW, _avgH, z - _mapHH);
			}
			
			_gl.glEnd();
		}
	}
	
	public void dispose(GLAutoDrawable drawable) { }
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) { }

}
