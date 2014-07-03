package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class AdvDetailRequest extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamAlias("id")
		String id;
	}
	
}
