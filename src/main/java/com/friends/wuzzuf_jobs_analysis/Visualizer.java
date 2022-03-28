package com.friends.wuzzuf_jobs_analysis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;


public class Visualizer {
	
	private final int CHART_WIDTH = 1024;
	private final int CHART_HEIGHT = 800;
	
	public void displayPieChart(Map<String, Long> data) {
		// Create Chart.
        PieChart chart = new PieChartBuilder()
        		.width(CHART_WIDTH)
        		.height(CHART_HEIGHT)
        		.title(getClass().getSimpleName())
        		.build();
        
        // Add series
        data.forEach((k, v) -> chart.addSeries(k, v));
        
        // Show it
        new SwingWrapper(chart).displayChart();
	}
	
	public void savePieChart(String chartTitle, Map<String, Long> data, String path) throws IOException {
		// Create Chart.
        PieChart chart = new PieChartBuilder()
        		.width(CHART_WIDTH)
        		.height(CHART_HEIGHT)
        		.title(chartTitle)
        		.build();
        
        // Add series
        data.forEach((k, v) -> chart.addSeries(k, v));
        
        File f = new File(path);
        if(f.exists()) 
        	f.delete();
        
        BitmapEncoder.saveJPGWithQuality(chart, path, 1f);
	}
	
	
    public void saveBarChart(String chartTitle, List xData, List yData, String xAxisTitle, String yAxisTitle, String path) throws IOException {
         
    	// Create Chart
         CategoryChart chart = new CategoryChartBuilder()
        		 .width (CHART_WIDTH)
        		 .height (CHART_HEIGHT)
        		 .title(chartTitle)
        		 .xAxisTitle(xAxisTitle)
        		 .yAxisTitle(yAxisTitle)
        		 .build ();
         
         // Customize Chart
         chart.getStyler().setXAxisLabelRotation(45);
         chart.getStyler().setLegendPosition (Styler.LegendPosition.InsideNW);
         chart.getStyler().setHasAnnotations (true);
         chart.getStyler().setStacked (true);
         
         // Series
         chart.addSeries (xAxisTitle+yAxisTitle, xData, yData);
         
         // Show it
         //new SwingWrapper (chart).displayChart ();
         File f = new File(path);
         if(f.exists())
             f.delete();
         
         BitmapEncoder.saveJPGWithQuality(chart, path, 1f);
     }
	
	
	
}
