/**
 * Light1.java
 * Copyright 2012, Craig A. Damon
 * all rights reserved
 */
package vtc.cd.jogl;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.fixedfunc.GLMatrixFunc;

import com.jogamp.opengl.util.gl2.GLUT;


/**
 * Light1 - description
 * @author Craig A. Damon
 *
 */
public class Light1 extends RenderWindow
{

/**
	 * @param name
	 * @throws Exception
	 */
	protected Light1(String name) throws Exception
		{
			super(name,3);
			pack();
			setVisible(true);
		}

private float[] cube_color = {1,0,0,1};
private FloatBuffer cube_color_buffer = FloatBuffer.wrap(cube_color);




protected void localInit(GL2 gl)
{
  float[] light_pos = { 1,2,3,1};
  float[] ambient_color = { 0.4f,0.4f,0.4f,1};
  // add a light, enabling it first
  gl.glEnable(GL2.GL_LIGHTING);
  gl.glEnable(GL2.GL_LIGHT0);
  gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, FloatBuffer.wrap(light_pos));
  gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_AMBIENT, FloatBuffer.wrap(ambient_color));
}

	/**
	 * @param canvas
	 * @see edu.vtc.cis4210.RenderWindow#display(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void display(GLAutoDrawable canvas)
	{
	  GL2 gl = (GL2) canvas.getGL();
	  GLUT glut = new GLUT();

	  // Change back to model view matrix.
	  gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
	  gl.glLoadIdentity();
	  gl.glClear(GL.GL_COLOR_BUFFER_BIT| GL.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
	   gl.glRotatef(45,0,1,0);
	   gl.glRotatef(30,1,0,0);
	   gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_DIFFUSE,cube_color_buffer);
	   gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,cube_color_buffer);
	   glut.glutSolidCube(1.0f);
	  gl.glPopMatrix();

	}

	/** description
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		new Light1("Java lighting example");
	}

}
