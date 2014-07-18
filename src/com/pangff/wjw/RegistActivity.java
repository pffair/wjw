package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.RegistRequest;
import com.pangff.wjw.model.RegistResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;

public class RegistActivity extends BaseActivity {
	public static final String METHOD_ZHUCE = "zhuce";
	private static final String[] style = { "A区", "B区"};
	private static final String[] star = { "1星", "2星", "3星" };
	private ArrayAdapter<String> adapterStyle, adapterStar;

	@AndroidView(R.id.textStarT)
	TextView textStarT;

	@AndroidView(R.id.spinnerStyle)
	Spinner spinnerStyle;

	@AndroidView(R.id.spinnerStar)
	Spinner spinnerStar;

	@AndroidView(R.id.sView)
	ScrollView sView;
	
	@AndroidView(R.id.tureNameE)
	EditText tureNameE;
	
	@AndroidView(R.id.vipNameE)
	EditText vipNameE;
	
	@AndroidView(R.id.phoneNumberE)
	EditText phoneNumberE;
	
	@AndroidView(R.id.spreadNumberE)
	EditText spreadNumberE;
	
	@AndroidView(R.id.loginPasswordE)
	EditText loginPasswordE;
	
	@AndroidView(R.id.checkPasswordE)
	EditText checkPasswordE;
	
	@AndroidView(R.id.payPasswordE)
	EditText payPasswordE;
	
	@AndroidView(R.id.aPayPasswordE)
	EditText aPayPasswordE;
	
	@AndroidView(R.id.aPersonNumberE)
	EditText aPersonNumberE;
	
	@AndroidView(R.id.registerB)
	Button registerB;
	
	String userName;
	String password;
	String starS;
	String quS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initConfig();
	}

	private void initConfig() {
		// 将可选内容与ArrayAdapter连接起来
		adapterStyle = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, style);
		adapterStar = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, star);

		// 设置下拉列表的风格
		adapterStyle
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterStar
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinnerStyle.setAdapter(adapterStyle);
		spinnerStar.setAdapter(adapterStar);

		// 添加事件Spinner事件监听
		spinnerStyle.setOnItemSelectedListener(new SpinnerSelectedListener());
		spinnerStar.setOnItemSelectedListener(new SpinnerStarListener());

		// 设置默认值
		spinnerStyle.setVisibility(View.VISIBLE);
		spinnerStar.setVisibility(View.VISIBLE);
	}

	public static void invoteToRegist(BaseActivity context) {
		Intent intent = new Intent();
		intent.setClass(context, RegistActivity.class);
		context.startActivity(intent);
	}
	
	

	class SpinnerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			quS = spinnerStyle.getSelectedItem().toString();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	class SpinnerStarListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			starS = spinnerStar.getSelectedItem().toString();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	private void doRegist(){
		RegistRequest registRequest = new RegistRequest();
		registRequest.userid = UserInfoUtil.getInstanse().getUserId();
		registRequest.body = new RegistRequest.Body();
		registRequest.body.zsname = tureNameE.getText().toString();
		registRequest.body.tel = phoneNumberE.getText().toString();
		registRequest.body.jibie = starS.substring(0, 1);
		registRequest.body.memname = vipNameE.getText().toString();
		registRequest.body.qu = quS.substring(0, 1);
		registRequest.body.tuijian = spreadNumberE.getText().toString();
		
		registRequest.body.dlpass = ParseMD5.parseStrToMd5L16(loginPasswordE.getText().toString());
		registRequest.body.pass2 = ParseMD5.parseStrToMd5L16(checkPasswordE.getText().toString());
		registRequest.body.paypass = ParseMD5.parseStrToMd5L16(payPasswordE.getText().toString());
		registRequest.body.password = ParseMD5.parseStrToMd5L16(aPayPasswordE.getText().toString());
		
		userName = vipNameE.getText().toString();
		password = loginPasswordE.getText().toString();
		
		String xml = registRequest.getParams(METHOD_ZHUCE,registRequest.body);
		new HttpRequest<RegistResponse>().postDataXml(METHOD_ZHUCE, xml, this,RegistResponse.class);
	}
	
	public void myClick(View view){
		if(view.getId()==R.id.registerB){
			doRegist();
		}
	}
	
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_ZHUCE)){
			RegistResponse registResponse = (RegistResponse) result;
			if(registResponse.body.returns.equals(ResponseState.SUCCESS)){
				ToastUtil.show("注册成功,账号为:"+registResponse.body.message);
				registerB.setText("注册成功:["+registResponse.body.message+"]");
			}else{
				ToastUtil.show(registResponse.body.message);
			}
		}
	}
}
