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

public class PerformExerciseActivity extends Activity implements OnItemClickListener
{
	Exercise ex;
	Workout wo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perform_exercise);
		wo = UseWorkoutActivity.wo;
		Log.i("PerformExerciseActivity", "onCreate");
		
		// Get passed in variables
		Intent intent = getIntent();
		int eid = intent.getExtras().getInt("eid");
		for(int i = 0; i < wo.getExercises().size(); i++){
			if(wo.getExercises().get(i).getEid() == eid)
				ex = wo.getExercises().get(i);
		}
		
		Log.i("PerformExerciseActivity", "Exercise : " + eid + " - " + ex.getName());
		
		// Grab and set text views of activity
		TextView exName = (TextView)findViewById(R.id.exercise_name);
		TextView exDesc = (TextView)findViewById(R.id.exercise_desc);
		TextView exType = (TextView)findViewById(R.id.type);
		TextView exSets = (TextView)findViewById(R.id.num_sets);
		
		exName.setText(ex.getName());
		exDesc.setText("Description : " + ex.getDescription());
		exType.setText("Type : " + ex.getType());
		exSets.setText("Suggested number of sets: " + ((Integer)ex.getSets().size()).toString() );		

		// TODO: set information here
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.i("PerformExerciseActivity", "onItemClick");
	}
}