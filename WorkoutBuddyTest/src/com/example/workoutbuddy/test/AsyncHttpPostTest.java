package com.example.workoutbuddy.test;

import helperClasses.Exercise;
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
		String response = wrapper.makeRequest(new HashMap<String,String>(), URL);
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
		
		int del = wrapper.deleteWorkout(workout.getWid());
		assertEquals(1, del);
		
		
		
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
		
		int del = wrapper.deleteExercise(exercise.getEid());
		assertEquals(1, del);
		
	}
	
	@UiThreadTest
	public void testAddGetTemplates() throws InterruptedException, ExecutionException{
		Workout tworkout = new Workout();
		tworkout.setName("TestTemplate");
		tworkout.setDescription("Test desc");
		tworkout.setUsername("usernameA");
		
		Exercise texercise1 = new Exercise();
		texercise1.setPriority(1);
		texercise1.setEid(1);
		texercise1.setNumSets(1);
		texercise1.setReps(2);
		
		Exercise texercise2 = new Exercise();
		texercise2.setPriority(2);
		texercise2.setEid(2);
		texercise2.setNumSets(4);
		texercise2.setReps(4);
		
		ArrayList<Exercise> texercises = new ArrayList<Exercise>();
		texercises.add(texercise1);
		texercises.add(texercise2);
		tworkout.setExercises(texercises);
		int tid = wrapper.addTemplateWorkout(tworkout);
		Workout getTworkout = wrapper.getTemplateWorkout(tid);
		
		assertEquals(getTworkout.getName(), tworkout.getName());
		assertEquals(getTworkout.getDescription(), tworkout.getDescription());
		assertEquals(getTworkout.getUsername(), tworkout.getUsername());
		
		ArrayList<Exercise> getTexercises = getTworkout.getExercises();
		Log.d("Exercise value", Integer.toString(getTexercises.get(0).getPriority()));
		Log.d("Exercise value", Integer.toString(getTexercises.get(0).getEid()));
		Log.d("Exercise value", Integer.toString(getTexercises.get(0).getNumSets()));
		Log.d("Exercise value", Integer.toString(getTexercises.get(0).getReps()));
		assertEquals(getTexercises.get(0).getPriority(), texercise1.getPriority());
		assertEquals(getTexercises.get(0).getEid(), texercise1.getEid());
		assertEquals(getTexercises.get(0).getNumSets(), texercise1.getNumSets());
		assertEquals(getTexercises.get(0).getReps(), texercise1.getReps());
		
		assertEquals(getTexercises.get(1).getPriority(), texercise2.getPriority());
		assertEquals(getTexercises.get(1).getEid(), texercise2.getEid());
		assertEquals(getTexercises.get(1).getNumSets(), texercise2.getNumSets());
		assertEquals(getTexercises.get(1).getReps(), texercise2.getReps());
		
	}
	

	/**
	 * Signals a completed request
	 */
	@Override
	public void requestComplete() {
		signal.countDown();
	}

}
