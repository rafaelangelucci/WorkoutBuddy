package com.uiuc.workoutbuddy;

import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import helperClasses.Exercise;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
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
public class ExerciseFragment extends Fragment implements OnClickListener,
		HttpRequestListener {
	View view;
	Context c;
	public static ArrayList<Exercise> exercises = new ArrayList<Exercise>();
	Button newExercise, myExercises;

	/**
	 * Default Constructor
	 */
	public ExerciseFragment() {
	}

	/**
	 * Constructor
	 * 
	 * @param c
	 *            context
	 */
	public ExerciseFragment(Context c) {
		this.c = c;
	}

	/**
	 * On Creation of this fragment, this method executes to load the layout and
	 * registers all buttons and sets onclick listeners
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.exercise_fragment, container,
					false);
		} catch (InflateException e) {
			Log.i("ExerciseFragment", "InflateException : onCreateView");
		}

		exercises.clear();

		// Set up all button call backs
		newExercise = (Button) view.findViewById(R.id.btn_new_exercise);
		myExercises = (Button) view.findViewById(R.id.btn_my_exercises);

		newExercise.setOnClickListener(this);
		myExercises.setOnClickListener(this);

		// Set up text view from database pull
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		try {
			Exercise[] responses = wrapper
					.getExerciseList(LoginActivity.userName);
			for (int i = 0; i < responses.length; i++) {
				exercises.add(new Exercise(responses[i].getEid(), responses[i]
						.getName(), responses[i].getType(), responses[i]
						.getDescription(), LoginActivity.userName, null));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * On click listener for exercise top row buttons
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_my_exercises:
			// TODO: add refresh functionality
			Log.i("ExerciseFragment", "OnClick : New Exercise");
			Intent i = new Intent(c, ExerciseListActivity.class);
			startActivity(i);
			break;
		case R.id.btn_new_exercise:
			Log.i("ExerciseFragment", "OnClick : Add Exercise");
			startActivity(new Intent(c, NewExerciseActivity.class));
			break;
		default:
			Log.i("ExerciseFragment", "OnClick : No ID matched");
		}

	}

	@Override
	public void requestComplete() {
		Log.i("requestComplete()", "Request Completed countDown()");
	}

	/************** GETTERS AND SETTERS **************/

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exs) {
		exercises = exs;
	}

	public Button getNewExercise() {
		return newExercise;
	}

	public void setNewExercise(Button newExercise) {
		this.newExercise = newExercise;
	}

	public Button getMyExercises() {
		return myExercises;
	}

	public void setMyExercises(Button myExercises) {
		this.myExercises = myExercises;
	}
}
