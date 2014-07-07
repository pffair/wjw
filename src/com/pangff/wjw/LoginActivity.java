package com.pangff.wjw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.LoginRequest;
import com.pangff.wjw.model.LoginResponse;

public class LoginActivity extends BaseActivity {

	@AndroidView(R.id.userNameE)
	EditText userNameE;

	@AndroidView(R.id.passwordE)
	EditText passwordE;

	@AndroidView(R.id.loginB)
	Button loginB;

	@AndroidView(R.id.registB)
	Button registB;

	@AndroidView(R.id.forgetPwdT)
	TextView forgetPwdT;

	
	public static final String METHOD_LOGIN = "login";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginB.setOnClickListener(onOneOffClickListener);
		registB.setOnClickListener(onOneOffClickListener);
		forgetPwdT.setOnClickListener(onOneOffClickListener);
	}

	@Override
	protected void onMyClick(View v) {
		switch (v.getId()) {
		case R.id.loginB:
			doLogin();
			break;
		case R.id.registB:
			goRegist();
			break;
		case R.id.forgetPwdT:

			break;
		default:
			break;
		}
	}
	
	private void doLogin(){
		String xml = new LoginRequest().getParams(METHOD_LOGIN,userNameE.getText().toString(),passwordE.getText().toString());
		new HttpRequest<LoginResponse>().postDataXml(METHOD_LOGIN, xml, this,LoginResponse.class);
	}
	
	private void goRegist(){
		
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		
	}

}
