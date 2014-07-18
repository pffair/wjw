package com.pangff.wjw.http;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.pangff.wjw.util.LogUtil;
import com.pangff.wjw.util.PhoneUtils;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.XStreamTranslator;

public class MyAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler {
	ResponseCallBack callBack;
	String method;
	Class<T> cls;
	
	public MyAsyncHttpResponseHandler(ResponseCallBack callBack,String method,Class<T> cls) {
		this.callBack = callBack;
		this.method = method;
		this.cls = cls;
	}

	@Override
	public void onFailure(Throwable error, String content) {
		LogUtil.error("请求失败:" + error);
		try {
			if(PhoneUtils.isNetAvailable()){
				ToastUtil.show("网络异常，请稍后再试");
				callBack.onFailure(method,content);
			}else{
				ToastUtil.show("没有网络，请检查网络连接");
				callBack.onFailure(method,content);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStart() {
		callBack.onStartRequest();
	}

	@Override
	public void onSuccess(String content) {
		try {
			LogUtil.debug("content:"+content);
			callBack.onSuccess(method,parseResponse(content));
		} catch (Throwable e) {
			LogUtil.error("解析错误:" + content);
			e.printStackTrace();
		}
	}

	protected Object parseResponse(String res) throws Throwable {
		LogUtil.debug("contentdd:"+res);
		return XStreamTranslator.getInstance().toObject(res, cls);
	}

}
