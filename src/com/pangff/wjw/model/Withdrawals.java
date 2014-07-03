package com.pangff.wjw.model;

import java.util.List;

public class Withdrawals extends BaseBean{
	
	Body body;
	
	public static class Body{
		String zxname;
		String money;
		String sxf;
		String bank;
		String zhihang;
		String zhanghao;
		String jin;
		String password;
		String riqi;
		List<Body> list;
		public static class WithdrawalsBean{
			String jin;
			String sxf;
			String bank;
			String riqi;
		}
	}



}
