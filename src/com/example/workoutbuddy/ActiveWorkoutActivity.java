package com.example.workoutbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActiveWorkoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_workout);
		
		LinearLayout ll = (LinearLayout)findViewById(R.id.myworkouts_layout);
		String[] exerciseNames = getExercises();
		for(int idx=0; idx < exerciseNames.length; idx++)
		{
			Button myButton = new Button(this);
			myButton.setText(exerciseNames[idx]);
			ll.addView(myButton);	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.active_workout, menu);
		return true;
	}

	private String[] getExercises() {
		// TODO Access Database to get this.
		return new String[]{"Squats", "Leg Press", "Leg Curls", "Calf Raises", "Leg Extensions"};
	}
}