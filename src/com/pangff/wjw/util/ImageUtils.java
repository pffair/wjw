package com.pangff.wjw.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.MediaColumns;

/**
 * 图片工具类
 * 
 * @author Mr.Right
 * 
 */
public class ImageUtils {
  private final static String TAG = ImageUtils.class.getSimpleName();
  public static String IMAGE_SAVE_PATH = PhoneUtils.getSDPath() + "/wjw/photo/";
  public static String CAMERA_SAVE_PATH = PhoneUtils.getSDPath() + "/DCIM/atm/";

  /**
   * Get a file path from a Uri. This will get the the path for Storage Access Framework Documents,
   * as well as the _data field for the MediaStore and other file-based ContentProviders.
   * 
   * @param context The context.
   * @param uri The Uri to query.
   * @author paulburke
   */
  @SuppressLint("NewApi")
  public static String getRealPath(final Context context, final Uri uri) {

    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
      // ExternalStorageProvider
      if (isExternalStorageDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
          return Environment.getExternalStorageDirectory() + "/" + split[1];
        }

        // TODO handle non-primary volumes
      }
      // DownloadsProvider
      else if (isDownloadsDocument(uri)) {

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri =
            ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                Long.valueOf(id));

        return getDataColumn(context, contentUri, null, null);
      }
      // MediaProvider
      else if (isMediaDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
          contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
          contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
          contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        final String selection = "_id=?";
        final String[] selectionArgs = new String[] {split[1]};

        return getDataColumn(context, contentUri, selection, selectionArgs);
      }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {
      return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
      return uri.getPath();
    }

    return null;
  }

  /**
   * Get the value of the data column for this Uri. This is useful for MediaStore Uris, and other
   * file-based ContentProviders.
   * 
   * @param context The context.
   * @param uri The Uri to query.
   * @param selection (Optional) Filter used in the query.
   * @param selectionArgs (Optional) Selection arguments used in the query.
   * @return The value of the _data column, which is typically a file path.
   */
  public static String getDataColumn(Context context, Uri uri, String selection,
      String[] selectionArgs) {

    Cursor cursor = null;
    final String column = "_data";
    final String[] projection = {column};

    try {
      cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
      if (cursor != null && cursor.moveToFirst()) {
        final int column_index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(column_index);
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return null;
  }


  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is ExternalStorageProvider.
   */
  public static boolean isExternalStorageDocument(Uri uri) {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is DownloadsProvider.
   */
  public static boolean isDownloadsDocument(Uri uri) {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is MediaProvider.
   */
  public static boolean isMediaDocument(Uri uri) {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
  }

  /**
   * 获取图片缩略图 只有Android2.1以上版本支持
   * 
   * @param imgName
   * @param kind MediaStore.Images.Thumbnails.MICRO_KIND
   * @return
   */
  @SuppressWarnings("deprecation")
  public static Bitmap loadImgThumbnail(Activity context, String imgName, int kind) {
    Bitmap bitmap = null;

    String[] proj = {BaseColumns._ID, MediaColumns.DISPLAY_NAME};

    Cursor cursor =
        context.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
            MediaColumns.DISPLAY_NAME + "='" + imgName + "'", null, null);

    if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
      ContentResolver crThumb = context.getContentResolver();
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inSampleSize = 1;
      bitmap = Thumbnails.getThumbnail(crThumb, cursor.getInt(0), kind, options);
    }
    return bitmap;
  }

  public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
    Bitmap bitmap = getBitmapByPath(filePath);
    return zoomBitmap(bitmap, w, h);
  }

  /**
   * 获取bitmap
   * 
   * @param filePath
   * @return
   */
  public static Bitmap getBitmapByPath(String filePath) {
    return getBitmapByPath(filePath, null);
  }

  public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
    FileInputStream fis = null;
    Bitmap bitmap = null;
    try {
      File file = new File(filePath);
      fis = new FileInputStream(file);
      bitmap = BitmapFactory.decodeStream(fis, null, opts);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (OutOfMemoryError e) {
      Runtime runtime = Runtime.getRuntime();
      int mb = 1024 * 1024;
      StringBuilder sb = new StringBuilder();
      sb.append(", totalMemory:").append(runtime.totalMemory() / mb).append("mb");
      sb.append(", freeMemory:").append(runtime.freeMemory() / mb).append("mb");
      sb.append(", maxMemory:").append(runtime.maxMemory() / mb).append("mb");
      throw new Error(sb.toString(), e);
    } finally {
      IOUtils.close(fis);
    }
    return bitmap;
  }

  /**
   * 放大缩小图片
   * 
   * @param bitmap
   * @param w
   * @param h
   * @return
   */
  public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
    Bitmap newbmp = null;
    if (bitmap != null) {
      int width = bitmap.getWidth();
      int height = bitmap.getHeight();
      Matrix matrix = new Matrix();
      float scaleWidht = ((float) w / width);
      float scaleHeight = ((float) h / height);
      matrix.postScale(scaleWidht, scaleHeight);
      newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
    return newbmp;
  }

  /**
   * 加载适合图片
   * 
   * @param photoPtah 图片路径
   * @param reqWidth 需求宽度
   * @param reqHeight 需求高度
   * @param degree 旋转角度
   * @return
   */
  public static Bitmap compressBitmapFromFile(String photoPtah, int reqWidth, int reqHeight,
      int degree, String filePath) {
    // BitmapFactory.Options options = new BitmapFactory.Options();
    // options.inJustDecodeBounds = true;
    // BitmapFactory.decodeFile(photoPtah, options);
    // options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
    // options.inJustDecodeBounds = false;
    // Bitmap bm = rotateImage(degree, BitmapFactory.decodeFile(photoPtah, options));
    Bitmap bm = compress(photoPtah, reqWidth, reqHeight, degree);
    return saveBitmap(bm, filePath) ? bm : null;
  }


  public static Bitmap compress(String photoPath, int reqWidth, int reqHeight, int degree) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(photoPath, options);
    if (options.outHeight <= 0 || options.outWidth <= 0) {
      return null;
    }
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqWidth);
    options.inJustDecodeBounds = false;
    Bitmap bm = rotateImage(degree, BitmapFactory.decodeFile(photoPath, options));
    return bm;
  }

  /**
   * 重新计算大小
   * 
   * @param options
   * @param reqWidth
   * @param reqHeight
   * @return
   */
  public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    int height = options.outHeight;
    int width = options.outWidth;
    int inSampleSize = 1;
    // Log4JHSS.i(TAG, String.format("Original Photo size :[Height:%d][Width:%d]", height, width));
    if (height > reqHeight || width > reqWidth) {
      if (width > height) {
        inSampleSize = Math.round((float) height / (float) reqHeight);
      } else {
        inSampleSize = Math.round((float) width / (float) reqWidth);
      }
    }
    // Log4JHSS.i(TAG, String.format("InSampleSize:%d;RequsetWidth:%d;RequsetHeight:%d",
    // inSampleSize,
    // reqWidth, reqHeight));
    return inSampleSize;
  }

  /**
   * 获取相片旋转角度
   * 
   * @param imagePath
   * @return
   */
  public static int getCameraPhotoOrientation(String imagePath) {
    int rotate = 0;
    try {
      File imageFile = new File(imagePath);
      ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
      int orientation =
          exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_270:
          rotate = 270;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          rotate = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_90:
          rotate = 90;
          break;
      }
      // Log4JHSS.i(TAG, "Exif orientation: " + orientation);
      // Log4JHSS.i(TAG, "Rotate value: " + rotate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rotate;
  }

  /**
   * 旋转图片
   * 
   * @param angle
   * @param bitmap
   * @return Bitmap
   */
  public static Bitmap rotateImage(int angle, Bitmap bitmap) {
    if (bitmap == null) {
      return null;
    }
    // 旋转图片 动作
    Matrix matrix = new Matrix();
    matrix.postRotate(angle);
    // 创建新的图片
    Bitmap resizedBitmap =
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    return resizedBitmap;
  }

  /**
   * 保存压缩图片
   * 
   * @param bitmap
   * @param path
   * @return
   */
  public static boolean saveBitmap(Bitmap bitmap, String path) {
    // Log4JHSS.i(TAG, "Save Compress Image File:" + path);
    boolean isSucceed = false;
    FileOutputStream fos = null;
    ByteArrayOutputStream bos = null;
    try {
      File f = new File(path);
      if (!f.getParentFile().exists()) {
        f.getParentFile().mkdirs();
      }
      if (f.exists()) {
        f.delete();
      }
      f.createNewFile();
      bos = new ByteArrayOutputStream();
      bitmap.compress(CompressFormat.JPEG, 75, bos);
      byte[] bitmapdata = bos.toByteArray();
      fos = new FileOutputStream(f);
      fos.write(bitmapdata);
      isSucceed = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtils.close(fos);
      IOUtils.close(bos);
    }
    return isSucceed;
  }

  public static int[] reCaculate(int viewWidth, int viewHeight, int imageWidth, int imageHeight) {
    int width = imageWidth;
    int height = imageHeight;
    float scaleWidth = imageWidth / viewWidth;
    float scaleHeight = imageHeight / viewHeight;
    float scale = Math.max(Math.max(scaleWidth, scaleHeight), 1);
    return new int[] {(int) (width / scale), (int) (height / scale)};
  }


  /**
   * 按照比例裁剪图片
   * 
   * @param bitmap
   * @param widthUnit
   * @param heightUnit
   * @return
   */
  public static Bitmap clipImage(Bitmap bitmap, float widthUnit, float heightUnit) {
    float heightScale = heightUnit / bitmap.getHeight();
    float widthScale = widthUnit / bitmap.getWidth();
    float scale = Math.max(heightScale, widthScale);
    int clipHeight = (int) (heightUnit / scale);
    int clipWidth = (int) (widthUnit / scale);
    Matrix matrix = new Matrix();
    Bitmap clipBitmap =
        Bitmap.createBitmap(bitmap, (bitmap.getWidth() - clipWidth) / 2,
            (bitmap.getHeight() - clipHeight) / 2, clipWidth, clipHeight, matrix, true);
    return clipBitmap;
  }

  /**
   * 转化为流
   * 
   * @param bm
   * @return
   */
  public static byte[] bitmap2Bytes(Bitmap bm) {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

    return baos.toByteArray();
  }

  public static Bitmap zoomBitmaptoMin(Bitmap bitmap, int minSize) {
    Bitmap newbmp = null;
    if (bitmap != null) {
      int width = bitmap.getWidth();
      int height = bitmap.getHeight();
      Matrix matrix = new Matrix();
      float scaleWidht = ((float) minSize / width);
      float scaleHeight = ((float) minSize / height);
      float scale = Math.max(scaleWidht, scaleHeight);
      matrix.postScale(scale, scale);
      newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
    return newbmp;
  }

}
