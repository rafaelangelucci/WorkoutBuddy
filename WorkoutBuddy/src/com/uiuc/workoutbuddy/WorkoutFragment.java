package com.uiuc.workoutbuddy;

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

		Button new_workout = (Button)view.findViewById(R.id.btn_new_workout);
		
		new_workout.setOnClickListener(this);

		return view;
	}

	/**
	 * On click listener for new workout button
	 */
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_new_workout:
			Intent i = new Intent(c, BasicActivity.class);
			startActivity(i);
			Log.i( "WorkoutFragment", "OnClick : New Workout");
			break;
		default:
			Log.i( "WorkoutFragment", "OnClick : No ID matched");
		}
		
	}
}
