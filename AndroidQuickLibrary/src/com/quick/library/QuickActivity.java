package com.quick.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.http.LoadControler;
import com.android.http.RequestManager.RequestListener;
import com.quick.library.app.R;

public class QuickActivity extends Activity implements RequestListener {
	private LoadControler mLoadControler = null;
	private ProgressDialog mProgressDialog = null;
	private TitleManager mTitleManager = null;

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
		this.mTitleManager = new TitleManager(titleLayout);

		super.setContentView(layout);
	}

	protected TitleManager getTitleManager() {
		return mTitleManager;
	}

	protected class TitleManager implements View.OnClickListener {
		private RelativeLayout mTitleLayout = null;

		public TitleManager(RelativeLayout titleLayout) {
			this.mTitleLayout = titleLayout;
			this.mTitleLayout.setVisibility(View.GONE);
		}

		/**
		 * set title by CharSequence, not to show back indicator
		 */
		public TitleManager setTitle(CharSequence text) {
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
		public TitleManager setTitle(CharSequence text, boolean back) {
			((TextView) mTitleLayout.findViewById(R.id.quick_title_text)).setText(text);

			View backView = mTitleLayout.findViewById(R.id.quick_back_icon);
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
		public TitleManager setTitleListener(View.OnClickListener listener) {
			if (listener != null) {
				mTitleLayout.findViewById(R.id.quick_left_view).setOnClickListener(listener);
			}
			return this;
		}

		/**
		 * set Custom View on right of TitleLayout
		 * 
		 * @param view
		 */
		public TitleManager setOptionView(View.OnClickListener listener) {
			ImageView imageView = new ImageView(QuickActivity.this);
			imageView.setImageResource(R.drawable.quick_right_more);
			this.setOptionView(imageView, listener);
			return this;
		}

		/**
		 * set Custom View on right of TitleLayout
		 * 
		 * @param view
		 */
		public TitleManager setOptionView(View view, View.OnClickListener listener) {
			LinearLayout optionLayout = (LinearLayout) mTitleLayout.findViewById(R.id.quick_right_layout);
			optionLayout.addView(view);
			optionLayout.setOnClickListener(listener);
			return this;
		}

		public void commit() {
			mTitleLayout.setVisibility(View.VISIBLE);
		}
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

	public void onBackPressed() {
		if (mLoadControler != null) {
			mLoadControler.cancel();
		}
		super.onBackPressed();
	}

}
