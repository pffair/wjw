package com.pangff.wjw.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pangff.wjw.R;
import com.pangff.wjw.autowire.AndroidAutowire;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.model.RewardDetailResponse.Body.RewardDetail;

public class RewardDetailAdapter extends BaseAdapter {

	
	List<RewardDetail>rewardDetailList = new ArrayList<RewardDetail>();
	LayoutInflater mInflater;

	public RewardDetailAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void refresh(List<RewardDetail> list){
		rewardDetailList.clear();
		rewardDetailList.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rewardDetailList.size();
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
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.list_item_reward_detail, null);
			holder = new ViewHolder(convertView);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		RewardDetail rewardDetail = rewardDetailList.get(position);
		holder.rewardamountT.setText(rewardDetail.jin);
		holder.rewardStyleT.setText(rewardDetail.leixing);
		holder.timeT.setText(rewardDetail.riqi);
		return convertView;
	}

	static class ViewHolder{
		public ViewHolder(View root){
			AndroidAutowire.autowire(root, this);
		}
		
		@AndroidView(R.id.rewardamountT)
		TextView rewardamountT;
		
		@AndroidView(R.id.rewardStyleT)
		TextView rewardStyleT;
				
		@AndroidView(R.id.timeT)
		TextView timeT;
	}
}
