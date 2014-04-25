package httpRequests;

import helperClasses.Exercise;
import helperClasses.Set;
import helperClasses.TemplateExercise;
import helperClasses.TemplateWorkout;
import helperClasses.Workout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Wrapper class to make calls to AsyncHttpPost
 * 
 * @author RafMac
 * 
 */
public class AsyncHttpPostWrapper {
	private static final String GET = "get";
	private static final String ADD = "add";
	private static final String GET_LIST = "getlist";
	private static final String DELETE = "delete";
	private static final String UPDATE = "update";
	private HttpRequestListener requestListener;

	/**
	 * Constructor for AsyncPostWrapper
	 * 
	 * @param requestListener
	 *            reference to our interface class
	 */
	public AsyncHttpPostWrapper(HttpRequestListener requestListener) {
		this.requestListener = requestListener;
	}

	/**
	 * Makes a post request
	 * 
	 * @param data
	 *            data sent in the post request
	 * @param URL
	 *            location for post request to be sent
	 * @return the string received from the HTTP response
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String makeRequest(HashMap<String, Object> data, String URL)
			throws InterruptedException, ExecutionException {
		return new AsyncHttpPost(data).execute(URL).get();
	}

	// *************Workouts**************************

	/**
	 * 
	 * @param username
	 *            name associated with the account
	 * @return array data array[0]=names, array[1]=dates, array[2]=descriptions
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Workout[] getWorkoutList(String username)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/WorkoutDatabaseOperations.php";
		String[] keys = { "operation", "username" };
		Object[] values = { GET_LIST, username };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		Workout[] workouts = {};
		try {
			JSONArray jArray = new JSONArray(response);
			int arrayLength = jArray.length();
			workouts = new Workout[arrayLength];
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonData = jArray.getJSONObject(i);
				String name = jsonData.getString("name");
				String date = jsonData.getString("date");
				String desc = jsonData.getString("description");
				int wid = Integer.parseInt(jsonData.getString("w_id"));
				Workout workout = new Workout(wid, name, date, desc, username,
						new ArrayList<Exercise>());
				workouts[i] = workout;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return workouts;
	}

	/**
	 * Query the database and return the workout
	 * 
	 * @param wid
	 *            id associated with the workout
	 * @return returns all of the information about the workout in array form
	 *         workout[0] = username, w[1] = name, w[2] = date, w[3] =
	 *         description
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public Workout getWorkout(int wid) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with e_id
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/WorkoutDatabaseOperations.php";
		String[] keys = { "operation", "w_id" };
		Object[] values = { GET, wid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);
		Workout workout = null;

		// parse JSON
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject jsonData = jArray.getJSONObject(0);
			String username = jsonData.getString("username");
			String name = jsonData.getString("name");
			String date = jsonData.getString("date");
			String desc = jsonData.getString("description");
			ArrayList<Exercise> exercises = getExercisesAndSets(wid);
			Log.i("getExercises list size", Integer.toString(exercises.size()));
			workout = new Workout(wid, name, date, desc, username, exercises);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return workout;
	}

	/**
	 * Takes in a workout object, adds it to the database, then sets its wid
	 * 
	 * @param workout
	 *            the workout to be added
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addWorkout(Workout workout) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/WorkoutDatabaseOperations.php";
		String[] keys = { "operation", "username", "date", "name",
				"description" };
		Object[] values = { ADD, workout.getUsername(), workout.getDate(),
				workout.getName(), workout.getDescription() };
		HashMap<String, Object> postData = createPostData(keys, values);
		int wid = toInt(this.makeRequest(postData, URL));
		workout.setWid(wid);
		if (workout.getExercises() != null) {
			addExercisesInWorkout(wid, workout.getExercises());
		}
	}

	/**
	 * Helper function for adding new sets in a workout
	 * 
	 * @param wid
	 *            id of workout to add set
	 * @param exercises
	 *            list of exercises to add sets from
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void addExercisesInWorkout(int wid, ArrayList<Exercise> exercises)
			throws InterruptedException, ExecutionException {
		// loop through all exercises and add sets
		for (int i = 0; i < exercises.size(); i++) {
			ArrayList<Set> sets = exercises.get(i).getSets();
			if (sets == null) {
				// If no Set ArrayList exists make one and add the set to it
				Set set = new Set(0, 0, "", i, exercises.get(i).getEid(), wid);
				addSet(set);
				exercises.get(i).setSets(new ArrayList<Set>());
				exercises.get(i).getSets().add(set);
			} else if (sets.size() == 0) {
				// If set list is empty, create a set and add to it
				Set set = new Set(0, 0, "", i, exercises.get(i).getEid(), wid);
				addSet(set);
				sets.add(set);
			} else {
				// loop through sets and add them
				for (int j = 0; j < sets.size(); j++) {
					addSet(sets.get(j));
				}
			}
		}
	}

	/**
	 * Update the workout with the given information. All fields need to be
	 * provided even if there is no change
	 * 
	 * @param workout
	 *            the workout with updated information (must contain wid)
	 * @return number of rows affected
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean updateWorkout(Workout workout) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with w_id and all update fields
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/WorkoutDatabaseOperations.php";
		String[] keys = { "operation", "w_id", "username", "name", "date",
				"description" };
		Object[] values = { UPDATE, workout.getWid(), workout.getUsername(),
				workout.getName(), workout.getDate(), workout.getDescription() };
		HashMap<String, Object> postData = createPostData(keys, values);
		// update sets
		if (workout.getExercises() != null) {
			updateSetsInWorkout(workout.getExercises());
		}

		int response = toInt(this.makeRequest(postData, URL));
		if (response == 1)
			return true;
		return false;
	}

	/**
	 * Helper function that walks through exercise Arraylist and updates or adds
	 * sets
	 * 
	 * @param wid
	 *            id of the workout
	 * @param exercises
	 *            list of exercises
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateSetsInWorkout(ArrayList<Exercise> exercises)
			throws InterruptedException, ExecutionException {
		// Loop through exercises to update sets
		for (int i = 0; i < exercises.size(); i++) {
			ArrayList<Set> sets = exercises.get(i).getSets();
			if (sets != null) {
				// loop through sets and update them
				for (int j = 0; j < sets.size(); j++) {
					if (sets.get(j).getSid() == -1) {
						addSet(sets.get(j));
					} else {
						updateSet(sets.get(j));
					}
				}
			}
		}
	}

	/**
	 * Deletes the workout from the database
	 * 
	 * @param wid
	 *            id associated with the workout
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean deleteWorkout(int wid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/WorkoutDatabaseOperations.php";
		String[] keys = { "operation", "w_id" };
		Object[] values = { DELETE, wid };
		HashMap<String, Object> postData = createPostData(keys, values);
		// Loop through exercises and delete sets
		deleteSetsFromWorkout(wid);

		String response = this.makeRequest(postData, URL);
		if (toInt(response) == 1)
			return true;
		return false;
	}

	/**
	 * Deletes all the sets in a workout
	 * 
	 * @param wid
	 *            id of the workout
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void deleteSetsFromWorkout(int wid) throws InterruptedException,
			ExecutionException {
		// Walks through sets and delets
		ArrayList<Set> sets = getSetList(wid);
		for (int i = 0; i < sets.size(); i++) {
			deleteSet(sets.get(i).getSid());
		}
	}

	// *************Exercises*************************
	/**
	 * Returns an exercise list that contains all exercises and sets in a given
	 * workout
	 * 
	 * @param wid
	 *            id of the workout we want to query
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private ArrayList<Exercise> getExercisesAndSets(int wid)
			throws InterruptedException, ExecutionException {
		ArrayList<Set> sets = getSetList(wid);
		Log.i("GetSetList size", Integer.toString(sets.size()));
		ArrayList<Integer> eids = new ArrayList<Integer>();
		for (int i = 0; i < sets.size(); i++) {
			if (!eids.contains(sets.get(i).getEid())) {
				eids.add(sets.get(i).getEid());
			}
		}
		Log.i("Eids list size", Integer.toString(eids.size()));
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		for (int i = 0; i < eids.size(); i++) {
			Exercise ex = getExercise(eids.get(i));
			Log.i("Exercise ID", ex.getName());
			ArrayList<Set> exSets = new ArrayList<Set>();
			for (int j = 0; j < sets.size(); j++) {
				if (sets.get(j).getEid() == eids.get(i)) {
					exSets.add(sets.get(j));
					sets.remove(j);
				}
			}
			ex.setSets(exSets);
			exercises.add(ex);
		}

		return exercises;
	}

	/**
	 * 
	 * @param username
	 *            name associated with the account
	 * @return array data array[0]=names, array[1]=types, array[2]=descriptions
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Exercise[] getExerciseList(String username)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php";
		String[] keys = { "operation", "username" };
		Object[] values = { GET_LIST, username };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// get response and parse it into an array
		Exercise[] exercises = {};
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			int arrayLength = jArray.length();
			exercises = new Exercise[arrayLength];
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonData = jArray.getJSONObject(i);
				String name = jsonData.getString("name");
				String type = jsonData.getString("type");
				String desc = jsonData.getString("description");
				int eid = Integer.parseInt(jsonData.getString("e_id"));
				Exercise exercise = new Exercise(eid, name, type, desc,
						username, null);
				exercises[i] = exercise;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return exercises;
	}

	/**
	 * Takes in an exercise object, adds it to the database, then sets its eid
	 * 
	 * @param exercise
	 *            the exercise to be added
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addExercise(Exercise exercise) throws InterruptedException,
			ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php";
		String[] keys = { "operation", "username", "type", "name",
				"description" };
		Object[] values = { ADD, exercise.getUsername(), exercise.getType(),
				exercise.getName(), exercise.getDescription() };
		HashMap<String, Object> postData = createPostData(keys, values);
		int eid = Integer.parseInt(this.makeRequest(postData, URL).trim());
		exercise.setEid(eid);
	}

	/**
	 * Takes in all parameters and creates the account in the database
	 * 
	 * @param username
	 *            username to name the account
	 * @param password
	 *            password for the account
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String addUser(String username, String password)
			throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php";
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("username", username);
		postData.put("password", password);
		return this.makeRequest(postData, URL);
	}

	/**
	 * Takes in all parameters and deletes the account in the database
	 * 
	 * @param username
	 *            username to delete
	 * @param password
	 *            password for the account
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String deleteUser(String username, String password)
			throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php";
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("username", username);
		postData.put("password", password);
		return this.makeRequest(postData, URL);
	}

	/**
	 * Takes in all parameters and checks for the user in the db to login
	 * 
	 * @param username
	 *            username to login
	 * @param password
	 *            password for the user
	 * @return true if user exists and the password is correct
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String userLogin(String username, String password)
			throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php";
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("username", username);
		postData.put("password", password);

		return this.makeRequest(postData, URL);
	}

	/**
	 * Query the database and return the exercise
	 * 
	 * @param eid
	 *            the id of the exercise
	 * @return returns all of the information about the exercise in array form
	 *         exercise[0] = username, e[1] = name, e[2] = type, e[3] =
	 *         description
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Exercise getExercise(int eid) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with e_id
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php";
		String[] keys = { "operation", "e_id" };
		Object[] values = { GET, eid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// parse JSON
		// take JSON format and put into array
		Exercise exercise = null;
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject jsonData = jArray.getJSONObject(0);
			String username = jsonData.getString("username");
			String name = jsonData.getString("name");
			String type = jsonData.getString("type");
			String desc = jsonData.getString("description");
			exercise = new Exercise(eid, name, type, desc, username, null);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return exercise;
	}

	/**
	 * Updates the exercise with the given information. All fields need to be
	 * provided even if there is no change
	 * 
	 * @param exercise
	 *            the exercise to be changed (must contain eid field)
	 * @return the number of rows affected
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int updateExercise(Exercise exercise) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with e_id and all update fields
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php";
		String[] keys = { "operation", "e_id", "username", "name", "type",
				"description" };
		Object[] values = { UPDATE, exercise.getEid(), exercise.getUsername(),
				exercise.getName(), exercise.getType(),
				exercise.getDescription() };
		HashMap<String, Object> postData = createPostData(keys, values);

		return Integer.parseInt(this.makeRequest(postData, URL).trim());
	}

	/**
	 * Deletes exercise from the database
	 * 
	 * @param eid
	 *            id associated with the exercise
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean deleteExercise(int eid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/ExerciseDatabaseOperations.php";
		String[] keys = { "operation", "e_id" };
		Object[] values = { DELETE, eid };
		HashMap<String, Object> postData = createPostData(keys, values);

		String response = this.makeRequest(postData, URL);
		Log.i("Exercise delete response", response);
		if (toInt(response) == 1) {
			return true;
		}
		return false;
	}

	// *************Sets******************************
	/**
	 * Gets a list of sets in a workout
	 * 
	 * @param wid
	 *            id of the workout
	 * @return list of sets
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<Set> getSetList(int wid) throws InterruptedException,
			ExecutionException {
		// Make post request with template exercise info
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php";
		String[] keys = { "operation", "w_id" };
		Object[] values = { GET_LIST, wid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		ArrayList<Set> sets = new ArrayList<Set>();
		try {
			JSONArray jArray = new JSONArray(response);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonData = jArray.getJSONObject(i);
				int sid = Integer.parseInt(jsonData.getString("s_id").trim());
				int reps = Integer.parseInt(jsonData.getString("reps").trim());
				int weight = Integer.parseInt(jsonData.getString("weight")
						.trim());
				int eid = Integer.parseInt(jsonData.getString("e_id").trim());
				String time = jsonData.getString("time");
				int priority = Integer.parseInt(jsonData.getString("priority")
						.trim());
				Set set = new Set(sid, reps, weight, time, priority, eid, wid);
				sets.add(set);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sets;
	}

	/**
	 * Gets a set based on its id
	 * 
	 * @param sid
	 *            id of the set
	 * @return Set
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Set getSet(int sid) throws InterruptedException, ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php";
		String[] keys = { "operation", "s_id" };
		Object[] values = { GET, sid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// take JSON format and parse
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject jsonData = jArray.getJSONObject(0);
			int reps = toInt(jsonData.getString("reps"));
			int weight = toInt(jsonData.getString("weight"));
			String time = jsonData.getString("time");
			int priority = toInt(jsonData.getString("priority"));
			int eid = toInt(jsonData.getString("e_id"));
			int wid = toInt(jsonData.getString("w_id"));
			return new Set(sid, reps, weight, time, priority, eid, wid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds the given set to the database
	 * 
	 * @param set
	 *            set object to be added
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addSet(Set set) throws InterruptedException, ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php";
		String[] keys = { "operation", "reps", "weight", "time", "priority",
				"e_id", "w_id" };
		Object[] values = { ADD, set.getReps(), set.getWeight(), set.getTime(),
				set.getPriority(), set.getEid(), set.getWid() };
		HashMap<String, Object> postData = createPostData(keys, values);

		String response = this.makeRequest(postData, URL);
		set.setSid(toInt(response));
	}

	/**
	 * Deletes a set from the database given an id
	 * 
	 * @param sid
	 *            id of the set to delete
	 * @return true if the set is successfully deleted, false otherwise
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public boolean deleteSet(int sid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php";
		String[] keys = { "operation", "s_id" };
		Object[] values = { DELETE, sid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);
		if (toInt(response) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Updates a set with the given information
	 * 
	 * @param set
	 * @return true if set is successfully updated, false otherwise
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean updateSet(Set set) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/setDatabaseOperations.php";
		String[] keys = { "operation", "s_id", "reps", "weight", "time",
				"priority", "e_id", "w_id" };
		Object[] values = { UPDATE, set.getSid(), set.getReps(),
				set.getWeight(), set.getTime(), set.getPriority(),
				set.getEid(), set.getWid() };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);
		if (toInt(response) == 1) {
			return true;
		}
		return false;

	}

	// *************TemplateWorkouts******************
	/**
	 * Returns list of fully built workout objects representing template
	 * workouts
	 * 
	 * @param username
	 *            associated with the template workouts
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<TemplateWorkout> getTemplateWorkoutList(String username)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		String[] keys = { "operation", "username" };
		Object[] values = { GET_LIST, username };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		ArrayList<TemplateWorkout> tworkouts = new ArrayList<TemplateWorkout>();
		try {
			JSONArray jArray = new JSONArray(response);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonData = jArray.getJSONObject(i);
				// Get info from t-workout
				String name = jsonData.getString("name");
				String desc = jsonData.getString("description");
				int tid = Integer.parseInt(jsonData.getString("t_id").trim());
				// Gets the template exercise list associated with this template
				// workout
				ArrayList<TemplateExercise> texercises = getTemplateExerciseList(tid);
				TemplateWorkout tWorkout = new TemplateWorkout(tid, name, desc,
						username, texercises);
				tworkouts.add(tWorkout);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tworkouts;
	}

	/**
	 * Gets templateWorkout given by id
	 * 
	 * @param tid
	 *            id of templateWorkout
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public TemplateWorkout getTemplateWorkout(int tid)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		String[] keys = { "t_id", "operation" };
		Object[] values = { tid, GET };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		TemplateWorkout tWorkout = null;
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject jsonData = jArray.getJSONObject(0);
			// Get info from t-workout
			String name = jsonData.getString("name");
			String desc = jsonData.getString("description");
			String username = jsonData.getString("username");
			// Gets the template exercise list associated with this template
			// workout
			ArrayList<TemplateExercise> texercises = getTemplateExerciseList(tid);
			tWorkout = new TemplateWorkout(tid, name, desc, username,
					texercises);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tWorkout;
	}

	/**
	 * Adds template workout along with their exercises
	 * 
	 * @param tworkout
	 *            workout to add
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int addTemplateWorkout(TemplateWorkout tworkout)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		String[] keys = { "operation", "name", "description", "username" };
		Object[] values = { ADD, tworkout.getName(), tworkout.getDescription(),
				tworkout.getUsername() };
		HashMap<String, Object> postData = createPostData(keys, values);

		// add workout
		int tid = toInt(this.makeRequest(postData, URL));

		// add exercises
		for (int i = 0; i < tworkout.getExercises().size(); i++) {
			addTemplateExercise(tid, tworkout.getExercises().get(i));
		}
		tworkout.setTid(tid);
		return tid;
	}

	/**
	 * Deletes TemplateWorkout and its tempalteExercises
	 * 
	 * @param tid
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean deleteTemplateWorkout(int tid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		String[] keys = { "operation", "t_id" };
		Object[] values = { DELETE, tid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		ArrayList<TemplateExercise> tExercises = getTemplateExerciseList(tid);
		for (int i = 0; i < tExercises.size(); i++) {
			deleteTemplateExercise(tExercises.get(i).getTeid());
		}

		if (toInt(response) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Updates TemplateWorkout and TemplateExercises contained by it
	 * 
	 * @param tWorkout
	 *            templateWorkout to delete
	 * @return boolean whether the database operation failed of not
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean updateTemplateWorkout(TemplateWorkout tWorkout)
			throws InterruptedException, ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		String[] keys = { "operation", "name", "description" };
		Object[] values = { UPDATE, tWorkout.getName(),
				tWorkout.getDescription() };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);
		boolean success = true;
		if (toInt(response) != 1) {
			return false;
		}
		// Loop through and update all templateExercises
		ArrayList<TemplateExercise> tExercises = tWorkout.getExercises();
		for (int i = 0; i < tExercises.size(); i++) {
			if (tExercises.get(i).getTeid() == -1) {
				// add
				addTemplateExercise(tWorkout.getTid(), tExercises.get(i));
			} else {
				success = success && updateTemplateExercise(tExercises.get(i));
			}
		}
		return success;
	}

	// *************TemplateExercises*****************
	/**
	 * Gets a list of template exercises to build template workout
	 * 
	 * @param tid
	 *            The id corresponding to the template workout
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<TemplateExercise> getTemplateExerciseList(int tid)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with templateid in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		String[] keys = { "operation", "t_id" };
		Object[] values = { GET_LIST, tid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		ArrayList<TemplateExercise> texercises = new ArrayList<TemplateExercise>();
		try {
			JSONArray jArray = new JSONArray(response);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonData = jArray.getJSONObject(i);
				// get e_id from template
				int eid = Integer.parseInt(jsonData.getString("e_id").trim());
				// query db for exercise corresponding to e_id
				Exercise e = getExercise(eid);
				int teid = Integer.parseInt(jsonData.getString("te_id").trim());
				int priority = Integer.parseInt(jsonData.getString("priority")
						.trim());
				int numSets = Integer.parseInt(jsonData.getString("numSets")
						.trim());
				int reps = Integer.parseInt(jsonData.getString("reps").trim());
				TemplateExercise te = new TemplateExercise(teid, tid, priority,
						eid, numSets, reps, e);
				texercises.add(te);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return texercises;
	}

	/**
	 * Adds template exercise to the database
	 * 
	 * @param tid
	 *            id that corresponds to template workout
	 * @param exercise
	 *            exercise to be added
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void addTemplateExercise(int tid, TemplateExercise exercise)
			throws InterruptedException, ExecutionException {
		// Make post request with template exercise info
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		String[] keys = { "operation", "t_id", "priority", "e_id", "numSets",
				"reps" };
		Object[] values = { ADD, tid, exercise.getPriority(),
				exercise.getEid(), exercise.getNumSets(), exercise.getReps() };
		HashMap<String, Object> postData = createPostData(keys, values);

		exercise.setTeid(toInt(this.makeRequest(postData, URL)));
	}

	/**
	 * Returns single template exercise
	 * 
	 * @param teid
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public TemplateExercise getTemplateExercise(int teid)
			throws InterruptedException, ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("te_id", teid);
		postData.put("operation", GET);
		String response = this.makeRequest(postData, URL);
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject jsonData = jArray.getJSONObject(0);
			int tid = toInt(jsonData.getString("t_id"));
			int priority = toInt(jsonData.getString("priority"));
			int eid = toInt(jsonData.getString("e_id"));
			int numSets = toInt(jsonData.getString("numSets"));
			int reps = toInt(jsonData.getString("reps"));

			// query db for exercise corresponding to e_id
			Exercise e = getExercise(eid);

			return new TemplateExercise(teid, tid, priority, eid, numSets,
					reps, e);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new TemplateExercise();
	}

	/**
	 * Deletes template exercise
	 * 
	 * @param teid
	 *            id of the template exercise
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private boolean deleteTemplateExercise(int teid)
			throws InterruptedException, ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		String[] keys = { "operation", "te_id" };
		Object[] values = { DELETE, teid };
		HashMap<String, Object> postData = createPostData(keys, values);
		String response = this.makeRequest(postData, URL);
		if (toInt(response) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Updates the given template exercise
	 * 
	 * @param tExercise
	 *            template exercise to be updated
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private boolean updateTemplateExercise(TemplateExercise tExercise)
			throws InterruptedException, ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		String[] keys = { "operation", "te_id", "t_id", "priority", "e_id",
				"numSets", "reps" };
		Object[] values = { UPDATE, tExercise.getTeid(), tExercise.getTid(),
				tExercise.getPriority(), tExercise.getEid(),
				tExercise.getNumSets(), tExercise.getReps() };
		HashMap<String, Object> postData = createPostData(keys, values);

		String response = this.makeRequest(postData, URL);
		if (toInt(response) == 1) {
			return true;
		}
		return false;

	}

	// ***************HELPER FUNCTIONS***************
	private static int toInt(String s) {
		return Integer.parseInt(s.trim());
	}

	/**
	 * Loops through arrays of keys and values and creates postData hashmap
	 * 
	 * @param keys
	 *            Array of keys
	 * @param values
	 *            Array of values
	 * @return postData item created
	 */
	private static HashMap<String, Object> createPostData(String[] keys,
			Object[] values) {
		HashMap<String, Object> postData = new HashMap<String, Object>();
		for (int i = 0; i < keys.length; i++) {
			postData.put(keys[i], values[i]);
		}

		return postData;
	}

	private class AsyncHttpPost extends AsyncTask<String, String, String> {

		String TAG = "WorkoutBuddy";
		private HashMap<String, Object> postData = null;

		// constructor
		public AsyncHttpPost(HashMap<String, Object> data) {
			postData = data;
		}

		// structure taken from
		// http://veerasundar.com/blog/2011/09/making-get-and-post-requests-in-android-application/
		// background activity
		@Override
		protected String doInBackground(String... params) {

			StringBuilder response = new StringBuilder();

			try {
				// Creates post request and adds url
				HttpPost post = new HttpPost(params[0]);

				// iterates through post data and creates a request entity
				ArrayList<NameValuePair> requestData = new ArrayList<NameValuePair>();
				for (HashMap.Entry<String, Object> e : postData.entrySet()) {
					requestData.add(new BasicNameValuePair(e.getKey(), e
							.getValue().toString()));
				}
				post.setEntity(new UrlEncodedFormEntity(requestData));

				// creates client to serve response
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(post);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					Log.d(TAG, "HTTP post succeeded");

					// parses the http response
					HttpEntity messageEntity = httpResponse.getEntity();
					InputStream is = messageEntity.getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}

				} else {
					Log.e(TAG, httpResponse.getStatusLine().getStatusCode()
							+ "HTTP post status code not 200");
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}

			Log.d(TAG, "Done with HTTP posting");
			return response.toString();
		}

		protected void onPostExecute(String result) {
			if (requestListener != null) {
				requestListener.requestComplete();
			}
		}
	}
}
