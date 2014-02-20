package com.example.workoutbuddy;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
 
    public void goToMyWorkouts(View view) {
    	Intent intent = new Intent(this, MyWorkoutsActivity.class);
    	startActivity(intent);
    }
    
    public void goToCreateWorkOut(View view) {
//    	Intent intent = new Intent(this, CreateWorkoutActivity.class);
//    	startActivity(intent);
    }
}
