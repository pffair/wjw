package com.pangff.wjw;

import com.pangff.wjw.RegistActivity.SpinnerSelectedListener;
import com.pangff.wjw.RegistActivity.SpinnerStarListener;
import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class VipTransferActivity extends BaseActivity{
	
	private static final String[] style = { "钱包", "积分"};
	private ArrayAdapter<String> adapterTransferStyle;
	
	@AndroidView(R.id.transferStyle)
	Spinner transferStyle;
	
	@AndroidView(R.id.transferStyleT)
	TextView transferStyleT;
	
	
	@AndroidView(R.id.transferRegisterB)
	Button transferRegisterB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		initConfig();
		
		transferRegisterB.setOnClickListener(onOneOffClickListener);
	}
	
	@Override
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.transferRegisterB:
			VipTransferSureActivity.invoteToVipTransferSure(this);break;
		}
	}

	private void initConfig() {
		// 将可选内容与ArrayAdapter连接起来
		adapterTransferStyle = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, style);

		// 设置下拉列表的风格
		adapterTransferStyle
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		transferStyle.setAdapter(adapterTransferStyle);

		// 添加事件Spinner事件监听
		transferStyle.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		transferStyle.setVisibility(View.VISIBLE);
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			transferStyleT.setText("转帐类型：");
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public static void  invoteToVipTransfer(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, VipTransferActivity.class);  
        context.startActivity(intent); 
	}


}
