package com.quick.library;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quick.library.app.R;

/**
 * QuickTitleManager，用于处理标题设置
 * 
 * @author steven-pan
 * 
 */
public class QuickTitleManager implements View.OnClickListener {

	private RelativeLayout mTitleLayout = null;

	private Activity mActivity = null;

	public QuickTitleManager(Activity activity, RelativeLayout titleLayout) {
		this.mTitleLayout = titleLayout;
		this.mActivity = activity;
		this.mTitleLayout.setVisibility(View.GONE);
	}

	/**
	 * set title by CharSequence, not to show back indicator
	 */
	public QuickTitleManager setTitle(CharSequence text) {
		this.setTitle(text, false);

		return this;
	}

	/**
	 * set title by CharSequence
	 * 
	 * @param text
	 * @param back
	 *            whether to show back indicator
	 */
	public QuickTitleManager setTitle(CharSequence text, boolean back) {
		((TextView) this.mTitleLayout.findViewById(R.id.quick_title_text)).setText(text);

		View backView = this.mTitleLayout.findViewById(R.id.quick_back_icon);
		if (back) {
			backView.setVisibility(View.VISIBLE);
			setTitleListener(this);
		} else {
			backView.setVisibility(View.GONE);
		}

		return this;
	}

	/**
	 * back indicator close listener
	 * 
	 * @param arg0
	 */
	@Override
	public void onClick(View arg0) {
		try {
			this.mActivity.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set a new View.OnClickListener for left of TitleLayout
	 * 
	 * @param listener
	 */
	public QuickTitleManager setTitleListener(View.OnClickListener listener) {
		if (listener != null) {
			this.mTitleLayout.findViewById(R.id.quick_left_view).setOnClickListener(listener);
		}

		return this;
	}

	/**
	 * set Custom View on right of TitleLayout
	 * 
	 * @param view
	 */
	public QuickTitleManager setOptionView(View.OnClickListener listener) {
		ImageView imageView = new ImageView(mActivity);
		imageView.setImageResource(R.drawable.quick_right_more);
		this.setOptionView(imageView, listener);

		return this;
	}

	/**
	 * set Custom View on right of TitleLayout
	 * 
	 * @param view
	 */
	public QuickTitleManager setOptionView(View view, View.OnClickListener listener) {
		LinearLayout optionLayout = (LinearLayout) this.mTitleLayout.findViewById(R.id.quick_right_layout);
		optionLayout.addView(view);
		optionLayout.setOnClickListener(listener);

		return this;
	}

	public void commit() {
		this.mTitleLayout.setVisibility(View.VISIBLE);
	}
}