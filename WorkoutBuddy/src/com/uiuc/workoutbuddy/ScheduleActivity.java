package com.uiuc.workoutbuddy;

import java.util.Calendar;

import helperClasses.Workout;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		String[] workoutNames = new String[WorkoutFragment.workouts.size()];
		for(int idx=0; idx < workoutNames.length; idx++)
			workoutNames[idx] = WorkoutFragment.workouts.get(idx).getName();

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item, workoutNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears4
		Spinner s = (Spinner)findViewById(R.id.spnr_workouts);
		s.setAdapter(adapter);
		
		DatePicker dpStart = (DatePicker) findViewById(R.id.dp_start);
		
		final Calendar c = Calendar.getInstance();
		int year, month, day;
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		dpStart.init(year, month, day, null);
		

		Button btn_done = (Button)findViewById(R.id.btn_done);
		Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
		btn_done.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.btn_done:
			Spinner wspnr = (Spinner)findViewById(R.id.spnr_workouts);
			int selectedIdx = wspnr.getSelectedItemPosition();
			
			Workout w = WorkoutFragment.workouts.get(selectedIdx);
			Toast.makeText(this.getApplicationContext(), w.getName(), Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_cancel:
			//TODO Transfer back to main or previous page.
			break;
		default:
			break;
		}
	}
}