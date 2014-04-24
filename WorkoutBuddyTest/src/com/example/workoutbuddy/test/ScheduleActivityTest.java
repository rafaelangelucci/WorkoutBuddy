package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


import junit.framework.Assert;

import com.uiuc.workoutbuddy.ScheduleActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class ScheduleActivityTest extends
ActivityInstrumentationTestCase2<ScheduleActivity> {

	ScheduleActivity myActivity;
	String date;
	int numtimes;
	int numbetween;
	Workout w;

	public ScheduleActivityTest(Class<ScheduleActivity> activityClass) {
		super(activityClass);
	}

//
//	public ScheduleActivityTest(Class<ScheduleActivity> activityClass, Workout w, String dateStr, int numTimes, int numBetween) {
//		super(activityClass);
//
//		date = dateStr;
//		numtimes = numTimes;
//		numbetween = numBetween;
//		this.w = w;
//	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		myActivity = getActivity();
	}


	public void testLayoutVisible(){
		View v;
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.spnr_workouts);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.et_date);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.cb_repeat);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.et_numbetween);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.et_numtimes);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_done);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_cancel);
		Assert.assertTrue(v.isShown());
	}


//
//	@Parameters
//	public static Collection<Object[]> generateData()
//	{   
//		Workout("TestWorkout", "4/03/2054", null, "usernameA"String desc, String username,
//				ArrayList<Exercise> exercises)
//		Workout w = new Workout()
//		ArrayList<Object[]> params = new ArrayList<Object[]>();
//		params.add(	new Object[]{ new ScheduleActivity(), "4/03/2054", 5, 5 });
//		params.add(	new Object[]{ new ScheduleActivity(), "4/03/2054", 5, 5 });
//		params.add(	new Object[]{ new ScheduleActivity(), "4/03/2054", 5, 5 });
//		params.add(	new Object[]{ new ScheduleActivity(), "4/03/2054", 5, 5 });
//		params.add(	new Object[]{ new ScheduleActivity(), "4/03/2054", 5, 5 });
//		return params;
//	}
//
//
//
//	@Test
//	public void testRepeatWorkout()
//	{
//		Assert.assertTrue(myActivity.repeatWorkout(this.w, this.date, this.numtimes, this.numbetween);
//	}
}