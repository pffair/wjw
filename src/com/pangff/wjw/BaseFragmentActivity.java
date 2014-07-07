package com.pangff.wjw;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.view.OnOneOffClickListener;

/**
 * FragmentActivity 基类 全局变量生成
 * 
 * @author pangff
 */
public class BaseFragmentActivity extends FragmentActivity {

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

	protected void onEvent(IEvent event) {

	}

	@Override
	protected void onDestroy() {
		BaseApplication.self.controlBus.unregister(this);
		super.onDestroy();
	}

}
