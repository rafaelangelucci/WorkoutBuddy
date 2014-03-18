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
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonMinus));
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonPlus));
		testButtonLayout(myActivity.findViewById(com.uiuc.workoutbuddy.R.id.buttonDone));
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
	

	public void testExerciseAdded()
	{
		//get list of exercise inputs
		//check list is 1
		//btn_PlusClicked()
		//get list of exercise inputs
		//check list is 2
		Assert.fail();
	}
	
	public void testExerciseRemoved()
	{
//		//btn_PlusClicked()
//		List<View> exercisesInputs = myActivity.getExerciseInputs();
//		//get list of exercise inputs
//		//check list is 2
//		//btn_MinusClicked()
//		List<View> exercisesInputs = myActivity.getExerciseInputs();
//		//check list is 1
//		//btn_MinusClicked()
//		List<View> exercisesInputs = myActivity.getExerciseInputs();
//		//check list is 1
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