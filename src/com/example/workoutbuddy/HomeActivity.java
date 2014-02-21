package com.example.workoutbuddy;


import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        if(getActionBar() != null)
//        {
//        	Tab tab0 = getActionBar().newTab();
//        	tab0.setText("Tab 0 title");
//        	Tab tab1 = getActionBar().newTab();
//        	tab0.setText("Tab 1 title");
//        	Tab tab2 = getActionBar().newTab();
//        	tab0.setText("Tab 2 title");
//        	Tab tab3 = getActionBar().newTab();
//        	tab0.setText("Tab 3 title");
//
//        	getActionBar().addTab(tab0);
//        	getActionBar().addTab(tab1);
//        	getActionBar().addTab(tab2);
//        	getActionBar().addTab(tab3);
//        }
//        else
//        {
//        	System.out.print("ActionBar is null");
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
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