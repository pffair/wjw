package com.pangff.wjw.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.model.Img;
import com.pangff.wjw.util.IOUtils;
import com.pangff.wjw.util.LogUtil;

public class AdvImgDBManager {
	private AdvImgDBHelper msgCenterDBHelper;
	private static SQLiteDatabase db;
	SharedPreferences sharedPreferences;

	/**
	 * 构造函数
	 */
	private AdvImgDBManager() {
		msgCenterDBHelper = new AdvImgDBHelper(BaseApplication.self);
		if (AdvImgDBManager.db == null) {
			AdvImgDBManager.db = msgCenterDBHelper.getWritableDatabase();
		}
		sharedPreferences = BaseApplication.self.getSharedPreferences(
				"daily_message_count", Context.MODE_PRIVATE);
	}

	private static class SingletonHolder {
		static AdvImgDBManager instance = new AdvImgDBManager();
	}

	public static AdvImgDBManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 批量新增消息
	 * 
	 * @param msg
	 */
	public synchronized void addImgs(List<Img> imgList) {
		db.beginTransaction();
		String sql = "insert into adv_img(id,photoUrl) values(?,?)";
		for (int i = 0; i < imgList.size(); i++) {
			LogUtil.error("添加图片:"+imgList.get(i).imgbig);
			AdvImgDBManager.db.execSQL(sql, new Object[] { imgList.get(i).id,
					imgList.get(i).imgs });
		}
		db.setTransactionSuccessful();
	}

	/**
	 * 获取全部图片
	 * @return
	 */
	public List<Img> getAllImgs() {
		List<Img> imgList = new ArrayList<Img>();
		String sql = "SELECT * FROM adv_img";
		Cursor cursor = null;
		try {
			cursor = AdvImgDBManager.db.rawQuery(sql, null);
			LogUtil.error("获取图片:"+cursor.getCount());
			if (null != cursor && cursor.moveToFirst()) {
				do {
					String id = cursor.getString(cursor.getColumnIndex("id"));
					String photoUrl = cursor.getString(cursor.getColumnIndex("photoUrl"));
					Img img = new Img();
					img.imgbig = photoUrl;
					img.id = id;
					LogUtil.error("获取图片:"+img.imgbig);
					if(BaseApplication.self.IMAGE_CACHE.containsKey(photoUrl)){
						imgList.add(img);
						LogUtil.error("获取缓存图片:"+img.imgbig);
					}
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(cursor);
		}
		return imgList;
	}

	/**
	 * 删除消息
	 * @param uid
	 */
	public synchronized void delAllImgs() {
		String sql = "delete from adv_img";
		AdvImgDBManager.db.execSQL(sql);
	}

	/**
	 * 随机获取图片
	 * @return
	 */
	public synchronized Img getRandomImgs() {
		List<Img> imgList = getAllImgs();
		LogUtil.error("开始获取图片:"+imgList.size());
		if(imgList.size()>0){
			int i = (int) (Math.random()*(imgList.size()-1));
			LogUtil.error("获取随机图片:"+i);
			return imgList.get(i);
		}
		return null;
	}

}
