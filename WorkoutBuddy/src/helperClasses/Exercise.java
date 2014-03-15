package helperClasses;

public class Exercise 
{
	private String type;
	private String name;
	private String description;
	//TODO: Decide if we should add a list of sets here
	
	/**
	 * Default Constructor
	 */
	public Exercise()
	{
		this.type = "";
		this.name = "";
		this.description = "";
	}
	
	/**
	 * Constructor
	 * @param type specifies exercise type
	 * @param name title of exercise
	 * @param desc description of exercise
	 */
	public Exercise(String type, String name, String desc)
	{
		this.type = type;
		this.name = name;
		this.description = desc;
	}

	
/*************** GETTERS AND SETTERS ***************/	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}
