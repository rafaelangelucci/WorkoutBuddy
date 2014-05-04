package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Workout;

import java.util.ArrayList;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ListView;
import com.uiuc.workoutbuddy.UseWorkoutActivity;
import com.uiuc.workoutbuddy.WorkoutListActivity;
import customListAdapter.WorkoutListAdapter;

public class WorkoutListActivityTest extends ActivityUnitTestCase<WorkoutListActivity> {

	private WorkoutListActivity activity;
	private ListView list;
	private WorkoutListAdapter adapter;

	public WorkoutListActivityTest(){
		super(WorkoutListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Intent intent = new Intent(getInstrumentation().getTargetContext(), WorkoutListActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Function that generates mock workout objects to inject into list
	 * @return ArrayList of workout objects
	 */
	public ArrayList<Workout> mockWorkouts()
	{
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		for(Integer i = 0; i < 3; i++) {
			workouts.add(new Workout(500 + i, "Workout " + i.toString(),
					"date", "desc " + i.toString(), "usernameA", new ArrayList<Exercise>()));
		}

		return workouts;
	}

	/**
	 * Test activity created successfully
	 */
	public void testViewsCreated() {
		assertNotNull(getActivity());
	}

	/**
	 * Test correct activity was launched
	 */
	public void testActivityStartup()
	{
		final String expected = activity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}

	/**
	 * Test to check proper list creation with specified size
	 */
	public final void testListCreation() {
		getInstrumentation().callActivityOnStart(activity);
		getInstrumentation().callActivityOnResume(activity);

		// Check if list exists
		list = activity.getListView();
		assertNotNull("Intent was null", list);

		// Load test data
		adapter = new WorkoutListAdapter(getInstrumentation().getContext(), mockWorkouts());
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		assertTrue(adapter.getCount() > 0);
		assertTrue(adapter.getCount() == 3);
	}
	
	/**
	 * Test to execute list item click and corresponding activity startup
	 */
	public final void testItemClick()
	{
		if(list != null && adapter != null)
		{
			// Check if list has at least one item to click
			View firstItem = list.getAdapter().getView(0, null, null);
			assertNotNull(firstItem);
	
			// Perform a click on the first item
			list.performItemClick(firstItem, 0, list.getAdapter().getItemId(0));
	
			// Check if the contact details activity got started
			Intent intent = getStartedActivityIntent();
			assertNotNull(intent);
			assertEquals(UseWorkoutActivity.class.getName(),intent.getComponent().getClassName());
		}
	}
}