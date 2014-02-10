package com.fisherevans.com.physsim.lab1;

import org.jfree.ui.RefineryUtilities;

/**
 * Created with IntelliJ IDEA.
 * User: David Fisher Evans
 * Date: 1/24/13
 * Time: 10:13 AM
 * Email: contact@fisherevans.com
 */
public class Main
{
    public static void main(String[] args)
    {
        Charter chart = new Charter("Fisher Evans - Lab 2 - #4", new FunctionLab2Q1A());
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
