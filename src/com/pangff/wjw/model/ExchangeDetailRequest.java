package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ExchangeDetailRequest extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
	}
	
}

