package com.pangff.wjw;

import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AcountChangeActivity extends BaseActivity{
	
	@AndroidView(R.id.loginPasswordChangeT)
	TextView loginPwd;
	
	@AndroidView(R.id.catchPasswordChangeT)
	TextView catchPwd;
	
	@AndroidView(R.id.payPasswordChangeT)
	TextView payPwd;
	
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_change);
		loginPwd.setOnClickListener(onOneOffClickListener);
		catchPwd.setOnClickListener(onOneOffClickListener);
		payPwd.setOnClickListener(onOneOffClickListener);
	}
	
	
	public static void invotoacountchange(BaseActivity context){
		Intent intent = new Intent();
        intent.setClass(context, AcountChangeActivity.class);  
        context.startActivity(intent);
	}


	@Override
	public void onMyClick(View v) {
		// TODO Auto-generated method stub
		//super.onMyClick(v);
		switch (v.getId()){
		case R.id.loginPasswordChangeT:;
		     goLogin();
		     break;
		     
		case R.id.catchPasswordChangeT:;
	        goCheck();
	        break;
	        
		case R.id.payPasswordChangeT:;
	        goPay();
	        break;
	        
		default:
			break;
		}
	}
	
	private void goLogin(){
		ResivePasswordActivity.invoteToResivePassword(this);
	}
	
	private void goCheck(){
		ResivePasswordActivity.invoteToResivePassword( this);
	}
	
	private void goPay(){
		ResivePasswordActivity.invoteToResivePassword( this);
	}
	
}
