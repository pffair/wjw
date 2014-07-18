package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.RegistActivity;
import com.pangff.wjw.ResivePasswordActivity;
import com.pangff.wjw.autowire.AndroidView;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class MoreFragment extends PagerFragment {

	@AndroidView(R.id.registB)
	Button registB;
	
	@AndroidView(R.id.loginPasswordChangeB)
	Button loginB;
	
	@AndroidView(R.id.checkPasswordChangeB)
	Button checkB;
	
	@AndroidView(R.id.payPasswordChangeB)
	Button payB;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_more, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		registB.setOnClickListener(onOneOffClickListener);
		loginB.setOnClickListener(onOneOffClickListener);
		checkB.setOnClickListener(onOneOffClickListener);
		payB.setOnClickListener(onOneOffClickListener);
	}
	
	@Override
	protected void onMyClick(View v) {
		//super.onMyClick(v);
		switch (v.getId()) {
		case R.id.registB:
			goRegist();
			break;
		case R.id.loginPasswordChangeB:
			ResivePasswordActivity.invoteToResivePassword((BaseActivity) this.getActivity());
			break;
		case R.id.checkPasswordChangeB:
			ResivePasswordActivity.invoteToResivePassword((BaseActivity) this.getActivity());
			break;
		case R.id.payPasswordChangeB:
			ResivePasswordActivity.invoteToResivePassword((BaseActivity) this.getActivity());
			break;

		default:
			break;
		}
	}
	
	private void goRegist(){
		RegistActivity.invoteToRegist((BaseActivity) this.getActivity());
	}
	
	

	
}
