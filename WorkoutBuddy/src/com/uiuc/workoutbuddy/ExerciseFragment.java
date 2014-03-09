package com.uiuc.workoutbuddy;


import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.uiuc.workoutbuddy.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
	TextView tv;
	CountDownLatch signal;

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
			//already made
		}

		// Set up all button call backs
		Button new_exercise = (Button)view.findViewById(R.id.btn_new_exercise);
		Button add_exercise = (Button)view.findViewById(R.id.btn_add_exercise);
		Button delete_exercise = (Button)view.findViewById(R.id.btn_delete_exercise);
		
		new_exercise.setOnClickListener(this);
		add_exercise.setOnClickListener(this);
		delete_exercise.setOnClickListener(this);
		
		String dbResponse = "";
		
		// Set up text view from database pull
		tv = (TextView)view.findViewById(R.id.exercise_tv);
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
        signal = new CountDownLatch(1);
        try {
			String[][] responses = wrapper.getExerciseList("usernameA");
			signal.await(5, TimeUnit.SECONDS);
			for(int i = 0; i < responses.length; i++)
				for(int j = 0; j < responses[i].length; j++)
				{
					Log.i( "ExerciseFragment", "OnClick DB response : " + responses[i][j]);
					if(responses[i][j] != null)
						dbResponse += responses[i][j] + "\n";
				}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
        tv.setText(dbResponse);

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
		case R.id.btn_new_exercise:
			Log.i( "ExerciseFragment", "OnClick : New Exercise");
			break;
		case R.id.btn_add_exercise:
			Log.i( "ExerciseFragment", "OnClick : Add Exercise");
			break;
		case R.id.btn_delete_exercise:
			Log.i( "ExerciseFragment", "OnClick : Delete Exercise");
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
}
