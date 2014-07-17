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

public class IncomingBroadcastReceiver extends BroadcastReceiver {
	static WindowManager wm;
	static LinearLayout view;
    @Override
    public void onReceive(final Context context,final Intent intent) {


        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                		popPhone(context,"123456");
                }
            }, 500);
            
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                		popPhoneRemove();
                }
            }, 10500);
        }
        if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
	    		new Handler().postDelayed(new Runnable() {
	                @Override
	                public void run() {
	                		popPhoneRemove();
	                }
	            }, 100);
	    } 
        if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
        		new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                		popPhoneRemove();
                }
            }, 100);
        }

    }
    
    //弹窗具体实现
    private void popPhone(Context context,String phone) {
    	 	wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

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
    private synchronized void popPhoneRemove(){
    		if(wm != null && view!=null){  
            wm.removeView(view);  
            view = null;
        } 
    }
}