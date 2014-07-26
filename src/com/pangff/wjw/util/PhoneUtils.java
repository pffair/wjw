package com.pangff.wjw.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.pangff.wjw.BaseApplication;

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

	/**
	 * 得到sd卡的路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			File f = new File("wjw");
			try {
				boolean ismaked = false;
				if(!f.exists()){
					 ismaked = f.mkdirs();
				}
				if(ismaked){
					sdDir = Environment.getExternalStorageDirectory();
				}else{
					StringBuilder sbPhone = new StringBuilder();
					sbPhone.append(Environment.getDataDirectory());
					sbPhone.append("/data/com.pangff.wjw");
					return sbPhone.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringBuilder sbPhone = new StringBuilder();
				sbPhone.append(Environment.getDataDirectory());
				sbPhone.append("/data/com.pangff.wjw");
				return sbPhone.toString();
			}

		} else {
			StringBuilder sbPhone = new StringBuilder();
			sbPhone.append(Environment.getDataDirectory());
			sbPhone.append("/data/com.pangff.wjw");
			// sbPhone.append(product_name).append("/");
			return sbPhone.toString();
		}
		return sdDir.toString();

	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param mContext
	 * 
	 * @return
	 */
	public static int[] getScreenSize() {
		Display display = ((WindowManager) BaseApplication.self
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		int result[] = { dm.heightPixels, dm.widthPixels };
		return result;
	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param ctx
	 * @return [0]height [1]width
	 */
	public static int[] getScreenSizeArray(Context ctx) {
		Display display = ((WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return new int[] { dm.heightPixels, dm.widthPixels };
	}
}
