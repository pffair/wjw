package com.pangff.wjw;



import com.pangff.wjw.autowire.AndroidView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WithDrawalsApplySureActivity extends BaseActivity {

	@AndroidView(R.id.sureWithdrawalCashB)
	Button sureWithdrawalCashB;
	
	@AndroidView(R.id.withdrawalCancelB)
	Button withdrawalCancelB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_sure);
		
		sureWithdrawalCashB.setOnClickListener(onOneOffClickListener);
		withdrawalCancelB.setOnClickListener(onOneOffClickListener);
	}
	
	@Override
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sureWithdrawalCashB:
			ApplyCompleteActivity.invoteApplyComplete(this);
		case R.id.withdrawalCancelB:
			
		}
	}




	public static void  invoteToWithDrawalsApplySure(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplySureActivity.class);  
        context.startActivity(intent); 
	}
}