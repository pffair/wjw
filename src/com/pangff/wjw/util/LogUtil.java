package com.pangff.wjw.util;

import android.util.Log;

/**
 * Created with IntelliJ IDEA. User: wangjun Date: 14-1-13 Time: 下午1:53 To
 * change this template use File | Settings | File Templates.
 */
public class LogUtil {

	private static final String TAG = "leyi";

	public static void error(String msg) {
		Log.e(TAG, msg);
	}

	public static void warn(String msg) {
		Log.w(TAG, msg);
	}

	public static void debug(String msg) {
		Log.d(TAG, msg);
	}

	public static void info(String msg) {
		Log.i(TAG, msg);
	}

}
