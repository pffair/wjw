package com.pangff.wjw.model;

import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class CallRequest extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		@XStreamAlias("id")
		public String id;
	}

	public  String getParams(String method,String id){
		this.method = method;
		this.userid = UserInfoUtil.getInstanse().getUserId();
		this.body = new Body();
		this.body.id = id;
		return XStreamTranslator.getInstance().toXMLString(this);
	}
}
