package helperClasses;

public class Workout 
{
	private String name;
	private String date;
	private String description;
	private String username;
	private int wid;
	private Exercise[] exercises;
	
	/**
	 * Default Constructor
	 */
	public Workout()
	{
		this.name = "";
		this.date = "";
		this.description = "";
		this.username = "";
		this.exercises = null;
		this.wid = -1;
	}
	
	
	/**
	 * Constructor for when a user creates the workout
	 * @param name identifies the workout
	 * @param date of the workout
	 * @param desc description of the workout
	 */
	public Workout(String name, String date, String desc, String username, int wid, Exercise[] exercises)
	{
		this.name = name;
		this.date = date;
		this.description = desc;
		this.username = username;
		this.wid = wid;
		this.exercises = exercises;
	}
	
	/**
	 * Constructor for when the database creates the workout
	 * @param wid id of the workout
	 * @param name identifies the workout
	 * @param date of the workout
	 * @param desc description of the workout
	 * @param username of the user
	 * @param exercises list of exercises in the workout
	 */
	public Workout(int wid, String name, String date, String desc, String username, Exercise[] exercises){
		this.wid = wid;
		this.date = date;
		this.description = desc;
		this.username = username;
		this.exercises = exercises;
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
}
