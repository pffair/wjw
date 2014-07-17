package com.pangff.wjw.adapter;

import java.util.ArrayList;
import java.util.List;

import com.pangff.wjw.R;
import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.model.ExchangeDetailResponse.Body.ExchangeDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExchangeDetailAdapter extends BaseAdapter{

	List<ExchangeDetail>exchangeDetailList = new ArrayList<ExchangeDetail>();
	LayoutInflater mInflater;
	
	
	public ExchangeDetailAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void refresh(List<ExchangeDetail> list){
		exchangeDetailList.clear();
		exchangeDetailList.addAll(list);
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return exchangeDetailList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.list_item_exchange_detail, null);
			holder = new ViewHolder(convertView);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ExchangeDetail exchangeDetail = exchangeDetailList.get(position);
		holder.labelExchangeAmountT.setText(exchangeDetail.jin);
		holder.timeT.setText(exchangeDetail.riqi);
		return convertView;
	}
	
	static class ViewHolder{
		public ViewHolder(View root){
			AndroidAutowire.autowire(root, this);
		}
		@AndroidView(R.id.labelExchangeAmountT)
		TextView labelExchangeAmountT;
		
		@AndroidView(R.id.timeT)
		TextView timeT;
	}
}
