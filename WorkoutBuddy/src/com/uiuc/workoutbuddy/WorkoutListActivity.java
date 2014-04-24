package com.uiuc.workoutbuddy;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WorkoutListActivity extends ListActivity implements OnItemClickListener
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_list);
		
		Log.i("WorkoutListActivity", "onCreate");
		
        WorkoutListAdapter adapter = new WorkoutListAdapter(this, WorkoutFragment.workouts);

        setListAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String name = WorkoutFragment.workouts.get(position).getName();
		Toast.makeText(this.getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
}
