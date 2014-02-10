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
	
	private int _mapWidth, _mapHeight;
	
	private int[][] ter;
	
	private void update()
	{
		theta += 0.005;
		
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
		_glu.gluLookAt(s*(_mapWidth/2), 16, c*(_mapHeight/2), 0, 3, 0, 0, 1, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_POSITION,  new float[]{ c*10, 20, s*12}, 1);

		_gl.glColor3f(0.05f, 0.75f, 0.15f);
		for(int x = 0;x < _mapWidth - 1; x++)
		{
			_gl.glBegin(_gl.GL_TRIANGLE_STRIP);
			for(int y = 0;y < Controler.map.getHeight(); y++)
			{
				_gl.glVertex3f(x - _mapWidth/2, ter[x][y]/14f, y - _mapHeight/2);
				_gl.glVertex3f(x+1 - _mapWidth/2, ter[x+1][y]/14f, y - _mapHeight/2);
			}
			_gl.glEnd();
		}
		
		/*
		_gl.glColor3f(0f, 0.1f, 0.85f);
		_gl.glBegin(_gl.GL_TRIANGLE_STRIP);
		_gl.glVertex3f(-_mapWidth/2, 3f, -_mapHeight/2);
		_gl.glVertex3f(-_mapWidth/2, 3f, _mapHeight/2);
		_gl.glVertex3f(_mapWidth/2, 3f, _mapHeight/2);
		_gl.glVertex3f(_mapWidth/2, 3f, -_mapHeight/2);
		_gl.glVertex3f(-_mapWidth/2, 3f, -_mapHeight/2);
		_gl.glEnd();
		*/
		
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
		_mapWidth = Controler.map.getWidth();
		_mapHeight = Controler.map.getHeight();
		ter = new int[_mapWidth][_mapHeight];
		
		for(int x = 0;x < _mapWidth; x++)
		{
			for(int y = 0;y < _mapHeight; y++)
			{
				//ter[x][y] = Controler.map.getRGB(x, y) & 0x000000FF;
				ter[x][y] = (int) (Math.random()*255);
				
				System.out.println(ter[x][y]);
			}
		}
		
		_gl = drawable.getGL().getGL2();
		_glu = new GLU();
		_glut = new GLUT();
		
		_gl.glClearColor(0.5f, 0.7f, 1f, 1);
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glLoadIdentity();
		_gl.glEnable(_gl.GL_MULTISAMPLE);
		
		_gl.glEnable(_gl.GL_DEPTH_TEST);

		_gl.glEnable(_gl.GL_COLOR_MATERIAL);
		_gl.glEnable(_gl.GL_LIGHTING);
		_gl.glEnable(_gl.GL_LIGHT0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_AMBIENT, new float[]{ 0.1f, 0.1f, 0.1f, 1f}, 0); // se up lighting
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_SPECULAR,  new float[]{ 0.9f, 0.9f, 0.9f, 1f }, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_DIFFUSE,  new float[]{ 0.3f, 0.3f, 0.3f, 1f }, 0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4)
	{
		
	}

}
