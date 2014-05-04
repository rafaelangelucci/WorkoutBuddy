package com.uiuc.workoutbuddy;

import customListAdapter.ExerciseListAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

public class ExerciseListActivity extends ListActivity implements OnItemClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);

		ExerciseListAdapter adapter = new ExerciseListAdapter(this, ExerciseFragment.exercises);

		setListAdapter(adapter);
	}

	/**
	 * Function to handle list item click
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		CheckBox cb = (CheckBox)view.findViewById(R.id.btn_check_box);
		cb.performClick();
		if(cb.isChecked())
		{
			String name = ExerciseFragment.exercises.get(position).getName();
			Toast.makeText(this.getApplicationContext(), name, Toast.LENGTH_SHORT).show();
			Log.i("ExerciseListActivity", "onItemClick : cb is checked");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
