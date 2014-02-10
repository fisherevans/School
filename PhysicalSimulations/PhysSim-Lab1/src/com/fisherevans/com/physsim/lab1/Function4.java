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
public class Function4 extends Function
{
    @Override
    public XYDataset getDataset()
    {
        float startTime = 0;
        float duration = 2;
        float h = 0.1f;
        float y;

        float initialY = -2f;


        y = initialY;
        XYSeries term = new XYSeries("Term Guess");
        for(float x = startTime;x <= startTime+duration;x += h)
        {
            term.add(x, y);
            System.out.println("RK4 (x, y) > (" + x + ", " + y + ")");

            float k1 = h*getY(x, y);
            float k2 = h*getY(x + (h/2f), y + (k1/2f));
            float k3 = h*getY(x + (h/2f), y + (k2/2f));
            float k4 = h*getY(x + h, y + k3);
            y += ((k1 + 2*k2 + 2*k3 + k4)/6f);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(term);

        return dataset;
    }

    public float getY(float x, float y)
    {
        float g = 9.8f;
        float p = 1.225f;
        float Cd = 0.5f;
        float m = 100;
        float a = 3;
        return g + p*Cd*y*y*a*0.5f/m;
    }
}
