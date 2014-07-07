package com.pangff.wjw;


import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.http.ResponseCallBack;
import com.pangff.wjw.view.OnOneOffClickListener;

import android.app.Activity;
import android.view.View;

/**
 * Activty 基类
 * @author pangff
 */
public class BaseActivity extends Activity implements ResponseCallBack{
	
	protected OnOneOffClickListener onOneOffClickListener;
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		AndroidAutowire.autowire(getWindow().getDecorView(), this);
		BaseApplication.self.controlBus.register(this);
		onOneOffClickListener = new OnOneOffClickListener(500) {
			@Override
			public void onOneClick(View v) {
				onMyClick(v);
			}
		};
	}
	
	protected void onMyClick(View v) {
		
	}
	
	protected void onEvent(IEvent event){
		
	}

	@Override
	protected void onDestroy() {
		BaseApplication.self.controlBus.unregister(this);
		super.onDestroy();
	}

	@Override
	public void onFailure(String method, String errorMsg) {
		
	}

	@Override
	public void onSuccess(String method, Object result) {
		
	}

	@Override
	public void onStartRequest() {
		// TODO Auto-generated method stub
		
	}
	
}
