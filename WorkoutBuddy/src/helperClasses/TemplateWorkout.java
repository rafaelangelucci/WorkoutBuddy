package helperClasses;

import java.util.ArrayList;

public class TemplateWorkout {
	private int tid;
	private String name;
	private String description;
	private String username;
	private ArrayList<TemplateExercise> exercises;

	/**
	 * Default constructor
	 */
	public TemplateWorkout() {
		this.tid = -1;
		this.name = "";
		this.description = "";
		this.username = "";
		this.exercises = null;
	}

	/**
	 * Constructor for when user(app) creates the TemplateWorkout
	 * 
	 * @param name
	 *            of template workout
	 * @param description
	 *            of template workout
	 * @param username
	 *            of the creator of template workout
	 */
	public TemplateWorkout(String name, String description, String username,
			ArrayList<TemplateExercise> exercises) {
		this.tid = -1;
		this.name = name;
		this.description = description;
		this.username = username;
		this.exercises = exercises;
	}

	/**
	 * Constructor for when the database creates the TemplateWorkout
	 * 
	 * @param tid
	 *            of the template workout
	 * @param name
	 *            of template workout
	 * @param description
	 *            of template workout
	 * @param username
	 *            of the creator of template workout
	 */
	public TemplateWorkout(int tid, String name, String description,
			String username, ArrayList<TemplateExercise> exercises) {
		this(name, description, username, exercises);
		this.tid = tid;
	}

	// ************GETTERS AND SETTERS********
	
	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ArrayList<TemplateExercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<TemplateExercise> exercises) {
		this.exercises = exercises;
	}
}
