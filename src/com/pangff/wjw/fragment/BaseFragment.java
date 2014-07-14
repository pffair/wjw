package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.http.ResponseCallBack;
import com.pangff.wjw.view.OnOneOffClickListener;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class BaseFragment extends Fragment implements ResponseCallBack {

	private boolean isInit; // 是否可以开始加载数据
	protected OnOneOffClickListener onOneOffClickListener;
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		AndroidAutowire.autowire(view, this);
		super.onViewCreated(view, savedInstanceState);
		BaseApplication.self.controlBus.register(this);
		onOneOffClickListener = new OnOneOffClickListener(500) {
			@Override
			public void onOneClick(View v) {
				onMyClick(v);
			}
		};
		isInit = true;
	}
	
	protected void onMyClick(View v) {
		
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		// 每次切换fragment时调用的方法
		if (isVisibleToUser) {
			showData();
		}
	}

	/**
	 * 初始化数据
	 * 
	 * @author yubin
	 * @date 2014-1-16
	 */
	private void showData() {
		if (isInit) {
			isInit = false;// 加载数据完成
			// 加载各种数据
			initData();
		}
	}

	protected void initData() {

	}

	public void onEvent(IEvent event) {
	}

	@Override
	public void onResume() {
		super.onResume();
		// 判断当前fragment是否显示
		if (getUserVisibleHint()) {
			showData();
		}
	}

	@Override
	public void onDestroyView() {
		BaseApplication.self.controlBus.unregister(this);
		super.onDestroyView();
	}

	@Override
	public void onFailure(String mothod, String errorMsg) {

	}

	@Override
	public void onSuccess(String mothod, Object result) {

	}

	@Override
	public void onStartRequest() {
		// TODO Auto-generated method stub
		
	}

}
