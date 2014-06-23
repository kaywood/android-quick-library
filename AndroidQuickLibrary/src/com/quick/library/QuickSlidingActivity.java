package com.quick.library;

import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.quick.library.app.R;
/**
 * 左边侧滑基类
 * 
 * @author steven-pan
 *
 */
public class QuickSlidingActivity extends SlidingActivity implements OnOpenedListener, OnClosedListener {
	
	private boolean isOpen = false;
	
	protected QuickLogger logger=null;

	public void setContentView(int resId, int behindId) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setBehindContentView(behindId);
		super.setContentView(resId);
		
		this.logger=QuickLogger.getLogger(QuickSlidingActivity.this);
		
		SlidingMenu sm = getSlidingMenu();// more option see PropertiesActivity
											// sample
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setFadeDegree(0.35f);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindWidth(200);
		sm.setBehindScrollScale(0.333f);

		setSlidingActionBarEnabled(false);

		getSlidingMenu().setOnOpenedListener(this);
		getSlidingMenu().setOnClosedListener(this);

	}
	
	@Override
	public void onOpened() {
		this.isOpen = true;
	}

	@Override
	public void onClosed() {
		this.isOpen = false;
	}

	public boolean isSlidingMenuOpen() {
		return this.isOpen;
	}

}
