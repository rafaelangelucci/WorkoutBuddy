package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import com.uiuc.workoutbuddy.GraphActivity;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;


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