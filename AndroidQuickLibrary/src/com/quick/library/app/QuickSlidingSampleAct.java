package com.quick.library.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.quick.library.QuickHelper;
import com.quick.library.QuickSlidingActivity;

public class QuickSlidingSampleAct extends QuickSlidingActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.slide_right, R.layout.slide_left);

		findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getSlidingMenu().showMenu();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isSlidingMenuOpen()) {
				getSlidingMenu().toggle();
				return true;
			}

			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if (arg1 == DialogInterface.BUTTON_POSITIVE) {
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