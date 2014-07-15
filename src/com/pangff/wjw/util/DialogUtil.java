package com.pangff.wjw.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import com.pangff.wjw.view.OnOneOffClickListenerInDialog;

public class DialogUtil {
	private static final String TAG = "DialogUtil";

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
