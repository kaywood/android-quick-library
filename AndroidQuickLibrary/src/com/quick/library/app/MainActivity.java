package com.quick.library.app;

import android.os.Bundle;
import android.view.Menu;

import com.quick.library.QuickActivity;
import com.quick.library.R;

public class MainActivity extends QuickActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
