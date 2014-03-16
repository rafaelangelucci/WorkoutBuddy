package com.uiuc.workoutbuddy;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ExerciseActivity extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);
		
		final ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < ExerciseFragment.exercises.size(); i++)
		{
			list.add(ExerciseFragment.exercises.get(i).getName());
		}
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>
				(this, android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
	}
}
