package com.pangff.wjw.view;

import android.content.DialogInterface;
import android.util.SparseArray;

/**
 * This class allows a single click and prevents multiple clicks on the same button in rapid
 * succession. Setting unclickable is not enough because click events may still be queued up.
 * 
 * Override onOneClick() to handle single clicks. Call reset() when you want to accept another
 * click.
 */
public abstract class OnOneOffDialogClickListener implements DialogInterface.OnClickListener {

  private static final int DEFAULT_RESET_TIMEMILLIS = 3 * 1000;
  private SparseArray<Long> lastClickTimestamps = new SparseArray<Long>();
  private int resetTimeMillis = DEFAULT_RESET_TIMEMILLIS;

  public OnOneOffDialogClickListener() {
    super();
  }

  public OnOneOffDialogClickListener(int resetTimeMillis) {
    super();
    this.resetTimeMillis = resetTimeMillis;
  }

  /**
   * Override onOneClick() instead.
   */
  @Override
  public final void onClick(DialogInterface dialog, int which) {
    if (isClickable(which)) {
      lastClickTimestamps.put(which, System.currentTimeMillis());
      onOneClick(dialog, which);
    }
  }

  private boolean isClickable(int which) {
    Long lastClickTimestamp = lastClickTimestamps.get(which);
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
  public abstract void onOneClick(DialogInterface dialog, int which);


}
