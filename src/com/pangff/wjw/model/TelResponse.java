package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class TelResponse extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;

	public static class Body {
		
		@XStreamAlias("id")
		public String id;
		
		@XStreamAlias("img")
		public String img;
	}
}
