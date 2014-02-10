package com.fisherevans.vtc.rubiks;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.fisherevans.vtc.rubiks.Block.Color;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.gl2.GLUT;

public class Render implements GLEventListener, MouseListener, KeyListener
{
	private GLU _glu;
	private GL2 _gl;
	
	private final int RENDER = 1, SELECT = 2;
	private int _cmd = RENDER;
	
	private IntBuffer _selBuf;
	
	private ArrayList<Block> _blocks;
	
	private double mx = 0, my = 0;
	private int _selected = -1;
	
	private boolean[] _wasd = { false, false, false, false };
	private int _vA = 0, _hA = 0;

	private final float BORDER_SIZE = 0.1f;
	private final float STICKER_SCALE = 1.001f;
	private final double PICK_SIZE = 0.01;
	private static final int BUFSIZE = 512;
	
	public Render(GLCanvas canvas)
	{
		canvas.addMouseListener(this);
		canvas.addKeyListener(this);
	}

	public void init(GLAutoDrawable drawable)
	{
		_gl = drawable.getGL().getGL2();
		_glu = new GLU();
		
		generateCube();
		
		_gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
		_gl.glEnable(_gl.GL_DEPTH_TEST);
	}
	
	public void display(GLAutoDrawable drawable)
	{
		update();

		_gl.glClear(_gl.GL_COLOR_BUFFER_BIT);
		_gl.glClear(_gl.GL_DEPTH_BUFFER_BIT);
		_gl.glLoadIdentity();
		
		if(_cmd == RENDER)
		{
		    _gl.glRenderMode(_gl.GL_RENDER);
		    setupCamera();
			draw(drawable);
		    _gl.glFlush();
		}
		else if(_cmd == SELECT)
		{
		    pick(drawable);
		}
	}
	
	private void update()
	{
		_vA = _wasd[2] ? _vA-3 : _vA;
		_vA = _wasd[0] ? _vA+3 : _vA;
		_hA = _wasd[1] ? _hA+3 : _hA;
		_hA = _wasd[3] ? _hA-3 : _hA;

		_vA = _vA > 90 ? 90 : _vA;
		_vA = _vA < -90 ? -90 : _vA;
		_hA %= 360;
	}
	
	private void draw(GLAutoDrawable drawable)
	{
		_gl.glPushName(999);
		
		for(int index = 0;index < _blocks.size();index++)
		{
			_gl.glLoadName(index);
		    _gl.glBegin(_gl.GL_QUADS);
		    _gl.glColor3fv(_blocks.get(index).getColor(), 0);
		    _gl.glVertex3fv(_blocks.get(index).getColored()[0], 0);
		    _gl.glVertex3fv(_blocks.get(index).getColored()[1], 0);
		    _gl.glVertex3fv(_blocks.get(index).getColored()[2], 0);
		    _gl.glVertex3fv(_blocks.get(index).getColored()[3], 0);
		    if(_selected == index) { _gl.glColor3f(1, 0, 1); }
		    else { _gl.glColor3f(0, 0, 0); }
		    _gl.glVertex3fv(_blocks.get(index).getBlack()[0], 0);
		    _gl.glVertex3fv(_blocks.get(index).getBlack()[1], 0);
		    _gl.glVertex3fv(_blocks.get(index).getBlack()[2], 0);
		    _gl.glVertex3fv(_blocks.get(index).getBlack()[3], 0);
		    _gl.glEnd();
		}
		
	    _gl.glPopName();
	}

	private void pick(GLAutoDrawable drawable)
	{
        int[] viewPort = new int[4];
        _gl.glGetIntegerv(_gl.GL_VIEWPORT, viewPort, 0);
        
		_selBuf = GLBuffers.newDirectIntBuffer(BUFSIZE);
		_gl.glSelectBuffer(BUFSIZE, _selBuf);

		_gl.glRenderMode(_gl.GL_SELECT);
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glPushMatrix();
		_gl.glLoadIdentity();
		
		_glu.gluPickMatrix(mx, viewPort[3]-my, PICK_SIZE, PICK_SIZE, viewPort, 0);
		setupCamera();
		_gl.glMatrixMode(_gl.GL_MODELVIEW);
		_gl.glInitNames();
		
		draw(drawable);
		
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glPopMatrix();
		_gl.glMatrixMode(_gl.GL_MODELVIEW);
		
		int hits = _gl.glRenderMode(_gl.GL_RENDER);

		_selected = getHit(hits, _selBuf);
		_cmd = RENDER;
	}

	private void setupCamera()
	{
		_glu.gluPerspective(60, 1, 1, 128);
		_glu.gluLookAt(0, 0, 11, 0, 0, 0, 0, 1, 0);
		_gl.glRotatef(_vA, 1, 0, 0);
		_gl.glRotatef(_hA, 0, 1, 0);
	}
	
    public int getHit(int hits, IntBuffer buffer)
    {
    	int name = -1;
    	float lowest = 99999f;
    	int offset = 0;
    	int names;
    	float z1;
    	for (int i = 0;i < hits;i++)
		{
			names = buffer.get(offset); offset++;
			z1 = (float) (buffer.get(offset)& 0xffffffffL) / 0x7fffffff; offset += 2;
			if(z1 < lowest)
			{
				lowest = z1;
				for(int j = 0;j < names;j++)
				{
					name = buffer.get(offset);
					offset++;
				}
			}
		}
    	return name;
	}
    
    public void generateCube()
    {
    	// Proudest part of my code. All original content - not once googled.
    	// That came off as "sarcasmy". It wasn't sarcasm.
    	
		_blocks = new ArrayList<Block>(); // Arraylist of "blocks" (each of the 54 faces)
    	Color[] colors = { Color.Blue, Color.White, Color.Red, Color.Green, Color.Yellow, Color.Orange }; // Face colors
    	int mods[][] = { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 2, 0 } }; // 3 possible "sides", perp to x axis, y axis or z axis
    	int side = 3; // turns negative for last 3 colors (other side of origin).
    	int mod = 0; // cycles through each possible "side" per colors.
    	
    	
    	for(int color = 0;color < 6;color++)
    	{
    		side = color < 3 ? 3 : -3; // Last three or neg side of origin
    		mod++; // incr. mod with color.
    		mod %= 3; // Repeat each axis orien. for both pos. and neg.
    		
    		for(int x = -3;x < 3;x += 2) // Then a simple loop through all 9 squares
    		{
    			for(int y = -3;y < 3;y += 2)
    			{
    				// generate the black "background"
    				float[][] black = new float[4][3]; 
    				black[0][mods[mod][0]] = x;
    				black[0][mods[mod][1]] = y;
    				black[0][mods[mod][2]] = side;
    				
    				black[1][mods[mod][0]] = x+2;
    				black[1][mods[mod][1]] = y;
    				black[1][mods[mod][2]] = side;
    				
    				black[2][mods[mod][0]] = x+2;
    				black[2][mods[mod][1]] = y+2;
    				black[2][mods[mod][2]] = side;
    				
    				black[3][mods[mod][0]] = x;
    				black[3][mods[mod][1]] = y+2;
    				black[3][mods[mod][2]] = side;
    				
    				// Then the sticker. Has BorderSide - Sticks out a little (clipping)
    				float[][] colored = new float[4][3]; 
    				colored[0][mods[mod][0]] = x+BORDER_SIZE;
    				colored[0][mods[mod][1]] = y+BORDER_SIZE;
    				colored[0][mods[mod][2]] = side*STICKER_SCALE;
    				
    				colored[1][mods[mod][0]] = x+2-BORDER_SIZE;
    				colored[1][mods[mod][1]] = y+BORDER_SIZE;
    				colored[1][mods[mod][2]] = side*STICKER_SCALE;
    				
    				colored[2][mods[mod][0]] = x+2-BORDER_SIZE;
    				colored[2][mods[mod][1]] = y+2-BORDER_SIZE;
    				colored[2][mods[mod][2]] = side*STICKER_SCALE;
    				
    				colored[3][mods[mod][0]] = x+BORDER_SIZE;
    				colored[3][mods[mod][1]] = y+2-BORDER_SIZE;
    				colored[3][mods[mod][2]] = side*STICKER_SCALE;
    				
    				// add it to the block list.
    	    		_blocks.add(new Block(colors[color], black, colored)); 
    			}
    		}
    	}
    }

	public void mousePressed(MouseEvent mouse)
	{
		mx = mouse.getPoint().x;
		my = mouse.getPoint().y;
		_cmd = SELECT;
	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyChar())
		{
		case 'w': _wasd[0] = true; break;
		case 'a': _wasd[1] = true; break;
		case 's': _wasd[2] = true; break;
		case 'd': _wasd[3] = true; break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyChar())
		{
		case 'w': _wasd[0] = false; break;
		case 'a': _wasd[1] = false; break;
		case 's': _wasd[2] = false; break;
		case 'd': _wasd[3] = false; break;
		}
	}

	public void keyTyped(KeyEvent e) { }
	public void dispose(GLAutoDrawable drawable) { }
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) { }
	public void mouseClicked(MouseEvent arg0) { }
	public void mouseEntered(MouseEvent arg0) { }
	public void mouseExited(MouseEvent arg0) { }
	public void mouseReleased(MouseEvent arg0) { }

}
