package com.uiuc.workoutbuddy;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

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
			Toast.makeText(this.getApplicationContext(), "Login Clicked", Toast.LENGTH_SHORT).show();
			break;
		case R.id.signup_button:
			Toast.makeText(this.getApplicationContext(), "Sign Up Clicked", Toast.LENGTH_SHORT).show();
			break;
		case R.id.skip_button:
			Intent intent = new Intent(this, MainActivity.class);
	    	startActivity(intent);
			break;
		default:
		}
	}

}
