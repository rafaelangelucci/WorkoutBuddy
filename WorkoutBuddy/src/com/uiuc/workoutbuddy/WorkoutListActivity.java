package com.uiuc.workoutbuddy;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class WorkoutListActivity extends ListActivity implements OnItemClickListener
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);
		
		Log.i("WorkoutListActivity", "onCreate");
		
        CustomWorkoutAdapter adapter = new CustomWorkoutAdapter(this, WorkoutFragment.workouts);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String name = WorkoutFragment.workouts.get(position).getName();
		Log.i("WorkoutListActivity", "onItemClick : " + name);
		Toast.makeText(this.getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
}
