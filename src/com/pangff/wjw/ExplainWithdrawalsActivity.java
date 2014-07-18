package com.pangff.wjw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class ExplainWithdrawalsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explain_withdrawals);
	}
	
	public static void  invoteToExplainWithdrawals(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExplainWithdrawalsActivity.class);  
        context.startActivity(intent); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_explain_withdrawals, menu);
		return true;
	}

}
