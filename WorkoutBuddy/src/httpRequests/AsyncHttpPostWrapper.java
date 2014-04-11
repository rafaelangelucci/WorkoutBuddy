package httpRequests;

import helperClasses.Exercise;
import helperClasses.Workout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	public String makeRequest(HashMap<String, String> data, String URL)
			throws InterruptedException, ExecutionException {
		return new AsyncHttpPost(data).execute(URL).get();
	}

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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkoutList.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		Workout[] workouts = {};
		try {
			JSONArray jArray = new JSONArray(response);
			int arrayLength = jArray.length();
			workouts = new Workout[arrayLength];
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				String name = json_data.getString("name");
				String date = json_data.getString("date");
				String desc = json_data.getString("description");
				int wid = Integer.parseInt(json_data.getString("w_id"));
				Workout workout = new Workout(wid, name, date, desc, username,
						null);
				workouts[i] = workout;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// TODO uncomment this
		return workouts;
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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExerciseList.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		String response = this.makeRequest(postData, URL);

		// get response and parse it into an array
		Exercise[] exercises = {};
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			int arrayLength = jArray.length();
			exercises = new Exercise[arrayLength];
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				String name = json_data.getString("name");
				String type = json_data.getString("type");
				String desc = json_data.getString("description");
				int eid = Integer.parseInt(json_data.getString("e_id"));
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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", exercise.getUsername());
		postData.put("type", exercise.getType());
		postData.put("name", exercise.getName());
		postData.put("description", exercise.getDescription());
		int eid = Integer.parseInt(this.makeRequest(postData, URL).trim());
		exercise.setEid(eid);
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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", workout.getUsername());
		postData.put("date", workout.getDate());
		postData.put("name", workout.getName());
		postData.put("description", workout.getDescription());
		int wid = Integer.parseInt(this.makeRequest(postData, URL).trim());
		workout.setWid(wid);
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
	public void addUser(String username, String password)
			throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("password", password);
		this.makeRequest(postData, URL);
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
	public void deleteUser(String username, String password)
			throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteUser.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("password", password);
		this.makeRequest(postData, URL);
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
	public boolean userLogin(String username, String password)
			throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("password", password);

		String response = this.makeRequest(postData, URL);
		if (response.equals("success"))
			return true;
		else
			return false;
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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("e_id", Integer.toString(eid));
		String response = this.makeRequest(postData, URL);

		// parse JSON
		// take JSON format and put into array
		Exercise exercise = null;
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject json_data = jArray.getJSONObject(0);
			String username = json_data.getString("username");
			String name = json_data.getString("name");
			String type = json_data.getString("type");
			String desc = json_data.getString("description");
			exercise = new Exercise(eid, name, type, desc, username, null);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return exercise;
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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("w_id", Integer.toString(wid));
		String response = this.makeRequest(postData, URL);
		Workout workout = null;

		// parse JSON
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject json_data = jArray.getJSONObject(0);
			String username = json_data.getString("username");
			String name = json_data.getString("name");
			String date = json_data.getString("date");
			String desc = json_data.getString("description");
			workout = new Workout(wid, name, date, desc, username, null);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return workout;
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
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/modifyExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("e_id", Integer.toString(exercise.getEid()));
		postData.put("username", exercise.getUsername());
		postData.put("name", exercise.getName());
		postData.put("type", exercise.getType());
		postData.put("description", exercise.getDescription());

		return Integer.parseInt(this.makeRequest(postData, URL).trim());
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
	public int updateWorkout(Workout workout) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with w_id and all update fields
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/modifyWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("w_id", Integer.toString(workout.getWid()));
		postData.put("username", workout.getUsername());
		postData.put("name", workout.getName());
		postData.put("date", workout.getDate());
		postData.put("description", workout.getDescription());

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
	public int deleteExercise(int eid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("e_id", Integer.toString(eid));

		return Integer.parseInt(this.makeRequest(postData, URL).trim());
	}

	/**
	 * Deletes the workout from the database
	 * 
	 * @param wid
	 *            id associated with the workout
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int deleteWorkout(int wid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("w_id", Integer.toString(wid));

		return Integer.parseInt(this.makeRequest(postData, URL).trim());
	}

	// *****************************ITERATION 5 BELOW*************
	
	/**
	 * Returns list of fully built workout objects representing template workouts
	 * @param username associated with the template workouts
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<Workout> getTemplateWorkoutList(String username)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("operation", GET_LIST);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		ArrayList<Workout> tworkouts = new ArrayList<Workout>();
		try {
			JSONArray jArray = new JSONArray(response);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				//Get info from t-workout
				String name = json_data.getString("name");
				String desc = json_data.getString("description");
				int tid = Integer.parseInt(json_data.getString("t_id").trim());
				//Gets the template exercise list associated with this template workout
				ArrayList<Exercise> texercises = getTemplateExercises(tid);
				Workout workout = new Workout(tid, name, "", desc, username,
						texercises);
				tworkouts.add(workout);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tworkouts;
	}
	public Workout getTemplateWorkout(int tid) throws InterruptedException, ExecutionException{
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("t_id", Integer.toString(tid));
		postData.put("operation", GET);
		String response = this.makeRequest(postData, URL);
		
		// take JSON format and put into array
		Workout workout = null;
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject json_data = jArray.getJSONObject(0);
			//Get info from t-workout
			String name = json_data.getString("name");
			String desc = json_data.getString("description");
			String username = json_data.getString("username");
			//Gets the template exercise list associated with this template workout
			ArrayList<Exercise> texercises = getTemplateExercises(tid);
			workout = new Workout(tid, name, "", desc, username,
						texercises);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return workout;
	}
	
	/**
	 * Adds template workout along with their exercises
	 * @param tworkout workout to add
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int addTemplateWorkout(Workout tworkout) throws InterruptedException, ExecutionException{
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateWorkoutDatabaseOperations.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("operation", ADD);
		postData.put("name", tworkout.getName());
		postData.put("description", tworkout.getDescription());
		postData.put("username", tworkout.getUsername());
		
		//add workout
		int tid = Integer.parseInt(this.makeRequest(postData, URL).trim());
		
		//add exercises
		for(int i = 0; i < tworkout.getExercises().size(); i++){
			addTemplateExercise(tid, tworkout.getExercises().get(i));
		}
		return tid;
	}

	/**
	 * Gets a list of template exercises to build template workout
	 * 
	 * @param tid
	 *            The id corresponding to the template workout
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<Exercise> getTemplateExercises(int tid)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with templateid in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("t_id", Integer.toString(tid));
		postData.put("operation", GET_LIST);
		String response = this.makeRequest(postData, URL);

		// take JSON format and put into array
		ArrayList<Exercise> texercises = new ArrayList<Exercise>();
		try {
			JSONArray jArray = new JSONArray(response);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				// get e_id form template
				int eid = Integer.parseInt(json_data.getString("e_id").trim());
				// query db for exercise corresponding to e_id
				Exercise e = getExercise(eid);
				e.setPriority(Integer.parseInt(json_data.getString("priority").trim()));
				e.setNumSets(Integer.parseInt(json_data.getString("numSets").trim()));
				e.setReps(Integer.parseInt(json_data.getString("reps").trim()));
				texercises.add(e);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return texercises;
	}
	
	/**
	 * Adds template exercise to the database 
	 * @param tid	id that corresponds to template workout
	 * @param exercise exercise to be added
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private void addTemplateExercise(int tid, Exercise exercise) throws InterruptedException, ExecutionException{
		//Make post request with template exercise info
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/TemplateExerciseDatabaseOperations.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("operation", ADD);
		postData.put("t_id", Integer.toString(tid));
		postData.put("priority", Integer.toString(exercise.getPriority()));
		postData.put("e_id", Integer.toString(exercise.getEid()));
		postData.put("numSets", Integer.toString(exercise.getNumSets()));
		postData.put("reps", Integer.toString(exercise.getReps()));
		
		this.makeRequest(postData, URL);
	}

	private class AsyncHttpPost extends AsyncTask<String, String, String> {

		String TAG = "WorkoutBuddy";
		private HashMap<String, String> postData = null;

		// constructor
		public AsyncHttpPost(HashMap<String, String> data) {
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
				Iterator<String> it = postData.keySet().iterator();

				while (it.hasNext()) {
					String key = it.next();
					requestData.add(new BasicNameValuePair(key, postData
							.get(key)));
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
			requestListener.requestComplete();
		}

	}

}
