package com.pangff.wjw.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class StringUtil {
  private static final String TAG = "StringUtil";

  private static final String REGEX_PHONE = "^(1[358]\\d{9,9})$";

  /**
   * 字符串是否是空
   * 
   * @param temp
   * @return
   */
  public static boolean isEmpty(String temp) {

    return temp == null || temp.trim().length() == 0;
  }

  /**
   * 字符串是否是空（\t,\r,\n均为空）
   * 
   * @param input
   * @return
   */
  public static boolean isEmptyExtra(String input) {
    if (input == null || "".equals(input)) return true;

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否是数字开头
   * 
   * @return
   */
  public static boolean isNumOpen(String text) {

    if (StringUtil.isEmpty(text)) {
      return false;
    }

    char s = text.charAt(0);

    if (s >= '0' && s <= '9') {
      return true;
    }
    return false;
  }

  /**
   * 是否是数字开头
   * 
   * @return
   */
  public static boolean isNum(String text) {

    if (StringUtil.isEmpty(text)) {
      return false;
    }

    char s = text.charAt(0);

    if (s >= '0' && s <= '9') {
      return true;
    }
    return false;
  }

  private static final boolean isChinese(char c) {

    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
      return true;
    }
    return false;
  }

  public static final boolean isChinese(String strName) {

    char[] ch = strName.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      char c = ch[i];
      if (isChinese(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 若连接资源中有中文字符 则encode转码
   * 
   * @param url
   * @return
   */
  public static String getEncodeUrl(String url) {

    String fileName = "";
    String encodeUrl = "";
    // 从路径中获取
    if (url == null || "".equals(url.trim())) {
      return "";
    }
    fileName = url.substring(url.lastIndexOf("/") + 1);
    if (StringUtil.isChinese(fileName)) {
      try {
        encodeUrl = url.replace(fileName, URLEncoder.encode(fileName, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        Log.e(TAG, "", e);
      }
    } else {
      return url;
    }
    return encodeUrl;
  }

  public static boolean isLetterOpen(String text) {

    if (StringUtil.isEmpty(text)) {
      return false;
    }

    char s = text.charAt(0);

    if ((s >= 'a' && s <= 'z') || (s >= 'A' && s <= 'Z')) {
      return true;
    }

    return false;
  }

  /**
   * 手机号码判断. 包括空判断,手机号合法性判断, 号码段： 移动：134、135、136、137、138、139、147、
   * 150、151、152、157、158、159、182、187、188 联通：130、131、132、155、156、185、186 电信：133、153、180、189 <br>
   * 130-139 <br>
   * 153,155,156, <br>
   * 180,182,185,186,187,188,189,
   * 
   * @param phoneNumber 手机号
   * @return boolean
   */
  public static boolean isMobileNO(String mobiles) {
    if (isEmpty(mobiles)) {
      return false;
    }
    return mobiles.matches(REGEX_PHONE);
  }



  public static boolean isValidMailAddress(String mailStr) {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(mailStr).matches();
  }

  public static boolean isValidQQ(String mailStr) {
    String check = "^\\d+$";
    Pattern regex = Pattern.compile(check);
    Matcher matcher = regex.matcher(mailStr);
    boolean isMatched = matcher.matches();
    return isMatched;
  }


  public static boolean isValidUserName(String userName) {
    // 用户名验证
    if (isEmpty(userName)) {
      ToastUtil.show("请输入用户名");
      return false;
    }

    if (userName.length() < 3) {
      ToastUtil.show("用户名不能少于3位");
      return false;
    }

    if (userName.length() > 17) {
      ToastUtil.show("用户名由3-16位中文、字母或数字组成，请重新输入");
      return false;
    }

    char firstChar = userName.charAt(0);
    if ('0' <= firstChar && firstChar <= '9') {
      ToastUtil.show("用户名不能以数字开头");
      return false;
    }

    Matcher matcher =
        Pattern.compile("[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]{2,11}").matcher(userName);
    boolean match = matcher.matches();
    if (!match) {
      ToastUtil.show("用户名由3-12位中文、字母或数字组成，请重新输入");
      return false;
    }

    return true;
  }
  public static String listToString(List<String> stringList){
		if (stringList==null) {
			return null;
		}
		StringBuilder result=new StringBuilder();
		boolean flag=false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			}else {
				flag=true;
			}
			result.append("\""+string+"\"");
		}
		return result.toString();
	}


}
