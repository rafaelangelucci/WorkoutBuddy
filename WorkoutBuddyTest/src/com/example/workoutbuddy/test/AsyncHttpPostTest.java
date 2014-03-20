package com.example.workoutbuddy.test;

import helperClasses.Exercise;
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
	public void testAddGetModifyGetDeleteWorkout() throws InterruptedException, ExecutionException
	{
		Workout workout = new Workout("TestWorkout", "03/20/2014", "testdesc", "usernameA", null);
		wrapper.addWorkout(workout);
		signal.await(1, TimeUnit.SECONDS);
		
		Workout dbworkout = wrapper.getWorkout(workout.getWid());
		assertEquals(dbworkout.getName(), "TestWorkout");
		
	}
	
	@UiThreadTest
	public void testgetExerciseList() throws InterruptedException, ExecutionException
	{
		Exercise[] responses = wrapper.getExerciseList("usernameA");
		signal.await(1, TimeUnit.SECONDS);
		
		assertEquals(responses[0].getName(), "ExerciseA");
		assertEquals(responses[1].getName(), "ExerciseB");
		assertEquals(responses[0].getType(), "strength");
		assertEquals(responses[0].getDescription(), "desc");
		
	}
	
	@UiThreadTest
	public void testgetExercise() throws InterruptedException, ExecutionException 
	{
		Exercise response = wrapper.getExercise(2);
		signal.await(1, TimeUnit.SECONDS);
		
		assertEquals(response.getUsername(), "usernameA");
		assertEquals(response.getName(), "ExerciseA");
		assertEquals(response.getType(), "strength");
		assertEquals(response.getDescription(), "desc");
	}
	

	/**
	 * Signals a completed request
	 */
	@Override
	public void requestComplete() {
		signal.countDown();
	}

}
