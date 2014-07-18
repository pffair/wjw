package com.pangff.wjw;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ChangePasswordRequest;
import com.pangff.wjw.model.ChangePasswordResponse;
import com.pangff.wjw.model.LoginRequest;
import com.pangff.wjw.model.LoginResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ResivePasswordActivity extends BaseActivity{
	
	public static final String METHOD_PASS = "pass";
	String oldpassword;
	String newpassword;
	String style;
	
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
		initConfig();
	}
	
	private void initConfig() {
		// TODO Auto-generated method stub
		sureResiveB.setOnClickListener(onOneOffClickListener);
		cancelResiveB.setOnClickListener(onOneOffClickListener);
	}

	@Override
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sureResiveB:
			doRequestCommitPassword();
			;break;
		case R.id.cancelResiveB:
			this.finish();break;
		default :
		break;
		}
	}

	private void doRequestCommitPassword(){
		ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest();
		ChangePasswordRequest.Body body=new ChangePasswordRequest.Body();
	//	body.LX="1";
		body.oldpass=originalPasswordE.getText().toString();
		body.newpass=newPasswordSureE.getText().toString();
		String xml = new ChangePasswordRequest().getParams(METHOD_PASS,changePasswordRequest.body);
		new HttpRequest<ChangePasswordResponse>().postDataXml(METHOD_PASS, xml, this,ChangePasswordResponse.class);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_PASS)){
			ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) result;
			if(changePasswordResponse.body.returns.equals(ResponseState.SUCCESS)){
				Log.i("!!!!!!!!!!!!!!!!!!","!!!!!!!!!!!!!!!!!!");
			}else{
				ToastUtil.show(changePasswordResponse.body.message);
			}
		}
	}
	
	public static void  invoteToResivePassword(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ResivePasswordActivity.class);  
        context.startActivity(intent); 
	}
}
