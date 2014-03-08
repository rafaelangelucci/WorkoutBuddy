package com.example.workoutbuddy.test;

<<<<<<< HEAD
import com.uiuc.workoutbuddy.MainActivity;

=======
>>>>>>> 5426e1b0d8075d1394ae46647734f640a8a9f181
import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import com.example.workoutbuddy.MainActivity;
import com.example.workoutbuddy.R;


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
	
	
	
	public void testLayout(){
		testButtonLayout(mActivity.findViewById(R.id.button1));
		testButtonLayout(mActivity.findViewById(R.id.button2));
	}
	
	public void testButtonLayout(View view)
	{
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		Assert.assertTrue(view.isClickable());
	}
	
	@SuppressLint("NewApi")
	public void testNewWorkoutClick(){
		Button button = (Button)mActivity.findViewById(R.id.button2);
		Assert.assertTrue(button.callOnClick());
	}
}