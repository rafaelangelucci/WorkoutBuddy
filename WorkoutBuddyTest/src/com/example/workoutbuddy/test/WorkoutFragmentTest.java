package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.MainActivity;
import com.uiuc.workoutbuddy.WorkoutFragment;

import android.test.ActivityInstrumentationTestCase2;

public class WorkoutFragmentTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	MainActivity main;
	WorkoutFragment frag;
	
	public WorkoutFragmentTest()
	{
		super(MainActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        main = (MainActivity) getActivity();
        frag = main.myFragment;
    }
}
