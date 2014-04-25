package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.uiuc.workoutbuddy.ExerciseGraphListActivity;
import com.uiuc.workoutbuddy.GraphActivity;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;


public class GraphActivityTest extends
		ActivityInstrumentationTestCase2<GraphActivity> implements HttpRequestListener {

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
	
	public void testGetDates() {
		ArrayList<String> dates = lActivity.getDates();
		Assert.assertEquals(1, dates.size()); // 
	}
	
	public void testGetEid() throws InterruptedException, ExecutionException {
		String eName = "Squats";
		int eid = lActivity.getEidFromEName(eName);
		Assert.assertEquals(11, eid);
	}
	
	public void testGetAllExercises() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		Exercise[] exercisesAll = wrapper.getExerciseList("usernameA");
		
		Assert.assertFalse(0 == exercisesAll.length); // Not empty
	}
	
	public void testGetAllWorkouts() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		Workout[] exercisesAll = wrapper.getWorkoutList("usernameA");
		
		Assert.assertFalse(0 == exercisesAll.length); // Not empty
	}

	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub
		
	}
	
	
}