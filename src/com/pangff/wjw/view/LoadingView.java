package com.pangff.wjw.view;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pangff.wjw.R;

public class LoadingView extends RelativeLayout {
	private View view;

	public LoadingView(Context context) {
		super(context);
		view = ((Activity) context).getLayoutInflater().inflate(
				R.layout.loading, null);
	}

	public void addLoadingTo(ViewGroup anchor) {
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		if (anchor == null) {
			ViewGroup root = (ViewGroup) ((ViewGroup) ((Activity) getContext())
					.findViewById(android.R.id.content)).getChildAt(0);
			root.addView(view, lp);
		} else {
			anchor.addView(view, lp);
		}
	}

	public void removeLoadingFrom(ViewGroup anchor) {
		if (anchor == null) {
			ViewGroup root = (ViewGroup) ((ViewGroup) ((Activity) getContext())
					.findViewById(android.R.id.content)).getChildAt(0);
			root.addView(view);
		} else {
			anchor.removeView(view);
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

}
