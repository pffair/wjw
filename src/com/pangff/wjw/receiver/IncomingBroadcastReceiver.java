package com.pangff.wjw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.pangff.wjw.IncomingCallActivity;

public class IncomingBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context,final Intent intent) {

        Log.d("IncomingBroadcastReceiver: onReceive: ", "flag1");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("IncomingBroadcastReceiver: onReceive: ", state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.d("Ringing", "Phone is ringing");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                		Intent i = new Intent(context, IncomingCallActivity.class);
                    i.putExtras(intent);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i);
                }
            }, 2000);
        }
    }
}