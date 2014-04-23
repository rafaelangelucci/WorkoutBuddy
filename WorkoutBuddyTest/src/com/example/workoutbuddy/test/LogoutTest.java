package com.example.workoutbuddy.test;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import com.uiuc.workoutbuddy.MainActivity;
import junit.framework.Assert;


public class LogoutTest extends
ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity mActivity;
	
	public LogoutTest(){
		super(MainActivity.class);
	}
	
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		setActivityInitialTouchMode(false);
	    mActivity = getActivity();
	}
	
	
	
	public void testLogout_wLoginUser()
	{
		SharedPreferences un= mActivity.getSharedPreferences("username", Context.MODE_PRIVATE);
		un.edit().putString("username", "bobobob").commit();
		
		Assert.assertTrue(loginDataExists());
		Assert.assertEquals("bobobob", getLoginUsername());
		mActivity.logout();
		Assert.assertFalse(loginDataExists());
		Assert.assertEquals("No Logged in User", getLoginUsername());
	}
	
	public void testLogout_woLoginUser()
	{
		Assert.assertFalse(loginDataExists());
		Assert.assertEquals("No Logged in User", getLoginUsername());
		mActivity.logout();
		Assert.assertFalse(loginDataExists());
		Assert.assertEquals("No Logged in User", getLoginUsername());
	}
	
	
	public boolean loginDataExists()
	{
		SharedPreferences un= mActivity.getSharedPreferences("username", Context.MODE_PRIVATE);
		return un.contains("username");
	}
	
	public String getLoginUsername()
	{
		SharedPreferences un= mActivity.getSharedPreferences("username", Context.MODE_PRIVATE);
		return un.getString("username", "No Logged in User");
	}
}