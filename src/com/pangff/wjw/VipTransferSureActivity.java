package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class VipTransferSureActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_sure);
	}

	public static void  invoteToVipTransferSure(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, VipTransferSureActivity.class);  
        context.startActivity(intent); 
	}
}
