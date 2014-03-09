package com.example.workoutbuddy.test;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.example.workoutbuddy.R;
import com.uiuc.workoutbuddy.NewWorkoutActivity;

public class NewWorkoutActivityTest extends
	ActivityInstrumentationTestCase2<NewWorkoutActivity> {

	private NewWorkoutActivity nwActivity;

	
	public NewWorkoutActivityTest(){
		super(NewWorkoutActivity.class);
	}

	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    
	    nwActivity = getActivity();
	}

	public void testPreConditions(){
		Assert.assertEquals(1, nwActivity.numExercises);
	}
	
	
	public void testLayout(){
		testButtonLayout(nwActivity.findViewById(R.id.buttonMinus));
		testButtonLayout(nwActivity.findViewById(R.id.buttonPlus));
		testButtonLayout(nwActivity.findViewById(R.id.buttonDone));
	}
	
	public void testButtonLayout(View view)
	{
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		Assert.assertTrue(view.isClickable());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonDoneClick(){
		Button button = (Button)nwActivity.findViewById(R.id.buttonDone);
		Assert.assertTrue(button.callOnClick());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonPlusClick(){
		Button button = (Button)nwActivity.findViewById(R.id.buttonPlus);
		Assert.assertTrue(button.callOnClick());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonMinusClick(){
		Button button = (Button)nwActivity.findViewById(R.id.buttonMinus);
		Assert.assertTrue(button.callOnClick());
	}
}