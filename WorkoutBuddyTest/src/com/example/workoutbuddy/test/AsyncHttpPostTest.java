package com.example.workoutbuddy.test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.UiThreadTest;
import com.example.workoutbuddy.HttpRequest.AsyncHttpPostWrapper;
import com.example.workoutbuddy.HttpRequest.HttpRequestListener;

import junit.framework.TestCase;

public class AsyncHttpPostTest extends TestCase implements HttpRequestListener{

	AsyncHttpPostWrapper wrapper;
	CountDownLatch signal;
	
	/**
	 * Creates a new signal to signal call to requestComplete
	 * Creates instance of Wrapper to test AsyncHttpPost
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		signal = new CountDownLatch(1);
		wrapper = new AsyncHttpPostWrapper(this);
	}
	
	/**
	 * Makes an empty request to the given URL
	 * @throws InterruptedException
	 * @throws ExecutionException 
	 */
	@UiThreadTest
	public void testMakeEmptyRequest() throws InterruptedException, ExecutionException
	{
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/emptyPostRequestTest.php";
		String response = wrapper.makeRequest(new HashMap<String,String>(), URL);
		signal.await(5, TimeUnit.SECONDS);

		assertEquals(response, "success");
		
	}
	
	@UiThreadTest
	public void testgetWorkoutList() throws InterruptedException, ExecutionException
	{
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/getWorkoutList.php";
		HashMap<String, String> postData = new HashMap<String,String>();
		postData.put("username", "usernameA");
		String response = wrapper.makeRequest(postData, URL);
		signal.await(5, TimeUnit.SECONDS);
		
		//now parse
		String[] names = {};
		String[] dates = {};
		String[] descriptions = {};
			
		try{
			JSONArray jArray = new JSONArray(response);
			int arrayLength = jArray.length();
			names = new String[arrayLength];
			dates = new String[arrayLength];
			descriptions = new String[arrayLength];
			for(int i = 0; i<jArray.length(); i++){
				JSONObject json_data = jArray.getJSONObject(i);
				names[i] = json_data.getString("name");
				dates[i] = json_data.getString("date");
				descriptions[i] = json_data.getString("description");
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		assertEquals(names[0], "WorkoutA");
		assertEquals(names[1], "WorkoutB");
		assertEquals(dates[0], "03-4-2014");
		assertEquals(descriptions[0], "desc");
		
	}
	

	/**
	 * Signals a completed request
	 */
	@Override
	public void requestComplete() {
		signal.countDown();
		
	}

}
