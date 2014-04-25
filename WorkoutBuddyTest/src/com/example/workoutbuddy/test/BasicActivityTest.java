package com.example.workoutbuddy.test;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import com.uiuc.workoutbuddy.NewWorkoutActivity;

public class BasicActivityTest extends
	ActivityInstrumentationTestCase2<NewWorkoutActivity> {

	private NewWorkoutActivity myActivity;

	
	public BasicActivityTest(){
		super(NewWorkoutActivity.class);
	}

	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    
	    myActivity = getActivity();
	}
	
	/**
	 * Test successful start up of NewWorkoutActivity aka BasicActivity
	 */
	public void testActivityStartup()
	{
		final String expected = myActivity.getString(com.uiuc.workoutbuddy.R.string.title_activity_new_workout);
		assertEquals(expected, "NewWorkoutActivity");
	}
	
	/**
	 * Test proper activity layout with all the necessary buttons
	 */
	public void testLayout(){
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_plus));
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_done));
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_minus));
	}
	
	/**
	 * Test all button clicks and verify they work as designed
	 * @param view
	 */
	public void testButtonLayout(View view)
	{
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		Assert.assertTrue(view.isClickable());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonDoneClick(){
		Button button = (Button)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_done);
		Assert.assertTrue(button.callOnClick());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonPlusClick(){
		Button button = (Button)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_plus);
		Assert.assertTrue(button.callOnClick());
	}
	
	@SuppressLint("NewApi")
	public void testbuttonMinusClick(){
		Button button = (Button)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_minus);
		Assert.assertTrue(button.callOnClick());
	}
	

	public void testExerciseAdded()
	{
		Assert.fail();
	}
	
	public void testExerciseRemoved()
	{
		Assert.fail();
	}
	
	public void testWorkoutAdded()
	{
		Assert.fail();
	}
	
	public void testWorkoutNotAdded()
	{
		Assert.fail();
	}
}