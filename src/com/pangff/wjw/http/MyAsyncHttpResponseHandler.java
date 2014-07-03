package com.pangff.wjw.http;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.pangff.wjw.util.LogUtil;

public class MyAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler {
	ResponseCallBack<T> callBack;
	Class<T> t;
	int responseType = 0;
	public static final int RESPONSE_TYPE_JSON = 1;
	public static final int RESPONSE_TYPE_XML = 2;

	public MyAsyncHttpResponseHandler(Class<T> t, ResponseCallBack<T> callBack,
			int type) {
		this.t = t;
		this.responseType = type;
		this.callBack = callBack;
	}

	@Override
	public void onFailure(Throwable error, String content) {
		LogUtil.error("请求失败:" + error);
		callBack.onFailure(content);
	}

	@Override
	public void onStart() {
		callBack.onStart();
	}

	@Override
	public void onSuccess(String content) {
		try {
			callBack.onSuccess(parseResponse(content));
		} catch (Throwable e) {
			LogUtil.error("解析错误:" + content);
			e.printStackTrace();
		}
	}

	protected Object parseResponse(String res) throws Throwable {
		if (responseType == RESPONSE_TYPE_JSON) {
			return JSONObject.parseObject(res, t);
		} else {
			return res;
		}
	}

}
