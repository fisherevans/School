package com.fisherevans.poolsim;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/30/13
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pocket
{
    // http://billiards.colostate.edu/threads/physics.html
    public static final float DEFAULT_RADIUS = 3f;

    private Vector2f _position;
    private float _radius, _diameter;

    public Pocket(Vector2f position, float radius)
    {
        _position = position;
        _radius = radius;
        _diameter = _radius*2;
    }

    public Pocket(float x, float y)
    {
        this(new Vector2f(x, y), DEFAULT_RADIUS);
    }

    public boolean isIn(Ball ball)
    {
        float dist = position().copy().sub(ball.position()).length();
        return dist < radius();
    }

    /** GETTERS AND SETTERS ARE BELOW **/

    public Vector2f position()
    {
        return _position;
    }

    public float radius()
    {
        return _radius;
    }

    public float diameter()
    {
        return _diameter;
    }

    public void setRadius(float _radius)
    {
        this._radius = _radius;
    }

    public void setPosition(Vector2f _position)
    {
        this._position = _position;
    }
}
