package com.example.workoutbuddy.test;

import helperClasses.Exercise;
import helperClasses.Set;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;
import android.test.UiThreadTest;

public class FullWorkoutTest extends TestCase implements HttpRequestListener {

	AsyncHttpPostWrapper wrapper;
	CountDownLatch signal;
	Workout workout;
	Exercise exercise1;
	Exercise exercise2;

	/**
	 * Creates a new signal to signal call to requestComplete Creates instance
	 * of Wrapper to test AsyncHttpPost. Also creates a full workout for testing
	 */
	protected void setUp() throws Exception {
		super.setUp();

		signal = new CountDownLatch(1);
		wrapper = new AsyncHttpPostWrapper(null);

		// Construct a full workout
		workout = new Workout("Full Workout", "April242014",
				"this is a full workout", "test", new ArrayList<Exercise>());
		exercise1 = new Exercise("exercise1", "strength",
				"test exercise", "test", new ArrayList<Set>());
		exercise2 = new Exercise("exercise2", "strength",
				"test exercise", "test", new ArrayList<Set>());
	}

	@UiThreadTest
	public void testAddFullWorkoutNoSets() throws InterruptedException,
			ExecutionException {
		workout.getExercises().add(exercise1);
		workout.getExercises().add(exercise2);

		// Add test exercises to the DB
		wrapper.addExercise(exercise1);
		wrapper.addExercise(exercise2);

		// Add Full workout to the db
		wrapper.addWorkout(workout);
		Workout dbWorkout = wrapper.getWorkout(workout.getWid());
		ArrayList<Set> sets = wrapper.getSetList(workout.getWid());

		// delete workout and exercises
		wrapper.deleteWorkout(workout.getWid());
		wrapper.deleteExercise(exercise1.getEid());
		wrapper.deleteExercise(exercise2.getEid());

		ArrayList<Exercise> dbExercises = dbWorkout.getExercises();
		ArrayList<Set> dbSets = new ArrayList<Set>();
		for(int i = 0; i < dbExercises.size(); i++){
			ArrayList<Set> iterSet = dbExercises.get(i).getSets();
			for(int j = 0; j < iterSet.size(); j++){
				dbSets.add(iterSet.get(j));
			}
		}
		
		assertEquals(sets.size(), 2);
		assertEquals(dbSets.size(), 2);
		assertEquals(dbExercises.size(), 2);
		assertEquals(dbExercises.get(0).getName(), "exercise1");
		assertEquals(dbExercises.get(1).getName(), "exercise2");
		assertEquals(dbSets.get(0).getWid(), workout.getWid());	
	}

	/**
	 * Signals a completed request
	 */
	@Override
	public void requestComplete() {
		signal.countDown();
	}

}
