package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class WithDrawalsApplyCompleteActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_complete);
	}
	
	public static void  invoteToWithDrawalsApplyComplete(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplyCompleteActivity.class);  
        context.startActivity(intent); 
	}
}
