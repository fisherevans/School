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
public class FunctionLab2Q3 extends Function
{
    private double[] ys, k1, k2, k3, k4, nextY4k;

    @Override
    public XYDataset getDataset()
    {
        double startTime = 0;
        double duration = 10;
        double h = 0.1f;

        int n = 2;

        ys = new double[n];

        double[] baseInits = new double[n], actualys = new double[n];
        baseInits[0] = 0;
        baseInits[1] = 0;

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
            }

            for(int func = 0;func < n;func++)
            {
                k2[func] = h*getGuess(x + h*0.5, nextY4k, func);
                nextY4k[func] = ys[func] + k2[func]*0.5;
            }

            for(int func = 0;func < n;func++)
            {
                k3[func] = h*getGuess(x + h*0.5, nextY4k, func);
                nextY4k[func] = ys[func] + k3[func];
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

    private double G = 9.8;
    private double P = 1.225;
    private double CD = 0.5;
    private double M = 0.1;
    private double A = 0.04;

    public double getY(double x, double[] ys, int func, boolean actual)
    {
        switch(func)
        {
            case 0: return actual ? -Math.sqrt(2*M*G/P/A/CD) // actual
                                  : ys[1]; // guess
            case 1: return actual ? -G*x // actual
                                  : -G + P*CD*ys[1]*ys[1]*A/2.0; // guess
            default: return 0;
        }
    }
}
