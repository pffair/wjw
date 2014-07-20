package com.pangff.wjw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.pangff.wjw.util.ImageUtils;
import com.pangff.wjw.util.ToastUtil;

public class TouchImageView extends ImageView {

  public TouchImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    matrix = new Matrix();
    savedMatrix = new Matrix();
    matrixChange = new Matrix();
  }

  float x_down = 0;
  float y_down = 0;
  PointF start = new PointF();
  PointF mid = new PointF();
  Rect clipRect = null;
  float oldDist = 1f;
  float oldRotation = 0;
  Matrix matrix;
  Matrix matrixChange;
  Matrix savedMatrix;

  private static final int NONE = 0;
  private static final int DRAG = 1;
  private static final int ZOOM = 2;
  int mode = NONE;

  private Paint mPaint;
  private BitmapSaveCallback mBitmapSaveCallback;

  Bitmap gintama;
  private int sideWidth = 0;

  public boolean setImageClipBitmap(Bitmap bm) {
    if (clipRect == null) {
      clipRect = getClipRect();
    }
    if (bm.getWidth() < sideWidth || bm.getHeight() < sideWidth) {
      Bitmap zoomBitmaptoMin = ImageUtils.zoomBitmaptoMin(bm, sideWidth);
      if (zoomBitmaptoMin == null) {
        ToastUtil.show("加载图片失败");
        return false;
      }
      bm.recycle();
      gintama = zoomBitmaptoMin;
    } else {
      gintama = bm;
    }
    matrixChange.postTranslate((getMeasuredWidth() - gintama.getWidth()) / 2.0f,
        (getMeasuredHeight() - gintama.getHeight()) / 2.0f);
    matrix.set(matrixChange);
    setImageBitmap(gintama);
    return true;
  }

  protected void onDraw(Canvas canvas) {
    if (clipRect == null) {
      clipRect = getClipRect();
    }
    if (mPaint == null) {
      mPaint = new Paint();
    }
    canvas.save();
    if (gintama != null) {
      canvas.drawColor(Color.BLACK);
      canvas.drawBitmap(gintama, matrix, null);
      mPaint.reset();
      mPaint.setColor(Color.argb(128, 0, 0, 0));
      canvas.drawRect(0, 0, clipRect.left, getMeasuredHeight(), mPaint);
      canvas.drawRect(clipRect.left, 0, clipRect.right, clipRect.top, mPaint);
      canvas.drawRect(clipRect.left, clipRect.bottom, clipRect.right, getMeasuredHeight(), mPaint);
      canvas.drawRect(clipRect.right, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
      mPaint.reset();
      mPaint.setColor(Color.rgb(229, 229, 229));
      mPaint.setStyle(Style.STROKE);
      canvas.drawRect(clipRect, mPaint);
    }
    canvas.restore();
  }


  public void setSaveCallback(BitmapSaveCallback bitmapSaveCallback) {
    this.mBitmapSaveCallback = bitmapSaveCallback;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (gintama == null) {
      return true;
    }
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN:
        mode = DRAG;
        x_down = event.getX();
        y_down = event.getY();
        savedMatrix.set(matrix);
        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        mode = ZOOM;
        oldDist = spacing(event);
        oldRotation = rotation(event);
        savedMatrix.set(matrix);
        midPoint(mid, event);
        break;
      case MotionEvent.ACTION_MOVE:
        if (mode == ZOOM) {
          matrixChange.set(savedMatrix);
          float newDist = spacing(event);
          float scale = newDist / oldDist;
          matrixChange.postScale(scale, scale, mid.x, mid.y);// 縮放
          limitZoom();
          matrix.set(matrixChange);
          invalidate();
        } else if (mode == DRAG) {
          matrixChange.set(savedMatrix);
          matrixChange.postTranslate(event.getX() - x_down, event.getY() - y_down);// 平移
          limitMove();
          matrix.set(matrixChange);
          invalidate();
        }
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
        mode = NONE;
        break;
    }
    return true;
  }

  public Rect getClipRect() {
    sideWidth = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 1;
    int left = (getMeasuredWidth() - sideWidth) / 2;
    int right = left + sideWidth - 1;
    int top = (getMeasuredHeight() - sideWidth) / 2;
    int bottom = top + sideWidth - 1;
    Rect clipAreaRect = new Rect(left, top, right, bottom);
    return clipAreaRect;
  }

  /**
   * 限制缩放
   */
  private void limitZoom() {
    float[] f = new float[9];
    matrixChange.getValues(f);
    f[0] = Math.min(Math.max(f[0], (float) sideWidth / gintama.getWidth()), 3);
    f[4] = Math.min(Math.max(f[4], (float) sideWidth / gintama.getHeight()), 3);
    f[0] = f[4] = Math.max(f[0], f[4]);
    matrixChange.setValues(f);
    limitMove();
  }

  /**
   * 限制移动
   */
  private void limitMove() {
    if (clipRect == null) {
      clipRect = getClipRect();
    }
    float[] f = new float[9];
    matrixChange.getValues(f);
    // 图片4个顶点的坐标
    float left = f[0] * 0 + f[1] * 0 + f[2];
    float top = f[3] * 0 + f[4] * 0 + f[5];
    float right = f[0] * gintama.getWidth() + f[1] * gintama.getHeight() + f[2];
    float bottom = f[3] * gintama.getWidth() + f[4] * gintama.getHeight() + f[5];
    // 图片现在尺寸
    float width = Math.abs(left - right);
    float height = Math.abs(top - bottom);

    float maxX = clipRect.left;
    float minX = clipRect.right - width;
    float maxY = clipRect.top;
    float minY = clipRect.bottom - height;

    f[2] = Math.min(Math.max(left, minX), maxX);
    f[5] = Math.min(Math.max(top, minY), maxY);

    matrixChange.setValues(f);

  }

  // 触碰两点间距离
  private float spacing(MotionEvent event) {
    float x = event.getX(0) - event.getX(1);
    float y = event.getY(0) - event.getY(1);
    return (float) Math.sqrt(x * x + y * y);
  }

  // 取手势中心点
  private void midPoint(PointF point, MotionEvent event) {
    float x = event.getX(0) + event.getX(1);
    float y = event.getY(0) + event.getY(1);
    point.set(x / 2, y / 2);
  }

  // 取旋转角度
  private float rotation(MotionEvent event) {
    double delta_x = (event.getX(0) - event.getX(1));
    double delta_y = (event.getY(0) - event.getY(1));
    double radians = Math.atan2(delta_y, delta_x);
    return (float) Math.toDegrees(radians);
  }

  /**
   * 裁剪新图片
   * 
   * @return
   */
  public void saveClipImage() {
    Thread t = new Thread() {
      @Override
      public void run() {
        if (mBitmapSaveCallback != null) {
          mBitmapSaveCallback.onSaveStart();
        }
        // Bitmap bitmap =
        // Bitmap.createBitmap(gintama, 0, 0, gintama.getWidth(), gintama.getHeight(), matrix,
        // false);
        //
        // float[] f = new float[9];
        // matrix.getValues(f);
        // float left = f[2];
        // float top = f[5];
        // float minScale = Math.min(500f / sideWidth, 1f);
        // Matrix saveMatrix = new Matrix();
        // saveMatrix.postScale(minScale, minScale);
        // int x = (int) Math.abs(clipRect.left - left);
        // int y = (int) Math.abs(clipRect.top - top);
        // int width = Math.min(sideWidth, bitmap.getWidth() - x);
        // int height = Math.min(sideWidth, bitmap.getHeight() - y);
        // Bitmap saveBitmap = Bitmap.createBitmap(bitmap, x, y, width, height, saveMatrix, false);
        // if (saveBitmap == null) {
        // if (mBitmapSaveCallback != null) {
        // mBitmapSaveCallback.onSaveFailed("保存头像出错");
        // }
        // bitmap.recycle();
        // return;
        // }
        // bitmap.recycle();
        Bitmap saveBitmap = createClipBitmap(gintama, matrix, clipRect, 500f);
        String path = ImageUtils.IMAGE_SAVE_PATH + "clip_head.jpg";
        boolean succeed = false;
        if (saveBitmap != null) {
          succeed = ImageUtils.saveBitmap(saveBitmap, path);
        }
        if (mBitmapSaveCallback != null) {
          if (succeed) {
            mBitmapSaveCallback.onSaveSucceed(path);
          } else {
            mBitmapSaveCallback.onSaveFailed("保存头像出错");
          }
        }

      }
    };
    t.start();
  }

  /**
   * 生成以目标范围变换后的图片
   * 
   * @param bitmap
   * @param matrix
   * @param rect
   * @param target
   * @return
   */
  private Bitmap createClipBitmap(Bitmap bitmap, Matrix matrix, Rect rect, float target) {
    float[] f = new float[9];
    matrix.getValues(f);
    // 变换数据
    float scaleX = f[0];
    float scaleY = f[4];
    float translateX = f[2];
    float translateY = f[5];
    // 寻找屏幕原点在图片上的位置
    // 没有变换前的(0,0)在屏幕的(0，0)
    int startX = rect.left;
    int startY = rect.top;

    int x = (int) ((startX - translateX) / scaleX);
    int y = (int) ((startY - translateY) / scaleY);
    int width = (int) ((rect.right - startX) / scaleX);
    int height = (int) ((rect.bottom - rect.top) / scaleY);

    float minScale = Math.min(500f / Math.max(width, height), 1f);
    Matrix saveMatrix = new Matrix();
    saveMatrix.postScale(minScale, minScale);
    Bitmap clipbitBitmap = null;
    try {
      clipbitBitmap = Bitmap.createBitmap(bitmap, x, y, width, height, saveMatrix, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return clipbitBitmap;
  }


  /**
   * 保存截图回调
   * 
   * @author Vivian
   * 
   */
  interface BitmapSaveCallback {

    public void onSaveStart();

    public void onSaveSucceed(String path);

    public void onSaveFailed(String message);
  }

}
