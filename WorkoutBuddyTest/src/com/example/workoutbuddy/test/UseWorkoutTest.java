package com.example.workoutbuddy.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;
import android.widget.TextView;
import com.uiuc.workoutbuddy.UseWorkoutActivity;

public class UseWorkoutTest extends
ActivityInstrumentationTestCase2<UseWorkoutActivity> {

	private UseWorkoutActivity myActivity;
	private TextView name;
	private TextView desc;
	private ListView list;


	public UseWorkoutTest(){
		super(UseWorkoutActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Intent intent = new Intent();
		intent.setClassName("com.uiuc.workoutbuddy", "com.uiuc.workoutbuddy.UseWorkoutActivity");
		intent.putExtra("wid", 1);

		setActivityIntent(intent);

		myActivity = getActivity();
		name = (TextView)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.workout_name);
		desc = (TextView)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.workout_description);
		list = (ListView)myActivity.findViewById(com.uiuc.workoutbuddy.R.id.list);
	}

	/**
	 * Test successful start up of NewWorkoutActivity aka UseWorkoutActivity
	 */
	public void testActivityStartup()
	{
		final String expected = myActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}

	/**
	 * Test all text views and verify they work as designed
	 * @param view
	 */
	public void testViewsCreated() {
		assertNotNull(getActivity());
		assertNotNull(name);
		assertNotNull(desc);
		assertNotNull(list);
	}

	public void testViewsVisible() {
		ViewAsserts.assertOnScreen(name.getRootView(), desc);
		ViewAsserts.assertOnScreen(desc.getRootView(), name);
		ViewAsserts.assertOnScreen(name.getRootView(), list);
	}
	
	public void testList()
	{
		assertTrue(list.getCount() > 0);
	}
	
	
//	public void testStartingEmpty() {
//		assertTrue("Kilos field is empty", "".equals(editKilos.getText().toString()));
//		assertTrue("Pounds field is empty", "".equals(editPounds.getText().toString()));
//	}
}