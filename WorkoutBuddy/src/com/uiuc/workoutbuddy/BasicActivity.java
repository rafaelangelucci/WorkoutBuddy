package com.uiuc.workoutbuddy;

import helperClasses.Exercise;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class BasicActivity extends Activity implements OnClickListener, HttpRequestListener
{

	public int numExercises = 0;
	public Exercise[] exerciseList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_workout);

		Button done = (Button)findViewById(R.id.btn_done);
		Button minus = (Button)findViewById(R.id.btn_minus);
		Button plus = (Button)findViewById(R.id.btn_plus);

		done.setOnClickListener(this);
		plus.setOnClickListener(this);
		minus.setOnClickListener(this);

		exerciseList = new Exercise[ExerciseFragment.exercises.size()];
		for(int i = 0; i < ExerciseFragment.exercises.size(); i++)
		{
			exerciseList[i] = ExerciseFragment.exercises.get(i);
		}
//		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
//		signal = new CountDownLatch(1);
//		try {
//			exerciseList = wrapper.getExerciseList("usernameA");
//			signal.await(1, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
		addExerciseSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_done:
			btn_DoneClicked(v);
			break;
		case R.id.btn_plus:
			btn_PlusClicked(v);
			break;
		case R.id.btn_minus:
			btn_MinusClicked(v);
			break;
		default:
			break;
		}
	}

	private void btn_DoneClicked(View v)
	{
		EditText et = (EditText)findViewById(R.id.editText1);
		String name = et.getText().toString();
		et = (EditText)findViewById(R.id.EditText01);
		String descript = et.getText().toString();

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");		
		Calendar rightNow = Calendar.getInstance();
		String createDate = df.format(rightNow.getTime());
		
		if(name.equals(""))
			Toast.makeText(this.getApplicationContext(), "The name textbox must be filled.", Toast.LENGTH_SHORT).show();
		else
		{
			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				Workout workout = new Workout(name, createDate, descript, "usernameA", null);
				wrapper.addWorkout(workout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			Intent intent = new Intent(this, MainActivity.class);
	    	startActivity(intent);
		}
	}

	private void btn_MinusClicked(View v)
	{
		if(this.numExercises>1)
		{
			LinearLayout lv = (LinearLayout)findViewById(R.id.linearLayout);
			lv.removeViewAt(this.numExercises -1);
			this.numExercises--;
		}
	}

	private void btn_PlusClicked(View v)
	{
		addExerciseSpinner();	
	}

	@Override
	public void requestComplete() {
		Log.i( "requestComplete()", "Request Completed countDown()");
	}
	
	private void addExerciseSpinner()
	{
		LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
		if(ll==null) return;
		

		Spinner spinner = new Spinner(this);
		String[] exerciseNames = new String[exerciseList.length];
		for(int i = 0; i < exerciseNames.length; i++){
			exerciseNames[i] = exerciseList[i].getName();
		}
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item, exerciseNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
		spinner.setAdapter(adapter);
		ll.addView(spinner, this.numExercises);
		this.numExercises++;
	}
}