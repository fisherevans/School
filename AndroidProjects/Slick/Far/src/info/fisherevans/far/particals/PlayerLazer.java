package info.fisherevans.far.particals;

public class PlayerLazer
{
	private float dx, dy, v, x, y, r, b, g, angle;
	
	public PlayerLazer(float angle, float v, float x, float y, float r, float b, float g)
	{
		this.v = v;
		this.x = x;
		this.y = y;
		this.r = r;
		this.b = b;
		this.g = g;
		this.angle = angle;

		dx = (float) (Math.cos(Math.toRadians(angle)) * v);
		dy = (float) (Math.sin(Math.toRadians(angle)) * v);
	}
	
	public void update(int delta)
	{
		x += dx*delta;
		y += dy*delta;
	}
	
	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getAngle()
	{
		return angle;
	}
	
	public float getR()
	{
		return r;
	}
	public float getB()
	{
		return b;
	}
	public float getG()
	{
		return g;
	}
}
