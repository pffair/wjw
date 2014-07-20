package com.pangff.wjw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pangff.wjw.BaseApplication;
import com.pangff.wjw.R;
import com.pangff.wjw.fragment.FragmentFactory;

/**
 * 主页fragment适配器
 * @author pangff
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter implements BasePagerAdapter {
  protected static final String[] CONTENT = new String[] {
      BaseApplication.self.getString(R.string.fragment_home),
      BaseApplication.self.getString(R.string.fragment_adv),
      BaseApplication.self.getString(R.string.fragment_account),
      BaseApplication.self.getString(R.string.fragment_more)
      };

  private int mCount = CONTENT.length;

  public PagerFragmentAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return FragmentFactory.newInstance(position);
  }

  @Override
  public int getCount() {
    return mCount;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return PagerFragmentAdapter.CONTENT[position % CONTENT.length];
  }

  @Override
  public int getIconResId(int index) {
     return 0;//ICONS[index % ICONS.length]
  }

  public void setCount(int count) {
    if (count > 0 && count <= 10) {
      mCount = count;
      notifyDataSetChanged();
    }
  }
  
}
