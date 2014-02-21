package com.example.workoutbuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GraphsFragment extends Fragment {
	View view;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.graph_main, container, false);
		} catch(InflateException e) {
			//already made
		}
		return view;
	}
}
