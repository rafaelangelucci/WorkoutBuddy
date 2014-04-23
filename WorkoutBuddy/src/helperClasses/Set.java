package helperClasses;

public class Set {
	private int sid;
	private int reps;
	private int weight;
	private String time;
	private int priority;
	private int eid;
	private int wid;

	/**
	 * default constructor
	 */
	public Set() {
		sid = -1;
		reps = 0;
		weight = 0;
		time = "";
		priority = 0;
		eid = -1;
		wid = -1;
	}

	/**
	 * constructor for when a user creates a set
	 * 
	 * @param reps
	 *            how many reps in this set
	 * @param weight
	 *            what weight was used in the set
	 * @param time
	 *            how long the set was
	 * @param priority
	 *            the priority of the set (ordering)
	 * @param eid
	 *            the id of the exercise associated with it
	 * @param wid
	 *            the id of the workout associated with it
	 */
	public Set(int reps, int weight, String time, int priority, int eid, int wid) {
		this.reps = reps;
		this.weight = weight;
		this.time = time;
		this.priority = priority;
		this.eid = eid;
		this.wid = wid;
		this.sid = -1;
	}

	/**
	 * constructor for when a user creates a set
	 * 
	 * @param sid
	 *            the id of the set
	 * @param reps
	 *            how many reps in this set
	 * @param weight
	 *            what weight was used in the set
	 * @param time
	 *            how long the set was
	 * @param priority
	 *            the priority of the set (ordering)
	 * @param eid
	 *            the id of the exercise associated with it
	 * @param wid
	 *            the id of the workout associated with it
	 */
	public Set(int sid, int reps, int weight, String time, int priority,
			int eid, int wid) {
		this(reps, weight, time, priority, eid, wid);
		this.sid = sid;
	}

	/*************** GETTERS AND SETTERS ***************/

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

}
