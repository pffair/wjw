package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class ExchangeSureAvtivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_sure);
	}
	
	public static void  invoteToExchangeSure(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExchangeSureAvtivity.class);  
        context.startActivity(intent); 
	}
}
