package com.pangff.wjw;



import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExchangeIntegrationActivity extends BaseActivity{
	
	
	@AndroidView(R.id.exchangeB)
	Button exchangeB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_integration);
		
		exchangeB.setOnClickListener(onOneOffClickListener);
		
	}

	
	@Override
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.exchangeB:
			ExchangeSureAvtivity.invoteToExchangeSure(this);break;
		}
	}


	public static void  invoteToExchangeIntegration(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExchangeIntegrationActivity.class);  
        context.startActivity(intent); 
	}
}
