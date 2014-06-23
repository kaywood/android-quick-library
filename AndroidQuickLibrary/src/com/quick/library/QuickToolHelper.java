package com.quick.library;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.http.LoadControler;
import com.android.http.RequestManager;
import com.android.http.RequestManager.RequestListener;

public class QuickToolHelper {

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
	 * show single button dialog, with confirm only
	 * 
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void showDialog(Context context, String message, DialogInterface.OnClickListener listener) {
		QuickDialog dialog = new QuickDialog(context);
		dialog.setMessage(message);
		dialog.setMode(QuickDialog.BUTTON_POSITIVE_ONLY);
		dialog.setOnClickListener(listener);
		dialog.setCancelable(false);
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
		QuickDialog dialog = new QuickDialog(context);
		dialog.setMessage(message);
		dialog.setMode(QuickDialog.BUTTON_ALL);
		dialog.setOnClickListener(listener);
		dialog.setCancelable(cancelable);
		dialog.show();
	}

	/**
	 * show button with custom content view
	 * 
	 * @param context
	 * @param view
	 * @param buttonMode
	 * @param listener
	 * @param cancelable
	 */
	public static void showDialog(Context context, View view, int buttonMode, OnClickListener listener,
			boolean cancelable) {
		QuickDialog dialog = new QuickDialog(context);
		dialog.setView(view);
		dialog.setMode(buttonMode);
		dialog.setOnClickListener(listener);
		dialog.setCancelable(cancelable);
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
	 * 对字符串进行MD5加密。
	 */
	public static String encryptMD5(String strInput) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strInput.getBytes("UTF-8"));
			byte b[] = md.digest();
			buf = new StringBuffer(b.length * 2);
			for (int i = 0; i < b.length; i++) {
				if (((int) b[i] & 0xff) < 0x10) { /* & 0xff转换无符号整型 */
					buf.append("0");
				}
				buf.append(Long.toHexString((int) b[i] & 0xff)); /* 转换16进制,下方法同 */
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 加载Assert文本文件，转换成String类型
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String loadAssetsText(Context context, String fileName) throws IOException {
		InputStream inputStream = context.getAssets().open(fileName, Context.MODE_PRIVATE);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while ((len = inputStream.read(bytes)) > 0) {
			byteStream.write(bytes, 0, len);
		}

		return new String(byteStream.toByteArray(), "UTF-8");
	}
	
	/**
	 * 加载Raw文本文件，转换成String类型
	 * 
	 * @param context
	 * @param rawId
	 * @return
	 * @throws IOException
	 */
	public static String loadRawText(Context context, int rawId) throws IOException {
		InputStream inputStream = context.getResources().openRawResource(rawId);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while ((len = inputStream.read(bytes)) > 0) {
			byteStream.write(bytes, 0, len);
		}
		
		return new String(byteStream.toByteArray(), "UTF-8");
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