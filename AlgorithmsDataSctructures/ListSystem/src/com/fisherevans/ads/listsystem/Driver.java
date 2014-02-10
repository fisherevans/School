package com.fisherevans.ads.listsystem;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/30/13
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver
{
    public static void main(String[] args)
    {
        double a = Math.round(Math.random()*10000.0)/100.0;
        double b = Math.round(Math.random()*10000.0)/100.0;
        System.out.println("Testing with values (a, b): (" + a + ", " + b + ")");
        for(Operation operation:Operation.values())
            System.out.printf("%10s: %f\n", operation.name(), operation.calc(a, b));
    }
}
