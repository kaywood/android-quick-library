package com.quick.library.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.quick.library.QuickHelper;


public class HomeSlidingActivity extends SlidingActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setBehindContentView(R.layout.slide_left);
		super.setContentView(R.layout.slide_right);
		
		SlidingMenu sm = getSlidingMenu();//more option see PropertiesActivity sample
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setFadeDegree(0.35f);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindWidth(200);
		sm.setBehindScrollScale(0.333f);
		
		setSlidingActionBarEnabled(false);
		
		//getSlidingMenu().showMenu();//getSlidingMenu().toggle();//may not be effective
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