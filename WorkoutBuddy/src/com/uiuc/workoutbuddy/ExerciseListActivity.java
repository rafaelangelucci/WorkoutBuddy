package com.uiuc.workoutbuddy;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ExerciseListActivity extends ListActivity implements OnItemClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);
						
        CustomListAdapter adapter = new CustomListAdapter(this, ExerciseFragment.exercises);

//		final ArrayList<String> list = new ArrayList<String>();
//		for(int i = 0; i < ExerciseFragment.exercises.size(); i++)
//		{
//			list.add(ExerciseFragment.exercises.get(i).getName());
//		}
		
//		final ArrayAdapter<String> adapter = new ArrayAdapter<String>
//				(this, android.R.layout.simple_list_item_1, list);
//		setListAdapter(adapter);
        setListAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String name = ExerciseFragment.exercises.get(position).getName();
		Toast.makeText(this.getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	}
	
}
