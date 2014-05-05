package com.uiuc.workoutbuddy;
import java.util.ArrayList;
import java.util.Arrays;
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
import android.widget.LinearLayout;

/**
 * Activity class for holding the user's progress graph
 *
 */
public class GraphActivity extends Activity implements HttpRequestListener
{
	public GraphViewSeries exampleSeries;
	public Exercise exercise;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
        //Get the exercise from the Extra data
        String exerciseName = getIntent().getStringExtra("exercise");
        //String exerciseName = "Squat";
        
        Log.i("GraphActivity", exerciseName);
        
        // Create the GraphViewSeries to graph
        try {
			GraphViewSeries series = createGraphViewSeries(exercise);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
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
		
		// Create the ArrayList to hold all values for the exercise
		ArrayList<String> values = new ArrayList<String>();
		
		// Get all exercises
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		Exercise[] exercisesAll = wrapper.getExerciseList("usernameA");
		
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>(Arrays.asList(exercisesAll));
		
		int e_id = 0;
		try {
			//e_id = getEidFromEName(exercise.getName());
			e_id = getEidFromEName("Squats");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		//Get the exercise from exerciseList and put it into a ArrayList of size 1
		ArrayList<Exercise> ex = getSoloArrayList(exerciseList, e_id);
		
		
		
		// TODO test that ex is not empty

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
	 * Returns an ArrayList of size 1 with the exercise in the given exercise list that matches
	 * the exercise id given
	 * 
	 * @param list The exercise list to search in
	 * @param e_id The id to look for
	 * 
	 * @return An ArrayList with the matched exercise
	 */
	public ArrayList<Exercise> getSoloArrayList(ArrayList<Exercise> exerciseList, int e_id) {
		ArrayList<Exercise> ex = new ArrayList<Exercise>();
		
		for (int i = 0; i < exerciseList.size(); i++) {
			if (exerciseList.get(i).getEid() == e_id)
				ex.add(exerciseList.get(i));
		}
		
		return ex;
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
	 * Gets the dates that an exercise was performed on by searching through all exercises in all workouts
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
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//TODO Fill workouts with data
		//create exercise list of size 1
		//updateSetsInWorkout(exercise list)
		
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
