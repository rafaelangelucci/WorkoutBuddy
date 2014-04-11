package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.ExerciseGraphListActivity;
import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;


public class GraphListActivityTest extends
		ActivityInstrumentationTestCase2<ExerciseGraphListActivity> {

	private ExerciseGraphListActivity lActivity;
	
	public GraphListActivityTest(){
		super(ExerciseGraphListActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    lActivity = getActivity();
	}
	
	/**
	 * Test successful start up of MainActivity
	 */
	public void testActivityStartup()
	{
		String expected = lActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}
	
	public void testRowLayout(){
		View view = (lActivity.findViewById(com.uiuc.workoutbuddy.R.id.row_name));
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		
		view = (lActivity.findViewById(com.uiuc.workoutbuddy.R.id.row_name));
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_radio));		
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
	
}