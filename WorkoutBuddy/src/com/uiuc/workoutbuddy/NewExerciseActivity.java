package com.uiuc.workoutbuddy;

import httpRequests.AsyncHttpPostWrapper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class NewExerciseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_exercise);
		// Show the Up button in the action bar.
		setupActionBar();
		final Button button = (Button) findViewById(R.id.buttonNewExerciseOK);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText editTextDescription = (EditText) findViewById(R.id.editTextExerciseDescription);
            	EditText editTextName = (EditText) findViewById(R.id.editTextExerciseName);
            	//Not sure about connection information, is it created on the login page?
            	//AsyncHttpPostWrapper.addExercise("TestUser", "TestType", editTextName.getText().toString(), editTextDescription.getText().toString());
            	finish();
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

}
