package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Set;
import java.util.ArrayList;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.ListView;
import com.uiuc.workoutbuddy.ExerciseListActivity;
import customListAdapter.ExerciseListAdapter;

public class ExerciseListActivityTest extends ActivityUnitTestCase<ExerciseListActivity> {

	private ExerciseListActivity activity;
	private ListView list;
	private ExerciseListAdapter adapter;

	public ExerciseListActivityTest(){
		super(ExerciseListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Intent intent = new Intent(getInstrumentation().getTargetContext(), ExerciseListActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Function that generates mock exercise objects to inject into list
	 * @return ArrayList of exercise objects
	 */
	public ArrayList<Exercise> mockExercises()
	{
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		for(Integer i = 0; i < 3; i++) {
			exercises.add(
					new Exercise("Exercise(" + i.toString() + ")", "none", "mock", "usernameA", new ArrayList<Set>())
					);
		}

		return exercises;
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
		adapter = new ExerciseListAdapter(getInstrumentation().getContext(), mockExercises());
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		assertTrue(adapter.getCount() > 0);
		assertTrue(adapter.getCount() == 3);
	}
}