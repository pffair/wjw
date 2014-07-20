package com.pangff.wjw.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import com.pangff.wjw.R;
import com.pangff.wjw.view.OnOneOffClickListenerInDialog;

public class DialogUtil {
	private static final String TAG = "DialogUtil";
	
	 /**
	   * 显示自底向上弹出的选择菜单框，位于屏幕的底部，内含多项菜单；最底项的菜单固定为“取消”，点击后菜单框消失；
	   * 
	   * @param activity Activity dialog所在的Activity
	   * @param menuLayout 菜单框布局的资源文件
	   * @param menuItemViewIds 相应菜单项的View的id的列表
	   * @param onClickMenuItemListener 相应菜单项的View点击后的处理逻辑
	   */
	  public static Dialog showButtomPopUpDialog(Activity activity, int menuLayout,
	      int[] menuItemViewIds, OnOneOffClickListenerInDialog onClickMenuItemListener) {
	    return showButtomPopUpDialog(activity, menuLayout, menuItemViewIds, onClickMenuItemListener,
	        R.style.wjw_dialog, Gravity.BOTTOM);
	  }

	public static Dialog showButtomPopUpDialog(Activity activity,
			int menuLayout, int[] menuItemViewIds,
			OnOneOffClickListenerInDialog onClickMenuItemListener, int theme,
			int gravity) {
		if (activity == null || activity.isFinishing()) {
			return null;
		}
		final Dialog dialog = new Dialog(activity, theme);
		View parent = activity.getLayoutInflater().inflate(menuLayout, null);
		parent.setMinimumWidth(10000);
		for (int i = 0, len = menuItemViewIds == null ? 0
				: menuItemViewIds.length; i < len; i++) {
			parent.findViewById(menuItemViewIds[i]).setOnClickListener(
					onClickMenuItemListener);
		}
		onClickMenuItemListener.setDialog(dialog);

		LayoutParams lp = dialog.getWindow().getAttributes();
		lp.gravity = gravity;
		dialog.onWindowAttributesChanged(lp);

		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(parent);
		show(activity, dialog);
		return dialog;
	}

	/**
	 * 关闭对话框，忽略所有异常
	 * @param dialog Dialog
	 */
	public static void dismiss(Dialog dialog) {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			Log.e(TAG, "", e);
		}
	}

	/**
	 * 关闭对话框，忽略所有异常
	 * 
	 * @param dialog
	 *            Dialog
	 */
	public static void dismiss(DialogInterface dialog) {
		try {
			if (dialog != null) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			Log.e(TAG, "", e);
		}
	}

	/**
	 * 显示对话框
	 * 
	 * @param act
	 * @param dialog
	 */
	public static void show(Activity act, Dialog dialog) {
		if (act == null || act.isFinishing() || dialog == null
				|| dialog.isShowing()) {
			return;
		}
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}
}
