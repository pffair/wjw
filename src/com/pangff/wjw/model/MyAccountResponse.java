package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class MyAccountResponse extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamAlias("money")
		public String money;
		
		@XStreamAlias("jifen")
		public String jifen;
	}
	
}
