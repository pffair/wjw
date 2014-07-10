/*
 * Copyright (C) 2012 Jake Wharton
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pangff.wjw.vindicator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.pangff.wjw.R;
import com.pangff.wjw.util.PhoneUtils;

/**
 * Draws a line for each page. The current page line is colored differently than the unselected page
 * lines.
 */
public class UnderlinePageIndicator extends View implements PageIndicator {
  private static final int INVALID_POINTER = -1;
  private static final int FADE_FRAME_MS = 30;

  private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

  private boolean mFades;
  private int mFadeDelay;
  private int mFadeLength;
  private int mFadeBy;

  private ViewPager mViewPager;
  private ViewPager.OnPageChangeListener mListener;
  private int mScrollState;
  private int mCurrentPage;
  private float mPositionOffset;

  private int mTouchSlop;
  private float mLastMotionX = -1;
  private int mActivePointerId = INVALID_POINTER;
  private boolean mIsDragging;

  private final Runnable mFadeRunnable = new Runnable() {
    @Override
    public void run() {
      if (!mFades) return;

      final int alpha = Math.max(mPaint.getAlpha() - mFadeBy, 0);
      mPaint.setAlpha(alpha);
      invalidate();
      if (alpha > 0) {
        postDelayed(this, FADE_FRAME_MS);
      }
    }
  };

  public UnderlinePageIndicator(Context context) {
    this(context, null);
  }

  public UnderlinePageIndicator(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.vpiUnderlinePageIndicatorStyle);
  }

  public UnderlinePageIndicator(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    if (isInEditMode()) return;

    final Resources res = getResources();


    final ViewConfiguration configuration = ViewConfiguration.get(context);
    mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
  }

  public boolean getFades() {
    return mFades;
  }


  public int getFadeDelay() {
    return mFadeDelay;
  }


  public int getFadeLength() {
    return mFadeLength;
  }

  int left;
  int right;
  int top;
  int bottom;
  float pageWidth;
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (mViewPager == null) {
      return;
    }
    final int count = mViewPager.getAdapter().getCount();
    if (count == 0) {
      return;
    }

    if (mCurrentPage >= count) {
      setCurrentItem(count - 1);
      return;
    }

    final int paddingLeft = getPaddingLeft();
    pageWidth = (getWidth() - paddingLeft - getPaddingRight()) / (1f * count);
    left = (int) (paddingLeft + pageWidth * (mCurrentPage + mPositionOffset));
    right = (int) (left + pageWidth);
    top = getPaddingTop();
    bottom = getHeight() - getPaddingBottom();
    // mPaint.setColor(Color.RED);
    // canvas.drawRect(paddingLeft, top+20,left , bottom, mPaint);
    // canvas.drawRect(right, top+20,paddingLeft+ getWidth(), bottom, mPaint);

    // mPaint.setColor(Color.BLUE);
//    Bitmap tabBL = BitmapFactory.decodeResource(getResources(),  R.drawable.tab_bg);
//    NinePatch ninePatchL = new NinePatch(tabBL, tabBL.getNinePatchChunk(), null);
//    Rect patchL = new Rect((int) paddingLeft, (int) (top), (int) left, (int) bottom);
//    ninePatchL.draw(canvas, patchL);
//
    Bitmap tabBR = BitmapFactory.decodeResource(getResources(),  R.drawable.tab_bg);
    NinePatch ninePatchR = new NinePatch(tabBR, tabBR.getNinePatchChunk(), null);
    Rect patchR =
        new Rect((int) paddingLeft, (int) (top), (int) (paddingLeft + getWidth()), (int) bottom);
    ninePatchR.draw(canvas, patchR);

    Bitmap tabS = BitmapFactory.decodeResource(getResources(), R.drawable.tab_bg_pressed);
    NinePatch ninePatch = new NinePatch(tabS, tabS.getNinePatchChunk(), null);
    Rect rect_patch = new Rect((int) left, (int) top, (int) right, (int) bottom);
    ninePatch.draw(canvas, rect_patch);
    
    drawText(canvas);
  }
  
  public int getFontHeight(){
     FontMetrics fm = mPaint.getFontMetrics();
     return (int) Math.ceil(fm.descent - fm.top) + 2;
  } 

  private void drawText(Canvas canvas) {
    int color = Color.BLACK;
    int sColor = Color.RED;
    mPaint.setTextSize(PhoneUtils.dipToPixels(17));
    for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
      String text = (String) ((FragmentPagerAdapter) mViewPager.getAdapter()).getPageTitle(i);
      if(i==mViewPager.getCurrentItem()){
        mPaint.setColor(sColor);
      }else{
        mPaint.setColor(color);
      }
      int width = (int) mPaint.measureText(text);
      int height = getHeight();
      canvas.drawText(text, i * pageWidth + (pageWidth - width) / 2, top
          + (bottom - top - height) / 2 + height * 2 / 3, mPaint);
    }
  }
  
  boolean hasMsg;
  public void showMsg(boolean hasMsg){
    this.hasMsg = hasMsg;
    invalidate();
  }
  

  public boolean onTouchEvent(MotionEvent ev) {
    if (super.onTouchEvent(ev)) {
      return true;
    }
    if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
      return false;
    }

    final int action = ev.getAction();
    switch (action) {
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if(y>top&&y<bottom){
          for(int i=0;i<mViewPager.getAdapter().getCount();i++){
            if((i+1)<mViewPager.getAdapter().getCount()&&x>pageWidth*i&&x<pageWidth*(i+1)){
              setCurrentItem(i);
              Log.e("ddd", "setCurrentItem:" + i);
              break;
            }else if((i==mViewPager.getAdapter().getCount()-1)&&x>pageWidth*i){
              setCurrentItem(i);
              Log.e("ddd", "setCurrentItem--:" + (mViewPager.getAdapter().getCount()-1));
              break;
            }
          }
        }
        break;
    }

    return true;
  }

  @Override
  public void setViewPager(ViewPager viewPager) {
    if (mViewPager == viewPager) {
      return;
    }
    if (mViewPager != null) {
      // Clear us from the old pager.
      mViewPager.setOnPageChangeListener(null);
    }
    if (viewPager.getAdapter() == null) {
      throw new IllegalStateException("ViewPager does not have adapter instance.");
    }
    mViewPager = viewPager;
    mViewPager.setOnPageChangeListener(this);
    invalidate();
    post(new Runnable() {
      @Override
      public void run() {
        if (mFades) {
          post(mFadeRunnable);
        }
      }
    });
  }

  @Override
  public void setViewPager(ViewPager view, int initialPosition) {
    setViewPager(view);
    setCurrentItem(initialPosition);
  }

  @Override
  public void setCurrentItem(int item) {
    if (mViewPager == null) {
      throw new IllegalStateException("ViewPager has not been bound.");
    }
    if(Math.abs(item-mCurrentPage)==3){
      setCurrentItem(1);
      setCurrentItem(item);
    }
    mViewPager.setCurrentItem(item,true);
    mCurrentPage = item;
    invalidate();
    
  }

  @Override
  public void notifyDataSetChanged() {
    invalidate();
  }

  @Override
  public void onPageScrollStateChanged(int state) {
    mScrollState = state;

    if (mListener != null) {
      mListener.onPageScrollStateChanged(state);
    }
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    mCurrentPage = position;
    mPositionOffset = positionOffset;
    if (mFades) {
      if (positionOffsetPixels > 0) {
        removeCallbacks(mFadeRunnable);
        mPaint.setAlpha(0xFF);
      } else if (mScrollState != ViewPager.SCROLL_STATE_DRAGGING) {
        postDelayed(mFadeRunnable, mFadeDelay);
      }
    }
    invalidate();

    if (mListener != null) {
      mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }
  }

  @Override
  public void onPageSelected(int position) {
    if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
      mCurrentPage = position;
      mPositionOffset = 0;
      invalidate();
      mFadeRunnable.run();
    }
    if (mListener != null) {
      mListener.onPageSelected(position);
    }
    FragmentPagerAdapter adapter = ((FragmentPagerAdapter)mViewPager.getAdapter());
  }

  @Override
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
    mListener = listener;
  }

  @Override
  public void onRestoreInstanceState(Parcelable state) {
    SavedState savedState = (SavedState) state;
    super.onRestoreInstanceState(savedState.getSuperState());
    mCurrentPage = savedState.currentPage;
    requestLayout();
  }

  @Override
  public Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    SavedState savedState = new SavedState(superState);
    savedState.currentPage = mCurrentPage;
    return savedState;
  }

  static class SavedState extends BaseSavedState {
    int currentPage;

    public SavedState(Parcelable superState) {
      super(superState);
    }

    private SavedState(Parcel in) {
      super(in);
      currentPage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      super.writeToParcel(dest, flags);
      dest.writeInt(currentPage);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
      @Override
      public SavedState createFromParcel(Parcel in) {
        return new SavedState(in);
      }

      @Override
      public SavedState[] newArray(int size) {
        return new SavedState[size];
      }
    };
  }
}
