package com.pangff.wjw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.pangff.wjw.view.TitleBar;

public class AdvDetailActivity extends BaseActivity{
	
	public static final String METHOD_CHAKAN ="chakan";
	public static final String METHOD_SHOUQU = "shouqu";

	@AndroidView(R.id.contentT)
	TextView contentT;
	
	@AndroidView(R.id.bigImg)
	ImageView bigImg;
	
	@AndroidView(R.id.titleT)
	TextView titleT;
	
	@AndroidView(R.id.titleBar)
	TitleBar titleBar;
	
	@AndroidView(R.id.collectionB)
	Button collectionB;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_adv_deatail);
		doDataRequest();
	}
	
	private void doDataRequest(){
		String xml = new AdvDetailRequest().getParams(METHOD_CHAKAN);
		new HttpRequest<AdvDetailResponse>().postDataXml(METHOD_CHAKAN, xml, this,AdvDetailResponse.class);
	}
	
	private void doCollectionRequest(){
		String xml = new CollectRequest().getParams(METHOD_SHOUQU);
		new HttpRequest<CollectResponse>().postDataXml(METHOD_SHOUQU, xml, this,CollectResponse.class);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_CHAKAN)){
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
		contentT.setText(advDetailResponse.body.img.content);
		BaseApplication.self.IMAGE_CACHE.get(advDetailResponse.body.img.imgbig,bigImg);
		titleT.setText(advDetailResponse.body.img.title);
		collectionB.setText(advDetailResponse.body.img.content);
	}
}
