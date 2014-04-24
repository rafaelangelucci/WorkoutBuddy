package com.example.workoutbuddy.test;

import java.util.HashMap;

import java.util.concurrent.ExecutionException;

import httpRequests.AsyncHttpPostWrapper;

import com.uiuc.workoutbuddy.LoginActivity;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;


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
		String success = wrapper.userLogin(username, password);
		Assert.assertTrue(success.equals("success"));
		
		// Incorrect user and password
		success = wrapper.userLogin("fake", "fake");
		Assert.assertTrue(success.equals("fail"));
		
		// Correct user and incorrect password
		success = wrapper.userLogin(username, "fake");
		Assert.assertTrue(success.equals("fail"));
		
		// Incorrect user and password that exists in the db
		success = wrapper.userLogin("fake", password);
		Assert.assertTrue(success.equals("fail"));
	}
	
	public void testLoginResponseBad() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(lActivity);
		
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php";
		HashMap<String, String> postData = new HashMap<String, String>();
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
		
		String success = wrapper.userLogin(username, password);
		Assert.assertTrue(success.equals("success"));
		
		wrapper.deleteUser(username, password);
		
		success = wrapper.userLogin(username, password);
		Assert.assertTrue(success.equals("fail"));
	}
}