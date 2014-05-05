package com.uiuc.workoutbuddy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import helperClasses.Exercise;
import helperClasses.Set;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class NewExerciseActivity extends Activity implements HttpRequestListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_exercise);
		// Show the Up button in the action bar.
		setupActionBar();
		final Button button = (Button) findViewById(R.id.buttonNewExerciseOK);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					saveAndClose();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_exercise, menu);
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

	private void saveAndClose() throws InterruptedException, ExecutionException {
		if (((EditText) findViewById(R.id.editTextExerciseName)).getText().toString().isEmpty()) {
			Toast.makeText(this.getApplicationContext(), "Please enter an exercise name.", Toast.LENGTH_SHORT).show();
			return;
		}
		EditText editTextExerciseDescription = (EditText) findViewById(R.id.editTextExerciseDescription);
		EditText editTextExerciseName = (EditText) findViewById(R.id.editTextExerciseName);
		Spinner spinnerExerciseType = (Spinner) findViewById(R.id.spinnerExerciseType);
		Exercise e = new Exercise(editTextExerciseName.getText().toString(),
				spinnerExerciseType.getSelectedItem().toString(),
				editTextExerciseDescription.getText().toString(),
				LoginActivity.userName, //Where should this be coming from?
				null); //sets
		new AsyncHttpPostWrapper(this).addExercise(e);
		ExerciseFragment.exercises.add(e);
		finish();
	}

	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub
	}

}
