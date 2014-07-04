package com.pangff.wjw.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class HttpRequest<T> {

	String baseUrl = "http://tel.buyunnet.net/tel.aspx?data=";
	
	private final AsyncHttpClient client;

	public HttpRequest() {
		client = new AsyncHttpClient();
	}

	/**
	 * data xml
	 * @param url
	 * @param xml
	 * @param callBack
	 * @param cls
	 */
	public void postDataXml(String method,String xml, ResponseCallBack callBack,Class<T> t) {
		RequestParams params = new RequestParams();
		params.put("data", xml);
		client.post(baseUrl, params, new MyAsyncHttpResponseHandler(callBack,method,t));
	}

}
