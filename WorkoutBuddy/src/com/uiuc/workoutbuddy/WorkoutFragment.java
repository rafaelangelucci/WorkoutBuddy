package com.uiuc.workoutbuddy;

import java.util.ArrayList;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import com.uiuc.workoutbuddy.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Class to contain information for the Workout Fragment tab
 * 
 * @author tmadigan7
 * 
 */
@SuppressLint("ValidFragment")
public class WorkoutFragment extends Fragment implements OnClickListener,
		HttpRequestListener {
	View view;
	Context c;
	Button myWorkouts, newWorkout, schedule;

	static ArrayList<Workout> workouts = new ArrayList<Workout>();

	/**
	 * Default Constructor
	 */
	public WorkoutFragment() {
	}

	/**
	 * Constructor
	 * 
	 * @param c
	 *            context
	 */
	public WorkoutFragment(Context c) {
		this.c = c;
	}

	/**
	 * On Creation of this fragment, this method executes to load the layout and
	 * registers all buttons and sets onclick listeners
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater
					.inflate(R.layout.workout_fragment, container, false);
		} catch (InflateException e) {
			e.printStackTrace();
		}

		workouts.clear();

		// Grab all buttons and set their onClick listeners
		myWorkouts = (Button) view.findViewById(R.id.btn_my_workouts);
		newWorkout = (Button) view.findViewById(R.id.btn_new_workout);
		schedule = (Button) view.findViewById(R.id.btn_schedule);
		myWorkouts.setOnClickListener(this);
		newWorkout.setOnClickListener(this);
		schedule.setOnClickListener(this);

		// Execute database pull to grab all workouts
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);

		try {
			Workout[] responses = wrapper
					.getWorkoutList(LoginActivity.userName);
			if (responses.length == 0)
				Log.i("Asyn Task", "0 workouts");

			for (int i = 0; i < responses.length; i++) {
				workouts.add(responses[i]);
				Log.i("Workout Added", responses[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * On click listener for my workouts and new workout buttons
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_my_workouts:
			Log.i("WorkoutFragment", "OnClick : My Workouts");
			Intent intent = new Intent(c, WorkoutListActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_new_workout:
			Intent i = new Intent(c, NewWorkoutActivity.class);
			startActivity(i);
			Log.i("WorkoutFragment", "OnClick : New Workout");
			break;
		case R.id.btn_schedule:
			Intent i2 = new Intent(c, ScheduleActivity.class);
			startActivity(i2);
			Log.i("ScheduleActivity", "OnClick : Schedule");
			break;
		default:
			Log.i("WorkoutFragment", "OnClick : No ID matched");
		}
	}

	/**
	 * Function to grab workout from list by its specified id
	 * 
	 * @param id
	 *            workout id of workout object desired
	 * @return workout with specified id
	 */
	public static Workout getWorkoutById(int id) {
		Workout tmp;
		for (int i = 0; i < workouts.size(); i++) {
			tmp = workouts.get(i);
			if (tmp.getWid() == id) {
				return tmp;
			}
		}

		return null;
	}

	@Override
	public void requestComplete() {
		Log.i("requestComplete()", "Request Completed countDown()");

	}

	/************** GETTERS AND SETTERS **************/

	public Button getMyWorkouts() {
		return myWorkouts;
	}

	public void setMyWorkouts(Button my_workouts) {
		this.myWorkouts = my_workouts;
	}

	public Button getNewWorkout() {
		return newWorkout;
	}

	public void setNewWorkout(Button new_workout) {
		this.newWorkout = new_workout;
	}

	public Button getSchedule() {
		return schedule;
	}

	public void setSchedule(Button schedule) {
		this.schedule = schedule;
	}
}