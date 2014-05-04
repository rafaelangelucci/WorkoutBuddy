package com.example.workoutbuddy.test;

import com.uiuc.workoutbuddy.MainActivity;
import com.uiuc.workoutbuddy.WorkoutFragment;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class WorkoutFragmentTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	MainActivity main;
	WorkoutFragment frag;
	Button myWo, newWo, sched;
	
	public WorkoutFragmentTest()
	{
		super(MainActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        main = (MainActivity) getActivity();
        frag = main.woFrag;
    }
	
	public void testPreConditions() {
        assertNotNull(main);
        assertNotNull(frag);
    }
	
	public void testSetUp()
	{
		myWo = frag.getMyWorkouts();
		newWo = frag.getNewWorkout();
		sched = frag.getSchedule();
		
		assertNotNull("get from frag fails", myWo);
		assertNotNull("get from frag fails", newWo);
		assertNotNull("get from frag fails", sched);
		
		myWo = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_my_workouts);
		newWo = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_new_workout);
		sched = (Button)main.findViewById(com.uiuc.workoutbuddy.R.id.btn_schedule);
		
		assertNotNull("get from main fails", myWo);
		assertNotNull("get from main fails", newWo);
		assertNotNull("get from main fails", sched);
	}
}
