package com.example.workoutbuddy.test;
import com.uiuc.workoutbuddy.MainActivity;
import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;


public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	
	public MainActivityTest(){
		super(MainActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    mActivity = getActivity();
	}
	
	/**
	 * Test successful start up of MainActivity
	 */
	public void testActivityStartup()
	{
		final String expected = mActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "WorkoutBuddy");
	}
	
	/**
	 * Test proper activity layout with all the necessary buttons
	 */
	public void testLayout(){
		testButtonLayout(mActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_my_workouts));
		testButtonLayout(mActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_new_workout));
		testButtonLayout(mActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_my_exercises));
		testButtonLayout(mActivity.findViewById(com.uiuc.workoutbuddy.R.id.btn_new_exercise));
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