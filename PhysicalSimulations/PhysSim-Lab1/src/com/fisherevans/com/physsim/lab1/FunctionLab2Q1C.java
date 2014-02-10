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
public class FunctionLab2Q1C extends Function
{
    private float[] ys, k1, k2, k3, k4, nextY4k;
    @Override
    public XYDataset getDataset()
    {
        float startTime = 0;
        float duration = 2;
        float h = 0.1f;

        int n = 3;

        ys = new float[n];

        float[] baseInits = new float[n], actualys = new float[n];
        baseInits[0] = 1;
        baseInits[1] = 0;
        baseInits[1] = 1;

        System.arraycopy(baseInits, 0, ys, 0, n);
        System.arraycopy(baseInits, 0, actualys, 0, n);

        k1 = new float[n];
        k2 = new float[n];
        k3 = new float[n];
        k4 = new float[n];
        nextY4k = new float[n];

        XYSeries[] rk4s = new XYSeries[n];
        for(int func = 0;func < n;func++)
            rk4s[func] = new XYSeries(func + " (RK4)");
        for(float x = startTime;x <= startTime+duration;x += h)
        {
            for(int func = 0;func < n;func++)
                rk4s[func].add(x, ys[func]);

            for(int func = 0;func < n;func++)
            {
                k1[func] = h*getGuess(x, ys, func);
                nextY4k[func] = ys[func] + k1[func]*0.5f;
                System.out.println(nextY4k[func] + " " + h);
            }

            for(int func = 0;func < n;func++)
            {
                k2[func] = h*getGuess(x + h*0.5f, nextY4k, func);
                nextY4k[func] = ys[func] + k2[func]*0.5f;
                System.out.println(nextY4k[func] + " = " + ys[func] + " + " + k2[func] + "*0.5");
            }

            for(int func = 0;func < n;func++)
            {
                k3[func] = h*getGuess(x + h*0.5f, nextY4k, func);
                nextY4k[func] = ys[func] + k3[func];
                System.out.println(nextY4k[func] + " " + h);
            }

            for(int func = 0;func < n;func++)
                k4[func] = h*getGuess(x + h, nextY4k, func);

            for(int func = 0;func < n;func++)
            {
                ys[func] += ((k1[func] + 2*k2[func] + 2*k3[func] + k4[func])/6f);
            }
        }

        XYSeries[] actuals = new XYSeries[n];
        for(int func = 0;func < n;func++)
            actuals[func] = new XYSeries("Actual - "+func);
        for(float x = startTime;x <= startTime+duration;x += h)
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

    public float getGuess(float x, float[] ys, int func)
    {
        return getY(x, ys, func, false);
    }

    public float getActual(float x, float[] ys, int func)
    {
        return getY(x, ys, func, true);
    }

    public float getY(float x, float[] ys, int func, boolean actual)
    {
        switch(func)
        {
            case 0: return actual ? (float)(Math.cos(x) + Math.sin(x) - Math.exp(x) + 1) // actual
                                  : (float)(ys[1]); // guess
            case 1: return actual ? (float)(Math.cos(x) - Math.sin(x) - Math.exp(x)) // actual
                                  : (float)(-ys[0] - 2*Math.exp(x) + 1); //guess
            case 2: return actual ? (float)(Math.cos(x) - Math.sin(x)) // actual
                                  : (float)(-ys[0] - Math.exp(x) + 1); // guess
            default: return 0;
        }
    }
}
