package com.quick.library;

import android.content.Context;

/**
 * Log工具
 * 
 * @author steven-pan
 */
public class QuickLogger {

	private static volatile QuickLogger LOGGER = null;

	private static final String PREFIX = "*//**\\*";

	private String currentTag = null;

	private boolean output = false;

	public static QuickLogger getLogger(Context context) {
		if (null == LOGGER) {
			synchronized (QuickLogger.class) {
				if (null == LOGGER) {
					LOGGER = new QuickLogger();
				}
			}
		}
		if (null != context) {
			LOGGER.setTag(context.getClass().getSimpleName());
		}
		return LOGGER;
	}

	private QuickLogger() {
		this.currentTag = QuickLogger.class.getSimpleName();
	}

	public void setOutput(boolean output) {
		this.output = output;
		this.debug("output:" + this.output);
	}

	private void setTag(String currentTag) {
		this.currentTag = currentTag;
	}

	public void debug(String info) {
		if (output) {
			android.util.Log.d(currentTag, format(info));
		}
	}

	public void error(String info) {
		if (output) {
			android.util.Log.e(currentTag, format(info));
		}
	}

	public void info(String info) {
		if (output) {
			android.util.Log.i(currentTag, format(info));
		}
	}

	private String format(String info) {
		return PREFIX + info + PREFIX;
	}

}
