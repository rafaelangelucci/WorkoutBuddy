package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Workout;

import java.util.ArrayList;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.uiuc.workoutbuddy.UseWorkoutActivity;
import com.uiuc.workoutbuddy.WorkoutListActivity;

import customListAdapter.WorkoutListAdapter;

//public class WorkoutListActivityTest extends ActivityInstrumentationTestCase2<WorkoutListActivity> {
public class WorkoutListActivityTest extends ActivityUnitTestCase<WorkoutListActivity> {

	private WorkoutListActivity activity;
	private TextView name;
	private TextView desc;
	private ListView list;


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

	public ArrayList<Workout> mockWorkouts()
	{
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		for(Integer i = 0; i < 3; i++) {
			workouts.add(new Workout(500 + i, "Workout " + i.toString(),
					"date", "desc " + i.toString(), "usernameA", new ArrayList<Exercise>()));
		}

		return workouts;
	}

	public void testViewsCreated() {
		assertNotNull(getActivity());
	}

	public void testActivityStartup()
	{
		final String expected = activity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}

	public final void testItemClick() {
		getInstrumentation().callActivityOnStart(activity);
		getInstrumentation().callActivityOnResume(activity);

		// Check if list exists
		ListView list = activity.getListView();
		assertNotNull("Intent was null", list);

		// Load test data
		WorkoutListAdapter adapter = new WorkoutListAdapter(getInstrumentation().getContext(), mockWorkouts());
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		assertTrue(adapter.getCount() > 0);
		assertTrue(adapter.getCount() == 3);

		/*
		// Check if list has at least one item to click
		View firstItem = list.getAdapter().getView(0, null, null);
		assertNotNull(firstItem);

		// Perform a click on the first item
		list.performItemClick(
				firstItem,
				0,
				list.getAdapter().getItemId(0)
				);

		// Check if the contact details activity got started
		Intent intent = getStartedActivityIntent();
		assertNotNull(intent);
		assertEquals(
				ContactDetailActivity.class.getName(),
				intent.getComponent().getClassName()
				);*/
	}
}