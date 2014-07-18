package com.pangff.wjw;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;

import com.pangff.wjw.cache.CacheFile;
import com.pangff.wjw.view.MImageView;

import de.greenrobot.event.EventBus;

/**
 * Application 基类 全局变量生成
 * 
 * @author pangff
 */
public class BaseApplication extends Application {
	public static BaseApplication self = null;
	public EventBus controlBus;
	public Handler handlerCommon = new Handler();
	public static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	public final String TAG_CACHE = "image_cache";
	/** cache folder path which be used when saving images **/
	public  String DEFAULT_CACHE_FOLDER = CacheFile.PIC_FILE_PATH;
	/** icon cache **/
	public ImageCache IMAGE_CACHE;


	@Override
	public void onCreate() {
		super.onCreate();
		self = this;
		controlBus = new EventBus();
		IMAGE_CACHE = new ImageCache();
		initImageCache();
	}

	private void initImageCache() {
		IMAGE_CACHE.initData(this, TAG_CACHE);
		IMAGE_CACHE.setContext(this);
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage,
					View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
					imageView.setImageBitmap(loadedImage);
					// first time show with animation
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(2000));
					}
				}
			}

			@Override
			public void onPreGet(String imageUrl, View view) {
				// Log.e(TAG_CACHE, "pre get image");
			}

			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage,
					View view, FailedReason failedReason) {
				Log.e(TAG_CACHE,
						new StringBuilder(128).append("get image ")
								.append(imageUrl)
								.append(" error, failed type is: ")
								.append(failedReason.getFailedType())
								.append(", failed reason is: ")
								.append(failedReason.getCause().getMessage())
								.toString());
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null && view instanceof MImageView) {
					((MImageView) view).setImageResource(((MImageView) view)
							.getDefaultImageId());
				}
			}
		};
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
		IMAGE_CACHE
				.setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<Bitmap>());
		IMAGE_CACHE.setHttpReadTimeOut(10000);
		IMAGE_CACHE.setOpenWaitingQueue(true);
		IMAGE_CACHE.setValidTime(-1);
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
