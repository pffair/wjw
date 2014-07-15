package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class WithdrawalsResponse extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamAlias("zxname")
		public String zxname;
		
		@XStreamAlias("money")
		public String money;
		
		@XStreamAlias("sxf")
		public String sxf;
	}

}
