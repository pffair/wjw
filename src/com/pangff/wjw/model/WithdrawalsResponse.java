package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class WithdrawalsResponse extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamAlias("zxname")
		String zxname;
		
		@XStreamAlias("money")
		String money;
		
		@XStreamAlias("sxf")
		String sxf;
	}

}
