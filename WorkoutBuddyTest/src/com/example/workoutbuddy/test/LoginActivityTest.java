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
	
	public void testLoginResponseGood() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(lActivity);
		
		String URL = "http://workoutbuddy.web.engr.illinois.edu/PhpFiles/userLogin.php";
		HashMap<String, String> postData = new HashMap<String, String>();
		postData.put("username", "u");
		postData.put("password", "p");
		String response = wrapper.makeRequest(postData, URL);
		
		Assert.assertEquals("success", response);
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
	
	public void testUserLogin() throws InterruptedException, ExecutionException {
		AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(lActivity);
		
		Boolean success = wrapper.userLogin("u", "p");
		Assert.assertTrue(success);
		
		success = wrapper.userLogin("fake", "fake");
		Assert.assertFalse(success);
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