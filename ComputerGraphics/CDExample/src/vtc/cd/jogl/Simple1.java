/**
 * Simple1.java
 * Copyright 2012, Craig A. Damon
 * all rights reserved
 */
package vtc.cd.jogl;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.fixedfunc.GLMatrixFunc;

/**
 * Simple1 - description
 * @author Craig A. Damon
 *
 */
public class Simple1 extends RenderWindow
{
	
/**
	 * @param name
	 * @throws Exception
	 */
	protected Simple1(String name) throws Exception
		{
			super(name,2);
			pack();
			setVisible(true);
		}

public void display(GLAutoDrawable canvas)
{
  GL2 gl = (GL2) canvas.getGL();

  // Change back to model view matrix.
  gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
  gl.glLoadIdentity();
  gl.glClear(GL.GL_COLOR_BUFFER_BIT| GL.GL_DEPTH_BUFFER_BIT);
	gl.glPushMatrix();
	gl.glBegin(GL2.GL_POLYGON);
    gl.glVertex2f(-0.5f,-0.5f);
    gl.glVertex2f(-0.5f,0.5f);
    gl.glVertex2f(0.5f,0.5f);
    gl.glVertex2f(0.5f,-0.5f);
  gl.glEnd();  
  gl.glPopMatrix();
}

	/** description
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		new Simple1("Java example simple1");

	}

}
