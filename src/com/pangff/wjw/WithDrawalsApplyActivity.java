package com.pangff.wjw;

import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WithDrawalsApplyActivity extends BaseActivity {

	
	@AndroidView(R.id.WithdrawalB)
	Button WithdrawalB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply);
		
		WithdrawalB.setOnClickListener(onOneOffClickListener);
	}

	
	@Override
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.WithdrawalB:
				WithDrawalsApplySureActivity.invoteToWithDrawalsApplySure(this);
			}
	}



	public static void  invoteToWithDrawalsApply(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplyActivity.class);  
        context.startActivity(intent); 
	}
}
