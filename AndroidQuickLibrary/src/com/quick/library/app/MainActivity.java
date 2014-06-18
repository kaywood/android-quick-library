package com.quick.library.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.quick.library.QuickHelper;
import com.quick.library.R;


public class MainActivity extends SlidingActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setBehindContentView(R.layout.slide_left);
		super.setContentView(R.layout.slide_right);
		
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setBehindWidth(200);
		sm.setBehindScrollScale(0.333f);
		
		getSlidingMenu().showMenu();//toggle//直接调无效果
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