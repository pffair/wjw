package com.pangff.wjw;

import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ResivePasswordActivity extends BaseActivity{
	
	@AndroidView(R.id.originalPasswordE)
	EditText originalPasswordE;
	
	@AndroidView(R.id.newPasswordE)
	EditText newPasswordE;
	
	@AndroidView(R.id.newPasswordSureE)
	EditText newPasswordSureE;
	
	@AndroidView(R.id.sureResiveB)
	Button sureResiveB;
	
	@AndroidView(R.id.cancelResiveB)
	Button cancelResiveB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resive_password);
	}
	
	private void doRequestCommitPassword(){
		
	}
	
	public static void  invoteToResivePassword(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ResivePasswordActivity.class);  
        context.startActivity(intent); 
	}
	
	
	
	
}
