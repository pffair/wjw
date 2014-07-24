package com.pangff.wjw;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.FileNameRuleImageUrl;
import cn.trinea.android.common.service.impl.ImageSDCardCache;
import cn.trinea.android.common.service.impl.ImageSDCardCache.OnImageSDCallbackListener;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;

import com.pangff.wjw.cache.CacheFile;
import com.pangff.wjw.view.MImageView;

import de.greenrobot.event.EventBus;

/**
 * Application 基类 全局变量生成
 * 
 * @author pangff
 */
public class BaseApplication2 extends Application {
	public static BaseApplication2 self = null;
	public EventBus controlBus;
	public Handler handlerCommon = new Handler();
	public static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	public final String TAG_CACHE = "image_sdcard_cache";
	/** cache folder path which be used when saving images **/
	public  String DEFAULT_CACHE_FOLDER = CacheFile.PIC_FILE_PATH;
	/** icon cache **/
	public ImageSDCardCache IMAGE_CACHE;


	@Override
	public void onCreate() {
		super.onCreate();
		self = this;
		controlBus = new EventBus();
		IMAGE_CACHE = new ImageSDCardCache();
		initImageCache();
	}

	private void initImageCache() {
		IMAGE_CACHE.initData(this, TAG_CACHE);
		IMAGE_CACHE.setContext(this);
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		OnImageSDCallbackListener imageCallBack = new OnImageSDCallbackListener() {

			@Override
			public void onPreGet(String imageUrl, View view) {
				// Log.e(TAG_CACHE, "pre get image");
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null && view instanceof MImageView) {
					((MImageView) view).setImageResource(((MImageView) view)
							.getDefaultImageId());
				}
			}

			@Override
			public void onGetSuccess(String imageUrl, String imagePath,
					View view, boolean isInCache) {
				 ImageView imageView = (ImageView)view;
				   	if(imageView.getVisibility()==View.VISIBLE){
				   	// avoid oom caused by bitmap size exceeds VM budget
		                BitmapFactory.Options option = new BitmapFactory.Options();
		                option.inSampleSize = getImageScale(imagePath);
		                Bitmap bm = BitmapFactory.decodeFile(imagePath, option);
		                if (bm != null) {
		                    imageView.setImageBitmap(bm);

		                    // first time show with animation
		                    if (!isInCache) {
		                        imageView.startAnimation(getInAlphaAnimation(2000));
		                    }
		                    imageView.setScaleType(ScaleType.FIT_CENTER);
		                }
				   	}
			}

			@Override
			public void onGetFailed(String imageUrl, String imagePath,
					View view, FailedReason failedReason) {
				// TODO Auto-generated method stub
				Log.e(TAG_CACHE,
						new StringBuilder(128).append("get image ")
								.append(imageUrl)
								.append(" error, failed type is: ")
								.append(failedReason.getFailedType())
								.append(", failed reason is: ")
								.append(failedReason.getCause().getMessage())
								.toString());
			}
		};
		IMAGE_CACHE.setOnImageSDCallbackListener(imageCallBack);
		 IMAGE_CACHE.setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<String>());
	        IMAGE_CACHE.setFileNameRule(new FileNameRuleImageUrl());

	        IMAGE_CACHE.setHttpReadTimeOut(10000);
	        IMAGE_CACHE.setOpenWaitingQueue(true);
		IMAGE_CACHE.setValidTime(-1);
	}
	
	private static int   IMAGE_MAX_WIDTH  = 480;
    private static int   IMAGE_MAX_HEIGHT = 960;
	 /**
     * scale image to fixed height and weight
     * 
     * @param imagePath
     * @return
     */
    private static int getImageScale(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        // set inJustDecodeBounds to true, allowing the caller to query the bitmap info without having to allocate the
        // memory for its pixels.
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, option);

        int scale = 1;
        while (option.outWidth / scale >= IMAGE_MAX_WIDTH || option.outHeight / scale >= IMAGE_MAX_HEIGHT) {
            scale *= 2;
        }
        return scale;
    }

	private AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}

	public void beforeExist() {
		IMAGE_CACHE.saveDataToDb(this, TAG_CACHE);
	}
}
