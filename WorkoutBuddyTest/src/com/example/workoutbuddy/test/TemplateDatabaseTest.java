package com.example.workoutbuddy.test;

import helperClasses.Exercise;
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

public class TemplateDatabaseTest extends TestCase implements HttpRequestListener{

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
	

	@UiThreadTest
	public void testAddGetTemplates() throws InterruptedException, ExecutionException{
		TemplateWorkout tworkout = new TemplateWorkout();
		tworkout.setName("TestTemplate");
		tworkout.setDescription("Test desc");
		tworkout.setUsername("usernameA");
		
		TemplateExercise texercise1 = new TemplateExercise();
		texercise1.setPriority(1);
		texercise1.setEid(1);
		texercise1.setNumSets(1);
		texercise1.setReps(2);
		
		TemplateExercise texercise2 = new TemplateExercise();
		texercise2.setPriority(2);
		texercise2.setEid(2);
		texercise2.setNumSets(4);
		texercise2.setReps(4);
		
		ArrayList<TemplateExercise> texercises = new ArrayList<TemplateExercise>();
		texercises.add(texercise1);
		texercises.add(texercise2);
		tworkout.setExercises(texercises);
		int tid = wrapper.addTemplateWorkout(tworkout);
		TemplateWorkout getTworkout = wrapper.getTemplateWorkout(tid);
		
		assertEquals(getTworkout.getName(), tworkout.getName());
		assertEquals(getTworkout.getDescription(), tworkout.getDescription());
		assertEquals(getTworkout.getUsername(), tworkout.getUsername());
		
		ArrayList<TemplateExercise> getTexercises = getTworkout.getExercises();
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
