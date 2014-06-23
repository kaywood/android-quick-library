package com.quick.library.app;

import android.os.Bundle;
import android.view.View;

import com.quick.library.QuickRequestActivity;

public class QuickRequestSampleAct extends QuickRequestActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_activity_sample);
		
		setTitle();
		
		super.get("http://www.baidu.com/", this, 0);
	}

	private void setTitle() {
		getTitleManager().setTitle("hello world", true).setOptionView(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showToast("asfad");
			}
		}).commit();
	}
	
	@Override
	public void onSuccess(String arg0, String arg1, int arg2) {
		super.onSuccess(arg0, arg1, arg2);
		System.out.println(arg0);
	}
	
}
