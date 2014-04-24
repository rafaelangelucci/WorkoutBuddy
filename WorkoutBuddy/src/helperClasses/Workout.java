package helperClasses;

import java.util.ArrayList;

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
		String ret = "<b>" + this.name + "</b>" + "\n";
		if(this.exercises != null)
		{
			for(int i = 0; i < this.exercises.size(); i++)
			{
				if(this.exercises.get(i) != null)
				{	
					ret = ret + "\t" + this.exercises.get(i).toString();
				}
			}
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
