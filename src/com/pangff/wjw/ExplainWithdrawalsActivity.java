package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

import com.pangff.wjw.event.IEvent;

public class ExplainWithdrawalsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explain_withdrawals);
	}
	
	public static void  invoteToExplainWithdrawals(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExplainWithdrawalsActivity.class);  
        context.startActivity(intent); 
	}
}
