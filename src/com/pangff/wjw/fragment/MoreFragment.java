package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.RegistActivity;
import com.pangff.wjw.autowire.AndroidView;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class MoreFragment extends PagerFragment {

	@AndroidView(R.id.registB)
	Button registB;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_more, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		registB.setOnClickListener(onOneOffClickListener);
	}
	
	@Override
	protected void onMyClick(View v) {
		super.onMyClick(v);
		switch (v.getId()) {
		case R.id.registB:
			goRegist();
			break;

		default:
			break;
		}
	}
	
	private void goRegist(){
		RegistActivity.invoteToRegist((BaseActivity) this.getActivity());
	}

	protected void initData() {

	}

}
