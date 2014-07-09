package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pangff.wjw.R;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class MoreFragment extends PagerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_more, container, false);
	}

	protected void initData() {

	}

}
