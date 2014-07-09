package com.pangff.wjw;

import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistActivity extends BaseActivity{
	 private static final String[] style={"A","B","C","D","其他"};
	 private static final String[] star={"1星","2星","3星"};
	     private ArrayAdapter<String> adapterStyle,adapterStar;
	     
	     
	     @AndroidView(R.id.textStyleT)
	     TextView textStyle;
	     
	     @AndroidView(R.id.textStarT)
	     TextView textStarT;	
	     
	     @AndroidView(R.id.spinnerStyle)
	     Spinner spinnerStyle;
	     
	     @AndroidView(R.id.spinnerStar)
	     Spinner spinnerStar;
	     
	     @AndroidView(R.id.sView)
	     ScrollView sView;

	     
	     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

				
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
	
	public static void invoteToRegist(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, RegistActivity.class);  
        context.startActivity(intent); 
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener{
		        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		                long arg3) {
		        	textStyle.setText("区域：");
		        }
		        public void onNothingSelected(AdapterView<?> arg0) {
		        }
		    }
	
	class SpinnerStarListener implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	textStarT.setText("星级:");
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
