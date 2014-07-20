package com.pangff.wjw.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pangff.wjw.BaseApplication;

public class UserInfoUtil {
	private final SharedPreferences sharedPreferences;
	private static UserInfoUtil userInfoUtil;
	private static final String USER_INFO = "user_info";

	private static final String KEY_USERNAME = "key_username";
	private static final String KEY_PASSORD = "key_passord";
	private static final String KEY_USERID = "key_userid";
	private static final String KEY_PIC = "key_pic";

	private UserInfoUtil(Context context) {
		sharedPreferences = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
	}

	public static synchronized UserInfoUtil getInstanse() {
		if (userInfoUtil == null) {
			userInfoUtil = new UserInfoUtil(BaseApplication.self);
		}
		return userInfoUtil;
	}

	public String getUserName() {
		return sharedPreferences.getString(KEY_USERNAME, "");
	}

	public void setUserName(String username) {
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USERNAME, username);
		editor.commit();
	}

	public String getUserId() {
		return sharedPreferences.getString(KEY_USERID, "");
	}

	public void setUserId(String userid) {
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USERID, userid);
		editor.commit();
	}
	
	public String getUserHeader() {
		return sharedPreferences.getString(KEY_PIC, "");
	}

	public void setUserHeader(String url) {
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_PIC, url);
		editor.commit();
	}

	public String getUserPassword() {
		return sharedPreferences.getString(KEY_PASSORD, "");
	}

	public void setUserPassword(String passord) {
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_PASSORD, passord);
		editor.commit();
	}

	public void saveLoginInfo(String userid, String passord, String username) {
		setUserId(userid);
		setUserPassword(passord);
		setUserName(username);
	}

	public void clearUserInfo() {
		Editor editor = sharedPreferences.edit();
		editor.remove(KEY_PASSORD);
		editor.remove(KEY_USERID);
		editor.remove(KEY_USERNAME);
		editor.commit();
	}
}
