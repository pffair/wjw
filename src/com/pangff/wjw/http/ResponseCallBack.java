package com.pangff.wjw.http;


public abstract class ResponseCallBack<T> {


  public abstract void onStart();

  public abstract void onFailure(String errorMsg);

  public abstract void onSuccess(Object result);
}
