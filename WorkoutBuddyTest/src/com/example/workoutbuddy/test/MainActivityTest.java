package com.example.workoutbuddy.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import com.example.workoutbuddy.MainActivity;


public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	
	
	public MainActivityTest(){
		super(MainActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    
	    mActivity = getActivity();
	}
	
	
	public void testPreConditions() {
		Assert.assertTrue(true);
	}
	
	
	public void testLayout(){
		Assert.fail();
	}
	
	public void testNewWorkoutClick(){
		Assert.fail();
	}
}