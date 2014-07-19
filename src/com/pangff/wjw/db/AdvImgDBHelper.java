package com.pangff.wjw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 消息中心相关库
 * 创建于v5.0
 * @author pangff
 */
public class AdvImgDBHelper extends SQLiteOpenHelper {

  public final static String DATABASE_NAME = "adv_img.db";

  static final String SQL_CREATE_MSGCENTER = "CREATE TABLE IF NOT EXISTS  adv_img ("
      + "_id integer PRIMARY key AUTOINCREMENT,id varchar(100),photoUrl text)";

  public AdvImgDBHelper(Context context) {
    super(context, DATABASE_NAME, null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_MSGCENTER);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d("SQLiteOpenHelper", "onUpgrade msgcenter");
    db.execSQL("drop table if exists msgcenter");
    db.execSQL(SQL_CREATE_MSGCENTER);
  }
}
