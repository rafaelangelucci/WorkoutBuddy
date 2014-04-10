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

public class UseWorkoutActivity extends Activity //implements OnClickListener, HttpRequestListener
{

	public int numExercises = 0;
	public Exercise[] exerciseList;
	Workout wo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_workout);
		
		Intent i = getIntent();
		int wid = i.getExtras().getInt("wid");
		Log.i("UseWorkoutActivity", "onCreate: intent wid = " + wid);
		wo = WorkoutFragment.getWorkoutById(wid);
		Log.i("UseWorkoutActivity", "wo name: " + wo.getName());
		

//		Button done = (Button)findViewById(R.id.btn_done);
//		Button minus = (Button)findViewById(R.id.btn_minus);
//		Button plus = (Button)findViewById(R.id.btn_plus);
//
//		done.setOnClickListener(this);
//		plus.setOnClickListener(this);
//		minus.setOnClickListener(this);

//		exerciseList = new Exercise[ExerciseFragment.exercises.size()];
//		for(int i = 0; i < ExerciseFragment.exercises.size(); i++)
//		{
//			exerciseList[i] = ExerciseFragment.exercises.get(i);
//		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

//	@Override
//	public void onClick(View v) 
//	{
//		switch(v.getId())
//		{
//		case R.id.btn_done:
//			btn_DoneClicked(v);
//			break;
//		case R.id.btn_plus:
//			btn_PlusClicked(v);
//			break;
//		case R.id.btn_minus:
//			btn_MinusClicked(v);
//			break;
//		default:
//			break;
//		}
//	}
}