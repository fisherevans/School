package com.fisherevans.vtc.terrain;

import java.awt.GradientPaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class Render implements GLEventListener, KeyListener
{
	
	private GLUT _glut;
	private GLU _glu;
	private GL2 _gl;
	
	private float _rotation = 0;
	private float _sin, _cos;
	
	private float[][] _terrain;
	private float[][] _slopes;
	private float[][][] _colors;
	private float[][][] _normals;
	
	private final int TER_W = 256,
					  TER_H = 256;
	private final int TER_H_W = TER_W/2,
			  		  TER_H_H = TER_H/2;
	private final int TER_MAX_H = 60;
	private final int WATER_LVL = (int) (TER_MAX_H*0.4f);
	
	private float _cx = TER_H_W,
				  _cy = TER_MAX_H,
				  _cz = TER_H_H,
				  _ca = 0,
				  _cd = TER_H_H,
				  _csin = 0,
				  _ccos = 1;

	public void init(GLAutoDrawable drawable)
	{
		_gl = drawable.getGL().getGL2();
		_glu = new GLU();
		_glut = new GLUT();
		
		generateTerrain();

		_gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
		_gl.glMatrixMode(_gl.GL_PROJECTION);
		_gl.glEnable(_gl.GL_DEPTH_TEST);

		_gl.glEnable(_gl.GL_LIGHTING);
		
		_gl.glEnable(_gl.GL_LIGHT0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_AMBIENT, new float[]{ 0.1f, 0.1f, 0.1f, 1 }, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_SPECULAR,  new float[]{ 0.5f, 0.5f, 0.5f, 1 }, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_DIFFUSE,  new float[]{ 0.8f, 0.8f, 0.8f, 1 }, 0);
		_gl.glEnable(_gl.GL_COLOR_MATERIAL);
		
		_gl.glShadeModel(_gl.GL_SMOOTH);
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
		_rotation += 0.5;
		_rotation %= 360;
		
		float rotR = (float) Math.toRadians(_rotation);
		_sin = (float) Math.sin(rotR);
		_cos = (float) Math.cos(rotR);
	}
	
	private void draw(GLAutoDrawable drawable)
	{
		float[] lPos = { TER_H_W*_cos, -TER_MAX_H*1.25f, TER_H_H*_sin, 1 };
		float[] nlPos = { 0, 1, 0 };
		_glu.gluPerspective(60, 1, 1, 512);
		_glu.gluLookAt(_csin*_cd, _cy, _ccos*_cd, 0, 0, 0, 0, 1, 0);
		_gl.glLightfv(_gl.GL_LIGHT0, _gl.GL_POSITION, lPos, 0);

		_gl.glColor3f(0.4f, 0.7f, 0.2f);
		for(int x = 0;x < TER_W - 1;x++)
		{
			_gl.glBegin(_gl.GL_TRIANGLE_STRIP);
			
			for(int z = 0;z < TER_H;z++)
			{
				_gl.glNormal3fv(_normals[x][z], 0);
				_gl.glColor3fv (_colors[x][z], 0);
				_gl.glVertex3f (x-TER_H_W, _terrain[x][z], z-TER_H_H);
				
				_gl.glNormal3fv(_normals[x+1][z], 0);
				_gl.glColor3fv (_colors[x+1][z], 0);
				_gl.glVertex3f (x+1-TER_H_W, _terrain[x+1][z], z-TER_H_H);
			}
			
			_gl.glEnd();
		}
		
		_gl.glEnable(_gl.GL_BLEND);
		_gl.glBlendFunc(_gl.GL_SRC_ALPHA, _gl.GL_ONE_MINUS_SRC_ALPHA);
		_gl.glColor4f(50/255f, 50/255f, 80/255f, 0.9f);
		for(int x = 0;x < TER_W - 1;x++)
		{
			_gl.glBegin(_gl.GL_TRIANGLE_STRIP);
			for(int z = 0;z < TER_H;z++)
			{
				_gl.glVertex3f(x-TER_H_W, WATER_LVL, z-TER_H_H);
				_gl.glVertex3f(x+1-TER_H_W, WATER_LVL, z-TER_H_H);
			}
			_gl.glEnd();
		}
		_gl.glDisable(_gl.GL_BLEND);
	}
	
	private float[] getColor(float h, float s)
	{
		float[] c = { 0, 0, 0 };
		float hr = h/((float)TER_MAX_H);

		if(hr > 0.85f)
		{
			c = new float[] {
					(230/255f),
					(230/255f),
					(230/255f),
			};
		}
		if(hr > 0.8f)
		{
			float i = ((h/TER_MAX_H)-0.8f)/0.05f;
			c = new float[] {
					(89/255f)*(1f-i) + (230/255f)*i,
					(125/255f)*(1f-i) + (230/255f)*i,
					(77/255f)*(1f-i) + (230/255f)*i,
			};
		}
		else if(hr > 0.45f)
		{
			c = new float[] {
					(89/255f),
					(125/255f),
					(77/255f),
			};
		}
		else if(hr > 0.38f)
		{
			float i = ((h/TER_MAX_H)-0.38f)/0.07f;
			c = new float[] {
					(236/255f)*(1f-i) + (89/255f)*i,
					(238/255f)*(1f-i) + (125/255f)*i,
					(182/255f)*(1f-i) + (77/255f)*i,
			};
		}
		else
		{
			c = new float[] {
					(116/255f),
					(85/255f),
					(51/255f),
			};
		}
		return c;
	}
	
	private void generateTerrain()
	{
		int r = (int) (Math.random()*999999); // 99 - 
		Random rand = new Random(r);
		System.out.println("Seed: " + r);

		_terrain = new float[TER_W][TER_H];
		_slopes = new float[TER_W][TER_H];
		_colors = new float[TER_W][TER_H][3];
		_normals = new float[TER_W][TER_H][3];
		
		for(int x = 0;x < TER_W;x++)
		{
			for(int z = 0;z < TER_H;z++)
			{
				_terrain[x][z] = (float) rand.nextFloat();
				//System.out.println(_terrain[x][z]);
			}
		}

		_terrain = generatePerlinNoise(_terrain, 7);
		//_terrain = levelTerrain(_terrain, 1);
		//_terrain = scaleTerrain(_terrain);
		_terrain = levelTerrain(_terrain, TER_MAX_H);
		_terrain = gausBlur(_terrain, 1);
		
		_slopes = getSlopes(_terrain);
		_normals = getNormals(_terrain);

		for(int x = 0;x < TER_W;x++)
		{
			for(int z = 0;z < TER_H;z++)
			{
				_colors[x][z] = getColor(_terrain[x][z], _slopes[x][z]);
			}
		}
	}
	
	private float[][] scaleTerrain(float[][] src)
	{
		for(int x = 0;x < TER_W;x++)
		{
			for(int z = 0;z < TER_H;z++)
			{
				src[x][z] = (float) (4f*Math.pow(src[x][z]-0.45f, 3)+0.4f+3*src[x][z]);
			}
		}
		return src;
	}
	
	private float[][] getSlopes(float[][] src)
	{
		float[][] slopes = new float[src.length][src[0].length];
		
		for(int x = 0;x < src.length;x++)
		{
			for(int y = 0;y < src[0].length;y++)
			{
				float total = 0; int count = 0;
				if(x-1 >= 0) { total += Math.abs(src[x][y] - src[x-1][y]); count++; }
				if(x+1 < src.length) { total += Math.abs(src[x][y] - src[x+1][y]); count++; }
				if(y-1 >= 0) { total += Math.abs(src[x][y] - src[x][y-1]); count++; }
				if(y+1 > src[0].length) { total += Math.abs(src[x][y] - src[x][y+1]); count++; }
				slopes[x][y] = total/((float)count);
				//System.out.println(slopes[x][y]);
			}
		}
		
		return slopes;
	}
	
	private float[][][] getNormals(float[][] src)
	{
		float[][][] normals = new float[src.length][src[0].length][3];
		for(int x = 1;x < src.length - 1;x++)
		{
			for(int z = 1;z < src[0].length - 1;z++)
			{
				float total[] = { 0, 0, 0 };
				float ts[][][] = new float[6][3][3];
				
				ts[0][0][0] = x;
				ts[0][0][1] = src[x][z];
				ts[0][0][2] = z;

				ts[0][1][0] = x-1;
				ts[0][1][1] = src[x-1][z-1];
				ts[0][1][2] = z-1;

				ts[0][2][0] = x-1;
				ts[0][2][1] = src[x-1][z];
				ts[0][2][2] = z;
				

				ts[1][0][0] = x;
				ts[1][0][1] = src[x][z];
				ts[1][0][2] = z;

				ts[1][1][0] = x-1;
				ts[1][1][1] = src[x-1][z-1];
				ts[1][1][2] = z-1;

				ts[1][2][0] = x;
				ts[1][2][1] = src[x][z-1];
				ts[1][2][2] = z-1;
				

				ts[2][0][0] = x;
				ts[2][0][1] = src[x][z];
				ts[2][0][2] = z;

				ts[2][1][0] = x-1;
				ts[2][1][1] = src[x-1][z+1];
				ts[2][1][2] = z+1;

				ts[2][2][0] = x;
				ts[2][2][1] = src[x][z+1];
				ts[2][2][2] = z+1;
				

				ts[3][0][0] = x;
				ts[3][0][1] = src[x][z];
				ts[3][0][2] = z;

				ts[3][1][0] = x;
				ts[3][1][1] = src[x][z-1];
				ts[3][1][2] = z-1;

				ts[3][2][0] = x+1;
				ts[3][2][1] = src[x+1][z];
				ts[3][2][2] = z;
				

				ts[4][0][0] = x;
				ts[4][0][1] = src[x][z];
				ts[4][0][2] = z;

				ts[4][1][0] = x+1;
				ts[4][1][1] = src[x+1][z];
				ts[4][1][2] = z;

				ts[4][2][0] = x+1;
				ts[4][2][1] = src[x+1][z+1];
				ts[4][2][2] = z+1;
				

				ts[5][0][0] = x;
				ts[5][0][1] = src[x][z];
				ts[5][0][2] = z;

				ts[5][1][0] = x+1;
				ts[5][1][1] = src[x+1][z+1];
				ts[5][1][2] = z+1;

				ts[5][2][0] = x;
				ts[5][2][1] = src[x][z+1];
				ts[5][2][2] = z+1;
				
				for(int in = 0;in < 6;in++)
				{
					float[] n = getNormal(ts[in]);
					total[0] += n[0];
					total[1] += n[1];
					total[2] += n[2];
				}

				normals[x][z][0] = total[0]/6f;
				normals[x][z][1] = total[1]/6f;
				normals[x][z][2] = total[2]/6f;
			}
		}
		return normals;
	}
	
	private float[] getNormal(float[][] t)
	{
		float[] u = new float[3], v = new float[3], n = new float[3];
		for(int x = 0;x < 3;x++) { u[x] = t[1][x] - t[0][x]; }
		for(int x = 0;x < 3;x++) { v[x] = t[2][x] - t[0][x]; }
		for(int x = 0;x < 3;x++) { n[x] = u[(x+1)%3]*v[(x+2)%3] - u[(x+2)%3]*v[(x+1)%3]; }
		return n;
	}
	
	private float[][] generateSmoothNoise(float[][] baseNoise, int octave)
	{
		float[][] smoothNoise = new float[TER_W][TER_H];
		int samplePeriod = 1 << octave;
		float sampleFrequency = 1f / samplePeriod;

		for(int x = 0;x < TER_W;x++)
		{
			
			int sample_i0 = (x / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % TER_W; //wrap around
			float horizontal_blend = (x - sample_i0) * sampleFrequency;
			
			for(int z = 0;z < TER_H;z++)
			{
				//calculate the vertical sampling indices
				int sample_j0 = (z / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % TER_H; //wrap around
				float vertical_blend = (z - sample_j0) * sampleFrequency;

				//blend the top two corners
				float top = interpolate(baseNoise[sample_i0][sample_j0],
				baseNoise[sample_i1][sample_j0], horizontal_blend);

				//blend the bottom two corners
				float bottom = interpolate(baseNoise[sample_i0][sample_j1],
				baseNoise[sample_i1][sample_j1], horizontal_blend);

				//final blend
				smoothNoise[x][z] = interpolate(top, bottom, vertical_blend);
			}
		}
		
		return smoothNoise;
	}
	
	private float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount)
	{
	 
	   float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
	 
	   float persistance = 0.5f;
	 
	   //generate smooth noise
	   for (int i = 0; i < octaveCount; i++)
	   {
	       smoothNoise[i] = generateSmoothNoise(baseNoise, i);
	   }
	 
	    float[][] perlinNoise = new float[TER_W][TER_H];
	    float amplitude = 1.0f;
	    float totalAmplitude = 0.0f;
	 
	    //blend noise together
	    for (int octave = octaveCount - 1; octave >= 0; octave--)
	    {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	 
	       for (int i = 0; i < TER_W; i++)
	       {
	          for (int j = 0; j < TER_H; j++)
	          {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	          }
	       }
	    }
	 
	   //normalisation
	   for (int i = 0; i < TER_W; i++)
	   {
	      for (int j = 0; j < TER_H; j++)
	      {
	         perlinNoise[i][j] /= totalAmplitude;
	      }
	   }
	 
	   return perlinNoise;
	}
	
	private float interpolate(float x, float y, float prog)
	{
		return x*(1-prog) + prog*y;
	}
	
	private float[][] levelTerrain(float[][] orig, float scale)
	{
		float[][] newA = new float[orig.length][orig[0].length];
		
		float min = 1,
			  max = 0;
		
		for(int x = 0;x < orig.length;x++)
		{
			for(int y = 0;y < orig[0].length;y++)
			{
				min = orig[x][y] < min ? orig[x][y] : min;
				max = orig[x][y] > max ? orig[x][y] : max;
			}
		}
		
		for(int x = 0;x < orig.length;x++)
		{
			for(int y = 0;y < orig[0].length;y++)
			{
				newA[x][y] = (orig[x][y] - min)*(1f/(max-min))*scale;
			}
		}
		
		return newA;
	}

	private float[][] gausBlur(float[][] orig, int pix)
	{
		float[][] newA = new float[orig.length][orig[0].length];
		
		for(int x = 0;x < orig.length;x++)
		{
			for(int y = 0;y < orig[0].length;y++)
			{
				int num = 0;
				float height = 0;

				for(int x2 = biggest(0, x-pix);x2 < smallest(orig.length, x+pix);x2++)
				{
					for(int y2 = biggest(0, y-pix);y2 < smallest(orig.length, y+pix);y2++)
					{
						if(Math.sqrt(Math.pow(x-x2, 2) + Math.pow(y-y2, 2)) <= pix)
						{
							num++;
							height += orig[x2][y2];
						}
					}
				}
				
				newA[x][y] = num == 0 ? 0 : height/((float)(num));
			}
		}
		
		return newA;
	}
	
	private int biggest(int x, int y)
	{
		if(x > y)
			return x;
		else
			return y;
	}
	
	private int smallest(int x, int y)
	{
		if(x < y)
			return x;
		else
			return y;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		//System.out.println(e.getKeyChar());
		switch(e.getKeyChar())
		{
			case 'w': _cd--; break;
			case 's': _cd++; break;
			case 'a': _ca--; break;
			case 'd': _ca++; break;
			case 'q': _cy--; break;
			case 'e': _cy++; break;
		}
		_ca %= 360;
		
		float rotR = (float) Math.toRadians(_ca);
		_csin = (float) Math.sin(rotR);
		_ccos = (float) Math.cos(rotR);
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }
	@Override
	public void keyTyped(KeyEvent arg0) { }
	@Override
	public void dispose(GLAutoDrawable drawable) { }
	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) { }


}
