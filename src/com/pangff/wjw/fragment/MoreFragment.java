package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

	@AndroidView(R.id.registT)
	TextView registB;

	@AndroidView(R.id.loginPasswordChangeT)
	TextView loginB;

	@AndroidView(R.id.checkPasswordChangeT)
	TextView checkB;

	@AndroidView(R.id.payPasswordChangeT)
	TextView payB;

	String style;

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
		// super.onMyClick(v);
		switch (v.getId()) {
		case R.id.registT:
			goRegist();
			break;
		case R.id.loginPasswordChangeT: {
			style = "1";
			ResivePasswordActivity.invoteToResivePassword(
					(BaseActivity) this.getActivity(), style);
			break;
		}
		case R.id.checkPasswordChangeT: {
			style = "2";
			ResivePasswordActivity.invoteToResivePassword(
					(BaseActivity) this.getActivity(), style);
			break;
		}
		case R.id.payPasswordChangeT: {
			style = "3";
			ResivePasswordActivity.invoteToResivePassword(
					(BaseActivity) this.getActivity(), style);
			break;
		}

		default:
			break;
		}
	}

	private void goRegist() {
		RegistActivity.invoteToRegist((BaseActivity) this.getActivity());
	}
}
