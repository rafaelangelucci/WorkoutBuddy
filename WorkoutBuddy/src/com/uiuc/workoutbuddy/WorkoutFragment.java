package com.uiuc.workoutbuddy;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import helperClasses.Exercise;
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
public class WorkoutFragment extends Fragment implements OnClickListener, HttpRequestListener
{
    View view;
    Context c;
	CountDownLatch signal;
	static ArrayList<Workout> workouts = new ArrayList<Workout>();

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

        workouts.clear();
        
        Button my_workouts = (Button)view.findViewById(R.id.btn_my_workouts);
        Button new_workout = (Button)view.findViewById(R.id.btn_new_workout);

        my_workouts.setOnClickListener(this);
        new_workout.setOnClickListener(this);
        
        AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
        signal = new CountDownLatch(1);
        try {
			Workout[] responses = wrapper.getWorkoutList("usernameA");
			if(responses.length == 0)
				Log.i("Asyn Task", "0 workouts");
			signal.await(5, TimeUnit.SECONDS);
			for(int i = 0; i < responses.length; i++)
			{
				workouts.add(new Workout(responses[i].getName(), responses[i].getDate(), responses[i].getDescription(), responses[i].getUsername(),
						responses[i].getExercises()));
				Log.i("Workout Added", responses[i].getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

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
			Intent intent = new Intent(c, WorkoutListActivity.class);
			startActivity(intent);
            break;
        case R.id.btn_new_workout:
            Intent i = new Intent(c, BasicActivity.class);
            startActivity(i);
            Log.i( "WorkoutFragment", "OnClick : New Workout");
            break;
        default:
            Log.i( "WorkoutFragment", "OnClick : No ID matched");
        }

    }

	@Override
	public void requestComplete() 
	{
        Log.i( "requestComplete()", "Request Completed countDown()");
		signal.countDown();
	}
}