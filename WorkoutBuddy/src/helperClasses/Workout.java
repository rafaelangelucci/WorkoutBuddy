package helperClasses;

public class Workout 
{
	private String name;
	private String date;
	private String description;
	
	/**
	 * Default Constructor
	 */
	public Workout()
	{
		this.name = "";
		this.date = "";
		this.description = "";
	}
	
	/**
	 * Constructor
	 * @param name identifies the workout
	 * @param date of the workout
	 * @param desc description of the workout
	 */
	public Workout(String name, String date, String desc)
	{
		this.name = name;
		this.date = date;
		this.description = desc;
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
