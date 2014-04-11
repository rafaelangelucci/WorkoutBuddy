package com.example.workoutbuddy.test;

import junit.framework.Assert;

import com.uiuc.workoutbuddy.ScheduleActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class ScheduleActivityTest extends
ActivityInstrumentationTestCase2<ScheduleActivity> {

	ScheduleActivity myActivity;
	
	public ScheduleActivityTest(Class<ScheduleActivity> activityClass) {
		super(activityClass);
	}
	
	
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
	
}