package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class WithdrawalsCommitRequest extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamAlias("zxname")
		String zxname;
		
		@XStreamAlias("bank")
		String bank;
		
		@XStreamAlias("zhihang")
		String zhihang;
		
		@XStreamAlias("zhanghao")
		String zhanghao;
		
		@XStreamAlias("jin")
		String jin;
		
		@XStreamAlias("sxf")
		String sxf;
		
		@XStreamAlias("password")
		String password;
	}

}
