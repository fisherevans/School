package info.fisherevans.info.vtc.gfx.raytracer.components;

import java.awt.Color;

public class Light
{
	private Color _color;
	private Point _position;
	private float _distance;
	
	/** Created the light object
	 * @param position The x,y,z of the light
	 * @param color The color intensity of the light
	 */
	public Light(Point position, Color color, float distance)
	{
		_color = color;
		_position = position;
		_distance = distance;
	}

	/** @return the position of the light */
	public Point getPosition() { return _position; }
	
	/** @return the distance th light can reach */
	public float getDistance() { return _distance; }
	
	/** @return the color intensity of the light */
	public Color getColor() { return _color; }
	
	/** @return a string describing the lights attributes in a details way. */
	public String toString()
	{
		return "[Position: " + _position.getX() + ", " + _position.getY() + ", " + _position.getZ() + "][Color: RGB(" + _color.getRed() + "," + _color.getGreen() + "," + _color.getBlue() + ")]";
	}
}
