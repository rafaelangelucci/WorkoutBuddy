package helperClasses;

import httpRequests.AsyncHttpPostWrapper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.util.Log;

public class Workout {
	private String name;
	private String date;
	private String description;
	private String username;
	private int wid;
	private ArrayList<Exercise> exercises;

	/**
	 * Default Constructor
	 */
	public Workout() {
		this.name = "";
		this.date = "";
		this.description = "";
		this.username = "";
		this.exercises = null;
		this.wid = -1;
	}

	/**
	 * Constructor for when a user creates the workout
	 * 
	 * @param name
	 *            identifies the workout
	 * @param date
	 *            of the workout
	 * @param desc
	 *            description of the workout
	 */
	public Workout(String name, String date, String desc, String username,
			ArrayList<Exercise> exercises) {
		this.name = name;
		this.date = date;
		this.description = desc;
		this.username = username;
		this.wid = -1;
		this.exercises = exercises;
	}

	/**
	 * Constructor for when the database creates the workout
	 * 
	 * @param wid
	 *            id of the workout
	 * @param name
	 *            identifies the workout
	 * @param date
	 *            of the workout
	 * @param desc
	 *            description of the workout
	 * @param username
	 *            of the user
	 * @param exercises
	 *            list of exercises in the workout
	 */
	public Workout(int wid, String name, String date, String desc,
			String username, ArrayList<Exercise> exercises) {
		this(name, date, desc, username, exercises);
		this.wid = wid;
	}
	
	/**
	 * Function to convert a workout to string format
	 */
	public String toString()
	{
		String ret = "~~~~ " + this.name.toUpperCase() + " ~~~~" + "\n";
		AsyncHttpPostWrapper postWrapper = new AsyncHttpPostWrapper(null);
		Workout wo = null;
		try {
			wo = postWrapper.getWorkout(this.wid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(wo.getExercises().size() > 0)
		{
			Log.i("Workout", "exercises not null");
			for(int i = 0; i < wo.exercises.size(); i++)
			{
				if(wo.exercises.get(i) != null)
				{	
					Log.i("Workout", "Adding exercise : " + wo.exercises.get(i));
					ret = ret + "\t" + wo.exercises.get(i).toString();
				}
			}
		}
		else {
			Log.i("Workout", "No exercises to be added");
		}
		
		Log.i("Workout", "toString : " + ret);
		return ret;
	}
	
	/*************** GETTERS AND SETTERS ***************/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exercises) {
		this.exercises = exercises;
	}
}
