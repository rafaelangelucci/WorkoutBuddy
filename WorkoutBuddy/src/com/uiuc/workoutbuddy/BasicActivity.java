package com.uiuc.workoutbuddy;

import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BasicActivity extends Activity implements OnClickListener, HttpRequestListener
{

	CountDownLatch signal;
	public int numExercises = 0;
	public String[][] exerciseList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_workout);

		Button done = (Button)findViewById(R.id.buttonDone);
		Button plus = (Button)findViewById(R.id.buttonMinus);
		Button minus = (Button)findViewById(R.id.buttonPlus);

		done.setOnClickListener(this);
		plus.setOnClickListener(this);
		minus.setOnClickListener(this);


		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
		signal = new CountDownLatch(1);
		try {
			exerciseList = wrapper.getExerciseList("usernameA");
			signal.await(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		addExerciseSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.buttonDone:
			btn_DoneClicked(v);
			break;
		case R.id.buttonPlus:
			btn_PlusClicked(v);
			break;
		case R.id.buttonMinus:
			btn_MinusClicked(v);
			break;
		default:
		}
	}

	private void btn_DoneClicked(View v)
	{
		EditText et = (EditText)findViewById(R.id.editText1);
		String name = et.getText().toString();
		et = (EditText)findViewById(R.id.EditText01);
		String description = et.getText().toString();
		
		
//		
//
//		LinearLayout lv = (LinearLayout)findViewById(R.id.linearLayout);
//		lv.		lv.removeViewAt(this.numExercises -1);
//		this.numExercises--;
//	
//		
		Toast.makeText(this.getApplicationContext(), "Done Clicked", Toast.LENGTH_SHORT).show();
		Toast.makeText(this.getApplicationContext(), "description = " + description, Toast.LENGTH_SHORT).show();
		Toast.makeText(this.getApplicationContext(), "name = " + name, Toast.LENGTH_SHORT).show();
	}

	private void btn_MinusClicked(View v)
	{
		Toast.makeText(this.getApplicationContext(), "Minus Clicked", Toast.LENGTH_SHORT).show();

		if(this.numExercises>1)
		{
			LinearLayout lv = (LinearLayout)findViewById(R.id.linearLayout);
			lv.removeViewAt(this.numExercises -1);
			this.numExercises--;
		}
	}

	private void btn_PlusClicked(View v)
	{
		Toast.makeText(this.getApplicationContext(), "Plus Clicked", Toast.LENGTH_SHORT).show();
		addExerciseSpinner();	
	}

	@Override
	public void requestComplete() {
		Log.i( "requestComplete()", "Request Completed countDown()");
		signal.countDown();
	}
	
	private void addExerciseSpinner()
	{
		LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
		if(ll==null) return;
		

		Spinner spinner = new Spinner(this);
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item, exerciseList[0]);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
		spinner.setAdapter(adapter);
		ll.addView(spinner, this.numExercises);
		this.numExercises++;
	}
}