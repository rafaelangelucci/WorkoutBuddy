package com.example.workoutbuddy.test;

import java.util.concurrent.CountDownLatch;
import android.test.UiThreadTest;
import com.example.workoutbuddy.*;
import com.example.workoutbuddy.HttpRequest.AsyncHttpPostWrapper;
import com.example.workoutbuddy.HttpRequest.HttpRequestListener;

import junit.framework.TestCase;

public class AsyncHttpPostTest extends TestCase implements HttpRequestListener{

	AsyncHttpPostWrapper wrapper;
	CountDownLatch signal;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		signal = new CountDownLatch(1);
		wrapper = new AsyncHttpPostWrapper(this);
	}
	
	@UiThreadTest
	public void testMakeRequest()
	{
		//TODO implement this
		
	}

	@Override
	public void requestComplete() {
		signal.countDown();
		
	}

}
