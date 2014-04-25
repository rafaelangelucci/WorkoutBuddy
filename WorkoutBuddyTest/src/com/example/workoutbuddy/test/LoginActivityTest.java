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
		
		loginChecker(wrapper,username,password,"success");
		loginChecker(wrapper,"fake","fake","fail");
		loginChecker(wrapper,username,"fake","fail");
		loginChecker(wrapper,"fake",password,"fail");
		
		wrapper.deleteUser(username, password);
	}

	private void loginChecker(AsyncHttpPostWrapper wrapper, String username,String password, String expected)
			throws InterruptedException, ExecutionException {
		String success;
		success = wrapper.userLogin(username, password);
		Assert.assertTrue(success.equals(expected));
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
		loginChecker(wrapper,username,password,"success");	
		wrapper.deleteUser(username, password);
		loginChecker(wrapper,username,password,"fail");
	}
}