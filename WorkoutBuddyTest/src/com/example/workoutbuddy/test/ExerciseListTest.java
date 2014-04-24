package com.example.workoutbuddy.test;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.uiuc.workoutbuddy.ExerciseListActivity;

public class ExerciseListTest extends
	ActivityInstrumentationTestCase2<ExerciseListActivity> {

	private ExerciseListActivity myActivity;
	private ListView list;

	
	public ExerciseListTest(){
		super(ExerciseListActivity.class);
	}

	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    
	    myActivity = getActivity();
	    list = myActivity.getListView();
	}
	
	/**
	 * Test successful start up of NewWorkoutActivity aka BasicActivity
	 */
	public void testActivityStartup()
	{
		final String expected = myActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}
	
	
	/**
	 * Test all button clicks and verify they work as designed
	 * @param view
	 */
	public void testViews(View view)
	{
		Assert.assertNotNull(myActivity);
		Assert.assertTrue(view.isShown());
		Assert.assertNotNull(list);
	}
	
	public void testList()
	{
		assertTrue(list.getCount() > 0);
	}
	
}