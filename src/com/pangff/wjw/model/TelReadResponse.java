package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class TelReadResponse {
	
	@XStreamAlias("body")
	Body body;

	public static class Body {
		
		@XStreamAlias("return")
		String returns;
		
		@XStreamAlias("message")
		String message;
	}
}
