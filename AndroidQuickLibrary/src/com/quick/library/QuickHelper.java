package com.quick.library;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.http.LoadControler;
import com.android.http.RequestManager;
import com.android.http.RequestManager.RequestListener;

public class QuickHelper {
	
	private static final String UTF_CHARSET = "UTF-8";

	/**
	 * request network by "GET"
	 * 
	 * @param url
	 * @param requestListener
	 * @param actionId
	 * @return
	 */
	public static LoadControler get(String url, RequestListener requestListener, int actionId) {
		return RequestManager.getInstance().get(url, requestListener, actionId);
	}

	/**
	 * request network by "POST", with data
	 * 
	 * @param url
	 * @param data
	 * @param requestListener
	 * @param actionId
	 * @return
	 */
	public static LoadControler post(String url, String data, RequestListener requestListener, int actionId) {
		return RequestManager.getInstance().post(url, data, requestListener, actionId);
	}

	/**
	 * set title for activity
	 * 
	 * @param activity
	 * @param title
	 */
	public static void setTitle(Activity activity, String title) {

	}

	/**
	 * show single button dialog, with confirm only
	 * 
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void showDialog(Context context, String message, OnClickListener listener) {
		Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(context.getString(R.string.dialog_title));
		dialog.setMessage(message);
		dialog.setPositiveButton(context.getString(R.string.dialog_confirm), listener);
		dialog.show();
	}

	/**
	 * show double button dialog, one confirm, the other cancel
	 * 
	 * @param context
	 * @param message
	 * @param listener
	 * @param cancelable
	 */
	public static void showDialog(Context context, String message, OnClickListener listener, boolean cancelable) {
		Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(context.getString(R.string.dialog_title));
		dialog.setMessage(message);
		dialog.setCancelable(cancelable);
		dialog.setPositiveButton(context.getString(R.string.dialog_confirm), listener);
		dialog.setNegativeButton(context.getString(R.string.hello_cancel), listener);
		dialog.show();
	}

	/**
	 * show toast message
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * if text empty or "null"
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.length() == 0 || "null".equalsIgnoreCase(text);
	}

	/**
	 * check network available
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * check SDCard available
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSDCardAvailable(Context context) {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * convert byte data to String
	 * 
	 * @param data
	 * @return
	 */
	public static String convertToString(byte[] data) {
		if (data == null)
			return null;
		try {
			return new String(data, UTF_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * convert String to JSONObject
	 * 
	 * @param text
	 * @return
	 */
	public static JSONObject convertToJSONObject(String text) {
		if (!isEmpty(text)) {
			try {
				return new JSONObject(text);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * convert String to JSONArray
	 * 
	 * @param text
	 * @return
	 */
	public static JSONArray convertToJSONArray(String text) {
		if (!isEmpty(text)) {
			try {
				return new JSONArray(text);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * exit application(kill the process that app hold)
	 */
	public static void appExit() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 网址汉字编码
	 */
	public static String urlEncode(String str) {
		StringBuffer buf = new StringBuffer();
		byte c;
		byte[] utfBuf;
		try {
			utfBuf = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("URLEncode: Failed to get UTF-8 bytes from string.");
			utfBuf = str.getBytes();
		}
		for (int i = 0; i < utfBuf.length; i++) {
			c = utfBuf[i];
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
					|| (c == '.' || c == '-' || c == '*' || c == '_')
					|| (c == ':' || c == '/' || c == '=' || c == '?' || c == '&' || c == '%')) {
				buf.append((char) c);
			} else {
				buf.append("%").append(Integer.toHexString((0x000000FF & c)));
			}
		}
		return buf.toString();
	}

	/**
	 * get PackageInfo by packageName that installed
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context, String packageName) {
		try {
			return context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * check the app installation by packageName
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isApplicationInstalled(final Context context, final String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		return getPackageInfo(context, packageName) != null;
	}

	/**
	 * install app by apk
	 */
	public static void startInstall(final Context context, String filePath) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}