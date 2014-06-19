package com.quick.library;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.quick.library.app.R;

public class QuickSlidingActivity extends SlidingActivity {

	public void setContentView(int resId, int behindId) {
		super.setBehindContentView(behindId);
		super.setContentView(resId);

		SlidingMenu sm = getSlidingMenu();// more option see PropertiesActivity sample
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setFadeDegree(0.35f);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindWidth(200);
		sm.setBehindScrollScale(0.333f);

		setSlidingActionBarEnabled(false);
	}

}
