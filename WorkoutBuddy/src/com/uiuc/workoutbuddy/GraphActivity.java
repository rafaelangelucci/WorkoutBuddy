package com.uiuc.workoutbuddy;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;

public class GraphActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		      new GraphViewData(1, 2.0d)
		      , new GraphViewData(2, 1.5d)
		      , new GraphViewData(3, 2.5d)
		      , new GraphViewData(4, 1.0d)
		});
		 
		GraphView graphView = new LineGraphView(
		      this // context
		      , "GraphViewDemo" // heading
		);
		
		graphView.addSeries(exampleSeries); // data
		 
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph_main);
		layout.addView(graphView);
	}
}
