package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.MainActivity;
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
	
	
//	
//	public void testLayout(){
//		testButtonLayout(mActivity.findViewById(R.id.button1));
//		testButtonLayout(mActivity.findViewById(R.id.button2));
//	}
//	
//	public void testButtonLayout(View view)
//	{
//		Assert.assertNotNull(view);
//		Assert.assertTrue(view.isShown());
//		Assert.assertTrue(view.isClickable());
//	}
//	
//	@SuppressLint("NewApi")
//	public void testNewWorkoutClick(){
//		Button button = (Button)mActivity.findViewById(R.id.button2);
//		Assert.assertTrue(button.callOnClick());
//	}
	
	public void testWorkoutList(){
		ViewAsserts.assertOnScreen(mActivity.findViewById(com.uiuc.workoutbuddy.R.id.WorkoutsListFragment).getRootView(),
				mActivity.findViewById(com.uiuc.workoutbuddy.R.id.WorkoutsListFragment));
	}
}