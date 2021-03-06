package helperClasses;

import java.util.ArrayList;

public class Exercise {
	private int eid;
	private String username;
	private String name;
	private String type;
	private String description;
	private int priority;
	private int reps;
	private int numSets;
	private ArrayList<Set> sets;

	/**
	 * Default Constructor
	 */
	public Exercise() {
		this.type = "";
		this.name = "";
		this.username = "";
		this.description = "";
		this.eid = -1;
		this.priority = 0;
		this.reps = 0;
		this.numSets = 1;
		this.sets = new ArrayList<Set>();
	}

	/**
	 * Constructor for when a user creates the exercise
	 * 
	 * @param username
	 *            username of user who owns exercise
	 * @param name
	 *            name of exercise
	 * @param type
	 *            type of exercise
	 * @param description
	 *            description of exercise
	 * @param sets
	 *            the sets in an exercise
	 */
	public Exercise(String name, String type, String desc, String username,
			ArrayList<Set> sets) {
		this.eid = -1;
		this.name = name;
		this.username = username;
		this.type = type;
		this.description = desc;
		this.sets = sets;
		this.priority = 0;
		this.reps = 0;
		this.numSets = 1;
	}

	/**
	 * Constructor for when the database creates the exercise
	 * 
	 * @param eid
	 *            id of the exercise
	 * @param username
	 *            username of user who owns exercise
	 * @param name
	 *            name of exercise
	 * @param type
	 *            type of exercise
	 * @param description
	 *            description of exercise
	 * @param sets
	 *            the sets in an exercise
	 */
	public Exercise(int eid, String name, String type, String desc,
			String username, ArrayList<Set> sets) {
		this(name, type, desc, username, sets);
		this.eid = eid;

	}
	
	/**
	 * Function to convert an Exercise to string format
	 */
	public String toString()
	{
		String ret = this.name + " (" + this.type + ")\n";
		ret += "\t" + "Description : " + this.description + "\n";
		ret += "\t" + "Num sets: " + this.sets.size() + "\n";
		
		return ret;
	}
	
	/*************** GETTERS AND SETTERS ***************/
	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Set> getSets() {
		return sets;
	}

	public void setSets(ArrayList<Set> sets) {
		this.sets = sets;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public int getNumSets() {
		return numSets;
	}

	public void setNumSets(int numSets) {
		this.numSets = numSets;
	}
	

}
