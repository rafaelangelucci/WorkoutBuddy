package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Workout;

import java.util.ArrayList;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.uiuc.workoutbuddy.UseWorkoutActivity;
import com.uiuc.workoutbuddy.WorkoutListActivity;

import customListAdapter.WorkoutListAdapter;

public class WorkoutListActivityTest extends
ActivityInstrumentationTestCase2<WorkoutListActivity> {

	private WorkoutListActivity myActivity;
	private TextView name;
	private TextView desc;
	private ListView list;


	public WorkoutListActivityTest(){
		super(WorkoutListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		
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
	
	@UiThreadTest
	public void testList()
	{
		list = myActivity.getListView();
		assertNotNull(list);
		assertTrue(list.getCount() > 0);
		
		WorkoutListAdapter adapter = 
				new WorkoutListAdapter(getInstrumentation().getContext(), mockWorkouts());
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		assertNotNull(list.getChildAt(0));
		assertTrue(list.getCount() == 3);
	}
	
	
//	public void testStartingEmpty() {
//		assertTrue("Kilos field is empty", "".equals(editKilos.getText().toString()));
//		assertTrue("Pounds field is empty", "".equals(editPounds.getText().toString()));
//	}
}