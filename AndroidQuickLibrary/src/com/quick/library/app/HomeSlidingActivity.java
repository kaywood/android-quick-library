package com.quick.library.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.quick.library.QuickHelper;
import com.quick.library.QuickSlidingActivity;


public class HomeSlidingActivity extends QuickSlidingActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.slide_right, R.layout.slide_left);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if(arg1==DialogInterface.BUTTON_POSITIVE) {
						QuickHelper.appExit();
					}
				}
			};
			QuickHelper.showDialog(this, "exit app?", listener, true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}