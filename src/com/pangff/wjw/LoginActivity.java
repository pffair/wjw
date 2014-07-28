package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.LoginRequest;
import com.pangff.wjw.model.LoginResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;

public class LoginActivity extends BaseActivity {

	@AndroidView(R.id.userNameE)
	EditText userNameE;

	@AndroidView(R.id.passwordE)
	EditText passwordE;

	@AndroidView(R.id.loginB)
	Button loginB;


	@AndroidView(R.id.forgetPwdT)
	TextView forgetPwdT;

	
	public static final String METHOD_LOGIN = "login";
	String userName;
	String passord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginB.setOnClickListener(onOneOffClickListener);
		forgetPwdT.setOnClickListener(onOneOffClickListener);
		
		//userNameE.setText("ATM888888");
		//passwordE.setText("123456");
	}

	@Override
	protected void onMyClick(View v) {
		switch (v.getId()) {
		case R.id.loginB:
			doLogin();
			break;
		case R.id.forgetPwdT:

			break;
		default:
			break;
		}
	}
	
	private void doLogin(){

		
		this.userName = userNameE.getText().toString();
		this.passord = passwordE.getText().toString();
		String xml = new LoginRequest().getParams(METHOD_LOGIN,userName,passord);
		new HttpRequest<LoginResponse>().postDataXml(METHOD_LOGIN, xml, this,LoginResponse.class,false);
	}
	
	public static void invotoLogin(BaseActivity context){
		Intent intent = new Intent(context,LoginActivity.class);
		context.startActivity(intent);
		context.finish();
	}
	
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_LOGIN)){
			LoginResponse loginResponse = (LoginResponse) result;
			if(loginResponse.body.returns.equals(ResponseState.SUCCESS)){
				MainActivity.invoteToMain(this);
				finish();
				UserInfoUtil.getInstanse().saveLoginInfo(loginResponse.userid, passord, userName);
			}else{
				ToastUtil.show(loginResponse.body.message);
			}
		}
	}
}
