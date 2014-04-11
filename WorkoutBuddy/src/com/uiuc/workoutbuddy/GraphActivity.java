package com.uiuc.workoutbuddy;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;

public class GraphActivity extends Activity 
{
	public GraphViewSeries exampleSeries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
		// example series data
		exampleSeries = new GraphViewSeries(new GraphViewData[] {
		      new GraphViewData(1, 150.0d)
		      , new GraphViewData(2, 155.0d)
		      , new GraphViewData(3, 160.0d)
		      , new GraphViewData(4, 160.0d)
		      , new GraphViewData(5, 162.5d)
		      , new GraphViewData(6, 165.0d)
		      , new GraphViewData(7, 167.5d)
		});
		 
		GraphView graphView = new LineGraphView(
		      this // context
		      , "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries); // data
		
		graphView.setManualYAxisBounds(180.0d, 135.0d);
		// set view port, start=2, size=40
		//graphView.setViewPort(1, 3);
		graphView.setScrollable(true);
		// optional - activate scaling / zooming
		graphView.setScalable(true);
		
		// Background color
		((LineGraphView) graphView).setDrawBackground(true);
        ((LineGraphView) graphView).setBackgroundColor(Color.rgb(00, 30, 90));
        
        // custom static labels
        //graphView.setHorizontalLabels(new String[] {"4/1", "4/3", "4/5", "4/7", "4/9", "4/11", "4/13"});
        
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph_main);
		layout.addView(graphView);
	}
}
