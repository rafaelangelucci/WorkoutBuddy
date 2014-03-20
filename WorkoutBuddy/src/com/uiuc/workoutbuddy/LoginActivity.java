package com.uiuc.workoutbuddy;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, HttpRequestListener
{
	CountDownLatch signal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button login = (Button)findViewById(R.id.login_button);
		Button signup = (Button)findViewById(R.id.signup_button);
		Button skip = (Button)findViewById(R.id.skip_button);
		
		login.setOnClickListener(this);
		signup.setOnClickListener(this);
		skip.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.login_button:
			Toast.makeText(this.getApplicationContext(), "Login Clicked", Toast.LENGTH_SHORT).show();
			break;
		case R.id.signup_button:
			signup(v);
			break;
		case R.id.skip_button:
			Intent intent = new Intent(this, MainActivity.class);
	    	startActivity(intent);
			break;
		default:
		}
	}
	
	private void signup(View v)
	{
		// Get user password and username
		EditText et = (EditText)findViewById(R.id.inputUsername);
		String username = et.getText().toString();
		
		et = (EditText)findViewById(R.id.inputPassword);
		String password = et.getText().toString();
	
	
		if(username.equals(""))
			Toast.makeText(this.getApplicationContext(), "The name textbox must be filled.", Toast.LENGTH_SHORT).show();
		else
		{
			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			signal = new CountDownLatch(1);
			try {
				wrapper.addUser(username, password);
				signal.await(2, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			Toast.makeText(this.getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(this, MainActivity.class);
	    	startActivity(intent);
		}
	}

	@Override
	public void requestComplete() {
		Log.i( "requestComplete()", "Request Completed countDown()");
		signal.countDown();
		
	}

}
