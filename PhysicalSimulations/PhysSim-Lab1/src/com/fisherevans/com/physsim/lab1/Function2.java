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
public class Function2 extends Function
{
    @Override
    public XYDataset getDataset()
    {
        float startTime = 1;
        float duration = 1;
        float h = 0.1f;
        float y;

        float initialY = 1f;

        XYSeries exact = new XYSeries("Exact");
        for(float x = startTime;x <= startTime+duration;x += h)
        {
            exact.add(x, (float)(x/(1+Math.log(x))));
        }

        y = initialY;
        XYSeries euler = new XYSeries("Euler's");
        for(float x = startTime;x <= startTime+duration;x += h)
        {
            euler.add(x, y);
            //System.out.println("Euler (x, y) > (" + x + ", " + y + ")");

            y += getY(x, y)*h;
        }


        y = initialY;
        XYSeries rk4 = new XYSeries("RK4");
        for(float x = startTime;x <= startTime+duration;x += h)
        {
            rk4.add(x, y);
            //System.out.println("RK4 (x, y) > (" + x + ", " + y + ")");

            float k1 = h*getY(x, y);
            float k2 = h*getY(x + (h/2f), y + (k1/2f));
            float k3 = h*getY(x + (h/2f), y + (k2/2f));
            float k4 = h*getY(x + h, y + k3);
            y += ((k1 + 2*k2 + 2*k3 + k4)/6f);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(exact);
        dataset.addSeries(euler);
        dataset.addSeries(rk4);

        return dataset;
    }

    public float getY(float x, float y)
    {
        return (y/x) - ((y*y)/(x*x));
    }
}
