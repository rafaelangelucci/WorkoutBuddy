package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.LoginActivity;
import com.uiuc.workoutbuddy.MyWorkoutsListFragment;
import com.uiuc.workoutbuddy.WorkoutFragment;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	private LoginActivity lActivity;
	
	public LoginActivityTest(){
		super(LoginActivity.class);
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
		assertEquals(expected, "WorkoutBuddy");
	}
	
	/**
	 * Test proper activity layout with all the necessary buttons
	 */
	public void testLayout(){
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.login_button));
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.signup_button));
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.skip_button));
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