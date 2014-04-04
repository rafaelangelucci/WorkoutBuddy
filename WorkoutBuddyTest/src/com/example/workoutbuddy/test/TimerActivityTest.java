package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.TimerActivity;
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
import android.widget.EditText;
import android.widget.ListView;


public class TimerActivityTest extends
		ActivityInstrumentationTestCase2<TimerActivity> {

	private TimerActivity tActivity;
	
	public TimerActivityTest(){
		super(TimerActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    tActivity = getActivity();
	}
	
	/**
	 * Test successful start up of MainActivity
	 */
	public void testActivityStartup()
	{
		final String expected = tActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}
	
	/**
	 * Test proper activity layout with all the necessary buttons
	 */
	public void testLayout(){
		testButtonLayout(tActivity.findViewById(com.uiuc.workoutbuddy.R.id.timer_set_button));
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
	
	public void testText() {
		  // simulate user action to input some value into EditText:
		  final EditText time = (EditText) tActivity.findViewById(com.uiuc.workoutbuddy.R.id.time_input);
		  
		  tActivity.runOnUiThread(new Runnable() {
		    public void run() {
		      time.setText("2");
		    }
		  });

		  // Check if the EditText was given an input
		  assertNotNull(time.getText());
		}
}