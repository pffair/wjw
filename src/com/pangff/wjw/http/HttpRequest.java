package com.pangff.wjw.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.pangff.wjw.util.LogUtil;
import com.pangff.wjw.util.ParseMD5;

public class HttpRequest<T> {
	
	String baseUrl = "http://tel.buyunnet.net/tel.aspx";
	
	private final AsyncHttpClient client;
	
	public static final int TIMEOUT = 10000;

	public HttpRequest() {
		client = new AsyncHttpClient();
		client.setTimeout(TIMEOUT);
	}

	/**
	 * data xml
	 * @param url
	 * @param xml
	 * @param callBack
	 * @param cls
	 */
	public void postDataXml(String method,String xml, String key,ResponseCallBack callBack,Class<T> t,boolean hasCache) {
		RequestParams params = new RequestParams();
		params.put("data", xml);
		LogUtil.error("Request:"+xml);
		client.post(baseUrl, params, new MyAsyncHttpResponseHandler(callBack,method,ParseMD5.parseStrToMd5L16(key+method),t,hasCache));
	}
	
	/**
	 * data xml
	 * @param url
	 * @param xml
	 * @param callBack
	 * @param cls
	 */
	public void postDataXml(String method,String xml,ResponseCallBack callBack,Class<T> t,boolean hasCache) {
		RequestParams params = new RequestParams();
		params.put("data", xml);
		LogUtil.error("Request:"+xml);
		client.post(baseUrl, params, new MyAsyncHttpResponseHandler(callBack,method,method,t,hasCache));
	}

}
