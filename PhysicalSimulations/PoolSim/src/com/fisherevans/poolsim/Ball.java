package com.fisherevans.poolsim;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/30/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ball
{
    // http://billiards.colostate.edu/threads/physics.html
    public static final float DEFAULT_MASS = 0.170097f,
                              RESTITUTION = 0.95f,
                              DEFAULT_RADIUS = 1.125f,
                              TABLE_MU = 0.2f;

    private Vector2f _position, _velocity;
    private float _mass;
    private Color _color;
    private boolean _striped;
    private float _radius, _diameter;

    public Ball(Vector2f position, Vector2f velocity, float mass, Color color, boolean striped, float radius)
    {
        _position = position;
        _velocity = velocity;
        _mass = mass;
        _color = color;
        _striped = striped;
        _radius = radius;
        _diameter = _radius*2;
    }

    public Ball(float x, float y, Color color, boolean striped)
    {
        this(new Vector2f(x, y), new Vector2f(0, 0), DEFAULT_MASS, color, striped, DEFAULT_RADIUS);
    }

    public void update(float delta)
    {
        position().add(velocity().copy().scale(delta));
        velocity().sub(velocity().copy().scale(0.75f*delta));

        if(!inPocket())
        {
            if(position().x < radius() || position().x > PoolSim.TABLE_WIDTH_IN-radius())
            {
                position().x = PoolMath.clamp(radius(), position().x, PoolSim.TABLE_WIDTH_IN-radius());
                velocity().x *= -0.5f;
            }
            if(position().y < radius() || position().y > PoolSim.TABLE_HEIGHT_IN-radius())
            {
                position().y = PoolMath.clamp(radius(), position().y, PoolSim.TABLE_HEIGHT_IN-radius());
                velocity().y *= -0.5f;
            }
        }

        setVelocity(velocity().length() < 0.4 ? velocity().set(0, 0) : velocity());

        for(Ball ball:PoolSim.balls)
        {
            if(Physics.intersects(this, ball))
                Physics.resolveCollision(this, ball);
        }
    }

    public boolean inPocket()
    {
        boolean in = false;
        for(Pocket pocket:PoolSim.pockets)
        {
            if(pocket.isIn(this)) in = true;
        }
        return in;
    }

    public boolean inBounds()
    {
        boolean in = !(position().x < 0 || position().x > PoolSim.TABLE_WIDTH_IN || position().y < 0 || position().y > PoolSim.TABLE_HEIGHT_IN);
        return in;
    }

    /** GETTERS AND SETTERS ARE BELOW **/

    public Vector2f position()
    {
        return _position;
    }

    public Vector2f velocity()
    {
        return _velocity;
    }

    public float mass()
    {
        return _mass;
    }

    public float radius()
    {
        return _radius;
    }

    public float diameter()
    {
        return _diameter;
    }

    public boolean isStriped()
    {
        return _striped;
    }

    public Color getColor()
    {
        return _color;
    }

    public void setMass(float _mass)
    {
        this._mass = _mass;
    }

    public void setColor(Color _color)
    {
        this._color = _color;
    }

    public void setStriped(boolean _striped)
    {
        this._striped = _striped;
    }

    public void setRadius(float _radius)
    {
        this._radius = _radius;
    }

    public void setVelocity(Vector2f _velocity)
    {
        this._velocity = _velocity;
    }

    public void setPosition(Vector2f _position)
    {
        this._position = _position;
    }
}
