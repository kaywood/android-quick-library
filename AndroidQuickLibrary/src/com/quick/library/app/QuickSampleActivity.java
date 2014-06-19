package com.quick.library.app;

import android.graphics.Color;
import android.os.Bundle;

import com.quick.library.QuickActivity;

public class QuickSampleActivity  extends QuickActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_sample);
		setTitleLayoutBg(Color.BLUE);
		setTitle("hello world", true);
		super.get("http://www.baidu.com/", this, 0);
	}
	
	@Override
	public void onSuccess(String arg0, String arg1, int arg2) {
		super.onSuccess(arg0, arg1, arg2);
		System.out.println(arg0);
	}
	
}
