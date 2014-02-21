package com.example.workoutbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class MyWorkoutsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_workouts);
		// Show the Up button in the action bar.
		setupActionBar();
		
		LinearLayout ll = (LinearLayout)findViewById(R.id.myworkouts_layout);
		String[] workoutNames = getMyWorkOuts();
		for(int idx=0; idx < workoutNames.length; idx++)
		{
			Button myButton = new Button(this);
			myButton.setText(workoutNames[idx] + "\n 3/1/2014");
//			myButton.setOnClickListener(new View.OnClickListener() {
//		        public void onClick(View view) {
//		        	Intent intent = new Intent(null, ActiveWorkoutActivity.class);
//		        	startActivity(intent);
//		        }
//		    });
			ll.addView(myButton);	
		}
	}

	private String[] getMyWorkOuts() {
		// TODO Access Database to get this.
		return new String[]{"Chest 1", "Legs 1", "Shoulders 1", "Back 1", "Cardio 1", "Cardio 2"};
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_workout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}