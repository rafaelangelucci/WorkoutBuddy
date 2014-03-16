package helperClasses;

public class Exercise {
	private String name;
	private String type;
	private String description;
	private Set[] sets;

	/**
	 * Default Constructor
	 */
	public Exercise() {
		this.type = "";
		this.name = "";
		this.description = "";
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            specifies exercise type
	 * @param name
	 *            title of exercise
	 * @param desc
	 *            description of exercise
	 */
	public Exercise(String name, String type, String desc) {
		this.name = name;
		this.type = type;
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
