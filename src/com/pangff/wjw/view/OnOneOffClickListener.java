package com.pangff.wjw.view;

import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * This class allows a single click and prevents multiple clicks on the same button in rapid
 * succession. Setting unclickable is not enough because click events may still be queued up.
 * 
 * Override onOneClick() to handle single clicks. Call reset() when you want to accept another
 * click.
 */
public abstract class OnOneOffClickListener implements OnClickListener {

  private static final int DEFAULT_RESET_TIMEMILLIS = 3 * 1000;
  protected SparseArray<Long> lastClickTimestamps = new SparseArray<Long>();
  private int resetTimeMillis = DEFAULT_RESET_TIMEMILLIS;

  public OnOneOffClickListener() {
    super();
  }

  public OnOneOffClickListener(int resetTimeMillis) {
    super();
    this.resetTimeMillis = resetTimeMillis;
  }

  /**
   * Override onOneClick() instead.
   */
  @Override
  public void onClick(View v) {
    if (isClickable(v)) {
      lastClickTimestamps.put(v.getId(), System.currentTimeMillis());
      onOneClick(v);
    }
  }

  protected boolean isClickable(View v) {
    Long lastClickTimestamp = lastClickTimestamps.get(v.getId());
    if (null == lastClickTimestamp) {
      return true;
    }
    long diff = System.currentTimeMillis() - lastClickTimestamp;
    return diff > resetTimeMillis;
  }

  /**
   * Override this function to handle clicks. reset() must be called after each click for this
   * function to be called again.
   * 
   * @param v
   */
  public abstract void onOneClick(View v);


}
