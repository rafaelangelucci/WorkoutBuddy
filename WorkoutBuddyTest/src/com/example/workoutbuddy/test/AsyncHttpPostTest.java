package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Set;
import helperClasses.TemplateExercise;
import helperClasses.TemplateWorkout;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.UiThreadTest;
import android.util.Log;

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
		String response = wrapper.makeRequest(new HashMap<String,Object>(), URL);
		signal.await(1, TimeUnit.SECONDS);

		assertEquals(response, "success");
		
	}
	
	@UiThreadTest
	public void testAddGetModifyGetDeleteWorkout() throws InterruptedException, ExecutionException
	{
		Workout workout = new Workout("TestWorkout", "03/20/2014", "testdesc", "usernameA", null);
		wrapper.addWorkout(workout);
		
		Workout dbworkout = wrapper.getWorkout(workout.getWid());
		
		assertEquals(dbworkout.getName(), workout.getName());
		assertEquals(dbworkout.getUsername(), workout.getUsername());
		assertEquals(dbworkout.getDate(), workout.getDate());
		assertEquals(dbworkout.getDescription(), workout.getDescription());
		
		workout.setName("Modified Name");
		wrapper.updateWorkout(workout);
		
		dbworkout = wrapper.getWorkout(workout.getWid());
		assertEquals(dbworkout.getName(), workout.getName());
		
		boolean del = wrapper.deleteWorkout(workout.getWid());
		assertTrue(del);
		
		
		
	}
	
	@UiThreadTest
	public void testAddGetModifyGetDeleteExercise() throws InterruptedException, ExecutionException
	{
		Exercise exercise = new Exercise("TestExercise", "strength", "testdesc", "usernameA", null);
		wrapper.addExercise(exercise);
		
		Exercise dbworkout = wrapper.getExercise(exercise.getEid());
		assertEquals(dbworkout.getName(), exercise.getName());
		assertEquals(dbworkout.getUsername(), exercise.getUsername());
		assertEquals(dbworkout.getType(), exercise.getType());
		assertEquals(dbworkout.getDescription(), exercise.getDescription());
		
		exercise.setName("Modified Name");
		wrapper.updateExercise(exercise);
		
		dbworkout = wrapper.getExercise(exercise.getEid());
		assertEquals(dbworkout.getName(), exercise.getName());
		
		boolean del = wrapper.deleteExercise(exercise.getEid());
		assertTrue(del);
		
	}
	
	//UPDATE BROKEN
	@UiThreadTest
	public void testAddGetModifyGetDeleteSet() throws InterruptedException, ExecutionException{
		//Test Add
		Set set = new Set(10, 135, "", 1, 1, 1);
		wrapper.addSet(set);
		
		//Test Get
		Set dbSet = wrapper.getSet(set.getSid());
		assertEquals(dbSet.getEid(), set.getEid());
		assertEquals(dbSet.getPriority(), set.getPriority());
		assertEquals(dbSet.getReps(), set.getReps());
		assertEquals(dbSet.getTime(), set.getTime());
		assertEquals(dbSet.getWeight(), set.getWeight());
		assertEquals(dbSet.getWid(), set.getWid());
		
		//Test Update
		set.setPriority(2);
		set.setWeight(150);
		set.setReps(8);
		wrapper.updateSet(set);
		dbSet = wrapper.getSet(set.getSid());
		//assertEquals(dbSet.getPriority(), set.getPriority());
		assertEquals(dbSet.getReps(), set.getReps());
		assertEquals(dbSet.getWeight(), set.getWeight());
		
		boolean del = wrapper.deleteSet(set.getSid());
		assertTrue(del);
	}
	

	/**
	 * Signals a completed request
	 */
	@Override
	public void requestComplete() {
		signal.countDown();
	}

}
