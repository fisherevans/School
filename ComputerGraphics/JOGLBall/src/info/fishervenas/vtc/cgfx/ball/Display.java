package info.fishervenas.vtc.cgfx.ball;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class Display implements GLEventListener 
{
	private float theta = 0;
	private float s = 0;
	private float c = 0;
	
	private float v = 0,
				  a = -0.01f,
				  p = 4,
				  squish = 1;
	
	private GLUT _glut;
	private GLU _glu;
	private GL2 _gl;
	
	private void update()
	{
		theta += 0.01;
		
		s = (float) Math.sin(theta);
		c = (float) Math.cos(theta);
	}

	private void render(GLAutoDrawable drawable)
	{
		v += a;  // adjust velocity via acceleration
		p += v; // adjust position via velocity
		
		if(p < -4.5) // check for bounce back
		{
			p = -4.5f; // keep above 4.5
			v = 0.35f; // Reset velocity to bounce back wiht - DOESN'T SPEED UP
		}

		if(p < -4) { squish = 1 + (p + 4); } // calculate squish amount
		else { squish = 1; }
		
		_gl.glClear(_gl.GL_COLOR_BUFFER_BIT);
		_gl.glClear(_gl.GL_DEPTH_BUFFER_BIT);
		_gl.glLoadIdentity();
		_glu.gluPerspective(55, 1, 1, 80);
		_glu.gluLookAt(c*10, -3, s*10, 0, -2, 0, 0, 1, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_POSITION,  new float[]{ 10, 5, 12, 1 }, 0);

		_gl.glColor3d(s, -s + 0.5, c + 0.5); // draw the colored sphere
		_gl.glTranslatef(0, p, 0);
		_gl.glScalef(1, squish, 1);
		_glut.glutSolidSphere(1, 30, 30);
		_gl.glScalef(1, 1.0f/squish, 1);
		_gl.glTranslatef(0, -p, 0);

		_gl.glColor3d(0.6f, 0.6f, 0.6f); // draw the cube it bounces on.
		_gl.glTranslatef(0, -6f, 0);
		_glut.glutSolidCube(2);
		
		_gl.glFlush();
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		update();
		render(drawable);
	}

	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		
	}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		_gl = drawable.getGL().getGL2();
		_glu = new GLU();
		_glut = new GLUT();
		
		_gl.glClearColor(0, 0, 0, 0);
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glLoadIdentity();
		_gl.glEnable(_gl.GL_MULTISAMPLE);

		_gl.glEnable(_gl.GL_DEPTH_TEST);
		_gl.glEnable(_gl.GL_LIGHTING);
		_gl.glEnable(_gl.GL_LIGHT0);
		_gl.glEnable(_gl.GL_COLOR_MATERIAL);

		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_AMBIENT, new float[]{ 0.2f, 0.2f, 0.2f }, 0); // se up lighting
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_SPECULAR,  new float[]{ 0.9f, 0.9f, 0.9f }, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_DIFFUSE,  new float[]{ 0.4f, 0.4f, 0.2f }, 0);
		

		_gl.glMaterialfv(_gl.GL_FRONT, _gl.GL_SPECULAR, new float[]{ 0.5f, 0.5f, 0.5f, 1 }, 0); // set materials for ball and cube
		_gl.glMaterialf(_gl.GL_FRONT, _gl.GL_SHININESS, 5000f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4)
	{
		
	}

}
