package com.pangff.wjw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.db.AdvImgDBManager;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.http.ResponseCallBack;
import com.pangff.wjw.model.CallRequest;
import com.pangff.wjw.model.CallResponse;
import com.pangff.wjw.model.Img;
import com.pangff.wjw.util.PhoneUtils;

public class IncomingBroadcastReceiver extends BroadcastReceiver implements ResponseCallBack{
	static WindowManager wm;
	static LinearLayout view;
	static String advID;
	public static final String METHOD_TELOK = "telok";
	@Override
	public void onReceive(final Context context, final Intent intent) {
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (AdvImgDBManager.getInstance().getRandomImgs() != null) {
						popPhone(context, AdvImgDBManager.getInstance()
								.getRandomImgs());
					}
				}
			}, 500);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					popPhoneRemove();
				}
			}, 10500);
		}
		if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					popPhoneRemove();
				}
			}, 100);
		}
		if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					popPhoneRemove();
				}
			}, 100);
		}

	}

	// 弹窗具体实现
	private void popPhone(Context context, Img img) {
		if(view==null){
			wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

			WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
							| WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
							| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSPARENT);

			params.height = PhoneUtils.dipToPixels(135);
			params.gravity = Gravity.BOTTOM;
			params.width = LayoutParams.MATCH_PARENT;
			params.format = PixelFormat.TRANSLUCENT;

			view = new LinearLayout(context);
			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			view.addView(imageView,lp);
			BaseApplication.self.IMAGE_CACHE.get(img.imgbig, imageView);
			view.setBackgroundColor(Color.RED);
			view.setOrientation(LinearLayout.VERTICAL);

			wm.addView(view, params);
			advID = img.id;
		}
	}

	// 移除弹窗
	private synchronized void popPhoneRemove() {
		if (wm != null && view != null) {
			doAdvRequest();
			wm.removeView(view);
			view = null;
		}
	}
	
	private void doAdvRequest(){
		String xml = new CallRequest().getParams(METHOD_TELOK,advID);
		new HttpRequest<CallResponse>().postDataXml(METHOD_TELOK, xml, this,CallResponse.class,false);
	}

	@Override
	public void onStartRequest() {
		
	}

	@Override
	public void onFailure(String method, String errorMsg) {
		
	}

	@Override
	public void onSuccess(String method, Object result) {
		Log.e("", "########成功");
	}
}