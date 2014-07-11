package com.pangff.wjw.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.R;
import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.model.Img;
import com.pangff.wjw.model.WithdrawalsDetailResponse.Body.WithdrawalsDetail;
import com.pangff.wjw.view.MImageView;

public class WithDrawalsDetailAdapter extends BaseAdapter {

	List<WithdrawalsDetail> withdrawalsDetailList = new ArrayList<WithdrawalsDetail>();
	LayoutInflater mInflater;

	public WithDrawalsDetailAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void refresh(List<WithdrawalsDetail> list){
		withdrawalsDetailList.clear();
		withdrawalsDetailList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return withdrawalsDetailList.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.list_item_withdrawals_detail, null);
			holder = new ViewHolder(convertView);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		WithdrawalsDetail withdrawalsDetail = withdrawalsDetailList.get(position);
		holder.amountT.setText(withdrawalsDetail.jin);
		holder.timeT.setText(withdrawalsDetail.riqi);
		holder.feeT.setText(withdrawalsDetail.sxf);
		holder.bankT.setText(withdrawalsDetail.bank);
		return convertView;
	}
	
	static class ViewHolder{
		public ViewHolder(View root){
			AndroidAutowire.autowire(root, this);
		}
		
		@AndroidView(R.id.amountT)
		TextView amountT;
		
		@AndroidView(R.id.timeT)
		TextView timeT;
		
		@AndroidView(R.id.feeT)
		TextView feeT;
		
		@AndroidView(R.id.bankT)
		TextView bankT;
	}

}
