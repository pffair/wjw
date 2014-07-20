package com.pangff.wjw.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.pangff.wjw.R;
import com.pangff.wjw.util.ImageUtils;
import com.pangff.wjw.util.PhoneUtils;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.view.TouchImageView.BitmapSaveCallback;

public class ClipActivity extends Activity implements OnClickListener, BitmapSaveCallback {
  private static final String TAG = ClipActivity.class.getSimpleName();
  private String mPath;
  private TouchImageView imageLoadView;

  public static String EXTRA_PATH = "PATH";
  public static String SAVE_RESULT = "save_result";
  public static String SAVE_PATH = "save_path";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clip);
    if (initData()) {
      initView();
      initHeadPic();
    }
  }

  private boolean initData() {
    Intent intent = getIntent();
    if (intent == null) {
      finish();
      return false;
    }
    Bundle extras = intent.getExtras();
    if (extras == null) {
      finish();
      return false;
    }
    mPath = intent.getStringExtra(EXTRA_PATH);
    if (StringUtil.isEmpty(mPath)) {
      ToastUtil.show("加载图片失败，请重试");
      finish();
      return false;
    }
    Log.i(TAG, "Clip Picture Path:" + mPath);
    return true;
  }

  private void initView() {
    imageLoadView = (TouchImageView) findViewById(R.id.image_load_view);
    findViewById(R.id.cancel).setOnClickListener(this);
    findViewById(R.id.confirm).setOnClickListener(this);
  }

  private void initHeadPic() {
    int[] displaySize = PhoneUtils.getScreenSizeArray(this);
    int orientation = ImageUtils.getCameraPhotoOrientation(mPath);
    final Bitmap bitmap = ImageUtils.compress(mPath, displaySize[1], displaySize[0], orientation);
    if (bitmap == null) {
      ToastUtil.show("加载图片失败，请重试");
      finish();
    } else {
      imageLoadView.post(new Runnable() {

        @Override
        public void run() {
          if (!imageLoadView.setImageClipBitmap(bitmap)) {
            ToastUtil.show("加载图片失败，请重试");
            ClipActivity.this.finish();
          }
        }
      });
      imageLoadView.setSaveCallback(this);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cancel:
        finish();
        break;
      case R.id.confirm:
        imageLoadView.saveClipImage();
        break;
      default:
        break;
    }
  }

  @Override
  public void onSaveStart() {
    Log.i(TAG, "开始保存图片");
  }

  @Override
  public void onSaveSucceed(String path) {
    Log.i(TAG, "保存图片成功：" + path);
    Intent intent = new Intent();
    intent.putExtra(SAVE_RESULT, true);
    intent.putExtra(SAVE_PATH, path);
    setResult(RESULT_OK, intent);
    this.finish();
  }

  @Override
  public void onSaveFailed(String message) {
    Log.i(TAG, "保存图片失败：" + message);
    Intent intent = new Intent();
    intent.putExtra(SAVE_RESULT, false);
    setResult(RESULT_OK, intent);
    this.finish();
  }
}
