package com.uiuc.workoutbuddy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
            AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
            signal = new CountDownLatch(1);
            try {
				String[][] responses = wrapper.getWorkoutList("usernameA");
				signal.await(5, TimeUnit.SECONDS);
				for(int i = 0; i < responses.length; i++)
					for(int j = 0; j < responses[i].length; j++)
						Log.i( "WorkoutFragment", "OnClick DB response : " + responses[i][j]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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