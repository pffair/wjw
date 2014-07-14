package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.AdvDetailRequest;
import com.pangff.wjw.model.AdvDetailResponse;
import com.pangff.wjw.model.CollectRequest;
import com.pangff.wjw.model.CollectResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.view.LoadingView;
import com.pangff.wjw.view.TitleBar;

public class AdvDetailActivity extends BaseActivity{
	
	public static final String METHOD_CHAKAN ="chakan";
	public static final String METHOD_SHOUQU = "shouqu";

	@AndroidView(R.id.contentT)
	TextView contentT;
	
	@AndroidView(R.id.bigImg)
	ImageView bigImg;
	
	@AndroidView(R.id.contentTitleT)
	TextView contentTitleT;
	
	@AndroidView(R.id.titleBar)
	TitleBar titleBar;
	
	@AndroidView(R.id.collectionB)
	Button collectionB;
	
	String id;
	
	LoadingView listLoadingView;
	
	
	@AndroidView(R.id.contentFrame)
	FrameLayout contentFrame;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_adv_deatail);
		initData();
		initView();
		doDataRequest();
	}
	
	private void initView(){
		titleBar.rightT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doCollectionRequest();
			}
		});
	}
	
	private void initData(){
		id = this.getIntent().getStringExtra("id");
	}
	
	private void doDataRequest(){
		listLoadingView = new LoadingView(this);
		listLoadingView.addLoadingTo(contentFrame);
		String xml = new AdvDetailRequest().getParams(METHOD_CHAKAN,id);
		new HttpRequest<AdvDetailResponse>().postDataXml(METHOD_CHAKAN, xml, this,AdvDetailResponse.class);
	}
	
	private void doCollectionRequest(){
		String xml = new CollectRequest().getParams(METHOD_SHOUQU,id);
		new HttpRequest<CollectResponse>().postDataXml(METHOD_SHOUQU, xml, this,CollectResponse.class);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_CHAKAN)){
			listLoadingView.removeLoadingFrom(contentFrame);
			AdvDetailResponse advDetailResponse = (AdvDetailResponse) result;
			if(advDetailResponse!=null){
				setData(advDetailResponse);
			}
		}else if(method.equals(METHOD_SHOUQU)){
			CollectResponse collectResponse = (CollectResponse) result;
			if(collectResponse!=null && collectResponse.body.returns.equals(ResponseState.SUCCESS)){
				ToastUtil.show("收取成功");
			}else{
				ToastUtil.show(collectResponse.body.message);
			}
		}
	}
	
	public void myClick(View view){
		if(view.getId() == R.id.collectionB){
			doCollectionRequest();
		}
	}
	
	private void setData(AdvDetailResponse advDetailResponse){
		Log.e("ddddd", "advDetailResponse.body.img.title:"+advDetailResponse.body.img.title);
		Log.e("ddddd", "advDetailResponse.body.img.content:"+advDetailResponse.body.img.content);
		contentT.setText(advDetailResponse.body.img.content);
		BaseApplication.self.IMAGE_CACHE.get(advDetailResponse.body.img.imgbig,bigImg);
		contentTitleT.setText(advDetailResponse.body.img.title);
		contentT.setText(advDetailResponse.body.img.content);
	}
	
	public static void invotoToAdvDetail(BaseActivity activity,String id){
		Intent intent = new Intent(activity,AdvDetailActivity.class);
		intent.putExtra("id", id);
		activity.startActivity(intent);
	}
}
