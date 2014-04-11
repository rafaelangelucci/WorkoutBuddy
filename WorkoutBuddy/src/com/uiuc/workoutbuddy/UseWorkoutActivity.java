package com.uiuc.workoutbuddy;

import helperClasses.Exercise;
import helperClasses.Workout;
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

public class UseWorkoutActivity extends Activity implements OnItemClickListener//implements OnClickListener, HttpRequestListener
{

	public Exercise[] exerciseList;
	Workout wo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_workout);
		
		// Get passed in variables
		Intent i = getIntent();
		int wid = i.getExtras().getInt("wid");
		wo = WorkoutFragment.getWorkoutById(wid);
		Log.i("UseWorkoutActivity", "wo : " + wid + " - " + wo.getName());
		
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
			Log.i("UseWorkoutActivity", "ERROR. EXERCISE LIST IS NULL");
			adapter = new UseWorkoutAdapter(this, ExerciseFragment.exercises);
		}
		else
			adapter = new UseWorkoutAdapter(this, wo.getExercises());
		//TODO: fix

		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Exercise e = ExerciseFragment.exercises.get(position);
		String name = e.getName();
		Log.i("UseWorkoutActivity", "onItemClick : " + name);
	}
}