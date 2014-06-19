package com.quick.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.http.LoadControler;
import com.android.http.RequestManager.RequestListener;
import com.quick.library.app.R;

public class QuickActivity extends Activity implements View.OnClickListener, RequestListener {
	private RelativeLayout mTitleLayout = null;
	private LoadControler mLoadControler = null;
	private ProgressDialog mProgressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void setContentView(int resId) {
		LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_quick, null);
		ScrollView contentLayout = (ScrollView) layout.findViewById(R.id.contentView);
		contentLayout.addView(getLayoutInflater().inflate(resId, null));

		mTitleLayout = (RelativeLayout) layout.findViewById(R.id.titleView);
		mTitleLayout.setVisibility(View.GONE);

		super.setContentView(layout);
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
		QuickHelper.showToast(this, text);
	}

	/**
	 * show progress dialog
	 */
	public void showDialog() {
		showDialog(true);
	}

	/**
	 * show progress dialog, set cancelable
	 * 
	 * @param cancel
	 */
	public void showDialog(boolean cancel) {
		showDialog("loading...", cancel);
	}

	/**
	 * show progress dialog with message
	 * 
	 * @param message
	 */
	public void showDialog(String message) {
		showDialog(message, true);
	}

	/**
	 * show progress dialog
	 * 
	 * @param message
	 *            message
	 * @param cancel
	 *            cancelable
	 */
	public void showDialog(String message, boolean cancel) {
		if (isFinishing()) {
			return;
		}
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog.show(this, "", message);
			mProgressDialog.setCanceledOnTouchOutside(false);
		} else {
			mProgressDialog.setMessage(message);
			mProgressDialog.show();
		}
		mProgressDialog.setCancelable(false);
	}

	public boolean isDialogShowing() {
		return (mProgressDialog != null && mProgressDialog.isShowing());
	}

	public void dismissDialog() {
		if (isFinishing()) {
			return;
		}
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * set Bg fo TitleLayout by color value
	 * 
	 * @param colorId
	 */
	public void setTitleLayoutBg(int colorValue) {
		mTitleLayout.setBackgroundColor(colorValue);
	}

	/**
	 * set title by string resId, not to show back indicator
	 */
	public void setTitle(int resId) {
		this.setTitle(getString(resId));
	}

	/**
	 * set title by CharSequence, not to show back indicator
	 */
	public void setTitle(CharSequence text) {
		this.setTitle(text, false);
	}

	/**
	 * set title by string resId
	 * 
	 * @param resId
	 * @param back
	 *            whether to show back indicator
	 */
	public void setTitle(int resId, boolean back) {
		this.setTitle(getString(resId), back);
	}

	/**
	 * set title by CharSequence
	 * 
	 * @param text
	 * @param back
	 *            whether to show back indicator
	 */
	public void setTitle(CharSequence text, boolean back) {
		mTitleLayout.setVisibility(View.VISIBLE);
		((TextView) mTitleLayout.findViewById(R.id.titleText)).setText(text);

		View backView = mTitleLayout.findViewById(R.id.backImage);
		if (back) {
			backView.setVisibility(View.VISIBLE);
			setTitleListener(this);
		} else {
			backView.setVisibility(View.GONE);
		}
	}

	/**
	 * back indicator close listener
	 * 
	 * @param arg0
	 */
	@Override
	public void onClick(View arg0) {
		try {
			QuickActivity.this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set a new View.OnClickListener for left of TitleLayout
	 * 
	 * @param listener
	 */
	public void setTitleListener(View.OnClickListener listener) {
		if (listener != null) {
			mTitleLayout.findViewById(R.id.backView).setOnClickListener(listener);
		}
	}

	/**
	 * set Custom View on right of TitleLayout
	 * 
	 * @param view
	 */
	public void setTitleView(View view) {
		((LinearLayout) mTitleLayout.findViewById(R.id.rightView)).addView(view);
	}

	public void get(String url, RequestListener requestListener, int actionId) {
		mLoadControler = QuickHelper.get(url, requestListener, actionId);
	}

	public void post(String url, String data, RequestListener requestListener, int actionId) {
		mLoadControler = QuickHelper.post(url, data, requestListener, actionId);
	}

	/**
	 * onRequest start
	 */
	@Override
	public void onRequest() {
		showDialog();
	}

	/**
	 * onError
	 */
	@Override
	public void onError(String errorMsg, String url, int arg2) {
		dismissDialog();
	}

	/**
	 * onSuccess
	 */
	@Override
	public void onSuccess(String result, String url, int arg2) {
		dismissDialog();
	}
	
	public void onBackPressed() {
		if (mLoadControler != null) {
			mLoadControler.cancel();
		}
		super.onBackPressed();
	}

}
