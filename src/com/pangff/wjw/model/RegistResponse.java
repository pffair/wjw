package com.pangff.wjw.model;

import com.pangff.wjw.model.LoginResponse.Body;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class RegistResponse extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamAlias("return")
		String returns;
		
		@XStreamAlias("message")
		String message;
	}
}
