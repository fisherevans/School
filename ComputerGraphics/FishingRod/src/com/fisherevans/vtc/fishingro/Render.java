package com.fisherevans.vtc.fishingro;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.gl2.GLUT;

public class Render implements GLEventListener 
{
	
	private GLUT _glut;
	private GLU _glu;
	private GL2 _gl;

	private final float BEND_DELTA = 0.005f;
	
	private float _whipSale = 0.85f,
				  _whipSpeed = 0.3f,
				  _bendFini = 0.9f,
				  _dir = 1,
				  _rot = 0;
	
	private boolean _bending = true;
	
	private float _startPoints[] = {
		-0.8f, 0, 0,
		-0.2f, 0, 0,
		0, 0, 0,
		0.8f, 0.8f, 0
	};
	
	private FloatBuffer _pointBuffer;

	public void init(GLAutoDrawable drawable)
	{
		_gl = drawable.getGL().getGL2();
		_glu = new GLU();
		_glut = new GLUT();

		_gl.glClearColor(0, 0, 0, 1);
		_gl.glShadeModel(_gl.GL_FLAT);
		
		_gl.glEnable(_gl.GL_MAP1_VERTEX_3);
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
		if(_bending)
		{
			_rot -= BEND_DELTA;
			if(Math.abs(_rot) > Math.abs(_bendFini))
				_bending = false;
		}
		else
		{
			_rot += _dir*_whipSpeed;
			if(Math.abs(_rot) > Math.abs(_bendFini) && sign(_rot) == sign(_dir))
			{
				_bendFini *= _whipSale;
				_whipSpeed *= _whipSale;
				_dir *= -1;
			}
		}
		
		_startPoints[9] = (float) Math.cos(_rot)*0.8f;
		_startPoints[10] = (float) Math.sin(_rot)*0.8f;
		
		_pointBuffer = GLBuffers.newDirectFloatBuffer(_startPoints);
		_gl.glMap1f(_gl.GL_MAP1_VERTEX_3, 0, 1, 3, 4, _pointBuffer);
	}
	
	private void draw(GLAutoDrawable drawable)
	{
        if(_bending)
        {
        	_gl.glColor3f(1, 1, 1);
            _gl.glLineWidth(2.0f); 
            
        	_gl.glBegin(_gl.GL_LINE_STRIP);
        		_gl.glVertex3f(_startPoints[9], _startPoints[10], _startPoints[11]);
        		_gl.glVertex3f(1, _rot-0.5f, 0);
        	_gl.glEnd();
        	
            _gl.glLineWidth(6.0f); 
        	_gl.glColor3f(0.55f, 0.2f, 0.1f);
        }

        _gl.glBegin(_gl.GL_LINE_STRIP);
        for (int i=0; i<=14; i++)
            _gl.glEvalCoord1f((float)(i/14f));
        _gl.glEnd();
        
        _gl.glFlush();
	}
	
	public int sign(float x)
	{
		int sign = x < 0 ? -1 : 1;
		return sign;
	}

	public void dispose(GLAutoDrawable drawable) { }
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) { }

}
