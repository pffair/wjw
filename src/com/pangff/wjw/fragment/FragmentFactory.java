package com.pangff.wjw.fragment;

/**
 * 主页fragment工厂类
 * 
 * @author pangff
 */
public class FragmentFactory {
	/**
	 * fragment——id 根据 adapter position顺序
	 */
	
	public static final int FRAGMENT_HOME = 0;
	public static final int FRAGMENT_ADV = 1;
	public static final int FRAGMENT_MORE = 3;
	public static final int FRAGMENT_ACCOUNT = 2;

	/**
	 * 实例化fragment
	 * 
	 * @param id
	 * @return
	 */
	public static PagerFragment newInstance(int id) {
		PagerFragment fragment = null;
		switch (id) {
		case FRAGMENT_HOME:
			fragment = new HomeFragment();
			break;
		case FRAGMENT_ADV:
			fragment = new AdvFragment();
			break;
		case FRAGMENT_ACCOUNT:
			fragment = new AccountFragment();
			break;
		case FRAGMENT_MORE:
			fragment = new MoreFragment();
			break;
		default:
			break;
		}
		return fragment;
	}

}
