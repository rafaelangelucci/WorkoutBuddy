package com.uiuc.workoutbuddy;

import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import helperClasses.Exercise;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import com.uiuc.workoutbuddy.R;
import android.annotation.SuppressLint;
import android.app.ListFragment;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
* Class to contain information for the Workout Fragment tab 
* 
* @author tmadigan7
*
*/
@SuppressLint("ValidFragment")
public class ExerciseFragment extends Fragment implements OnClickListener, HttpRequestListener
{
	View view;
	Context c;
	CountDownLatch signal;
	static ArrayList<Exercise> exercises = new ArrayList<Exercise>();

	/**
	 * Default Constructor
	 */
	public ExerciseFragment(){}

	/**
	 * Constructor
	 * @param c context
	 */
	public ExerciseFragment(Context c)
	{
		this.c = c;
	}

	/**
	 * On Creation of this fragment, this method executes to load the layout
	 * and registers all buttons and sets onclick listeners
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		try {
			view = inflater.inflate(R.layout.exercise_fragment, container, false);
		} catch(InflateException e) {
			Log.i( "ExerciseFragment", "InflateException : onCreateView");
		}
		
		//listview = (ListView)view.findViewById(R.id.list_view);

		// Set up all button call backs
		Button new_exercise = (Button)view.findViewById(R.id.btn_new_exercise);
		Button my_exercises = (Button)view.findViewById(R.id.btn_my_exercises);
		
		new_exercise.setOnClickListener(this);
		my_exercises.setOnClickListener(this);
				
		// Set up text view from database pull
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
        signal = new CountDownLatch(1);
        try {
			String[][] responses = wrapper.getExerciseList("usernameA");
			signal.await(5, TimeUnit.SECONDS);
			for(int i = 0; i < responses[0].length; i++)
			{
				exercises.add(new Exercise(responses[0][i], responses[1][i], responses[2][i]));
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
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_my_exercises:
			Log.i( "ExerciseFragment", "OnClick : New Exercise");
			Intent i = new Intent(c, ExerciseListActivity.class);
			startActivity(i);
			break;
		case R.id.btn_new_exercise:
			Log.i( "ExerciseFragment", "OnClick : Add Exercise");
			break;
		default:
			Log.i( "ExerciseFragment", "OnClick : No ID matched");
		}
		
	}

	@Override
	public void requestComplete() 
	{
        Log.i( "requestComplete()", "Request Completed countDown()");
		signal.countDown();
	}

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exercises) {
		this.exercises = exercises;
	}
}
