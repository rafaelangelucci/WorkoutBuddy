package com.uiuc.workoutbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BasicActivity extends Activity implements OnClickListener{

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
			Toast.makeText(this.getApplicationContext(), "Done Clicked", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonPlus:
			Toast.makeText(this.getApplicationContext(), "Plus Clicked", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonMinus:
			Toast.makeText(this.getApplicationContext(), "Minus Clicked", Toast.LENGTH_SHORT).show();
			break;
		default:
		}
	}

}
