package com.pangff.wjw.model;

import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ExchangeRequest extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamAlias("jin")
		public String jin;
		
		@XStreamAlias("password")
		public String password;
	}
	public String getParams(String method) {
		this.method = method;
		this.userid = UserInfoUtil.getInstanse().getUserId();
		this.body = new Body();
		return XStreamTranslator.getInstance().toXMLString(this);
	}
}

