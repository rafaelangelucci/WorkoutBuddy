package com.example.workoutbuddy;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ExercisesListFragment extends ListFragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		     inflater.getContext(), android.R.layout.simple_list_item_1,
		     new String[] {"Exercise A", "Exercise B", "Exercise C", "Exercise D"});
    setListAdapter(adapter);
    return super.onCreateView(inflater, container, savedInstanceState);
	}
}
