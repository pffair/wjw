package com.pangff.wjw.model;

import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class LoginRequest extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		@XStreamAlias("username")
		public String username;
		
		@XStreamAlias("password")
		public String password;
	}

	public  String getParams(String method,String username,String password){
		this.method = method;
		this.body = new Body();
		this.body.username = username;
		this.body.password = ParseMD5.parseStrToMd5L16(password);
		return XStreamTranslator.getInstance().toXMLString(this);
	}
}
