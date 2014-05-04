package com.uiuc.workoutbuddy;

import customListAdapter.CustomGraphListAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Activity class to create the exercise list for the graph activity
 *
 */
public class ExerciseGraphListActivity extends ListActivity implements OnItemClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list); 
						
        CustomGraphListAdapter adapter = new CustomGraphListAdapter(this, ExerciseFragment.exercises);
        setListAdapter(adapter);
	}

	/**
	 * The actions to perform after an item in the exercise list is clicked
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(this.getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, GraphActivity.class);
		
		String name = ExerciseFragment.exercises.get(position).getName();
		intent.putExtra("exercise", name);
		
    	startActivity(intent);
	}
	
}
