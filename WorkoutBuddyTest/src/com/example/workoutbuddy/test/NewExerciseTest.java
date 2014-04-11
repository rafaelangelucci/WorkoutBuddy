package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.NewExerciseActivity;
//import com.uiuc.workoutbuddy.R;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class NewExerciseTest extends ActivityInstrumentationTestCase2<NewExerciseActivity> {
	NewExerciseActivity mActivity;
	Instrumentation mInstrumentation;
	Button mButton;

	public NewExerciseTest() {
		super(NewExerciseActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();

        mInstrumentation = getInstrumentation();
	    setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mButton = (Button) mActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonNewExerciseOK);
	}
	
	public void testDoneButton() throws Throwable {
        assertFalse(getActivity().isFinishing());
		runTestOnUiThread(new Runnable() { //Must push buttons on UI thread
		     @Override
		     public void run() {
		 		mButton.performClick();
		    }
		});
        assertTrue(getActivity().isFinishing());
	}
}
