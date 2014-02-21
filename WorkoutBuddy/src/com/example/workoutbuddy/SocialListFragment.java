package com.example.workoutbuddy;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SocialListFragment extends ListFragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		     inflater.getContext(), android.R.layout.simple_list_item_1,
		     new String[] {"Share a Workout", "Friend A", "Friend B", "Friend C", "Friend D"});
    setListAdapter(adapter);
    return super.onCreateView(inflater, container, savedInstanceState);
	}
}
