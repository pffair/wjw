package com.pangff.wjw;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.pangff.wjw.adapter.PagerFragmentAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.vindicator.UnderlinePageIndicator;

public class MainActivity extends BaseActivity {
	private long mExitTime;
	private PagerFragmentAdapter pagerAdapter;

	@AndroidView(R.id.pager)
	private ViewPager pager;
	
	@AndroidView(R.id.indicator)
	  private UnderlinePageIndicator indicator;

	Configuration CONFIGURATION_INFINITE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pagerAdapter = new PagerFragmentAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(4);
		
		 indicator.setViewPager(pager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				ToastUtil.show("再按一次退出程序");
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public static void  invoteToMain(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, MainActivity.class);  
        context.startActivity(intent); 
	}
}
