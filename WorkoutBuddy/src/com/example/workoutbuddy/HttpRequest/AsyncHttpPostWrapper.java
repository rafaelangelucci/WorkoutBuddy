package com.example.workoutbuddy.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.os.AsyncTask;
import android.util.Log;

/**
 * Wrapper class to make calls to AsyncHttpPost
 * @author RafMac
 *
 */
public class AsyncHttpPostWrapper {
	private HttpRequestListener requestListener;
	
	public AsyncHttpPostWrapper(HttpRequestListener requestListener){
		this.requestListener = requestListener;
	}
	
	public String makeRequest(HashMap<String, String> data, String URL) throws InterruptedException, ExecutionException{
		return new AsyncHttpPost(data).execute(URL).get();
	}
	
	


	private class AsyncHttpPost extends AsyncTask<String, String, String> {
		
		String TAG = "WorkoutBuddy";
		private HashMap<String, String> postData = null;
		public InputStream result = null;
		
		//constructor
		public AsyncHttpPost(HashMap<String, String> data){
			postData = data;
		}
		
		//structure taken from 
		//http://veerasundar.com/blog/2011/09/making-get-and-post-requests-in-android-application/
		//background activity
		@Override
		protected String doInBackground(String... params) {
			
			StringBuilder response = new StringBuilder();
			
			try{
				//Creates post request and adds url
				HttpPost post = new HttpPost(params[0]);
				
				//iterates through post data and creates a request entity
				ArrayList<NameValuePair> requestData = new ArrayList<NameValuePair>();
				Iterator<String> it = postData.keySet().iterator();
		
				while(it.hasNext()){
					String key = it.next();
					requestData.add(new BasicNameValuePair(key, postData.get(key)));
				}
				post.setEntity(new UrlEncodedFormEntity(requestData));
				
				//creates client to serve response
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(post);
				
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					Log.d(TAG, "HTTP post succeeded");
					
					//parses the http response
					HttpEntity messageEntity = httpResponse.getEntity();
					InputStream is = messageEntity.getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while((line = br.readLine())!= null){
						response.append(line);
					}
					
				} else {
					Log.e(TAG, httpResponse.getStatusLine().getStatusCode() + "HTTP post status code not 200");
				}
			} catch (Exception e){
				Log.e(TAG, e.getMessage());
			}
			
			Log.d(TAG, "Done with HTTP posting");
			return response.toString();
		}
		
		protected void onPostExecute(String result){
			requestListener.requestComplete();
		}

	}


}
