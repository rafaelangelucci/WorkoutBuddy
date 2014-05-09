package com.uiuc.workoutbuddy;

import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;
import java.util.concurrent.ExecutionException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity class for the login and registration functionality
 *
 */
public class LoginActivity extends Activity implements OnClickListener, HttpRequestListener {
	public static final String PREFS_NAME = "username";
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PASSWORD = "password";
	public static String userName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		String username = pref.getString(PREF_USERNAME, null);
		userName = username;
		String password = pref.getString(PREF_PASSWORD, null);
		if (username != null && password != null) {
			// Prompt for username and password
			/*EditText editUserText = (EditText) findViewById(R.id.inputUsername);
			editUserText.setText(username);
			EditText editPassText = (EditText) findViewById(R.id.inputPassword);
			editPassText.setText(password);*/
			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				String result = wrapper.userLogin(username, password);
				if(result.equals("success")){
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		setContentView(R.layout.activity_login);

		Button login = (Button) findViewById(R.id.login_button);
		Button signup = (Button) findViewById(R.id.signup_button);

		login.setOnClickListener(this);
		signup.setOnClickListener(this);
	}

	/**
	 * Behavior for the login and signup buttons
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			login(v);
			break;
		case R.id.signup_button:
			signup(v);
			break;
		default:
		}
	}

	/**
	 * Takes the user input and checks its validity by connecting to the user database
	 * 
	 * @param v
	 */
	private void login(View v) {
		String username = getUsername();
		userName = username;
		String password = getPassword();

		// Call userlogin() in the wrapper class to check is the user exists and
		// the password is correct
		if (username.equals("") || password.equals(""))
			Toast.makeText(this.getApplicationContext(),"Please fill in all fields.", Toast.LENGTH_SHORT).show();
		else {

			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				String result = wrapper.userLogin(username, password);

				if (result.equals("success")) {
					Toast.makeText(this.getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
					sharedPrefer(username, password);
				} else {
					Toast.makeText(this.getApplicationContext(),"Login information incorrect. Try again.",Toast.LENGTH_SHORT).show();
				}
				// signal.await(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Takes in the user input and adds it to the user database
	 * 
	 * @param v 
	 */
	private void signup(View v) {
		String username = getUsername();
		String password = getPassword();
		
		// Call the wrapper class to create the account
		if (username.equals("") || password.equals(""))
			Toast.makeText(this.getApplicationContext(),
					"Please fill in all fields.", Toast.LENGTH_SHORT).show();
		else {
			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				String result = wrapper.addUser(username, password);
				if (result.equals("success")) {
					Toast.makeText(this.getApplicationContext(),"Registration Complete", Toast.LENGTH_SHORT).show();
					sharedPrefer(username, password);
				} else if (result.equals("username already exists")){
					Toast.makeText(this.getApplicationContext(),"Username alreay exists. Try again.",Toast.LENGTH_SHORT).show();
				} else{
					Toast.makeText(this.getApplicationContext(),"Registration failes. Try again.",Toast.LENGTH_SHORT).show();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets the user name from the interface
	 * 
	 * @return The user name
	 */
	public String getUsername() {
		EditText et = (EditText)findViewById(R.id.inputUsername);
		return et.getText().toString();
	}

	/**
	 * Gets the user password from the interface
	 * 
	 * @return The password
	 */
	public String getPassword() {
		EditText et = (EditText)findViewById(R.id.inputPassword);
		return et.getText().toString();
	}
	

	@Override
	public void requestComplete() {
		Log.i("requestComplete()", "Request Completed countDown()");
		// signal.countDown();
	}

	/**
	 * Stores a key-value pair for the user name and password
	 * 
	 * @param username Key
	 * @param password Value
	 */
	private void sharedPrefer(String username, String password) {
		SharedPreferences un = this.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		un.edit().putString(PREF_USERNAME, username).commit();
		un.edit().putString(PREF_PASSWORD, password).commit();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
