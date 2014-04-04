package com.example.workoutbuddy.test;

import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;


import com.uiuc.workoutbuddy.MainActivity;
import com.uiuc.workoutbuddy.MyWorkoutsListFragment;
import com.uiuc.workoutbuddy.Schedule;
import com.uiuc.workoutbuddy.WorkoutFragment;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


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
	
	
	
	public void testRepeatWorkout_OneTime_ValidDateStr()
	{
		int numOldWorkouts = getAllWorkouts().length;
		
		Workout w5 = getWorkout(5);
		sch.repeatWorkout(w5, futureDateStr);
		
		Workout[] newWorkouts = getNewWorkouts(numOldWorkouts);
		Assert.assertEquals(1, newWorkouts.length);
		
		Assert.assertEquals(w5.getName(), newWorkouts[0].getName());
		Assert.assertEquals(w5.getDescription(), newWorkouts[0].getDescription());
		Assert.assertEquals(futureDateStr, newWorkouts[0].getDate());
	}

	public void testRepeatWorkout_Multiple_EveryWeek() throws ParseException
	{
		int numOldWorkouts = getAllWorkouts().length;
		
		Workout w5 = getWorkout(5);
		sch.repeatWorkout(w5, futureDateStr, 5, true);

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar futureCal = Calendar.getInstance();
		futureCal.setTime(df.parse(futureDateStr));
		
		
		Workout[] newWorkouts = getNewWorkouts(numOldWorkouts);
		Assert.assertEquals(5, newWorkouts.length);
		for(int idx=0; idx<5; idx++)
		{
			String dateStr = df.format(futureCal.getTime());
			Assert.assertEquals(w5.getName(), newWorkouts[idx].getName());
			Assert.assertEquals(w5.getDescription(), newWorkouts[idx].getDescription());
			Assert.assertEquals(dateStr, newWorkouts[idx].getDate());
			futureCal.add(Calendar.DATE, 7);
		}
	}
	
	public void testRepeatWorkout_Multiple_EveryOtherWeek() throws ParseException
	{
		int numOldWorkouts = getAllWorkouts().length;
		
		Workout w5 = getWorkout(5);
		sch.repeatWorkout(w5, futureDateStr, 5, false);
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar futureCal = Calendar.getInstance();
		futureCal.setTime(df.parse(futureDateStr));
		
		
		Workout[] newWorkouts = getNewWorkouts(numOldWorkouts);
		Assert.assertEquals(5, newWorkouts.length);
		for(int idx=0; idx<5; idx++)
		{
			String dateStr = df.format(futureCal.getTime());
			Assert.assertEquals(w5.getName(), newWorkouts[idx].getName());
			Assert.assertEquals(w5.getDescription(), newWorkouts[idx].getDescription());
			Assert.assertEquals(dateStr, newWorkouts[idx].getDate());
			futureCal.add(Calendar.DATE, 14);
		}
	}
	


	public void testChangeDateWorkout_Future()
	{
		Workout w1 = getWorkout(1);

		sch.changeWorkoutDate(w1, futureDateStr);
		w1 = getWorkout(1);
		
		Assert.assertEquals(futureDateStr, w1.getDate());
	}

	public void testChangeDateWorkout_Past()
	{
		Workout w1 = getWorkout(1);
		String oldDateStr = w1.getDate();


		sch.changeWorkoutDate(w1, pastDateStr);
		w1 = getWorkout(1);
		
		Assert.assertEquals(oldDateStr, w1.getDate()); 
	}

	public void testChangeDateWorkout_Invalid()
	{
		Workout w1 = getWorkout(1);
		String oldDateStr = w1.getDate();


		sch.changeWorkoutDate(w1, invalidDateStr);
		w1 = getWorkout(1);
		
		Assert.assertEquals(oldDateStr, w1.getDate()); 
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