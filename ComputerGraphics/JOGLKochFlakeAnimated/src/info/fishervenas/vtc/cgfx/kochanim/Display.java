package info.fishervenas.vtc.cgfx.kochanim;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class Display implements GLEventListener 
{
	private GLUT _glut;
	private GLU _glu;
	private GL2 _gl;
	
	private float[][] koch;
	
	private int ittrMax = 7;
	
	private int ittr = 0;

	float scale = 1;
	float scale2 = 1;
	float angle = 0;
	float h = 0;
	
	long lastUpdate;
	int updateTime = 20;
	
	private void ittrKoch()
	{
		float[][] newKoch = new float[koch.length*4][2]; // create a new array to hold the new points
		int newKochIndex = 0;
		
		for(int index = 0;index < koch.length;index++) // loop through each old point
		{
			float[] a = { 0, 0 }, // 5 points between next point and current point.in thirds and half.
					b = { 0, 0 },
					c = { 0, 0 },
					d = { 0, 0 },
					e = { 0, 0 },
					slope = { 0, 0 };
			
			a = koch[index];
			e = index+1 == koch.length ? koch[0] : koch[index+1];
			
			slope[0] = e[0]-a[0]; // get slope for pushing out c
			slope[1] = e[1]-a[1];
			
			b[0] = a[0] + slope[0]/3f;
			b[1] = a[1] + slope[1]/3f;

			c[0] = a[0] + slope[0]/2f - slope[1]*0.289f; // ratio of "slope" to length of opp of <CBD
			c[1] = a[1] + slope[1]/2f + slope[0]*0.289f;
			
			d[0] = a[0] + 2f*slope[0]/3f;
			d[1] = a[1] + 2f*slope[1]/3f;

			newKoch[newKochIndex++] = a; // add the points
			newKoch[newKochIndex++] = b;
			newKoch[newKochIndex++] = c;
			newKoch[newKochIndex++] = d;
		}
		
		koch = newKoch; // passing new array
		
		System.out.println("Points: " + koch.length);
	}
	
	public void update()
	{

		scale = scale > 3f ? (scale - 2f)*1.015f : scale*1.015f; // zoom in
		scale2 = scale2 < 1f ? (scale2 + 2f)*0.98f : scale2*0.9852f; // zoom out
		angle = angle > 360 ? (angle % 360) + 1 : angle + 1;
		h = (h % 1) + 0.005f;
	}

	private void render(GLAutoDrawable drawable)
	{
		_gl.glClear(_gl.GL_COLOR_BUFFER_BIT);
		_gl.glLoadIdentity();
		
		Color rgb = Color.getHSBColor(h, 1, 1);
		float r = rgb.getRed()/255f, 
			  g = rgb.getGreen()/255f, 
			  b = rgb.getBlue()/255f;

		_gl.glColor3f(g*0.2f, r*0.2f, b*0.2f);
		_gl.glBegin(_gl.GL_POLYGON);
		_gl.glVertex2f(1, 1);
		_gl.glVertex2f(-1, 1);
		_gl.glVertex2f(-1, -1);
		_gl.glVertex2f(1, -1);
		_gl.glEnd();
		
		_gl.glColor3f(r, g, b);
		_gl.glRotatef(angle, 0, 0, 1);
		_gl.glLineWidth((r+b)*2);
		_gl.glScalef(scale, scale, 1); // loop through all points, draw a line between each point. Line_loop draws random lines after 800ish points
		_gl.glBegin(_gl.GL_LINES);
		for(int index = 0;index < koch.length;index++)
		{
			float[] p1 = koch[index];
			float[] p2 = index+1 == koch.length ? koch[0] : koch[index+1];
			_gl.glVertex2f(p1[0], p1[1]);
			_gl.glVertex2f(p2[0], p2[1]);
			_gl.glVertex2f(p1[0], -p1[1]);
			_gl.glVertex2f(p2[0], -p2[1]);
		}
		_gl.glEnd();
		
		_gl.glLoadIdentity();
		
		_gl.glColor3f(b, r, g);
		_gl.glRotatef(angle+45, 0, 0, 1);
		_gl.glLineWidth((b+g)*2);
		_gl.glScalef(scale2, scale2, 1); // loop through all points, draw a line between each point. Line_loop draws random lines after 800ish points
		_gl.glBegin(_gl.GL_LINES);
		for(int index = 0;index < koch.length;index++)
		{
			float[] p1 = koch[index];
			float[] p2 = index+1 == koch.length ? koch[0] : koch[index+1];
			_gl.glVertex2f(p1[0], p1[1]);
			_gl.glVertex2f(p2[0], p2[1]);
			_gl.glVertex2f(p1[0], -p1[1]);
			_gl.glVertex2f(p2[0], -p2[1]);
		}
		_gl.glEnd();
		_gl.glFlush();
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		if(System.currentTimeMillis() - lastUpdate > updateTime)
		{
			lastUpdate = System.currentTimeMillis();
			update();
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
		_gl.glOrtho(-1.0,1.0,-1.0,1.0,-1.0,1.0);
		_gl.glLineWidth(0.2f);
		_gl.glEnable(_gl.GL_BLEND); _gl.glBlendFunc(_gl.GL_SRC_ALPHA, _gl.GL_ONE_MINUS_SRC_ALPHA);
		
		//float shift = -0.57733f; // shift so tip is at 0,0
		//koch = new float [][]{ { -1f, 0f+shift }, { 1f, 0f+shift } };
		float shift = -1.386f;
		koch = new float [][]{ { -0.8f, 0f+shift }, { 0f, 1.386f+shift }, { 0.8f, 0f+shift } };
		
		lastUpdate = System.currentTimeMillis();
		
		while(ittr < ittrMax) // generate ittrs (enough so it seems seemless.
		{
			ittr++;
			ittrKoch();
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4)
	{
		
	}

}
