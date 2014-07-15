package com.pangff.wjw.view;

import android.app.Dialog;
import android.view.View;

/**
 * This class allows a single click and prevents multiple clicks on the same button in rapid
 * succession. Setting unclickable is not enough because click events may still be queued up.
 * 
 * Override onOneClick() to handle single clicks. Call reset() when you want to accept another
 * click.
 */
public abstract class OnOneOffClickListenerInDialog extends OnOneOffClickListener {


  private Dialog dialog;

  public void setDialog(Dialog dialog) {
    this.dialog = dialog;
  }

  public Dialog getDialog() {
    return dialog;
  }

  public OnOneOffClickListenerInDialog() {
    super();
  }

  public OnOneOffClickListenerInDialog(int resetTimeMillis) {
    super(resetTimeMillis);
  }

  /**
   * Override onOneClick() instead.
   */
  @Override
  public final void onClick(View v) {
    if (isClickable(v)) {
      lastClickTimestamps.put(v.getId(), System.currentTimeMillis());
      if (dialog == null) {
        throw new IllegalStateException("The dialog must be set.");
      }
      onOneClick(dialog, v);
    }
  }
  
  public final void onOneClick(View v){};

  /**
   * Override this function to handle clicks. reset() must be called after each click for this
   * function to be called again.
   * 
   * @param v
   */
  public abstract void onOneClick(Dialog dialog, View v);


}
