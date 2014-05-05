package com.example.workoutbuddy.test;

import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;


import com.uiuc.workoutbuddy.Schedule;
import junit.framework.Assert;


public class ScheduleTest extends TestCase implements HttpRequestListener{

	Schedule sch;
	AsyncHttpPostWrapper wrapper;
	CountDownLatch signal;

	String futureDateStr = "4/03/2054";
	String pastDateStr = "4/03/2014";
	String invalidDateStr = "abaca";

	Workout w0;
	
	/**
	 * Creates a new signal to signal call to requestComplete
	 * Creates instance of Wrapper to test AsyncHttpPost
	 */
	/**
	 * Creates a new signal to signal call to requestComplete
	 * Creates instance of Wrapper to test AsyncHttpPost
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		sch = new Schedule();
		signal = new CountDownLatch(1);
		wrapper = new AsyncHttpPostWrapper(this);
	}
	
	

	public void testRepeatWorkout_OneTime_InvalidDateStr()
	{
		int numOldWorkouts = getAllWorkouts().length;

		Workout w5 = getWorkout(5);
		sch.repeatWorkout(w5, this.invalidDateStr);
		int numNewWorkouts = getNewWorkouts(numOldWorkouts).length;
		Assert.assertEquals(0, numNewWorkouts);
		
		sch.repeatWorkout(w5, this.pastDateStr);
		numNewWorkouts = getNewWorkouts(numOldWorkouts).length;
		Assert.assertEquals(0, numNewWorkouts);
	}
	

	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub

	}
	
	private Workout getWorkout(int wid)
	{
		try {
			return wrapper.getWorkout(wid);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	private Workout[] getAllWorkouts()
	{
		Workout[] workoutsAll;
		
		try {
			workoutsAll = wrapper.getWorkoutList("usernameA");
		} catch (InterruptedException e) {
			e.printStackTrace();
			workoutsAll = new Workout[0];
		} catch (ExecutionException e) {
			e.printStackTrace();
			workoutsAll = new Workout[0];
		}
		return workoutsAll;
	}
	
	private Workout[] getNewWorkouts(int numOldWorkouts)
	{
		Workout[] workoutsAll = getAllWorkouts();
		Workout[] workoutsNew;
		
		int numNewWorkouts = workoutsAll.length - numOldWorkouts;
		workoutsNew = new Workout[numNewWorkouts];

		for(int idx = 0; idx < numNewWorkouts; idx++)
		{
			workoutsNew[idx] = workoutsAll[numOldWorkouts + idx];
		}
		return workoutsNew;
	}
}