package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.MainActivity;
import com.uiuc.workoutbuddy.WorkoutFragment;

import android.test.ActivityInstrumentationTestCase2;

public class WorkoutFragmentTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	WorkoutFragment frag;
	
	public WorkoutFragmentTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
}
