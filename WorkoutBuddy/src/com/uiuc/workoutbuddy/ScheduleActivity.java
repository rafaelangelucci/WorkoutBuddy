package com.uiuc.workoutbuddy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener, HttpRequestListener{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		String[] workoutNames = new String[WorkoutFragment.workouts.size()];
		if(workoutNames.length==0)
		{
			Toast.makeText(this.getApplicationContext(), "You do not have any workout templates saved.  You must save a template workout before scheduling a workout.", Toast.LENGTH_SHORT).show();
			Intent i1 = new Intent(this, MainActivity.class);
			startActivity(i1);
		}
		for(int idx=0; idx < workoutNames.length; idx++)
			workoutNames[idx] = WorkoutFragment.workouts.get(idx).getName();
		
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item, workoutNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears4
		Spinner s = (Spinner)findViewById(R.id.spnr_workouts);
		s.setAdapter(adapter);
		
		findViewById(R.id.et_numtimes).setVisibility(4);
		findViewById(R.id.et_numbetween).setVisibility(4);

		findViewById(R.id.cb_repeat).setOnClickListener(this);
		findViewById(R.id.btn_done).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
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
			if(btn_doneClicked())
			{
				Intent i1 = new Intent(this, MainActivity.class);
				startActivity(i1);
			}
			break;
		case R.id.btn_cancel:
			Intent i1 = new Intent(this, MainActivity.class);
			startActivity(i1);
			break;
		case R.id.cb_repeat:
			CheckBox cb = (CheckBox) v;
			if(cb.isChecked())
			{
				findViewById(R.id.et_numtimes).setVisibility(0);
				findViewById(R.id.et_numbetween).setVisibility(0);
			}
			else
			{
				findViewById(R.id.et_numtimes).setVisibility(4);
				findViewById(R.id.et_numbetween).setVisibility(4);
			}
		default:
			break;
		}
	}

	private boolean btn_doneClicked() {
		Spinner wspnr = (Spinner)findViewById(R.id.spnr_workouts);
		int selectedIdx = wspnr.getSelectedItemPosition();	
		Workout w = WorkoutFragment.workouts.get(selectedIdx);
		EditText et = (EditText)findViewById(R.id.et_date);
		String dateStr = et.getText().toString();

		if(((CheckBox) findViewById(R.id.cb_repeat)).isChecked())
		{
			et = (EditText)findViewById(R.id.et_numbetween);
			int numTimes = Integer.parseInt(et.getText().toString());
			et = (EditText)findViewById(R.id.et_numtimes);
			int numBetween = Integer.parseInt(et.getText().toString());
			return repeatWorkout(w, dateStr, numTimes, numBetween);
		}
		else
		{
			return repeatWorkout(w, dateStr);
		}
	}




	public boolean repeatWorkout(Workout w1, String dateStr) {
		if(isDateStrValid(dateStr))
		{
			Workout w2 = new Workout(w1.getName(), dateStr, w1.getDescription(), w1.getUsername(), w1.getExercises());

			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				wrapper.addWorkout(w2);
				return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return false;
	}



	public boolean repeatWorkout(Workout w, String startDateStr, int numTimes, int numBetween) {
		if(isDateStrValid(startDateStr))
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(df.parse(startDateStr));
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}

			for(int idx = 0; idx < numTimes; idx++)
			{
				repeatWorkout(w, df.format(cal.getTime()));
				cal.add(Calendar.DATE, numBetween);
			}
			return true;
		}
		return false;
	}


	private boolean isDateStrValid(String newDateStr) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar newDateCal = Calendar.getInstance();

		try {
			newDateCal.setTime(df.parse(newDateStr));
		} catch (java.text.ParseException e1) {
			Toast.makeText(this.getApplicationContext(), "Start date must be in MM//dd/yyyy format.", Toast.LENGTH_SHORT).show();
			return false;
		}

		Calendar nowCal = Calendar.getInstance();
		if(nowCal.before(newDateCal))
			return true;
		else
		{
			Toast.makeText(this.getApplicationContext(), "Start date must be schedule in the future.", Toast.LENGTH_SHORT).show();
			return false;
		}
	}



	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub	
	}	
}