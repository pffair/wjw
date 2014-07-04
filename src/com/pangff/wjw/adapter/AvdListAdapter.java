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
import com.pangff.wjw.model.Adv;
import com.pangff.wjw.model.Img;
import com.pangff.wjw.view.MImageView;

public class AvdListAdapter extends BaseAdapter {

	List<Img> advList = new ArrayList<Img>();
	LayoutInflater mInflater;

	public AvdListAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void refresh(List<Img> list){
		advList.clear();
		advList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return advList.size();
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
			convertView = mInflater.inflate(R.layout.list_item_avd, null);
			holder = new ViewHolder(convertView);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Img img = advList.get(position);
		BaseApplication.self.IMAGE_CACHE.get(img.imgs,holder.picImage);
		holder.picImage.setDefaultImageId(R.drawable.ic_launcher);
		holder.display.setText(img.title);
		return convertView;
	}
	
	static class ViewHolder{
		public ViewHolder(View root){
			AndroidAutowire.autowire(root, this);
		}
		
		@AndroidView(R.id.picImage)
		MImageView picImage;
		
		@AndroidView(R.id.display)
		TextView display;
	}

}
