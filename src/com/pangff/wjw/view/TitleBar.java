package com.pangff.wjw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.util.StringUtil;

public class TitleBar extends RelativeLayout{
	
	@AndroidView(R.id.backI)
	private ImageView backI;
	
	@AndroidView(R.id.titleT)
	private TextView titleT;
	
	@AndroidView(R.id.rightT)
	public TextView rightT;

	private BaseActivity activity;
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (BaseActivity) context;
		LayoutInflater.from(context).inflate(R.layout.common_titlebar, this);
		AndroidAutowire.autowire(findViewById(R.id.titleBarRootView), this);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TitleBar);
		boolean canBack = a.getBoolean(R.styleable.TitleBar_canBack, false);
		String title = a.getString(R.styleable.TitleBar_title);
		String rightText = a.getString(R.styleable.TitleBar_rightText);
		if(canBack){
			backI.setVisibility(View.VISIBLE);
		}else{
			backI.setVisibility(View.GONE);
		}
		if(!StringUtil.isEmpty(title)){
			titleT.setVisibility(View.VISIBLE);
			titleT.setText(title);
		}else{
			titleT.setVisibility(View.GONE);
		}
		if(!StringUtil.isEmpty(rightText)){
			rightT.setVisibility(View.VISIBLE);
			rightT.setText(rightText);
		}else{
			rightT.setVisibility(View.GONE);
		}
		a.recycle();  
		
		backI.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}
	
	
	
	
}
