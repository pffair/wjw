package com.pangff.wjw.cache;

import java.io.File;
import java.io.IOException;

import android.util.Log;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.cache.DiskLruCache.Editor;
import com.pangff.wjw.cache.DiskLruCache.Snapshot;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.BaseBean;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.PhoneUtils;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;

/**
 * 文件缓存基类
 * 
 */
public class CacheFile {

	private final static String TAG = "CacheFile";
	/** 文件根目录 */
	public static final String ROOT_FILE_PATH = PhoneUtils.getSDPath();
	/** 缓存根目录 */
	public static final String MNCG_FILE_PATH = CacheFile.ROOT_FILE_PATH
			+ "/wjw";
	/** 图片缓存 */
	public static final String PIC_FILE_PATH = CacheFile.MNCG_FILE_PATH
			+ "/pic";
	/** 聊股缓存文件 **/
	public static final String CACHE_FILE_PATH = CacheFile.MNCG_FILE_PATH
			+ "/cache";

	/**
	 * 文件缓存的超时时间
	 */
	long timeout;

	public CacheFile() {
		this(Long.MAX_VALUE);
	}

	public CacheFile(final long timeout) {
		this.timeout = timeout;
	}

	static DiskLruCache cache;

	/**
	 * 获取文件缓存的工具类对象
	 * 
	 * @return DiskLruCache
	 */
	public synchronized static DiskLruCache getCache() {
		if (CacheFile.cache == null) {
			try {
				// sdcard must be mounted
				CacheFile.cache = DiskLruCache.open(new File(
						CacheFile.CACHE_FILE_PATH), 1, 1, 1024 * 1024 * 8);
			} catch (IOException e) {
				Log.e(CacheFile.TAG, "", e);
			}
		}
		return CacheFile.cache;
	}

	/**
	 * read cache from disk LRU cache by key
	 * 
	 * @param key
	 *            String
	 * @return String
	 */
	public String readCache(final String key) {
		try {
			DiskLruCache cacheUtil = CacheFile.getCache();
			if (cacheUtil == null) {
				return null;
			}
			Snapshot snapshot = cacheUtil.get(key);
			if (snapshot != null
					&& System.currentTimeMillis() - snapshot.getMtime(0) < timeout) {
				return snapshot.getString(0);
			}
		} catch (IOException e) {
			Log.e(CacheFile.TAG, "", e);
		}
		return null;
	}

	public static void saveCache(final String key, final String value) {

		try {
			DiskLruCache cacheUtil = CacheFile.getCache();
			// check null
			if (cacheUtil == null) {
				return;
			}
			// save content
			Editor edit = cacheUtil.edit(key);
			edit.set(0, value);
			edit.commit();
		} catch (IOException e) {
			Log.e(CacheFile.TAG, "", e);
		}
	}

	public static void clearCache(final String key) {

		try {
			DiskLruCache cacheUtil = CacheFile.getCache();
			// check null
			if (cacheUtil == null) {
				return;
			}
			cacheUtil.remove(key);
		} catch (IOException e) {
			Log.e(CacheFile.TAG, "", e);
		}
	}

	/**
	 * 检查图片缓存目录，如果存在的话，创建该目录
	 */
	public static File checkPicFile() {
		String path = CacheFile.PIC_FILE_PATH;
		return CacheFile.checkDirExist(path);
	}

	/**
	 * 检查文件目录，如果存在的话，创建该目录
	 * 
	 * @param path
	 *            文件目录路径
	 * @return File
	 */
	public static File checkDirExist(final String path) {
		File dir = new File(path);
		return CacheFile.checkDirExist(dir);
	}

	public static File checkDirExist(final File dir) {
		if (!dir.exists() && dir.mkdirs()) {
			Log.d(CacheFile.TAG,
					"failed to access dir: " + dir.getAbsolutePath());
		}
		return dir;
	}

	/**
	 * 读取缓存对象
	 * 
	 * @param key
	 *            String
	 * @param cls
	 *            Class<T>
	 * @param userSpecial
	 *            是否用户相关的
	 * @return T
	 */
	public <T extends BaseBean> T getCacheBaseBean(final String key,
			final Class<T> cls, final boolean userSpecial) {
		try {
			String realKey = userSpecial ? CacheFile
					.getUserSpecialKey(key, cls) : CacheFile.getCommonKey(key,
					cls);
			String jsonStr = readCache(ParseMD5.parseStrToMd5L32(realKey));
			if (jsonStr != null) {
				T info = XStreamTranslator.getInstance().toObject(jsonStr, cls);
				return info;
			}
		} catch (Exception e) {
			Log.e(CacheFile.TAG, "key: " + key, e);
		}

		return null;
	}

	/**
	 * 数据对象保存
	 * 
	 * @param info
	 */
	public static <T extends BaseBean> void saveBaseBean(final String key,
			final T info, final boolean userSpecial) {
		BaseApplication.executor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Class<? extends BaseBean> cls = info.getClass();
					String realKey = CacheFile.getRealKey(key, cls, userSpecial);
					String jsonStr = XStreamTranslator.getInstance().toXMLString(info);
					CacheFile.saveCache(ParseMD5.parseStrToMd5L32(realKey), jsonStr);
				} catch (Exception e) {
					Log.e(CacheFile.TAG, "key: " + key, e);
				} catch (Error e) {
					Log.e(CacheFile.TAG, "key: " + key, e);
				}
			}
		});
	}

	/**
	 * 数据对象保存<br>
	 * 
	 * 
	 * @param key
	 *            String
	 * @param cls
	 *            Class
	 * @param jsonText
	 *            String
	 * @param userSpecial
	 *            boolean
	 */
	public static <T extends BaseBean> void saveText(final String key,
			final Class<? extends BaseBean> cls, final String jsonText,
			final boolean userSpecial) {
		try {
			// create real key by userSpecial
			String realKey = CacheFile.getRealKey(key, cls, userSpecial);

			// save to cache file
			CacheFile.saveCache(ParseMD5.parseStrToMd5L32(realKey), jsonText);
		} catch (Exception e) {
			Log.e(CacheFile.TAG, "key: " + key, e);
		}
	}

	/**
	 * 返回由key, cls userSpecial三个属性生成复合的key，使用该key保存对象；
	 * 
	 * @param key
	 *            String
	 * @param cls
	 *            Class
	 * @param userSpecial
	 *            boolean
	 * @return String
	 */
	public static String getRealKey(final String key,
			final Class<? extends BaseBean> cls, final boolean userSpecial) {
		return userSpecial ? CacheFile.getUserSpecialKey(key, cls) : CacheFile
				.getCommonKey(key, cls);
	}

	/**
	 * 获取被多个用户共享的key
	 * 
	 * @param key
	 *            String
	 * @param cls
	 *            Class<T>
	 * @return String
	 */
	private static <T extends BaseBean> String getCommonKey(final String key,
			final Class<T> cls) {
		return key + cls.getSimpleName();
	}

	/**
	 * 获取用户相关的key
	 * 
	 * @param key
	 *            String
	 * @param cls
	 *            Class<T>
	 * @return String
	 */
	public static <T extends BaseBean> String getUserSpecialKey(
			final String key, final Class<T> cls) {
		return key + cls.getSimpleName()
				+ UserInfoUtil.getInstanse().getUserId();
	}
}
