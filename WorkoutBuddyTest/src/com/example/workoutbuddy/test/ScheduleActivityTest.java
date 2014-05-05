package com.example.workoutbuddy.test;

import helperClasses.Workout;
import junit.framework.Assert;
import com.uiuc.workoutbuddy.ScheduleActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;

/**
 * This class is for Testing ScheduleActivity
 * @author Joel
 *
 */
public class ScheduleActivityTest extends
ActivityInstrumentationTestCase2<ScheduleActivity> {

	ScheduleActivity myActivity;
	String date;
	int numtimes;
	int numbetween;
	Workout w;

	/**
	 * Basic Constructor
	 */
	public ScheduleActivityTest() {
		super(ScheduleActivity.class);
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		myActivity = getActivity();
	}


	/**
	 * Tests what should be visible at the beginning of the activity.
	 */
	public void testLayoutVisibleBegining(){
		View v;
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.spnr_workouts);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.et_date);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.cb_repeat);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.et_numbetween);
		Assert.assertFalse(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.et_numtimes);
		Assert.assertFalse(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_done);
		Assert.assertTrue(v.isShown());
		v = myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_cancel);
		Assert.assertTrue(v.isShown());
	}
}