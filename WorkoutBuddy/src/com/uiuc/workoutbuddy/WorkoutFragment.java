package com.uiuc.workoutbuddy;

import com.example.workoutbuddy.R;

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
import android.widget.Toast;

/**
* Class to contain information for the Workout Fragment tab 
* 
* @author tmadigan7
*
*/
@SuppressLint("ValidFragment")
public class WorkoutFragment extends Fragment implements OnClickListener
{
	View view;
	Context c;

	/**
	 * Default Constructor
	 */
	public WorkoutFragment(){}

	/**
	 * Constructor
	 * @param c context
	 */
	public WorkoutFragment(Context c)
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
			view = inflater.inflate(R.layout.workout_fragment, container, false);
		} catch(InflateException e) {
			//already made
		}

		Button my_workouts = (Button)view.findViewById(R.id.btn_my_workouts);
		Button new_workout = (Button)view.findViewById(R.id.btn_new_workout);
		
		my_workouts.setOnClickListener(this);
		new_workout.setOnClickListener(this);

		return view;
	}

	/**
	 * On click listener for my workouts and new workout buttons
	 */
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_my_workouts:
			Log.i( "WorkoutFragment", "OnClick : My Workouts");
			break;
		case R.id.btn_new_workout:
			Log.i( "WorkoutFragment", "OnClick : New Workout");
			break;
		default:
			Log.i( "WorkoutFragment", "OnClick : No ID matched");
		}
		
	}

/*
	@Override
	public void onClick(View v) 
	{
		new ClearDBTask().execute((Object)null);
	}

	private class ClearDBTask extends AsyncTask {

		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return ServerInterface.resetDB();
		}

		protected void onPostExecute(Object result) {
			// now update the textView here
			//TextView tv = (TextView) mView.findViewById(yourTextViewId);
			// now you have reference to tv, probably update the text by
			//tv.setText(yourString);
			Toast.makeText(c, "Database Reset", Toast.LENGTH_SHORT).show();
			DrinkFragment.reset();
		}
	}
*/
}
