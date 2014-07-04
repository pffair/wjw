package com.pangff.wjw.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.util.LogUtil;

public class HttpRequest<T> {

	String baseUrl = "http://tel.buyunnet.net/tel.aspx?data=";
	
	private final AsyncHttpClient client;
	int type;

	public HttpRequest(int type) {
		client = new AsyncHttpClient();
		this.type = type;
	}

	public void delete(String url, ResponseCallBack callBack, Class<T> cls) {
		client.delete(url, new MyAsyncHttpResponseHandler<T>(cls, callBack,type));
	}

	public void put(String url, RequestParams params,
			ResponseCallBack callBack, Class<T> cls) {
		client.put(url, params,
				new MyAsyncHttpResponseHandler<T>(cls, callBack,type));
	}

	public void get(String url, RequestParams params,
			ResponseCallBack callBack, Class<T> cls) {
		client.get(url, params,
				new MyAsyncHttpResponseHandler<T>(cls, callBack,type));
	}

	public void getWithAbsoluteUrl(String url, RequestParams params,
			ResponseCallBack callBack, Class<T> cls) {
		client.get(url, params,
				new MyAsyncHttpResponseHandler<T>(cls, callBack,type));
	}

	public void post(String url, RequestParams params,
			ResponseCallBack callBack, Class<T> cls) {
		client.post(url, params, new MyAsyncHttpResponseHandler<T>(cls,
				callBack,type));
	}
	
	/**
	 * data xml
	 * @param url
	 * @param xml
	 * @param callBack
	 * @param cls
	 */
	public void postDataXml(String url, String xml,
			ResponseCallBack callBack, Class<T> cls) {
		RequestParams params = new RequestParams();
		params.put("data", xml);
		client.post(url, params, new MyAsyncHttpResponseHandler<T>(cls,
				callBack,type));
	}

	/**
	 * post json
	 * @param url
	 * @param jsonParam
	 * @param callBack
	 * @param cls
	 */
	public void postJson(String url, String jsonParam,
			ResponseCallBack callBack, Class<T> cls) {
		StringEntity entity = null;
		try {
			entity = new StringEntity(jsonParam, "utf-8");
			AsyncHttpClient client = new AsyncHttpClient();

			client.post(BaseApplication.self, url, entity, "application/json",
					new MyAsyncHttpResponseHandler<T>(cls, callBack,type));
		} catch (UnsupportedEncodingException e) {
			LogUtil.error("post参数错误");
			e.printStackTrace();
		}
	}
	
	/**
	 * post postXml
	 * @param url
	 * @param xml
	 * @param callBack
	 * @param cls
	 */
	public void postXml(String url, String xml,
			ResponseCallBack callBack, Class<T> cls) {
		StringEntity entity = null;
		try {
			entity = new StringEntity(xml, "utf-8");
			AsyncHttpClient client = new AsyncHttpClient();
			String  contentType = "string/xml;UTF-8";
			client.post(BaseApplication.self, url, entity, contentType,
					new MyAsyncHttpResponseHandler<T>(cls, callBack,type));
		} catch (UnsupportedEncodingException e) {
			LogUtil.error("post参数错误");
			e.printStackTrace();
		}
	}

}
