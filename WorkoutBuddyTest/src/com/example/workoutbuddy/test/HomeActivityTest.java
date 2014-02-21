package com.example.workoutbuddy.test;

import com.example.workoutbuddy.MainActivity;

import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;

public class HomeActivityTest extends ActivityUnitTestCase<MainActivity> {
	public HomeActivityTest() {
	    super(MainActivity.class);
	}
	
	public HomeActivityTest(Class<MainActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}
	
	public void setUp()
	{
		
	}
	public void tearDown()
	{
		
	}

	@SmallTest
	public void testonCreate() {
		//MainActivity ha = new MainActivity();
		assertNotNull(null);
		//ha.onCreate(null);
		//TODO TEST list buttons.
	}
	
	@SmallTest
	public void testgoToMyWorkouts() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

		MainActivity ha = new MainActivity();
		//ha.goToCreateWorkOut(null);
		//ASSERT My Workouts activity started
		
		Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
}
