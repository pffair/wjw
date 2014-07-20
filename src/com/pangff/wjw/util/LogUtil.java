package com.pangff.wjw.util;

import android.util.Log;

/**
 * Created with IntelliJ IDEA. User: wangjun Date: 14-1-13 Time: 下午1:53 To
 * change this template use File | Settings | File Templates.
 */
public class LogUtil {

	private static final String TAG = "wjw";
	public static boolean isShow = true;
	public static void error(String msg) {
		if(isShow){
			Log.e(TAG, msg);
		}
	}

	public static void warn(String msg) {
		if(isShow){
			Log.w(TAG, msg);
		}
	}

	public static void debug(String msg) {
		if(isShow){
			Log.d(TAG, msg);
		}
	}

	public static void info(String msg) {
		if(isShow){
			Log.i(TAG, msg);
		}
	}

}
