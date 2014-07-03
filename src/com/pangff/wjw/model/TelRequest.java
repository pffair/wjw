package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class TelRequest {
	
	@XStreamAlias("body")
	Body body;

	public static class Body {

	}
}
