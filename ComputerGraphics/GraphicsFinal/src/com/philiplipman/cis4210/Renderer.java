package com.philiplipman.cis4210;
/**
 * 
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.GLBuffers;

import objects.block.Block;
import objects.block.BlockType;
import objects.block.Coordinate;
import objects.world.World;
import utils.Color;

import java.awt.Component; 
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author	Ost
 */

public class Renderer implements GLEventListener, KeyListener, MouseListener, MouseMotionListener
{  
	private static final int BUFSIZE = 512;
	
	private static int TOP = 0;
	private static int BOTTOM = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	private static int FRONT = 4;
	private static int BACK = 5;
	
	private float _rotateX = 0;
    private float _rotateY = 0;
    
    private int _worldX = 20;
    private int _worldY = 20;
    private int _worldZ = 20;
    
	private final int RENDER = 1;
	private final int SELECT = 2;
	private int _cmd = RENDER;
	private int _selected = -1;
	
	private int _mouseClicked = 0;
	
    private int _names = 0;
    
    private Point pickPoint = new Point();
        
    private World world = new World(_worldX, _worldY, _worldZ);
    
    private Map<Integer, Coordinate> squareIdToCoordinate = new HashMap<Integer, Coordinate>();

    private int _mouseButton;
    private BlockType _selectedBlockType = BlockType.STONE;
    	
    /**
     * 
     */
    public void display(GLAutoDrawable drawable) 
    {
   	
    	update();
        render(drawable);
        
    	_names = 0;
    	
    	GL2 gl = drawable.getGL().getGL2();
    	
    	update();
        
		
		if(_cmd == RENDER)
		{
	        render(drawable);

		}
		else if(_cmd == SELECT)
		{
			pickRects(drawable);
		}

    }

    /**
     * 
     */
    public void dispose(GLAutoDrawable drawable) 
    {
    	
    }

    /**
     * 
     * Name:		lighting
     * Date:		Dec 13, 2012
     * Comments:	sets up lighting
     * @param gl
     */
    private void lighting( GL2 gl )
    {
    	float[] mat_specular = {0.3f, 0.3f, 0.3f, 1.0f};
    	float[] mat_shininess = { 10.0f };
    	
    	float[] lightPos = {50005, 30000, 50000, 1};
    	
        gl.glEnable(GLLightingFunc.GL_LIGHTING);
        gl.glEnable(GLLightingFunc.GL_LIGHT0);
        
        float[] noAmbient ={ 0.05f, 0.05f, 0.05f, 1f }; // low ambient light
        float[] spec =    { .5f, 0.5f, 0f, 1f }; // low ambient light
        float[] diffuse ={ 1f, 1f, 1f, 1f };
        
        // properties of the light
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, noAmbient, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, spec, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightPos, 0);
        
        
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);       
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, mat_shininess, 0);
        gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
        
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        
        gl.glShadeModel(GL2.GL_SMOOTH);
    }
    
    /**
     * 
     */
    public void init(GLAutoDrawable drawable) 
    {
    	world.initWorld();
    	
    	GL2 gl = drawable.getGL().getGL2();
    	GLU glu = new GLU();
    	
		lighting(gl);

    	gl.glMatrixMode(GL2.GL_PROJECTION);
    	
    	((Component) drawable).addKeyListener(this);
    	((Component) drawable).addMouseListener(this);
    	((Component) drawable).addMouseMotionListener(this);
    	
    	gl.glLoadIdentity();

    	glu.gluPerspective(55, 1, 1, 64);
    	glu.gluLookAt(0, 0, 30, 0, 0, 0, 0, 1, 0);
    	        
    	gl.glEnable(GL2.GL_DEPTH_TEST);

    }
    
    /**
     * 
     * Name:		pickRects
     * Date:		Dec 13, 2012
     * Comments:	gets surface name
     * @param drawable
     */
    private void pickRects(GLAutoDrawable drawable)
    {
    	
    	GL2 gl = drawable.getGL().getGL2();
    	GLU glu = new GLU();

    	int viewport[] = new int[4];
    	
    	IntBuffer buffer = GLBuffers.newDirectIntBuffer(BUFSIZE);
    	
    	gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

    	gl.glSelectBuffer(BUFSIZE, buffer);
    	gl.glRenderMode(GL2.GL_SELECT);

    	gl.glInitNames();

    	// draw a triangle filling the window
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        gl.glPushMatrix();
        
        gl.glLoadIdentity();
                       
        gl.glRotatef(_rotateX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(_rotateY, 0.0f, 1.0f, 0.0f);

        float x = -(_worldX * Block.BLOCKSIZE)/2;
        float y = -(_worldY * Block.BLOCKSIZE)/2;
        float z = -(_worldZ * Block.BLOCKSIZE)/2;
        
        gl.glTranslatef(x, y, z);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
    	gl.glPushMatrix();
    	gl.glLoadIdentity();
    	/* create 5x5 pixel picking region near cursor location */
    	glu.gluPickMatrix((double) pickPoint.x,
    			(double) (viewport[3] - pickPoint.y), //
    			0.01, 0.01, viewport, 0);
    	
    	glu.gluPerspective(55, 1, 1, 64);
    	glu.gluLookAt(0, 0, 30, 0, 0, 0, 0, 1, 0);
    	
		gl.glMatrixMode(GL2.GL_MODELVIEW);

    	gl.glPushName(999);
    	
    	blockRender(drawable);
    	
    	gl.glPopName();
		gl.glMatrixMode(GL2.GL_PROJECTION);
    	
    	gl.glPopMatrix();
    	
		gl.glMatrixMode(GL2.GL_MODELVIEW);
    	
		gl.glPopMatrix();
	
    	int hits = gl.glRenderMode(GL2.GL_RENDER);

    	processHits(hits, buffer);
    	
		_cmd = RENDER;
		
		getBlock();

    }
 
    /**
     * 
     * Name:		processHits
     * Date:		Dec 13, 2012
     * Comments:	
     * @param hits
     * @param buffer
     */
    private void processHits(int hits, IntBuffer buffer)
    {
    	//int names = -1;
    	int ptr = 0;
    	Integer lowest = Integer.MAX_VALUE;
    	Integer z;
    	//System.out.println("hits = " + hits);
    	
    	_selected = -1;
    	
		// ptr = (GLuint *) buffer;
    	for (int i = 0; i < hits; i++)
    	{ 
    		/* for each hit */
    		//names = buffer.get(ptr);
    		//System.out.println(" number of names for hit = " + names);
    		ptr++;
    		z = buffer.get(ptr);
    		ptr++;
    		//System.out.println(" z2 is " + buffer.get(ptr));
    		ptr++;
    		
    		//System.out.print("\n   the name is ");
    		
    		if(z < lowest)
    		{
    			lowest = z;
    			_selected = buffer.get(ptr);
    		}
    		
    		ptr++;
    		
    	}
    	

		//_selected = buffer.get(ptr);
   	
    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) 
    {
    	
    }

    private void update() 
    {

    }

    private void render(GLAutoDrawable drawable) 
    {
        GL2 gl = drawable.getGL().getGL2();
    	GLU glu = new GLU();
    	
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT + GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        gl.glRotatef(_rotateX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(_rotateY, 0.0f, 1.0f, 0.0f);
        
        float x = -(_worldX * Block.BLOCKSIZE)/2;
        float y = -(_worldY * Block.BLOCKSIZE)/2;
        float z = -(_worldZ * Block.BLOCKSIZE)/2;
        
        gl.glTranslatef(x, y, z);
    	
        gl.glPushMatrix();
                          	
    	blockRender(drawable);
                
        gl.glPopMatrix();
        
    }

    public void blockRender(GLAutoDrawable drawable)
    {
        int i = 0;
    	
    	for(int x = 0; x < 20; x++)
        {
        	for(int y = 0; y < 20; y++)
        	{
        		for(int z = 0; z < 20; z++)
        		{
					
        			//Block block = world[x][y][z];
					
        			Block block = world.getBlock(x, y, z);
        			block.blockId = i;
					
					if(block.getType() != BlockType.EMPTY)
					{
						//System.out.println("TYPE: " + block.getType());
						squares(drawable, block);
					}
					//System.out.println("hello, world");
					i++;
        			
        		}
        	}
        }

    }
    
    public void squares(GLAutoDrawable drawable, Block block)
    {

    	int indexX = block.x;
    	int indexY = block.y;
    	int indexZ = block.z;
    	
    	int squareId = 0;
    	
    	Color color = block.getColor();
    	
        float size = Block.BLOCKSIZE;

        List<Float> point1 = new ArrayList<Float>();
        List<Float> point2 = new ArrayList<Float>();
        List<Float> point3 = new ArrayList<Float>();
        List<Float> point4 = new ArrayList<Float>();
        
        //Front
        point1 = Arrays.asList(new Float[]{indexX * size + size, indexY * size, indexZ * size});
        point2 = Arrays.asList(new Float[]{indexX * size, indexY * size, indexZ * size});
        point3 = Arrays.asList(new Float[]{indexX * size, indexY * size - size, indexZ * size});                 
        point4 = Arrays.asList(new Float[]{indexX * size + size, indexY * size - size, indexZ * size});    
                
        squareId = drawSquare(drawable, color, point1, point2, point3, point4, FRONT);
        block.frontName = squareId;
        squareIdToCoordinate.put(squareId, new Coordinate(block.x, block.y, block.z));
        
        //back
        point1 = Arrays.asList(new Float[]{indexX * size + size, indexY * size, indexZ * size - size});
        point2 = Arrays.asList(new Float[]{indexX * size, indexY * size, indexZ * size - size});
        point3 = Arrays.asList(new Float[]{indexX * size, indexY * size - size, indexZ * size - size});              
        point4 = Arrays.asList(new Float[]{indexX * size + size, indexY * size - size, indexZ * size - size});
               
        squareId = drawSquare(drawable, color, point1, point2, point3, point4, BACK);
        block.backName = squareId;
        squareIdToCoordinate.put(squareId, new Coordinate(block.x, block.y, block.z));
        
        //left
        point1 = Arrays.asList(new Float[]{indexX * size, indexY * size, indexZ * size - size});
        point2 = Arrays.asList(new Float[]{indexX * size, indexY * size, indexZ * size});
        point3 = Arrays.asList(new Float[]{indexX * size, indexY * size - size, indexZ * size});              
        point4 = Arrays.asList(new Float[]{indexX * size, indexY * size - size, indexZ * size - size});              
        
        squareId = drawSquare(drawable, color, point1, point2, point3, point4, LEFT);
        block.leftName = squareId;
        squareIdToCoordinate.put(squareId, new Coordinate(block.x, block.y, block.z));
   
        //right
        point1 = Arrays.asList(new Float[]{indexX * size + size, indexY * size, indexZ * size});
        point2 = Arrays.asList(new Float[]{indexX * size + size, indexY * size, indexZ * size - size});
        point3 = Arrays.asList(new Float[]{indexX * size + size, indexY * size - size, indexZ * size - size});
        point4 = Arrays.asList(new Float[]{indexX * size + size, indexY * size - size, indexZ * size});

        squareId = drawSquare(drawable, color, point1, point2, point3, point4, RIGHT);
        block.rightName = squareId;
        squareIdToCoordinate.put(squareId, new Coordinate(block.x, block.y, block.z));

        //top
        point1 = Arrays.asList(new Float[]{indexX * size + size, indexY * size, indexZ * size});
        point2 = Arrays.asList(new Float[]{indexX * size, indexY * size, indexZ * size});
        point3 = Arrays.asList(new Float[]{indexX * size, indexY * size, indexZ * size - size});
        point4 = Arrays.asList(new Float[]{indexX * size + size, indexY * size, indexZ * size - size});
                
        squareId = drawSquare(drawable, color, point1, point2, point3, point4, TOP);
        block.topName = squareId;
        squareIdToCoordinate.put(squareId, new Coordinate(block.x, block.y, block.z));

        //bottom
        point1 = Arrays.asList(new Float[]{indexX * size + size, indexY * size - size, indexZ * size});
        point2 = Arrays.asList(new Float[]{indexX * size, indexY * size - size, indexZ * size});
        point3 = Arrays.asList(new Float[]{indexX * size, indexY * size - size, indexZ * size - size});
        point4 = Arrays.asList(new Float[]{indexX * size + size, indexY * size - size, indexZ * size - size});
        
        squareId = drawSquare(drawable, color, point1, point2, point3, point4, BOTTOM);
        block.bottomName = squareId;
        squareIdToCoordinate.put(squareId, new Coordinate(block.x, block.y, block.z));
        
    }
    
    public int drawSquare(GLAutoDrawable drawable, Color color, List<Float> point1, List<Float> point2, List<Float> point3, List<Float> point4, int face)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glLoadName(_names);

        int name = _names;

        gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
        gl.glShadeModel(GL2.GL_SMOOTH);
        if(_names == _selected)
        {
            gl.glColor4f(color.R() + 0.7f, color.G() + 0.7f, color.B() + 0.7f, color.Alpha());
        }
        else
        {
            gl.glColor4f(color.R(), color.G(), color.B(), color.Alpha());
        }

        gl.glBegin(GL2.GL_POLYGON);
        	
        	switch(face)
        	{
            	case 0:
            	{
                	gl.glNormal3f(0.0f, 1.0f, 0.0f);        		
            		break;
            	}
            	case 1:
            	{
                	gl.glNormal3f(0.0f, -1.0f, 0.0f);        		
            		break;
            	}
            	case 2:
            	{
                	gl.glNormal3f(-1.0f, 0.0f, 0.0f);        		
            		break;
            	}
            	case 3:
            	{
                	gl.glNormal3f(1.0f, 0.0f, 0.0f);        		
            		break;
            	}
            	case 4:
            	{
                	gl.glNormal3f(0.0f, 0.0f, 1.0f);        		
            		break;
            	}
            	case 5:
            	{
                	gl.glNormal3f(0.0f, 0.0f, -1.0f);        		
            		break;
            	}
        	}
        	
            gl.glVertex3f(point1.get(0), point1.get(1), point1.get(2));
            gl.glVertex3f(point2.get(0), point2.get(1), point2.get(2));
            gl.glVertex3f(point3.get(0), point3.get(1), point3.get(2));
            gl.glVertex3f(point4.get(0), point4.get(1), point4.get(2));
            
        gl.glEnd();
        
        
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glLineWidth(4.0f);
        gl.glBegin(GL2.GL_LINES);
        	gl.glVertex3f(point1.get(0), point1.get(1), point1.get(2));
            gl.glVertex3f(point2.get(0), point2.get(1), point2.get(2));
                
            gl.glVertex3f(point2.get(0), point2.get(1), point2.get(2));
            gl.glVertex3f(point3.get(0), point3.get(1), point3.get(2));
                
            gl.glVertex3f(point3.get(0), point3.get(1), point3.get(2));
            gl.glVertex3f(point4.get(0), point4.get(1), point4.get(2));
                
            gl.glVertex3f(point4.get(0), point4.get(1), point4.get(2));
            gl.glVertex3f(point1.get(0), point1.get(1), point1.get(2));
        gl.glEnd();
    	
        _names++;
        return name;
    }

	public void keyPressed(KeyEvent key)
	{
		// TODO Auto-generated method stub
		switch(key.getKeyChar())
		{
    		case 'w':
    		{
    			_rotateX += 5.0f;
    			break;
    		}
    		case 's':
    		{
    			_rotateX -= 5.0f;
    			break;
    		}
    		
    		case 'a':
    		{
    			_rotateY -= 5.0f;
    			break;
    		}
    		case 'd':
    		{
    			_rotateY += 5.0f;
    			break;
    		}
    		case '1':
    		{
    			System.out.println("Stone");
    			_selectedBlockType = BlockType.STONE;
    			break;
    		}
    		case '2':
    		{
    			System.out.println("Grass");
    			_selectedBlockType = BlockType.GRASS;
    			break;
    		}
    		case '3':
    		{
    			System.out.println("Dirt");
    			_selectedBlockType = BlockType.DIRT;
    			break;
    		}
    		case 't':
    		{
    			try
				{
					world.writeWorld();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		case 'g':
    		{
    			try
				{
    				world.initWorldFromFile();
				} 
    			catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    			catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		case 'r':
    		{
    			world.initWorld();
    		}
		}
		//System.out.println("Pressed: " + key.getKeyChar());
		
	}

	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouse)
	{
		// TODO Auto-generated method stub
		//System.out.println("x: " + mouse.getPoint().x + " y: " + mouse.getPoint().y);
	}

	@Override
	public void mouseEntered(MouseEvent mouse)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouse)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouse)
	{
		// TODO Auto-generated method stub
		
		//System.out.println("button: " + mouse.getButton());
		_mouseButton = mouse.getButton();
		pickPoint.x = mouse.getPoint().x;
		pickPoint.y = mouse.getPoint().y;
		_mouseClicked = 1;
		_cmd = SELECT;		
		//getBlock();

		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
		
	public void getBlock()
	{
    	if(_selected > -1 && _mouseClicked == 1)
    	{
        	Coordinate coord = squareIdToCoordinate.get(_selected);
        	//Block block = world[coord.x][coord.y][coord.z];       	
        	Block block = world.getBlock(coord.x, coord.y, coord.z);       	
        	
        	if(_mouseButton == 3)
        	{
    			world.addBlock(new Block(BlockType.EMPTY, block.x, block.y, block.z), block.x, block.y, block.z);
        		
        	}
        	else if(_mouseButton == 1)
        	{
            	if(block.bottomName == _selected && block.y - 1 >= 0)
            	{
        			world.addBlock(new Block(_selectedBlockType, block.x, block.y - 1, block.z), block.x, block.y - 1, block.z);
    
            	}
            	else if(block.topName == _selected && block.y < _worldY - 1)
            	{
        			world.addBlock(new Block(_selectedBlockType, block.x, block.y + 1, block.z), block.x, block.y + 1, block.z);
        			
            	}
            	else if(block.leftName == _selected && block.x - 1 >= 0)
            	{
        			world.addBlock(new Block(_selectedBlockType, block.x - 1, block.y, block.z), block.x - 1, block.y, block.z);
        			
            	}
            	else if(block.rightName == _selected && block.x < _worldX - 1)
            	{
        			world.addBlock(new Block(_selectedBlockType, block.x + 1, block.y, block.z), block.x + 1, block.y, block.z);
        			
            	}
            	else if(block.frontName == _selected && block.z < _worldZ - 1)
            	{
        			world.addBlock(new Block(_selectedBlockType, block.x, block.y, block.z + 1), block.x, block.y, block.z + 1);
        			
            	}
            	else if(block.backName == _selected && block.z - 1 >= 0)
            	{
        			world.addBlock(new Block(_selectedBlockType, block.x, block.y, block.z - 1), block.x, block.y, block.z - 1);
            	}
        	}	
        	_selected = -1;
    	}
    	
    	_mouseClicked = 0;
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent mouse)
	{
		// TODO Auto-generated method stub
		//System.out.println("mousex: " + mouse.getPoint().x);
		//System.out.println("mousey: " + mouse.getPoint().y);
		//pickPoint.y = mouse.getPoint().y;
		//System.out.println("button: " + mouse.getButton());
		//_mouseButton = mouse.getButton();
		pickPoint.x = mouse.getPoint().x;
		pickPoint.y = mouse.getPoint().y;
		_cmd = SELECT;	
		
	}
}
