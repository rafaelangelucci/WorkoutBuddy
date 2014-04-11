package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import com.uiuc.workoutbuddy.NewExerciseActivity;
//import com.uiuc.workoutbuddy.R;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewExerciseTest extends ActivityInstrumentationTestCase2<NewExerciseActivity> implements HttpRequestListener {
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
        mTextName = (EditText) mActivity.findViewById(com.uiuc.workoutbuddy.R.id.editTextExerciseName);
        mTextDesc = (EditText) mActivity.findViewById(com.uiuc.workoutbuddy.R.id.editTextExerciseDescription);
        mSpinType = (Spinner) mActivity.findViewById(com.uiuc.workoutbuddy.R.id.spinnerExerciseType);
        mButton = (Button) mActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonNewExerciseOK);
	}
	
	public void testStaysOpen() {
        assertFalse(getActivity().isFinishing());
	}
	
	public void testNeedName() throws Throwable {
		runTestOnUiThread(new Runnable() {public void run() {
	 		mButton.performClick();
	    }});
        assertFalse(getActivity().isFinishing());
	}
	
	public void testSpinnerValues() throws Throwable {
		assertEquals("Reps", mSpinType.getSelectedItem().toString());
		runTestOnUiThread(new Runnable() {public void run() {mSpinType.setSelection(2);}});
		assertEquals("Time", mSpinType.getSelectedItem().toString());
		runTestOnUiThread(new Runnable() {public void run() {mSpinType.setSelection(1);}});
		assertEquals("Weight", mSpinType.getSelectedItem().toString());
		runTestOnUiThread(new Runnable() {public void run() {mSpinType.setSelection(0);}});
		assertEquals("Reps", mSpinType.getSelectedItem().toString());
	}
	
	public void testNameBox() throws Throwable {
		assertEquals("",mTextName.getText().toString());
		runTestOnUiThread(new Runnable() {public void run() {
			mTextName.setText("Unit Test Name");
		}});
		assertEquals("Unit Test Name", mTextName.getText().toString());
	}
	
	public void testDescBox() throws Throwable {
		assertEquals("",mTextDesc.getText().toString());
		runTestOnUiThread(new Runnable() {public void run() {
			mTextDesc.setText("Testing the description box...");
		}});
		assertEquals("Testing the description box...", mTextDesc.getText().toString());
	}
	
	public void testCreation() throws Throwable {
		runTestOnUiThread(new Runnable() {public void run() {
			mTextName.setText("Unit Test Exercise");
			mTextDesc.setText("This exercise was created for unit testing and should have been deleted immediately.");
			mSpinType.setSelection(2);
	 		mButton.performClick();
	    }});
        assertTrue(getActivity().isFinishing());
        AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
        Exercise[] exercises = wrapper.getExerciseList("usernameA"); //Will have to fix this
        Exercise createdExercise = exercises[exercises.length-1]; //Is this always the case?
        assertEquals("Unit Test Exercise", createdExercise.getName());
        assertEquals("This exercise was created for unit testing and should have been deleted immediately.", createdExercise.getDescription());
        assertEquals("Time", createdExercise.getType()); //I can't figure out whether this can point to the resource file instead of being hardcoded.
        wrapper.deleteExercise(createdExercise.getEid());
	}

	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub
	}
}
