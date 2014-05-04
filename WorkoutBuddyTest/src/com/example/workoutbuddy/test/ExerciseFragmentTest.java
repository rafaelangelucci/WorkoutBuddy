package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.ExerciseFragment;
import com.uiuc.workoutbuddy.ExerciseListActivity;
import com.uiuc.workoutbuddy.MainActivity;
import com.uiuc.workoutbuddy.NewExerciseActivity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class ExerciseFragmentTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	MainActivity main;
	ExerciseFragment frag;
	Button myEx, newEx;
	Button myEx2, newEx2;
	ActivityMonitor exerciseListMonitor, newExerciseMonitor;

	public ExerciseFragmentTest()
	{
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(true);

		main = (MainActivity) getActivity();
		frag = main.exFrag;

		exerciseListMonitor = getInstrumentation().addMonitor(ExerciseListActivity.class.getName(), null, false);
		newExerciseMonitor = getInstrumentation().addMonitor(NewExerciseActivity.class.getName(), null, false);

		//Grab buttons from the fragment
		myEx = frag.getMyExercises();
		newEx = frag.getNewExercise();

		//Grab the buttons from the main activity the fragment is contained in
		myEx2 = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_my_exercises);
		newEx2 = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_new_exercise);
	}

	public void testPreConditions() {
		assertNotNull(main);
		assertNotNull(frag);
	}

	public void testSetUp()
	{	
		assertNotNull("get from frag fails", myEx);
		assertNotNull("get from frag fails", newEx);

		assertNotNull("get from main fails", myEx2);
		assertNotNull("get from main fails", newEx2);
	}

	public void testMyExerciseButtonClick()
	{
		main.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// click button and open next activity.
				myEx.performClick();
			}
		});

		ExerciseListActivity nextActivity = (ExerciseListActivity)getInstrumentation()
				.waitForMonitorWithTimeout(exerciseListMonitor, 5000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
	
	public void testNewExerciseButtonClick()
	{
		main.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// click button and open next activity.
				newEx.performClick();
			}
		});

		NewExerciseActivity nextActivity = (NewExerciseActivity)getInstrumentation()
				.waitForMonitorWithTimeout(newExerciseMonitor, 5000);
		// next activity is opened and captured.
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
}
