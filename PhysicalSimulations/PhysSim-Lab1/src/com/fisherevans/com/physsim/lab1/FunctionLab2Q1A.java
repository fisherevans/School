package com.fisherevans.com.physsim.lab1;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created with IntelliJ IDEA.
 * User: David Fisher Evans
 * Date: 1/24/13
 * Time: 10:30 AM
 * Email: contact@fisherevans.com
 */
public class FunctionLab2Q1A extends Function
{
    private double[] ys, k1, k2, k3, k4, nextY4k;
    @Override
    public XYDataset getDataset()
    {
        double startTime = 0;
        double duration = 1;
        double h = 0.1;

        int n = 2;

        ys = new double[n];

        double[] baseInits = new double[n], actualys = new double[n];
        baseInits[0] = 1;
        baseInits[1] = 1;

        System.arraycopy(baseInits, 0, ys, 0, n);
        System.arraycopy(baseInits, 0, actualys, 0, n);

        k1 = new double[n];
        k2 = new double[n];
        k3 = new double[n];
        k4 = new double[n];
        nextY4k = new double[n];

        XYSeries[] rk4s = new XYSeries[n];
        for(int func = 0;func < n;func++)
            rk4s[func] = new XYSeries(func + " (RK4)");
        for(double x = startTime;x <= startTime+duration;x += h)
        {
            for(int func = 0;func < n;func++)
                rk4s[func].add(x, ys[func]);

            for(int func = 0;func < n;func++)
            {
                k1[func] = h*getGuess(x, ys, func);
                nextY4k[func] = ys[func] + k1[func]*0.5;
                System.out.println(nextY4k[func] + " " + h);
            }

            for(int func = 0;func < n;func++)
            {
                k2[func] = h*getGuess(x + h*0.5, nextY4k, func);
                nextY4k[func] = ys[func] + k2[func]*0.5;
                System.out.println(nextY4k[func] + " = " + ys[func] + " + " + k2[func] + "*0.5");
            }

            for(int func = 0;func < n;func++)
            {
                k3[func] = h*getGuess(x + h*0.5, nextY4k, func);
                nextY4k[func] = ys[func] + k3[func];
                System.out.println(nextY4k[func] + " " + h);
            }

            for(int func = 0;func < n;func++)
                k4[func] = h*getGuess(x + h, nextY4k, func);

            for(int func = 0;func < n;func++)
            {
                ys[func] += ((k1[func] + 2*k2[func] + 2*k3[func] + k4[func])/6.0);
            }
        }

        XYSeries[] actuals = new XYSeries[n];
        for(int func = 0;func < n;func++)
            actuals[func] = new XYSeries("Actual - "+func);
        for(double x = startTime;x <= startTime+duration;x += h)
        {
            for(int func = 0;func < n;func++)
            {
                actualys[func] = getActual(x, ys, func); // find the next point
                actuals[func].add(x, actualys[func]); // add the plot point
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int func = 0;func < n;func++)
        {
            dataset.addSeries(rk4s[func]);
            dataset.addSeries(actuals[func]);
        }

        return dataset;
    }

    public double getGuess(double x, double[] ys, int func)
    {
        return getY(x, ys, func, false);
    }

    public double getActual(double x, double[] ys, int func)
    {
        return getY(x, ys, func, true);
    }

    public double getY(double x, double[] ys, int func, boolean actual)
    {
        switch(func)
        {
            case 0: return actual ? (double)(1.0/3.0*Math.pow(Math.E, 5*x) - 1.0/3.0*Math.pow(Math.E, -1*x)) // actual
                    : (double)(3*ys[0] + 2*ys[1] - (2*x*x + 1)*Math.pow(Math.E, 2*x)); // guess
            case 1: return actual ? (double)(1.0/3.0*Math.pow(Math.E, 5*x) + 2.0/3.0*Math.pow(Math.E, -1*x) + x*x*Math.pow(Math.E, 2*x)) // actual
                    : (double)(4*ys[0] + ys[1] - (x*x + 2*x - 4)*Math.pow(Math.E, 2*x)); // guess
            default: return 0;
        }
    }
}
