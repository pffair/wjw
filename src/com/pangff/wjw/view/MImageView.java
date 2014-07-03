package com.pangff.wjw.view;

import com.pangff.wjw.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MImageView extends ImageView{
	
	private int defaultId = R.drawable.ic_launcher;

	public MImageView(Context context){
		super(context, null);
	}
	
	public MImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public void setDefaultImageId(int id){
		defaultId = id;
	}

	public int getDefaultImageId(){
		return defaultId;
	}
	
}
