package com.uiuc.workoutbuddy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import helperClasses.Exercise;
import helperClasses.Set;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class GraphActivity extends Activity implements HttpRequestListener
{
	public GraphViewSeries exampleSeries;
	public Exercise exercise;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
        Log.i("GraphActivity", "on create");
        //Get the exercise from the Extra data
        //String exerciseName = getIntent().getStringExtra("exercise");
        String exerciseName = "Squat";
        
        Log.i("GraphActivity", exerciseName);
        
        // Create the GraphViewSeries to graph
        try {
			GraphViewSeries series = createGraphViewSeries(exercise);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		// example series data
		exampleSeries = new GraphViewSeries(new GraphViewData[] {
		      new GraphViewData(1, 150.0d)
		      , new GraphViewData(2, 155.0d)
		      , new GraphViewData(3, 160.0d)
		      , new GraphViewData(4, 160.0d)
		      , new GraphViewData(5, 162.5d)
		      , new GraphViewData(6, 165.0d)
		      , new GraphViewData(7, 167.5d)
		});
		 
		GraphView graphView = new LineGraphView(
		      this // context
		      , exerciseName // heading
		);
		graphView.addSeries(exampleSeries); // data
		
		graphView.setManualYAxisBounds(180.0d, 135.0d);
		// set view port, start=2, size=40
		//graphView.setViewPort(1, 3);
		graphView.setScrollable(true);
		// optional - activate scaling / zooming
		graphView.setScalable(true);
		
		// Background color
		((LineGraphView) graphView).setDrawBackground(true);
        ((LineGraphView) graphView).setBackgroundColor(Color.rgb(00, 30, 90));
        
        // Create the custom static labels for the dates
        ArrayList<String> datesList = getDates();
        String[] dates = new String[datesList.size()];
        dates = datesList.toArray(dates);
        graphView.setHorizontalLabels(dates);
        
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph_main);
		layout.addView(graphView);
	}
	
	/**
	 * Create the GraphViewSeries for the graph by pulling data on the exercise given
	 * 
	 * @return the GraphViewSeries to be graphed
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public GraphViewSeries createGraphViewSeries(Exercise exercise) throws InterruptedException, ExecutionException {
		
		ArrayList<String> values = new ArrayList<String>();
		
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		Exercise[] exercisesAll = wrapper.getExerciseList("usernameA");
		
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>(Arrays.asList(exercisesAll));
		
		int e_id = 0;
		try {
			e_id = getEidFromEName("Squats");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get the exercise from exerciseList and put it into a ArrayList of size 1
		ArrayList<Exercise> ex = new ArrayList<Exercise>();
		for (int i = 0; i < exerciseList.size(); i++) {
			if (exerciseList.get(i).getEid() == e_id)
				ex.add(exerciseList.get(i));
		}

		// Update the set info of the exercise
		wrapper.updateSetsInWorkout(ex);
		 
		// Get the sets and their weights
		ArrayList<Set> sets = ex.get(0).getSets();
		//int weight = sets.get(0).getWeight();
		
		GraphViewData[] data = new GraphViewData[] {};
		
		GraphViewSeries series = new GraphViewSeries(data);
		
		return series;
	}
	
	/**
	 * 
	 * @param eName
	 * @return the id of the exercise name
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int getEidFromEName(String eName) throws InterruptedException, ExecutionException {
		int eid = 0;
		
		// Get all the exercises from the db
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		Exercise[] exercisesAll = wrapper.getExerciseList("usernameA");
		
		for (int i = 0; i < exercisesAll.length; i++) {
			if (exercisesAll[i].getName().equals("Squats"))
				eid = exercisesAll[i].getEid();
		}
		
		return eid;
	}
	
	/**
	 * Searches all the exercises of all workouts for the dates an exercise was performed in a workout
	 * 
	 * @return an ArrayList of dates 
	 */
	public ArrayList<String> getDates(){
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		ArrayList<String> dates = new ArrayList<String>();
		
		// Get all the workouts from the db
		Workout[] workoutsAll = new Workout[1];
		try {
			workoutsAll = wrapper.getWorkoutList("usernameA");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int e_id = 0;
		try {
			e_id = getEidFromEName("Squats");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Filter to get workout dates linked to the exercise
		for (int i = 0; i < workoutsAll.length; i++) {
			ArrayList<Exercise> eList = workoutsAll[i].getExercises();
			for (int j = 0; j < eList.size(); j++) {
				if (eList.get(j).getEid() == e_id) { // Exercise was performed in this workout
					// Add the workout date to dates
					dates.add(workoutsAll[i].getDate());
				}
			}
		}

		return dates;
	}

	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub
		
	}
}
