package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class ResivePasswordActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resive_password);
	}
	
	public static void  invoteToResivePassword(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ResivePasswordActivity.class);  
        context.startActivity(intent); 
	}
	
	
	
	
}
