package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import com.uiuc.workoutbuddy.LoginActivity;
import com.uiuc.workoutbuddy.NewExerciseActivity;
//import com.uiuc.workoutbuddy.R;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Tests for the NewExerciseActivity class.
 * 
 * @author Daniel
 */
public class NewExerciseTest extends
		ActivityInstrumentationTestCase2<NewExerciseActivity> implements
		HttpRequestListener {
	NewExerciseActivity mActivity;
	Instrumentation mInstrumentation;
	EditText mTextName;
	EditText mTextDesc;
	Spinner mSpinType;
	Button mButton;

	public NewExerciseTest() {
		super(NewExerciseActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		mInstrumentation = getInstrumentation();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mTextName = (EditText) mActivity
				.findViewById(com.uiuc.workoutbuddy.R.id.edit_exercise_name);
		mTextDesc = (EditText) mActivity
				.findViewById(com.uiuc.workoutbuddy.R.id.edit_exercise_description);
		mSpinType = (Spinner) mActivity
				.findViewById(com.uiuc.workoutbuddy.R.id.spinner_exercise_type);
		mButton = (Button) mActivity
				.findViewById(com.uiuc.workoutbuddy.R.id.btn_new_exercise_ok);
	}

	/**
	 * Test whether the activity is able to run without crashing
	 */
	public void testStaysOpen() {
		assertFalse(getActivity().isFinishing());
	}

	/**
	 * Test that the program will not accept an exercise with no name.
	 * 
	 * @throws Throwable
	 */
	public void testNeedName() throws Throwable {
		runTestOnUiThread(new Runnable() {
			public void run() {
				mButton.performClick();
			}
		});
		assertFalse(getActivity().isFinishing());
	}

	/**
	 * Test that the spinner is able to accept and read value correctly.
	 * 
	 * @throws Throwable
	 */
	public void testSpinnerValues() throws Throwable {
		assertEquals("Reps", mSpinType.getSelectedItem().toString());
		runTestOnUiThread(new Runnable() {
			public void run() {
				mSpinType.setSelection(2);
			}
		});
		assertEquals("Time", mSpinType.getSelectedItem().toString());
		runTestOnUiThread(new Runnable() {
			public void run() {
				mSpinType.setSelection(1);
			}
		});
		assertEquals("Weight", mSpinType.getSelectedItem().toString());
		runTestOnUiThread(new Runnable() {
			public void run() {
				mSpinType.setSelection(0);
			}
		});
		assertEquals("Reps", mSpinType.getSelectedItem().toString());
	}

	/**
	 * Test that the name box is set up correctly and accepts text.
	 * 
	 * @throws Throwable
	 */
	public void testNameBox() throws Throwable {
		assertEquals("", mTextName.getText().toString());
		runTestOnUiThread(new Runnable() {
			public void run() {
				mTextName.setText("Unit Test Name");
			}
		});
		assertEquals("Unit Test Name", mTextName.getText().toString());
	}

	/**
	 * Test that the description box is set up correctly and accepts text.
	 * 
	 * @throws Throwable
	 */
	public void testDescBox() throws Throwable {
		assertEquals("", mTextDesc.getText().toString());
		runTestOnUiThread(new Runnable() {
			public void run() {
				mTextDesc.setText("Testing the description box...");
			}
		});
		assertEquals("Testing the description box...", mTextDesc.getText()
				.toString());
	}

	/**
	 * Test that an exercise is actually created with the entered information
	 * when everything is done correctly.
	 * 
	 * @throws Throwable
	 */
	public void testCreation() throws Throwable {
		// Enter the information
		LoginActivity.userName = "usernameA";
		runTestOnUiThread(new Runnable() {
			public void run() {
				mTextName.setText("Unit Test Exercise");
				mTextDesc
						.setText("This exercise was created for unit testing and should have been deleted immediately.");
				mSpinType.setSelection(2);

				// press OK
				mButton.performClick();
			}
		});
		assertTrue(getActivity().isFinishing());

		// Read the most recent exercise and confirm it has the correct values.
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		Exercise[] exercises = wrapper.getExerciseList(LoginActivity.userName);
		Exercise createdExercise = exercises[exercises.length - 1];
		assertEquals("Unit Test Exercise", createdExercise.getName());
		assertEquals(
				"This exercise was created for unit testing and should have been deleted immediately.",
				createdExercise.getDescription());
		assertEquals("Time", createdExercise.getType());

		// delete the created exercise for cleanliness.
		wrapper.deleteExercise(createdExercise.getEid());
	}

	@Override
	public void requestComplete() {
	}
}
