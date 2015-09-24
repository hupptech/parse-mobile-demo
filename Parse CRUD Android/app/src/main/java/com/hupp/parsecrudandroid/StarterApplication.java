package com.hupp.parsecrudandroid;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class StarterApplication extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		String APPLICATION_ID = getResources().getString(R.string.parse_app_id);
		String CLIENT_KEY = getResources().getString(R.string.parse_client_key);

		Parse.enableLocalDatastore(this);

		Parse.initialize(getApplicationContext(),APPLICATION_ID,CLIENT_KEY);

		ParseUser.enableAutomaticUser();

		ParseACL defualtACL = new ParseACL();

		ParseACL.setDefaultACL(defualtACL, true);
	}
}
