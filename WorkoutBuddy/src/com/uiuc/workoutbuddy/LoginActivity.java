package com.uiuc.workoutbuddy;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, HttpRequestListener
{

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
			login(v);
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
	
	private void login(View v)
	{
		String username = getUsername();
		String password = getPassword();
	
		// Call userlogin() in the wrapper class to check is the user exists and the password is correct
		if(username.equals("") || password.equals(""))
			Toast.makeText(this.getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
		else
		{
			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				 boolean success = wrapper.userLogin(username, password);
				
				 if (success) {
						Toast.makeText(this.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

						SharedPreferences un= this.getSharedPreferences("username", Context.MODE_PRIVATE);
						un.edit().putString("username",username).commit();
						
						Intent intent = new Intent(this, MainActivity.class);
				    	startActivity(intent);
					} else {
						Toast.makeText(this.getApplicationContext(), "Login information incorrect. Try again.", Toast.LENGTH_SHORT).show();
					}
				//signal.await(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void signup(View v)
	{
		String username = getUsername();
		String password = getPassword();
	
		// Call the wrapper class to create the account
		if(username.equals("") || password.equals(""))
			Toast.makeText(this.getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
		else
		{
			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				wrapper.addUser(username, password);
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
	
	public String getUsername() {
		EditText et = (EditText)findViewById(R.id.inputUsername);
		return et.getText().toString();
	}

	public String getPassword() {
		EditText et = (EditText)findViewById(R.id.inputPassword);
		return et.getText().toString();
	}
	
	@Override
	public void requestComplete() {
		Log.i( "requestComplete()", "Request Completed countDown()");
		//signal.countDown();
		
	}

}
