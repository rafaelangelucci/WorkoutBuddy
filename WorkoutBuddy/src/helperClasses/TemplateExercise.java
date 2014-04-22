package helperClasses;

public class TemplateExercise {
	private int teid;
	private int priority;
	private int eid;
	private int numSets;
	private int reps;
	private Exercise exercise;
	
	/**
	 * Default constructor
	 */
	public TemplateExercise(){
		this.teid = -1;
		this.priority = -1;
		this.eid = -1;
		this.numSets = 0;
		this.reps = 0;
		this.exercise = null;
	}
	
	/** 
	 * Constructor when the user(app) creates template exercise
	 * @param priority of the exercise
	 * @param eid of the exercise this template represents
	 * @param numSets number of sets to complete in the workout
	 * @param reps how many reps should be done per set
	 * @param exercise the exercise associated with this templateexercise
	 */
	public TemplateExercise(int priority, int eid, int numSets, int reps, Exercise exercise){
		this.priority = priority;
		this.eid = eid;
		this.numSets = numSets;
		this.reps = reps;
		this.exercise = exercise;
	}
	
	/**
	 * Constructor for when the database creates the template exercise
	 * @param teid id of the template exercise
	 * @param priority of the exercise
	 * @param eid of the exercise this template represents
	 * @param numSets number of sets to complete in the workout
	 * @param reps how many reps should be done per set
	 * @param exercise the exercise associated with this templateexercise
	 */
	public TemplateExercise(int teid, int priority, int eid, int numSets, int reps, Exercise exercise){
		this(priority, eid, numSets, reps, exercise);
		this.teid = teid;
	}
	
	//*************GETTERS AND SETTERS****************
	public int getTeid() {
		return teid;
	}

	public void setTeid(int teid) {
		this.teid = teid;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public int getNumSets() {
		return numSets;
	}

	public void setNumSets(int numSets) {
		this.numSets = numSets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
}
