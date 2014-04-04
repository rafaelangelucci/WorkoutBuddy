package com.example.workoutbuddy.test;

import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

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

	
	public void testRepeatWorkout_OneTime_ValidDate()
	{
		Workout w1 = getWorkout1();
		//sch.repeatWorkout(w1, futureDateStr);
		Assert.assertTrue(false);
	}
	


	public void testChangeDateWorkout_Future()
	{
		Workout w1 = getWorkout1();

		sch.changeWorkoutDate(w1, futureDateStr);
		w1 = getWorkout1();
		
		Assert.assertEquals(futureDateStr, w1.getDate());
	}

	public void testChangeDateWorkout_Past()
	{
		Workout w1 = getWorkout1();
		String oldDateStr = w1.getDate();

		String pastDateStr = "4/03/2014";

		sch.changeWorkoutDate(w1, pastDateStr);
		w1 = getWorkout1();
		
		Assert.assertEquals(oldDateStr, w1.getDate()); 
	}

	public void testChangeDateWorkout_Invalid()
	{
		Workout w1 = getWorkout1();
		String oldDateStr = w1.getDate();

		String invalidDateStr = "abaca";

		sch.changeWorkoutDate(w1, invalidDateStr);
		w1 = getWorkout1();
		
		Assert.assertEquals(oldDateStr, w1.getDate()); 
	}


	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub

	}
	
	private Workout getWorkout1()
	{
		try {
			return wrapper.getWorkout(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Workout[] getNewWorkouts()
	{
		return null;
//		Workout[] newWorkouts = wrapper.getWorkoutList("usernameA");
//		
//		try {
//			return wrapper.getWorkout(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//		
	}
}