package com.example.workoutbuddy.test;

import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.uiuc.workoutbuddy.ExerciseGraphListActivity;
import com.uiuc.workoutbuddy.GraphActivity;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;


public class GraphActivityTest extends
		ActivityInstrumentationTestCase2<GraphActivity> {

	private GraphActivity lActivity;
	
	public GraphActivityTest(){
		super(GraphActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    lActivity = getActivity();
	}
	
	/**
	 * Test successful start up of MainActivity
	 */
	public void testActivityStartup()
	{
		String expected = lActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}
	
	public void testSeries() {
		GraphViewSeries series = new GraphViewSeries(new GraphViewData[] {
			      new GraphViewData(1, 150.0d)
			      , new GraphViewData(2, 155.0d)
			      , new GraphViewData(3, 160.0d)
			      , new GraphViewData(4, 160.0d)
			      , new GraphViewData(5, 162.5d)
			      , new GraphViewData(6, 165.0d)
			      , new GraphViewData(7, 167.5d)
			});
		
		GraphViewSeries activitySeries = lActivity.exampleSeries;
		//Assert.assertTrue(series == activitySeries);
	}
	
	
}