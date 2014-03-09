package com.example.workoutbuddy.test;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import com.uiuc.workoutbuddy.BasicActivity;

public class BasicActivityTest extends
	ActivityInstrumentationTestCase2<BasicActivity> {

	private BasicActivity myActivity;

	
	public BasicActivityTest(){
		super(BasicActivity.class);
	}

	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    
	    myActivity = getActivity();
	}
	
	public void testActivityStartup()
	{
		final String expected = myActivity.getString(com.uiuc.workoutbuddy.R.string.title_activity_new_workout);
		assertEquals(expected, "NewWorkoutActivity");
	}
	
	public void testLayout(){
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonMinus));
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonPlus));
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonDone));
	}
	
	public void testButtonLayout(View view)
	{
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		Assert.assertTrue(view.isClickable());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonDoneClick(){
		Button button = (Button)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonDone);
		Assert.assertTrue(button.callOnClick());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonPlusClick(){
		Button button = (Button)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonPlus);
		Assert.assertTrue(button.callOnClick());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonMinusClick(){
		Button button = (Button)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonMinus);
		Assert.assertTrue(button.callOnClick());
	}
}