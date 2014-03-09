package com.uiuc.workoutbuddy;

import java.util.concurrent.ExecutionException;

import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.ArrayAdapter;
import httpRequests.*;

public class MyWorkoutsListFragment extends ListFragment implements HttpRequestListener {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		String[][] workouts={{"a","b"}}; //Comment out the try block to display these
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		try {
			workouts = wrapper.getWorkoutList("usernameA");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				inflater.getContext(), android.R.layout.simple_list_item_1,
				workouts[0]);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void requestComplete() {
	}
}
