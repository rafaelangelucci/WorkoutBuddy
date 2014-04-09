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
* Class to contain information for the History Fragment tab
*
*/
@SuppressLint("ValidFragment")
public class HistoryFragment extends Fragment implements OnClickListener, HttpRequestListener
{
    View view;
    Context c;
	CountDownLatch signal;

    /**
     * Default Constructor
     */
    public HistoryFragment(){}

    /**
     * Constructor
     * @param c context
     */
    public HistoryFragment(Context c)
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
            view = inflater.inflate(R.layout.history_fragment, container, false);
        } catch(InflateException e) {
            //already made
        }

        Button graph = (Button)view.findViewById(R.id.btn_graph);

        graph.setOnClickListener(this);

        return view;
    }

    /**
     * On click listener for buttons
     */
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
        case R.id.btn_graph:
        	Intent intent = new Intent(getActivity(), GraphActivity.class);
	    	startActivity(intent);
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