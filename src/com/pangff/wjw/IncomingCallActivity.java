package com.pangff.wjw;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class IncomingCallActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Log.d("IncomingCallActivity: onCreate: ", "flag2");

            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            setContentView(R.layout.activity_call);
            String number = getIntent().getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            TextView text = (TextView) findViewById(R.id.text);
        } 
        catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
