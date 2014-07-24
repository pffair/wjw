package com.pangff.wjw.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
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
			root.removeView(view);
			root.addView(view, lp);
		} else {
			anchor.removeView(view);
			anchor.addView(view, lp);
		}
	}
	
	private AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(1,0);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}

	public void removeLoadingFrom(final ViewGroup anchor) {
		if(view!=null){
//			AlphaAnimation alphaAnimation = getInAlphaAnimation(800);
//			alphaAnimation.setAnimationListener(new AnimationListener() {
//				@Override
//				public void onAnimationStart(Animation animation) {
//				}
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//					
//				}
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					Log.e("ddd", "ddd");
//					if (anchor == null) {
//						ViewGroup root = (ViewGroup) ((ViewGroup) ((Activity) getContext())
//								.findViewById(android.R.id.content)).getChildAt(0);
//						root.removeView(view);
//					} else {
//						anchor.removeView(view);
//					}
//					view = null;
//				}
//			});
//			view.startAnimation(alphaAnimation);
			if (anchor == null) {
				ViewGroup root = (ViewGroup) ((ViewGroup) ((Activity) getContext())
						.findViewById(android.R.id.content)).getChildAt(0);
				root.removeView(view);
			} else {
				anchor.removeView(view);
			}
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

}
