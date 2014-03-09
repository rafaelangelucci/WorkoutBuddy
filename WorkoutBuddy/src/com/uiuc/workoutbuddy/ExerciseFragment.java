package com.uiuc.workoutbuddy;


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
import android.widget.Toast;

/**
* Class to contain information for the Workout Fragment tab 
* 
* @author tmadigan7
*
*/
@SuppressLint("ValidFragment")
public class ExerciseFragment extends Fragment implements OnClickListener
{
	View view;
	Context c;

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

		Button new_exercise = (Button)view.findViewById(R.id.btn_new_exercise);
		Button add_exercise = (Button)view.findViewById(R.id.btn_add_exercise);
		Button delete_exercise = (Button)view.findViewById(R.id.btn_delete_exercise);
		
		new_exercise.setOnClickListener(this);
		add_exercise.setOnClickListener(this);
		delete_exercise.setOnClickListener(this);

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
}
