package com.pangff.wjw;

import android.support.v4.app.FragmentActivity;

import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.event.IEvent;

/**
 * FragmentActivity 基类 全局变量生成
 * 
 * @author pangff
 */
public class BaseFragmentActivity extends FragmentActivity {
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		AndroidAutowire.autowire(getWindow().getDecorView(), this);
		BaseApplication.self.controlBus.register(this);
	}
	
	protected void onEvent(IEvent event){
		
	}

	@Override
	protected void onDestroy() {
		BaseApplication.self.controlBus.unregister(this);
		super.onDestroy();
	}

}
