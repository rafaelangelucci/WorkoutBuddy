package com.uiuc.workoutbuddy;

import helperClasses.Exercise;
import helperClasses.Workout;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class UseWorkoutActivity extends Activity //implements OnClickListener, HttpRequestListener
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}
}