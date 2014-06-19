package com.quick.library.app;

import android.app.Application;

import com.android.http.RequestManager;

public class QuickApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		RequestManager.getInstance().init(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
}