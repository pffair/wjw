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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pangff.wjw.IncomingCallActivity;

public class IncomingBroadcastReceiver extends BroadcastReceiver {
	WindowManager wm;
	LinearLayout view;
    @Override
    public void onReceive(final Context context,final Intent intent) {

        Log.e("IncomingBroadcastReceiver: onReceive: ", "flag1");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("IncomingBroadcastReceiver: onReceive: ", state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.d("Ringing", "Phone is ringing");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                		popPhone(context,"123456");
                }
            }, 1000);
        }
    }
    
    //弹窗具体实现
    private void popPhone(Context context,String phone) {
    	 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    	    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
    	        LayoutParams.MATCH_PARENT,
    	        LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
    	        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
    	        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
    	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
    	        PixelFormat.TRANSPARENT);

    	    params.height = 100;
    	    params.gravity = Gravity.BOTTOM;
    	    params.width = LayoutParams.MATCH_PARENT;
    	    params.format = PixelFormat.TRANSLUCENT;


    	    view = new LinearLayout(context);
    	    TextView textView = new TextView(context);
    	    textView.setText(phone);
    	    view.addView(textView);
    	    view.setBackgroundColor(Color.RED);
    	    view.setOrientation(LinearLayout.VERTICAL);

    	    wm.addView(view, params);
    }
    //移除弹窗
    private void popPhoneRemove(){
    		if(wm != null){  
            wm.removeView(view);  
        } 
    }
}