/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.pangff.wjw.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.R;
import com.pangff.wjw.model.TopGallery;
import com.pangff.wjw.view.MImageView;

/**
 * ImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context       context;
    private List<TopGallery> imageIdList = new ArrayList<TopGallery>();
    private boolean       isInfiniteLoop;

    public ImagePagerAdapter(Context context) {
        this.context = context;
        isInfiniteLoop = false;
    }
    
    public void refresh(List<TopGallery> list){
    		imageIdList.clear();
    		imageIdList.addAll(list);
    		notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % imageIdList.size() : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new MImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        TopGallery topGallery = imageIdList.get(getPosition(position));
        holder.imageView.setScaleType(ScaleType.FIT_XY);
        holder.imageView.setDefaultImageId(R.drawable.ic_launcher);
        BaseApplication.self.IMAGE_CACHE.get(topGallery.getUrl(),holder.imageView);
        return view;
    }

    private static class ViewHolder {
    		MImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
