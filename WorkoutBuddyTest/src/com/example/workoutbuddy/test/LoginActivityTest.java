package com.example.workoutbuddy.test;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import httpRequests.AsyncHttpPostWrapper;

import com.uiuc.workoutbuddy.LoginActivity;
import com.uiuc.workoutbuddy.MyWorkoutsListFragment;
import com.uiuc.workoutbuddy.WorkoutFragment;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	private LoginActivity lActivity;
	
	public LoginActivityTest(){
		super(LoginActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();

	    setActivityInitialTouchMode(false);
	    lActivity = getActivity();
	}
	
	/**
	 * Test successful start up of MainActivity
	 */
	public void testActivityStartup()
	{
		String expected = lActivity.getString(com.uiuc.workoutbuddy.R.string.app_name);
		assertEquals(expected, "Workout\nBuddy");
	}
	
	/**
	 * Test proper activity layout with all the necessary buttons
	 */
	public void testLayout(){
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.login_button));
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.signup_button));
		testButtonLayout(lActivity.findViewById(com.uiuc.workoutbuddy.R.id.skip_button));
	}
	
	/**
	 * Test all button clicks and verify they work as designed
	 * @param view
	 */
	public void testButtonLayout(View view)
	{
		Assert.assertNotNull(view);
		Assert.assertTrue(view.isShown());
		Assert.assertTrue(view.isClickable());
	}
	
	public void testLoginResponse() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(lActivity);
		
		
		String username = "newUser";
		String password = "newPassword";
		
		wrapper.addUser(username, password);
		
		// Correct user and password
		Boolean success = wrapper.userLogin(username, password);
		Assert.assertTrue(success);
		
		// Incorrect user and password
		success = wrapper.userLogin("fake", "fake");
		Assert.assertFalse(success);
		
		// Correct user and incorrect password
		success = wrapper.userLogin(username, "fake");
		Assert.assertFalse(success);
		
		// Incorrect user and password that exists in the db
		success = wrapper.userLogin("fake", password);
		Assert.assertFalse(success);
	}
	
	public void testLoginResponseBad() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(lActivity);
		
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php";
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("username", "fake");
		postData.put("password", "fake");
		String response = wrapper.makeRequest(postData, URL);
		
		Assert.assertEquals("fail", response);
	}
	
	
	public void testUserSignupAndDelete() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(lActivity);
		
		String username = "a8fs7dgsfdu";
		String password = "abc";
		
		wrapper.addUser(username, password);
		
		Boolean success = wrapper.userLogin(username, password);
		Assert.assertTrue(success);
		
		wrapper.deleteUser(username, password);
		
		success = wrapper.userLogin(username, password);
		Assert.assertFalse(success);
	}
}