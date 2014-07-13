package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class WithDrawalsApplyActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply);
	}
	
	public static void  invoteToWithDrawalsApply(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplyActivity.class);  
        context.startActivity(intent); 
	}
}
