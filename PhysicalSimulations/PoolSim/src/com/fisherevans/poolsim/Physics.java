package com.fisherevans.poolsim;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/30/13
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Physics
{
    public static boolean intersects(Ball b1, Ball b2)
    {
        if(b1.equals(b2)) return false;
        float distSqr = b1.position().distanceSquared(b2.position());
        return distSqr < Math.pow(b1.radius() + b2.radius(), 2);
    }

    public static void resolveCollision(Ball b1, Ball b2)
    {
        Vector2f delta = b1.position().copy().sub(b2.position());
        float d = delta.length();

        // get the mtd
        // minimum translation distance to push balls apart after intersecting
        Vector2f mtd = delta.copy().scale(((b1.radius() + b2.radius())-d)/d);

        // resolve intersection --
        // inverse mass quantities
        float im1 = 1/b1.mass();
        float im2 = 1/b2.mass();

        // push-pull them apart based off their mass
        b1.position().add(mtd.copy().scale(im1 / (im1+im2)));
        b2.position().add(mtd.copy().scale(im2 / (im1+im2)));

        // impact speed
        Vector2f v = b1.velocity().copy().sub(b2.velocity());
        float vn = v.copy().dot(mtd.copy().normalise());

        // sphere intersecting but moving away from each other already
        if(vn > 0) return;

        // collision impulse
        float i = (-1*(1f + Ball.RESTITUTION)*vn)/(im1 + im2);
        Vector2f impulse = mtd.copy().normalise().scale(i);

        // change in momentum
        b1.velocity().add(impulse.copy().scale(im1));
        b2.velocity().sub(impulse.copy().scale(im2));
    }
}
