package com.quick.library;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quick.library.app.R;

/**
 * 自定义Dialog类
 * 
 * @author steven-pan
 * 
 */
public class QuickDialog extends Dialog {
	private Button postiveBtn;
	private Button negativeBtn;
	private LinearLayout contentLayout;
	private DialogInterface.OnClickListener listener;

	public final static int BUTTON_POSITIVE_ONLY = 0;
	public final static int BUTTON_ALL = 1;
	public final static int BUTTON_NONE = 2;

	private int buttonMode = BUTTON_POSITIVE_ONLY;

	public QuickDialog(Context context) {
		super(context, R.style.quick_dialog_style);
		this.init();
	}

	public void init() {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.quick_dialog_layout,
				null);
		contentLayout = (LinearLayout) layout.findViewById(R.id.content_layout);
		postiveBtn = (Button) layout.findViewById(R.id.confirm_button);
		negativeBtn = (Button) layout.findViewById(R.id.cancel_button);

		postiveBtn.setVisibility(View.GONE);
		negativeBtn.setVisibility(View.GONE);

		ButtenListener buttenListener = new ButtenListener();
		postiveBtn.setOnClickListener(buttenListener);
		negativeBtn.setOnClickListener(buttenListener);

		super.setContentView(layout);
	}

	public QuickDialog setMessage(CharSequence message) {
		TextView textView = new TextView(getContext());
		textView.setText(message);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		textView.setTextColor(getContext().getResources().getColor(R.color.quick_title));
		contentLayout.addView(textView);
		
		return this;
	}

	public QuickDialog setMode(int buttonMode) {
		this.buttonMode = buttonMode;
		
		return this;
	}

	public QuickDialog setView(View view) {
		contentLayout.addView(view);
		
		return this;
	}

	public QuickDialog setOnClickListener(DialogInterface.OnClickListener listener) {
		this.listener = listener;
		
		return this;
	}

	class ButtenListener implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (postiveBtn == arg0) {
				listener.onClick(QuickDialog.this, DialogInterface.BUTTON_POSITIVE);
			} else {
				listener.onClick(QuickDialog.this, DialogInterface.BUTTON_NEGATIVE);
			}
			dismiss();
		}
	}

	public void show() {
		if (BUTTON_POSITIVE_ONLY == buttonMode) {
			postiveBtn.setVisibility(View.VISIBLE);
		} else if (BUTTON_ALL == buttonMode) {
			postiveBtn.setVisibility(View.VISIBLE);
			negativeBtn.setVisibility(View.VISIBLE);
		} else {
			// default, not show any button
		}
		super.show();
	}

}
