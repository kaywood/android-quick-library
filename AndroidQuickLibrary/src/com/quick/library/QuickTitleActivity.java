package com.quick.library;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.quick.library.app.R;

public class QuickTitleActivity extends Activity  {
	private QuickTitleManager mTitleManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void setContentView(int resId) {
		LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_quick, null);
		ScrollView contentLayout = (ScrollView) layout.findViewById(R.id.quick_content_layout);
		contentLayout.addView(getLayoutInflater().inflate(resId, null));

		RelativeLayout titleLayout = (RelativeLayout) layout.findViewById(R.id.quick_title_layout);
		this.mTitleManager = new QuickTitleManager(QuickTitleActivity.this, titleLayout);

		super.setContentView(layout);
	}

	protected QuickTitleManager getTitleManager() {
		return mTitleManager;
	}
	
	/**
	 * show toast
	 * 
	 * @param resId
	 */
	public void showToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * show toast
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		QuickUtils.showToast(this, text);
	}
}
