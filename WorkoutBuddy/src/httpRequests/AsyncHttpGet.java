package httpRequests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;


public class AsyncHttpGet extends AsyncTask<String, String, String> {
	
	String TAG = "WorkoutBuddy";
	private HashMap<String, String> getData = null;
	public InputStream result = null;
	
	//constructor
	public AsyncHttpGet(HashMap<String, String> data){
		getData = data;
	}
	
	//background activity
	//structure taken from 
	//http://veerasundar.com/blog/2011/09/making-get-and-post-requests-in-android-application/
	@Override
	protected String doInBackground(String... params) {
		
		Log.d(TAG, "Going to make a get request");
		StringBuilder response = new StringBuilder();
		
		try{
			//creates the get request with modified url
			HttpGet get = new HttpGet(addLocationToUrl(params[0]));
			
			//sets up http response
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(get);
			
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				Log.d(TAG, "HTTP get succeeded");
				
				//parse the http response into a string
				HttpEntity messageEntity = httpResponse.getEntity();
				InputStream is = messageEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				while((line = br.readLine())!= null){
					response.append(line);
				}
				
			} else {
				Log.e(TAG, "HTTP get status code not 200");
			}
		} catch (Exception e){
			Log.e(TAG, e.getMessage());
		}
		
		Log.d(TAG, "Done with HTTP getting");
		return response.toString();
	}
	
	
	//skeleton for this function taken from 
	//http://stackoverflow.com/questions/2959316/how-to-add-parameters-to-a-http-get-request-in-android
	//Adds data to the get request
	protected String addLocationToUrl(String url){
	    //adds '?=' clause
		if(!url.endsWith("?"))
	        url += "?";

	    List<NameValuePair> params = new LinkedList<NameValuePair>();
	    
	    //iterates through request data adding to params
		Iterator<String> it = getData.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			params.add(new BasicNameValuePair(key, getData.get(key)));
		}

		//formats params into a utf-8 string and appends to URL
	    String paramString = URLEncodedUtils.format(params, "utf-8");
	    url += paramString;
	    return url;
	}

}
