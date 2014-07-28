package com.pangff.wjw;

import android.os.Bundle;

import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.LoginRequest;
import com.pangff.wjw.model.LoginResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;

public class WelcomeActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		if(StringUtil.isEmpty(UserInfoUtil.getInstanse().getUserId())){
			BaseApplication.self.handlerCommon.postDelayed(new Runnable() {
				@Override
				public void run() {
					LoginActivity.invotoLogin(WelcomeActivity.this);
				}
			}, 1000);
		}else{
			doLogin();
		}
	}

	private void doLogin(){
		String username = UserInfoUtil.getInstanse().getUserName();
		String passowrd = UserInfoUtil.getInstanse().getUserPassword();
		String xml = new LoginRequest().getParams(LoginActivity.METHOD_LOGIN,username,passowrd);
		new HttpRequest<LoginResponse>().postDataXml(LoginActivity.METHOD_LOGIN, xml, this,LoginResponse.class,false);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(LoginActivity.METHOD_LOGIN)){
			LoginResponse loginResponse = (LoginResponse) result;
			if(loginResponse.body.returns.equals(ResponseState.SUCCESS)){
				MainActivity.invoteToMain(this);
				finish();
			}else{
				ToastUtil.show("自动登录失败");
				LoginActivity.invotoLogin(WelcomeActivity.this);
			}
		}
	}
	
	@Override
	public void onFailure(String method, String errorMsg) {
		super.onFailure(method, errorMsg);
		ToastUtil.show("自动登录失败");
		LoginActivity.invotoLogin(WelcomeActivity.this);
	}
	
}
