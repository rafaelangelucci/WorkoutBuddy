package com.example.workoutbuddy.test;

import com.example.workoutbuddy.HomeActivity;
import com.example.workoutbuddy.MyWorkoutsActivity;

import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;

public class HomeActivityTest extends ActivityUnitTestCase<HomeActivity> {

	
	public HomeActivityTest(Class<HomeActivity> activityClass) {
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
		HomeActivity ha = new HomeActivity();
		ha.onCreate(null);
		//TODO TEST list buttons.
	}
	
	@SmallTest
	public void testgoToMyWorkouts() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MyWorkoutsActivity.class.getName(), null, false);

		HomeActivity ha = new HomeActivity();
		ha.goToCreateWorkOut(null);
		//ASSERT My Workouts activity started
		
		Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
}
