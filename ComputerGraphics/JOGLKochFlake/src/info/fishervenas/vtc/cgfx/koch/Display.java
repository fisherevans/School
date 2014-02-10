package info.fishervenas.vtc.cgfx.koch;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;


/* #####################################
 * 
 * See animated code for details.
 * Same thing, however, starts with a triangle instead of a line. Centered. "update"s periodically to show progression.
 * 
 * ####################################
 */
public class Display implements GLEventListener 
{
	private GLUT _glut;
	private GLU _glu;
	private GL2 _gl;
	
	private float[][] koch;
	
	private int ittrMax = 8;
	private int ittrTime = 1800;
	
	private int ittr = 0;
	private long lastIttr;
	private int timeToIttr;
	
	private void update()
	{
		float[][] newKoch = new float[koch.length*4][2];
		int newKochIndex = 0;
		
		for(int index = 0;index < koch.length;index++)
		{
			float[] a = { 0, 0 },
					b = { 0, 0 },
					c = { 0, 0 },
					d = { 0, 0 },
					e = { 0, 0 },
					slope = { 0, 0 };
			
			a = koch[index];
			e = index+1 == koch.length ? koch[0] : koch[index+1];
			
			slope[0] = e[0]-a[0];
			slope[1] = e[1]-a[1];
			
			float length = (float)Math.sqrt(Math.pow(slope[0], 2) + Math.pow(slope[1], 2));
			
			b[0] = a[0] + slope[0]/3f;
			b[1] = a[1] + slope[1]/3f;

			c[0] = a[0] + slope[0]/2f - slope[1]*0.289f;
			c[1] = a[1] + slope[1]/2f + slope[0]*0.289f;
			
			d[0] = a[0] + 2f*slope[0]/3f;
			d[1] = a[1] + 2f*slope[1]/3f;

			newKoch[newKochIndex++] = a;
			newKoch[newKochIndex++] = b;
			newKoch[newKochIndex++] = c;
			newKoch[newKochIndex++] = d;
		}
		
		koch = newKoch;
		
		System.out.println("Points: " + koch.length);
	}

	private void render(GLAutoDrawable drawable)
	{
		_gl.glClear(_gl.GL_COLOR_BUFFER_BIT);
		_gl.glLoadIdentity();
		
		_gl.glBegin(_gl.GL_LINES);
		for(int index = 0;index < koch.length;index++)
		{
			float[] p1 = koch[index];
			float[] p2 = index+1 == koch.length ? koch[0] : koch[index+1];
			_gl.glVertex2f(p1[0], p1[1]);
			_gl.glVertex2f(p2[0], p2[1]);
		}
		_gl.glEnd();
		
		_gl.glFlush();
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		
		timeToIttr = ittrTime - (int)(System.currentTimeMillis() - lastIttr);
		
		if(ittr < ittrMax && timeToIttr < 0)
		{
			ittr++;
			update();
			lastIttr = System.currentTimeMillis();
		}
		
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
		_gl.glColor3f(1, 1, 1);
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glLoadIdentity();
		_gl.glOrtho(0, 2, 2, 0, 0, 1);
		_glu.gluLookAt(0,0,0, 0,0,0, 0,1,0);
		_gl.glLineWidth(0.2f);
		
		float shift = -0.46f;
		koch = new float [][]{ { -0.8f, 0f+shift }, { 0f, 1.386f+shift }, { 0.8f, 0f+shift } };
		lastIttr = System.currentTimeMillis();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4)
	{
		
	}

}
