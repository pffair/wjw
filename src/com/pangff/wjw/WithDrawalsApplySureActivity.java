package com.pangff.wjw;



import android.content.Intent;
import android.os.Bundle;

public class WithDrawalsApplySureActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_sure);
	}
	
	public static void  invoteToWithDrawalsApplySure(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplySureActivity.class);  
        context.startActivity(intent); 
	}
}