package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ExchangeRequest extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamAlias("jin")
		String jin;
		
		@XStreamAlias("password")
		String password;
	}
	
}

