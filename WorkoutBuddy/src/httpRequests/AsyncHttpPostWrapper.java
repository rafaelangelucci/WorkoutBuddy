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
				Workout workout = new Workout(wid, name, date, desc, username, null);
				workouts[i] = workout;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//TODO uncomment this
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
	public String[][] getExerciseList(String username)
			throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExerciseList.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		String response = this.makeRequest(postData, URL);

		// get response and parse it into an array
		String[] names = {};
		String[] types = {};
		String[] descriptions = {};
		
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			int arrayLength = jArray.length();
			names = new String[arrayLength];
			types = new String[arrayLength];
			descriptions = new String[arrayLength];
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				//TODO take out code below to stars
				names[i] = json_data.getString("name");
				types[i] = json_data.getString("type");
				descriptions[i] = json_data.getString("description");
				//*********************
				String name = json_data.getString("name");
				String type = json_data.getString("type");
				String desc = json_data.getString("description");
				int eid = Integer.parseInt(json_data.getString("e_id"));
				Exercise exercise = new Exercise(eid, name, type, desc, username, null);
				exercises.add(exercise);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String[][] returnArray = { names, types, descriptions };
		return returnArray;
		//TODO
		//return exercises;
	}

	/**
	 * Takes in all parameters and creates the exercise in the database
	 * 
	 * @param username
	 *            username associated with the exercise
	 * @param type
	 *            the type definition of the exercise
	 * @param name
	 *            name of the exercise
	 * @param desc
	 *            the description of how to do the exercise
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addExercise(String username, String type, String name,
			String desc) throws InterruptedException, ExecutionException {
		// Make the post request to URL with username in postdata
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("type", type);
		postData.put("name", name);
		postData.put("description", desc);
		this.makeRequest(postData, URL);
	}

	/**
	 * Takes in all parameters and creates the workout in the database
	 * 
	 * @param username
	 *            associated with the workout
	 * @param date
	 *            date the workout is performed
	 * @param name
	 *            of the workout
	 * @param desc
	 *            the description of the workout
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addWorkout(String username, String date, String name,
			String desc) throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("date", date);
		postData.put("name", name);
		postData.put("description", desc);
		this.makeRequest(postData, URL);
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
	public void addUser(String username, String password) throws InterruptedException, ExecutionException {
		// make the post request to URL with username of the workout
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/addUser.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", username);
		postData.put("password", password);
		this.makeRequest(postData, URL);
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
	public String[] getExercise(int eid) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with e_id
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("e_id", Integer.toString(eid));
		String response = this.makeRequest(postData, URL);
		String[] exercise = new String[4];

		// parse JSON
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject json_data = jArray.getJSONObject(0);
			exercise[0] = json_data.getString("username");
			exercise[1] = json_data.getString("name");
			exercise[2] = json_data.getString("type");
			exercise[3] = json_data.getString("description");
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
	public String[] getWorkout(int wid) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with e_id
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("w_id", Integer.toString(wid));
		String response = this.makeRequest(postData, URL);
		String[] workout = new String[4];

		// parse JSON
		// take JSON format and put into array
		try {
			JSONArray jArray = new JSONArray(response);
			JSONObject json_data = jArray.getJSONObject(0);
			workout[0] = json_data.getString("username");
			workout[1] = json_data.getString("name");
			workout[2] = json_data.getString("date");
			workout[3] = json_data.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return workout;
	}

	/**
	 * Updates the exercise with the given information. All fields need to be
	 * provided even if there is no change
	 * 
	 * @param eid
	 *            the id associated with the exercise
	 * @param username
	 *            username associated with the exercise
	 * @param type
	 *            the type definition of the exercise
	 * @param name
	 *            name of the exercise
	 * @param desc
	 *            the description of how to do the exercise
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateExercise(int eid, String name, String username,
			String description, String type) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with e_id and all update fields
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/modifyExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("e_id", Integer.toString(eid));
		postData.put("username", username);
		postData.put("name", name);
		postData.put("type", type);
		postData.put("description", description);

		this.makeRequest(postData, URL);
	}

	/**
	 * Update the workout with the given information. All fields need to be
	 * provided even if there is no change
	 * 
	 * @param wid
	 *            associated with the workout
	 * @param name
	 *            of the workout
	 * @param username
	 *            associated with the workout
	 * @param description
	 *            of the workout
	 * @param date
	 *            that the workout was performed
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateWorkout(int wid, String name, String username,
			String description, String date) throws InterruptedException,
			ExecutionException {
		// make the post request to URL with w_id and all update fields
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/modifyWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("w_id", Integer.toString(wid));
		postData.put("username", username);
		postData.put("name", name);
		postData.put("date", date);
		postData.put("description", description);

		this.makeRequest(postData, URL);
	}

	/**
	 * Deletes exercise from the database
	 * 
	 * @param eid
	 *            id associated with the exercise
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void deleteExercise(int eid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteExercise.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("e_id", Integer.toString(eid));

		this.makeRequest(postData, URL);
	}

	/**
	 * Deletes the workout from the database
	 * 
	 * @param wid
	 *            id associated with the workout
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void deleteWorkout(int wid) throws InterruptedException,
			ExecutionException {
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/deleteWorkout.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("w_id", Integer.toString(wid));

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
