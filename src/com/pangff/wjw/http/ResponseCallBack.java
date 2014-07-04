package com.pangff.wjw.http;


public interface  ResponseCallBack {

  public  void onStart();

  public  void onFailure(String errorMsg);

  public  void onSuccess(Object result);
}
