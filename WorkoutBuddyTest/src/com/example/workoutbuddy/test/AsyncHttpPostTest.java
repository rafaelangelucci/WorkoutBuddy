package com.example.workoutbuddy.test;

import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.UiThreadTest;

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
		signal.await(1, TimeUnit.SECONDS);

		assertEquals(response, "success");
		
	}
	
	@UiThreadTest
	public void testgetWorkoutList() throws InterruptedException, ExecutionException
	{
		Workout[] responses = wrapper.getWorkoutList("usernameA");
		signal.await(1, TimeUnit.SECONDS);
		
		assertEquals(responses[0].getName(), "WorkoutA");
		assertEquals(responses[1].getName(), "WorkoutB");
		assertEquals(responses[0].getDate(), "03-4-2014");
		assertEquals(responses[0].getDescription(), "desc");
		
	}
	
	@UiThreadTest
	public void testgetExerciseList() throws InterruptedException, ExecutionException
	{
		String[][] responses = wrapper.getExerciseList("usernameA");
		signal.await(1, TimeUnit.SECONDS);
		
		assertEquals(responses[0][0], "ExerciseA");
		assertEquals(responses[0][1], "ExerciseB");
		assertEquals(responses[1][0], "strength");
		assertEquals(responses[2][0], "desc");
		
	}
	
	@UiThreadTest
	public void testgetExercise() throws InterruptedException, ExecutionException 
	{
		String[] response = wrapper.getExercise(2);
		signal.await(1, TimeUnit.SECONDS);
		
		assertEquals(response[0], "usernameA");
		assertEquals(response[1], "ExerciseA");
		assertEquals(response[2], "strength");
		assertEquals(response[3], "desc");
	}
	

	/**
	 * Signals a completed request
	 */
	@Override
	public void requestComplete() {
		signal.countDown();
	}

}
