package com.pangff.wjw.http;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.pangff.wjw.cache.CacheFile;
import com.pangff.wjw.util.LogUtil;
import com.pangff.wjw.util.PhoneUtils;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.XStreamTranslator;

public class MyAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler {
	ResponseCallBack callBack;
	String method;
	Class<T> cls;
	boolean hasCache;
	String key;
	public MyAsyncHttpResponseHandler(ResponseCallBack callBack,String method,String key,Class<T> cls,boolean hasCache) {
		this.callBack = callBack;
		this.method = method;
		this.cls = cls;
		this.key = key;
		this.hasCache = hasCache;
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
		if(hasCache){
			String content = new CacheFile().readCache(key);
			try {
				if(!StringUtil.isEmpty(content)){
					LogUtil.error("取出缓存:"+content);
					callBack.onSuccess(method,parseResponse(content));
				}
			} catch (Throwable e) {
				LogUtil.error("解析错误:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onSuccess(String content) {
		try {
			LogUtil.debug("content:"+content);
			callBack.onSuccess(method,parseResponse(content));
			
			if(hasCache&&!StringUtil.isEmpty(content)){
				LogUtil.error("保存缓存:"+content);
				CacheFile.saveCache(key, content);
			}
		} catch (Throwable e) {
			LogUtil.error("解析错误:" +   e.getMessage());
			e.printStackTrace();
		}
	}

	protected Object parseResponse(String res) throws Throwable {
		return XStreamTranslator.getInstance().toObject(res, cls);
	}

}
