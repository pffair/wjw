package com.pangff.wjw.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.R;

/**
 * 系统相关工具
 * 
 * @author pangff
 */
public class PhoneUtils {

	/**
	 * 判断网络连接是否可用
	 * 
	 * @return
	 */

	public static boolean isNetAvailable() {
		boolean isAvailable = false;

		NetworkInfo mNetworkInfo = getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return isAvailable;
	}

	public static NetworkInfo getActiveNetworkInfo() {
		try {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.self
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			return mNetworkInfo;
		} catch (NullPointerException e) {
			return null;
		}
	}

	// dip转像素
	public static int dipToPixels(float dip) {

		final float SCALE = BaseApplication.self.getResources()
				.getDisplayMetrics().density;
		float valueDips = dip;
		int valuePixels = (int) (valueDips * SCALE + 0.5f);
		return valuePixels;
	}

	// 像素转dip
	public static float pixelsToDip(int pixels) {

		final float SCALE = BaseApplication.self.getResources()
				.getDisplayMetrics().density;
		float dips = pixels / SCALE;
		return dips;
	}

	public static float getDensity() {
		return BaseApplication.self.getResources().getDisplayMetrics().density;
	}

}
