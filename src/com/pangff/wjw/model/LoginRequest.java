package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class LoginRequest extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		@XStreamAlias("username")
		String username;
		
		@XStreamAlias("password")
		String password;
	}

}
