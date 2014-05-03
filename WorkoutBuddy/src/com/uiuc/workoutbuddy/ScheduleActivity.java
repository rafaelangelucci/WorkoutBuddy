package com.uiuc.workoutbuddy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import helperClasses.Exercise;
import helperClasses.TemplateExercise;
import helperClasses.TemplateWorkout;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Class is used to schedule a new workout.
 * @author Joel
 *
 */
public class ScheduleActivity extends Activity implements OnClickListener, HttpRequestListener{

	ArrayList<TemplateWorkout> templateWks = new ArrayList<TemplateWorkout>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);



		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		try {
			templateWks = wrapper.getTemplateWorkoutList(LoginActivity.userName);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		String[] workoutNames = new String[templateWks.size()];
		for(int idx=0; idx < workoutNames.length; idx++)
			workoutNames[idx] = templateWks.get(idx).getName();
		if(workoutNames.length==0)
		{
			Toast.makeText(this.getApplicationContext(), "You do not have any workout templates saved.  You must save a template workout before scheduling a workout.", Toast.LENGTH_SHORT).show();
			Toast.makeText(this.getApplicationContext(), "Username: " + LoginActivity.userName, Toast.LENGTH_SHORT).show();

			Intent i1 = new Intent(this, MainActivity.class);
			startActivity(i1);
		}

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

	/**
	 * Normally this would inflate the menu, but since there is no menu, this method does nothing.
	 * @param menu Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}

	/**
	 * This is the onclicklistener.
	 * @param v View is the button that is clicked.
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.btn_done:
			try {
				if(btn_doneClicked())
				{
					Intent i1 = new Intent(this, MainActivity.class);
					startActivity(i1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
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

	private boolean btn_doneClicked() throws InterruptedException, ExecutionException {
		Spinner wspnr = (Spinner)findViewById(R.id.spnr_workouts);
		int selectedIdx = wspnr.getSelectedItemPosition();
		TemplateWorkout w = templateWks.get(selectedIdx);
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




	/**
	 * This creates an new workout and adds it to the database with the specified dateStr
	 * @param w TemplateWorkout
	 * @param dateStr String represents the date of the scheduled workout
	 * @return true if successful, otherwise false.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean repeatWorkout(TemplateWorkout w, String dateStr) throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		if(isDateStrValid(dateStr))
		{
			ArrayList<TemplateExercise> Texercises = w.getExercises();
			ArrayList<Exercise> exercises = new ArrayList<Exercise>();
			for(int i = 0; i < Texercises.size(); i++){
				exercises.add(wrapper.getExercise(Texercises.get(i).getEid()));
			}
			Workout w2 = new Workout(w.getName(), dateStr, w.getDescription(), w.getUsername(), exercises);

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


	/**
	 * This schedules 1 to many workouts with the given TemplateWorkout.
	 * @param w TemplateWorkout
	 * @param startDateStr the first date of the scheduled workout
	 * @param numTimes the number of workouts to schedule
	 * @param numBetween the number of days between scheduled workouts of this type.
	 * @return true if schedule workouts successfully added to database.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean repeatWorkout(TemplateWorkout w, String startDateStr, int numTimes, int numBetween) throws InterruptedException, ExecutionException {
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



	/**
	 * Executes after a HTTP Request is finished.
	 */
	@Override
	public void requestComplete() {
	}	
}