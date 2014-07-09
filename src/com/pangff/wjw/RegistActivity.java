package com.pangff.wjw;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistActivity extends BaseActivity{
	 private static final String[] style={"A","B","C","D","其他"};
	 private static final String[] star={"1星","2星","3星"};
	     private TextView viewStyle,viewStar ;
	     private Spinner spinnerStyle,spinnerStar;
	     private ArrayAdapter<String> adapterStyle,adapterStar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	
		viewStyle = (TextView) findViewById(R.id.textStyleT);
		spinnerStyle = (Spinner) findViewById(R.id.spinnerStyle);
		
		viewStar=(TextView) findViewById(R.id.textStarT);
		spinnerStar=(Spinner) findViewById(R.id.spinnerStar);
		
		//将可选内容与ArrayAdapter连接起来
		adapterStyle = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,style);
		adapterStar= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,star);
		
		//设置下拉列表的风格
		adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterStar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将adapter 添加到spinner中
		spinnerStyle.setAdapter(adapterStyle);
		spinnerStar.setAdapter(adapterStar);
		
		//添加事件Spinner事件监听  
		spinnerStyle.setOnItemSelectedListener(new SpinnerSelectedListener());
		spinnerStar.setOnItemSelectedListener(new SpinnerStarListener());
		
		//设置默认值
		spinnerStyle.setVisibility(View.VISIBLE);
		spinnerStar.setVisibility(View.VISIBLE);
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener{
		        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		                long arg3) {
		        	viewStyle.setText("区域：");
		        }
		        public void onNothingSelected(AdapterView<?> arg0) {
		        }
		    }
	
	class SpinnerStarListener implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	viewStyle.setText("星级:");
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
