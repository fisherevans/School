package com.fisherevans.com.physsim.lab1;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: David Fisher Evans
 * Date: 1/24/13
 * Time: 10:28 AM
 * Email: contact@fisherevans.com
 */
public class Charter extends ApplicationFrame
{
    public Charter(String title, Function function)
    {
        super(title);

        XYDataset dataset = function.getDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart(XYDataset dataset)
    {
        JFreeChart chart = ChartFactory.createXYLineChart(
                this.getTitle(),        // Title
                "X",                    // X label
                "Y",                    // Y label
                dataset,                // data
                PlotOrientation.VERTICAL,
                true,                  // legend?
                false,                  // tooltips?
                false                   // urls?
        );

        chart.setAntiAlias(true); // display options
        chart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = chart.getXYPlot(); // Used for setting colors

        // show lines AND shapes
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        plot.setRenderer(renderer);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }
}
