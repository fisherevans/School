package com.fisherevans.poolsim;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/30/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PoolMath
{
    public static float clamp(float min, float x, float max)
    {
        if(x < min)
            x = min;
        else if (x > max)
            x = max;
        return x;
    }
}
