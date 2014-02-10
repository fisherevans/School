package com.fisherevans.ads.listsystem;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/30/13
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Operation
{
    SUM
            { public double calc(double a, double b) { return a + b; } },
    DIFFERENCE
            { public double calc(double a, double b) { return a - b; } },
    PRODUCT
            { public double calc(double a, double b) { return a * b; } },
    QUOTIENT
            { public double calc(double a, double b) { return a / b; } };

    public abstract double calc(double a, double b);
}
