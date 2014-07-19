package com.pangff.wjw.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.LoginActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.RegistActivity;
import com.pangff.wjw.ResivePasswordActivity;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.util.UserInfoUtil;

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
	
	@AndroidView(R.id.logoutB)
	Button logoutB;
	
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
		logoutB.setOnClickListener(onOneOffClickListener);
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
		case R.id.logoutB:
			logout();
			break;
		default:
			break;
		}
	}
	
	private void logout(){
		try {
			AlertDialog.Builder builder = new Builder(this.getActivity());
			builder.setTitle("退出");
			builder.setMessage("退出将清空登录信息，确定退出?");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					UserInfoUtil.getInstanse().clearUserInfo();
					Intent intent = new Intent(MoreFragment.this.getActivity(),LoginActivity.class);
					MoreFragment.this.getActivity().startActivity(intent);
					MoreFragment.this.getActivity().finish();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.create().show();
		} catch (Exception e) {
			Log.e("AlertDialog", "", e);
		}
	}
	
	private void goRegist(){
		RegistActivity.invoteToRegist((BaseActivity) this.getActivity());
	}
	
}
