package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.MainActivity;
import com.uiuc.workoutbuddy.NewWorkoutActivity;
import com.uiuc.workoutbuddy.ScheduleActivity;
import com.uiuc.workoutbuddy.UseWorkoutActivity;
import com.uiuc.workoutbuddy.WorkoutFragment;
import com.uiuc.workoutbuddy.WorkoutListActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

public class WorkoutFragmentTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	MainActivity main;
	WorkoutFragment frag;
	Button myWo, newWo, sched;
	Button myWo2, newWo2, sched2;
	ActivityMonitor workoutListMonitor, newWorkoutMonitor, scheduleMonitor;

	public WorkoutFragmentTest()
	{
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(true);

		main = (MainActivity) getActivity();
		frag = main.woFrag;

		workoutListMonitor = getInstrumentation().addMonitor(WorkoutListActivity.class.getName(), null, false);
		newWorkoutMonitor = getInstrumentation().addMonitor(NewWorkoutActivity.class.getName(), null, false);
		scheduleMonitor = getInstrumentation().addMonitor(ScheduleActivity.class.getName(), null, false);

		//Grab buttons from the fragment
		myWo = frag.getMyWorkouts();
		newWo = frag.getNewWorkout();
		sched = frag.getSchedule();

		//Grab the buttons from the main activity the fragment is contained in
		myWo2 = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_my_workouts);
		newWo2 = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_new_workout);
		sched2 = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_schedule);
	}

	public void testPreConditions() {
		assertNotNull(main);
		assertNotNull(frag);
	}

	public void testSetUp()
	{	
		assertNotNull("get from frag fails", myWo);
		assertNotNull("get from frag fails", newWo);
		assertNotNull("get from frag fails", sched);

		assertNotNull("get from main fails", myWo2);
		assertNotNull("get from main fails", newWo2);
		assertNotNull("get from main fails", sched2);
	}

	public void testMyWorkoutButtonClick()
	{
		main.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// click button and open next activity.
				myWo.performClick();
			}
		});

		WorkoutListActivity nextActivity = (WorkoutListActivity)getInstrumentation()
				.waitForMonitorWithTimeout(workoutListMonitor, 5000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
	
	public void testNewWorkoutButtonClick()
	{
		main.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// click button and open next activity.
				newWo.performClick();
			}
		});

		NewWorkoutActivity nextActivity = (NewWorkoutActivity)getInstrumentation()
				.waitForMonitorWithTimeout(newWorkoutMonitor, 5000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
	
	public void testScheduleButtonClick()
	{
		main.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// click button and open next activity.
				sched.performClick();
			}
		});

		ScheduleActivity nextActivity = (ScheduleActivity)getInstrumentation()
				.waitForMonitorWithTimeout(scheduleMonitor, 5000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
}
