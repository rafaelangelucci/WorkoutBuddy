package com.uiuc.workoutbuddy;

import java.util.ArrayList;
import customListAdapter.UseWorkoutAdapter;
import helperClasses.Exercise;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class UseWorkoutActivity extends Activity implements OnItemClickListener {
	
	AsyncHttpPostWrapper postWrapper;
	public ArrayList<Exercise> exerciseList;
	static Workout wo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_workout);
		
		postWrapper = new AsyncHttpPostWrapper(null);
		
		getIntentParameters();
		
		// Grab and set text views of activity
		TextView woName = (TextView)findViewById(R.id.workout_name);
		TextView woDesc = (TextView)findViewById(R.id.workout_description);
		
		woName.setText(wo.getName());
		woDesc.setText(wo.getDescription());
		
		// Populate list view of exercises
		ListView list = (ListView)findViewById(R.id.list);
		
		UseWorkoutAdapter adapter;
		if(wo.getExercises() == null)
		{
			//TODO: this should be removed
			Log.i("UseWorkoutActivity", "ERROR. EXERCISE LIST IS NULL");
			adapter = new UseWorkoutAdapter(this, ExerciseFragment.exercises);
		}
		else // Should execute here every time unless an error
		{
			exerciseList = wo.getExercises();
			Log.i("UseWorkoutActivity", "Using workout's exercises");
			adapter = new UseWorkoutAdapter(this, wo.getExercises());
		}

		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	/**
	 * Function to grab information about the selected workout 
	 * 	  that was passed in from the previous activity
	 */
	public void getIntentParameters()
	{
		Log.i("UseWorkoutActivity", "getIntentParameters");
		Intent i = getIntent();
		int wid = i.getExtras().getInt("wid");
		try {
			wo = postWrapper.getWorkout(wid);
		} catch (Exception e) {
			Log.i("UseWorkoutActivity", "EXCEPTION CAUGHT");
			e.printStackTrace();
		}
		Log.i("UseWorkoutActivity", "wo : " + wid + " - " + wo.getName());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Exercise e = exerciseList.get(position);
		String name = e.getName();
		Log.i("UseWorkoutActivity", "onItemClick : " + name);
		
		Intent i = new Intent(this, PerformExerciseActivity.class);
		i.putExtra("eid", e.getEid());
		startActivity(i);
	}
}