package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.event.ApplySuccessEvent;
import com.pangff.wjw.event.IEvent;

public class ApplyCompleteActivity extends BaseActivity{
	
	
	@AndroidView(R.id.applyComplete)
	Button applyComplete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_complete);
		applyComplete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseApplication.self.controlBus.post(new ApplySuccessEvent());
				finish();
			}
		});
	}
	
	public static void  invoteApplyComplete(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ApplyCompleteActivity.class);  
        context.startActivity(intent); 
	}
	
}
