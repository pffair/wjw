package com.pangff.wjw.http;



public interface ResponseCallBack {

  public  void onStart();

  public  void onFailure(String method,String errorMsg);

  public  void onSuccess(String method,Object result);
}
